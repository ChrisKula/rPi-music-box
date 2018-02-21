package com.christiankula.rpimusicbox.rainbowhat;

import android.os.Handler;
import android.util.Log;

import com.google.android.things.contrib.driver.apa102.Apa102;
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay;
import com.google.android.things.contrib.driver.pwmspeaker.Speaker;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;
import com.google.android.things.pio.Gpio;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class RainbowHatHelper {

    private static final String TAG = RainbowHatHelper.class.getSimpleName();

    private static final int ALPHA_NUMERIC_LENGTH = 4;

    private static final int RED_LED = 1;

    private static final int GREEN_LED = 2;

    private static final int BLUE_LED = 3;

    private Speaker speaker;

    private Apa102 ledStrip;

    private AlphanumericDisplay display;

    private Gpio redLed;

    private Gpio greenLed;

    private Gpio blueLed;

    private final MessageRoller messageRoller;

    public RainbowHatHelper() {
        try {
            this.ledStrip = RainbowHat.openLedStrip();
        } catch (IOException e) {
            Log.e(TAG, "Error while opening LED strip", e);
            throw new RuntimeException(e);
        }

        try {
            this.speaker = RainbowHat.openPiezo();
        } catch (IOException e) {
            Log.e(TAG, "Error while opening speaker", e);
            throw new RuntimeException(e);
        }

        try {
            this.display = RainbowHat.openDisplay();
        } catch (IOException e) {
            Log.e(TAG, "Error while initializing alphanumeric display", e);
            throw new RuntimeException(e);
        }

        try {
            redLed = RainbowHat.openLedRed();
            greenLed = RainbowHat.openLedGreen();
            blueLed = RainbowHat.openLedBlue();
        } catch (IOException e) {
            Log.e(TAG, "Error while opening LEDs", e);
            throw new RuntimeException(e);
        }

        init();

        this.messageRoller = new MessageRoller();
    }

    private void init() {
        try {
            speaker.stop();

            ledStrip.setDirection(Apa102.Direction.NORMAL);

            display.setEnabled(true);
            display.clear();

            redLed.setValue(false);
            greenLed.setValue(false);
            blueLed.setValue(false);
        } catch (IOException e) {
            Log.e(TAG, "Error while initializing RainbowHat peripherals", e);

            throw new RuntimeException(e);
        }
    }

    /**
     * Displays the given String on the alphanumeric display. If the given String is longer than 4 characters,the
     * message will roll automatically. If the String is empty or null, clears the display.
     */
    public void display(String message) {
        messageRoller.cancel();

        if (message.length() <= ALPHA_NUMERIC_LENGTH) {
            try {
                display.display(message);
            } catch (IOException e) {
                Log.e(TAG, "Error while displaying on alphanumeric display", e);

                throw new RuntimeException(e);
            }
        } else {
            messageRoller.roll(message);
        }
    }

    /**
     * Displays the given String on the alphanumeric display then clears the display after the given duration. If the
     * given String is longer than 4 characters,the message will roll automatically. If the String is empty or null,
     * clears the display immediately.
     */
    public void display(String str, long durationInMillis) {
        if (!str.isEmpty()) {
            display(str);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    clearDisplay();
                }
            }, durationInMillis);
        } else {
            clearDisplay();
        }
    }

    /**
     * Clears the alphanumeric display
     */
    public void clearDisplay() {
        messageRoller.cancel();

        try {
            display.clear();
        } catch (IOException e) {
            Log.e(TAG, "Error while displaying on alphanumeric display", e);

            throw new RuntimeException(e);
        }
    }

    /**
     * Turn on or off the red LED corresponding to the A button.
     * <br/>
     * <br/>
     * All LEDs function as a radio buttons group : only one LED can be turn on at a time. Thus, turning on this LED
     * will turn off the others.
     */
    public void setRedLedValue(boolean turnOn) {
        setLedValue(turnOn, RED_LED);
    }

    /**
     * Turn on or off the green LED corresponding to the B button.
     * <br/>
     * <br/>
     * All LEDs function as a radio buttons group : only one LED can be turn on at a time. Thus, turning on this LED
     * will turn off the others.
     */
    public void setGreenLedValue(boolean turnOn) {
        setLedValue(turnOn, GREEN_LED);
    }

    /**
     * Turn on or off the blue LED corresponding to the C button.
     * <br/>
     * <br/>
     * All LEDs function as a radio buttons group : only one LED can be turn on at a time. Thus, turning on this LED
     * will turn off the others.
     */
    public void setBlueLedValue(boolean turnOn) {
        setLedValue(turnOn, BLUE_LED);
    }

    /**
     * Turn on or off the given LED
     * <br/>
     * <br/>
     * All LEDs function as a radio buttons group : only one LED can be turn on at a time.
     *
     * @param led an int representing an on-board LED
     */
    private void setLedValue(boolean turnOn, int led) {
        try {
            if (turnOn) {
                switch (led) {
                    case RED_LED:
                        redLed.setValue(true);

                        greenLed.setValue(false);
                        blueLed.setValue(false);
                        break;

                    case GREEN_LED:
                        greenLed.setValue(true);

                        redLed.setValue(false);
                        blueLed.setValue(false);
                        break;

                    case BLUE_LED:
                        blueLed.setValue(true);

                        redLed.setValue(false);
                        greenLed.setValue(false);
                        break;
                }
            } else {
                switch (led) {
                    case RED_LED:
                        redLed.setValue(false);
                        break;

                    case GREEN_LED:
                        greenLed.setValue(false);
                        break;

                    case BLUE_LED:
                        blueLed.setValue(false);
                        break;
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error while changing the LED n°" + led + " state", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Class in charge of making a message roll on the alphanumeric display
     */
    private class MessageRoller {

        private static final int ROLLING_MESSAGE_PERIOD_MS = 250;

        //4 spaces
        private static final String SPACES_BETWEEN_ROLLOVER = "    ";

        private int headStartIndex;

        private int headEndIndex;

        private String rollingMessage;

        private Timer timer;

        /**
         * Rolls the given message on the alphanumeric display
         */
        void roll(final String message) {
            cancel();

            rollingMessage = message + SPACES_BETWEEN_ROLLOVER;

            this.headStartIndex = 0;

            this.timer = new Timer();

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    String subMessage;

                    headEndIndex = headStartIndex + ALPHA_NUMERIC_LENGTH;

                    if (headEndIndex <= rollingMessage.length()) {
                        subMessage = rollingMessage.substring(headStartIndex, headEndIndex);
                    } else {
                        headEndIndex = ALPHA_NUMERIC_LENGTH - (rollingMessage.length() - headStartIndex);

                        subMessage = rollingMessage.substring(headStartIndex, rollingMessage.length()) +
                                rollingMessage.substring(0, headEndIndex + 1);
                    }

                    try {
                        display.display(subMessage);
                    } catch (IOException e) {
                        Log.e(TAG, "Error while displaying on alphanumeric display", e);

                        throw new RuntimeException(e);
                    }

                    headStartIndex++;

                    if (headStartIndex >= rollingMessage.length()) {
                        headStartIndex = 0;
                    }
                }
            }, 0, ROLLING_MESSAGE_PERIOD_MS);
        }

        void cancel() {
            if (this.timer != null) {
                this.timer.cancel();
                this.timer.purge();

                this.timer = null;

            }

            this.headStartIndex = 0;
            this.headEndIndex = 0;

            this.rollingMessage = "";
        }
    }
}

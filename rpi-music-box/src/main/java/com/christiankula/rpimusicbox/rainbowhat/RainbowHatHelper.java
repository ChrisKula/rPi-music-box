package com.christiankula.rpimusicbox.rainbowhat;

import android.os.Handler;
import android.util.Log;

import com.google.android.things.contrib.driver.apa102.Apa102;
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay;
import com.google.android.things.contrib.driver.pwmspeaker.Speaker;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;
import com.google.android.things.pio.Gpio;

import java.io.IOException;

public class RainbowHatHelper {

    private static final String TAG = RainbowHatHelper.class.getSimpleName();

    private static final int RED_LED = 1;

    private static final int GREEN_LED = 2;

    private static final int BLUE_LED = 3;

    private Speaker speaker;

    private Apa102 ledStrip;

    private AlphanumericDisplay display;

    private Gpio redLed;

    private Gpio greenLed;

    private Gpio blueLed;

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
     * Displays the given String on the alphanumeric display. The alphanumeric display can only display the first 4
     * characters of the given String. If the String is empty or null, clears the display.
     */
    public void display(String str) {
        try {
            display.display(str);
        } catch (IOException e) {
            Log.e(TAG, "Error while displaying on alphanumeric display", e);

            throw new RuntimeException(e);
        }
    }

    /**
     * Displays the given String on the alphanumeric display then clears the display after the given duration.
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
}

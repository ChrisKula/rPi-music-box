package com.christiankula.rpimusicbox.nearby.connection;

public class Endpoint {

    private String endpointId;

    private String endpointName;

    private State state;

    public Endpoint(String endpointId, String endpointName, State state) {
        this.endpointId = endpointId;
        this.endpointName = endpointName;
        this.state = state;
    }

    public String getEndpointId() {
        return endpointId;
    }

    public void setEndpointId(String endpointId) {
        this.endpointId = endpointId;
    }

    public String getEndpointName() {
        return endpointName;
    }

    public void setEndpointName(String endpointName) {
        this.endpointName = endpointName;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public enum State {
        CONNECTION_INITIATED,
        CONNECTED
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Endpoint endpoint = (Endpoint) o;

        return endpointId != null ? endpointId.equals(endpoint.endpointId) : endpoint.endpointId == null;
    }

    @Override
    public int hashCode() {
        return endpointId != null ? endpointId.hashCode() : 0;
    }
}

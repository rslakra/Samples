package com.rslakra.springbootsamples.jwtauthentication.payload.response;

/**
 * Generic message response for API endpoints
 */
public class MessageResponse {

    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


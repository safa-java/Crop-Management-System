package com.agcltr.order.exception;

public class CustomFeignException extends RuntimeException {
    private final int status;
    private final String responseBody;

    public CustomFeignException(int status, String responseBody) {
        super("HTTP status: " + status + ", Response: " + responseBody);
        this.status = status;
        this.responseBody = responseBody;
    }

    public int getStatus() {
        return status;
    }

    public String getResponseBody() {
        return responseBody;
    }
}

	
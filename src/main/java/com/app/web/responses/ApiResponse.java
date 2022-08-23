package com.app.web.responses;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

public class ApiResponse {

    private final JSONObject response;
    private int status;

    public ApiResponse() {
        response = new JSONObject();
    }


    public ApiResponse setStatus(int status) {
        this.response.put("status", status);
        this.status = status;
        return this;
    }

    public ApiResponse setMessage(String message) {
        this.response.put("message", message);
        return this;
    }

    public <T> ApiResponse addField(String key, T value) {
        this.response.put(key, value);
        return this;
    }

    public ResponseEntity<String> build() {
        return ResponseEntity
                .status(this.status)
                .body(this.response.toString());
    }
}

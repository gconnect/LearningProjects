package com.glory.imageuploadusingretrofit;

import com.google.gson.annotations.SerializedName;

public class ServerResponse {

    //Variable name should be the same as in the json response

    @SerializedName("success")
    boolean success;
    @SerializedName("message")
    String message;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}

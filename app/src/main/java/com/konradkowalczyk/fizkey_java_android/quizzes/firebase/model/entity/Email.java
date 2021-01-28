package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.model.entity;

import com.google.gson.annotations.SerializedName;

public class Email {

    @SerializedName("to")
    private final String to;

    @SerializedName("topic")
    private final String topic;

    @SerializedName("message")
    private final String message;

    public Email(String to, String topic, String message) {
        this.to = to;
        this.topic = topic;
        this.message = message;
    }

    public String getTo() {
        return to;
    }

    public String getTopic() {
        return topic;
    }

    public String getMessage() {
        return message;
    }
}

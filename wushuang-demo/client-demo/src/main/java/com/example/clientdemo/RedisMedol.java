package com.example.clientdemo;

import java.io.Serializable;

public class RedisMedol implements Serializable {
    String key;
    String content;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

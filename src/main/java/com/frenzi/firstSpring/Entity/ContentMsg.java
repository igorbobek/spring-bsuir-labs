package com.frenzi.firstSpring.Entity;

import java.io.Serializable;

public class ContentMsg implements Serializable {

    enum Type{
        tick,
        stop,
        start
    }

    private String content;

    public ContentMsg() {
    }

    public ContentMsg(String content/*, Type type*/) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}
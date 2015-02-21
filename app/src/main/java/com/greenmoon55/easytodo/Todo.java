package com.greenmoon55.easytodo;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by greenmoon55 on 2015/2/21.
 */
@AVClassName("Todo")
public class Todo extends AVObject {
    private static final String CONTENT_KEY = "content";

    public String getContent() {
        return this.getString(CONTENT_KEY);
    }

    public void setContent(String content) {
        this.put(CONTENT_KEY, content);
    }
}

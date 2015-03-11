package com.greenmoon55.easynote;

import com.avos.avoscloud.AVACL;
import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

/**
 * Created by greenmoon55 on 2015/2/21.
 */
@AVClassName("Note")
public class Note extends AVObject {
    private static final String CONTENT_KEY = "content";

    public String getContent() {
        return this.getString(CONTENT_KEY);
    }

    public void setContent(String content) {
        this.put(CONTENT_KEY, content);
    }

    public Note() {
        AVACL acl = new AVACL();
        acl.setPublicReadAccess(false);
        acl.setPublicWriteAccess(false);
        acl.setReadAccess(AVUser.getCurrentUser(),true);
        acl.setWriteAccess(AVUser.getCurrentUser(), true);
        this.setACL(acl);
    }
}

package com.greenmoon55.easytodo;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.SaveCallback;

import java.util.Collections;
import java.util.List;

/**
 * Created by greenmoon55 on 2015/2/21.
 */
public class AVService {
    public static void AVInit(Context ctx) {
        AVOSCloud.setDebugLogEnabled(true);
        AVOSCloud.initialize(ctx, "u468p378t89hoh08c5sw54vc1fxkgu4qn3yryp4si3n22sfd",
                "jl1yeddvb4swgeamix5h4pyl7errwh3uyinwkj56ukkouuyv");
        AVObject.registerSubclass(Todo.class);
    }

    public static void createOrUpdateTodo(String objectId, String content, SaveCallback saveCallback) {
        Todo todo = new Todo();
        if (!TextUtils.isEmpty(objectId)) {
            todo.setObjectId(objectId);
        }
        todo.setContent(content);
        todo.saveInBackground(saveCallback);
    }

    public static List<Todo> findTodos() {
        AVQuery<Todo> query = AVQuery.getQuery(Todo.class);
        query.orderByDescending("updatedAt");
        query.limit(1000);
        try {
            return query.find();
        } catch (AVException e) {
            Log.e("tag", "Query todos failed.", e);
            return Collections.emptyList();
        }
    }

}

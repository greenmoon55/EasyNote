package com.greenmoon55.easytodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by greenmoon55 on 2015/2/22.
 */
public class TodoAdapter extends BaseAdapter {
    List<Todo> todos;
    Context context;

    public TodoAdapter(Context context, List<Todo> todos) {
        this.context = context;
        this.todos = todos;
    }

    @Override
    public int getCount() {
        return todos == null? 0: todos.size();
    }

    @Override
    public Object getItem(int position) {
        if (todos != null) {
            return todos.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.todo_row, null);
            holder = new ViewHolder();
            holder.todo = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Todo todo = todos.get(position);
        if (todo != null) {
            holder.todo.setText(todo.getContent());
        }
        return convertView;
    }

    static class ViewHolder {
        TextView todo;
    }
}

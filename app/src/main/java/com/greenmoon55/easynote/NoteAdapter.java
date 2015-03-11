package com.greenmoon55.easynote;

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
public class NoteAdapter extends BaseAdapter {
    List<Note> notes;
    Context context;

    public NoteAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @Override
    public int getCount() {
        return notes == null? 0: notes.size();
    }

    @Override
    public Object getItem(int position) {
        if (notes != null) {
            return notes.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.note_row, null);
            holder = new ViewHolder();
            holder.note = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Note note = notes.get(position);
        if (note != null) {
            holder.note.setText(note.getContent());
        }
        return convertView;
    }

    static class ViewHolder {
        TextView note;
    }
}

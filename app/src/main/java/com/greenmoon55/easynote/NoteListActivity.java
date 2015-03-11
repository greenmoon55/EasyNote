package com.greenmoon55.easynote;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.*;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;

import java.util.List;


public class NoteListActivity extends ListActivity {
    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;
    private static final int ACTIVITY_AUTH = 2;

    private static final int DELETE_ID = Menu.FIRST + 1;

    private volatile List<Note> notes;

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            notes = AVService.findNotes();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            NoteAdapter adapter = new NoteAdapter(NoteListActivity.this, notes);
            setListAdapter(adapter);
            registerForContextMenu(getListView());
            TextView empty = (TextView) findViewById(android.R.id.empty);
            if (notes != null && !notes.isEmpty()) {
                empty.setVisibility(View.INVISIBLE);
            } else {
                empty.setVisibility(View.VISIBLE);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AVUser.getCurrentUser() == null) {
            Intent intent = new Intent(NoteListActivity.this, AuthActivity.class);
            startActivityForResult(intent, ACTIVITY_AUTH);
        }
        setContentView(R.layout.activity_note_list);
        new RemoteDataTask().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note_list, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, "Delete Note");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        final Note note = notes.get(info.position);
        switch (item.getItemId()) {
            case DELETE_ID:
                new RemoteDataTask() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            note.delete();
                        } catch (AVException e) {
                            e.printStackTrace();
                        }
                        super.doInBackground();
                        return null;
                    }
                }.execute();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.create_note) {
            createNote();
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this, CreateNote.class);
        i.putExtra("content", notes.get(position).getString("content"));
        i.putExtra("objectId", notes.get(position).getObjectId());
        startActivityForResult(i, ACTIVITY_EDIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (intent == null) {
            return;
        }
        // 暂时提示信息
        boolean success = intent.getBooleanExtra("success", true);
        Toast toast = null;
        if (success) {
            // 重新查询，刷新ListView
            new RemoteDataTask().execute();
        } else {
            toast = Toast.makeText(getApplicationContext(), "Save failed!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void createNote() {
        Intent i = new Intent(this, CreateNote.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }
}

package com.greenmoon55.easytodo;

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

import com.avos.avoscloud.AVException;

import java.util.List;


public class TodoListActivity extends ListActivity {
    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;

    private static final int DELETE_ID = Menu.FIRST + 1;

    private volatile List<Todo> todos;

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            todos = AVService.findTodos();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            TodoAdapter adapter = new TodoAdapter(TodoListActivity.this, todos);
            setListAdapter(adapter);
            registerForContextMenu(getListView());
            TextView empty = (TextView) findViewById(android.R.id.empty);
            if (todos != null && !todos.isEmpty()) {
                empty.setVisibility(View.INVISIBLE);
            } else {
                empty.setVisibility(View.VISIBLE);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        new RemoteDataTask().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo_list, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, "Delete Todo");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        final Todo todo = todos.get(info.position);
        switch (item.getItemId()) {
            case DELETE_ID:
                new RemoteDataTask() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            todo.delete();
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

        if (id == R.id.create_todo) {
            createTodo();
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
        Intent i = new Intent(this, CreateTodo.class);
        i.putExtra("content", todos.get(position).getString("content"));
        i.putExtra("objectId", todos.get(position).getObjectId());
        startActivityForResult(i, ACTIVITY_EDIT);
    }

    private void createTodo() {
        Intent i = new Intent(this, CreateTodo.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }
}

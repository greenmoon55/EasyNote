package com.greenmoon55.easynote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;


public class CreateNote extends Activity {

    private EditText contentText;
    private String objectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        contentText = (EditText) findViewById(R.id.content);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String content = extras.getString("content");
            objectId = extras.getString("objectId");

            contentText.setText(content);
        }

        // Set cursor to the end of the text
        contentText.setSelection(contentText.getText().length());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.confirm) {
            SaveCallback saveCallback = new SaveCallback() {
                @Override
                public void done(AVException e) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("success", e == null);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            };
            String content = contentText.getText().toString();
            AVService.createOrUpdateTodo(objectId, content, saveCallback);
        }

        return super.onOptionsItemSelected(item);
    }
}

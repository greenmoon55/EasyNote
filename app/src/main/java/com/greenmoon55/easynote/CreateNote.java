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

            if (intent.hasExtra("requestCode")) {
                int requestCode = extras.getInt("requestCode");

                // Is there a better way?
                if (requestCode == NoteListActivity.ACTIVITY_EDIT) {
                    this.setTitle(R.string.title_activity_edit_note);
                }
            }
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
        String content = contentText.getText().toString();
        switch (item.getItemId()) {
            case R.id.confirm:
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
                AVService.createOrUpdateTodo(objectId, content, saveCallback);
                break;
            case R.id.menu_item_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, content);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

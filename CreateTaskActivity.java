package com.ac23amit.tasklist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by pc on 05/11/13.
 */
public class CreateTaskActivity extends Activity
{
    Connect_DB connectorDB = Connect_DB.getInstance(this);

    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task);
        Button createBtn = (Button) findViewById(R.id.create_Btn);
        createBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v)
            {

                EditText editText = (EditText) findViewById(R.id.edit_message);
                connectorDB.addItem(new ItemDetails(0,editText.getText().toString(), "Done", "50"));

                startActivity(new Intent(CreateTaskActivity.this, TaskListActivity.class));
            }
        });
    }
}




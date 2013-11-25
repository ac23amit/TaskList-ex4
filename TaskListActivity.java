package com.ac23amit.tasklist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import static java.util.Arrays.asList;

public class TaskListActivity extends Activity
{
    @Override
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final ListView listView = (ListView) findViewById(R.id.listV_main);
        Connect_DB connectorDB = Connect_DB.getInstance(this);
        if(connectorDB.getSize()==0)
            connectorDB.populateItemsArr();

        listView.setAdapter(new ItemListBaseAdapter(this));
        Button addBtn = (Button) findViewById(R.id.add_Btn);
        addBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v)
            {

                startActivity(new Intent(TaskListActivity.this, CreateTaskActivity.class));
            }
        });

    }
}



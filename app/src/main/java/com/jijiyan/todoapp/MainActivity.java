package com.jijiyan.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;//data
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    EditText etNewItem;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        etNewItem = (EditText)findViewById(R.id.etNewItem);
        setupListViewListener();
    }

    private void initData() {
        lvItems = (ListView)findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        items.add("item 1");
        items.add("item 2");
        items.add("item 3");
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemsAdapter);
    }

    private void setupListViewListener(){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                String itemName = lvItems.getItemAtPosition(position).toString();
                i.putExtra("ItemName", itemName);
                i.putExtra("ItemPos", position);
                startActivityForResult(i, REQUEST_CODE); // brings up the second activity
            }
        });
    }

    public void onAddItem(View view) {
        itemsAdapter.add(etNewItem.getText().toString());
        etNewItem.setText("");
    }


    // ActivityOne.java, time to handle the result of the sub-activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String ItemName = data.getExtras().getString("ItemName");
            int pos = data.getExtras().getInt("ItemPos", 0);
            // Toast the name to display temporarily on screen
            items.set(pos, ItemName);
            itemsAdapter.notifyDataSetChanged();
        }
    }
}

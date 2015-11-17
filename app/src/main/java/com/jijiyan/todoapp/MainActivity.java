package com.jijiyan.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jijiyan.todoapp.adapter.ItemAdapter;
import com.jijiyan.todoapp.model.Item;
import com.jijiyan.todoapp.sqlite.ItemDBHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Item> items = new ArrayList<Item>();//data
    //ArrayAdapter<String> itemsAdapter;
    ItemAdapter itemAdapter;
    ListView lvItems;
    private final int REQUEST_CODE_EDIT = 20;
    private final int REQUEST_CODE_ADD = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        setupListViewListener();
    }

    private void initData() {
        ItemDBHelper itemDBHelper = ItemDBHelper.getInstance(this);
        items.addAll(itemDBHelper.getItems());
        lvItems = (ListView)findViewById(R.id.lvItems);
        itemAdapter = new ItemAdapter(this, items);
        lvItems.setAdapter(itemAdapter);
    }

    private void setupListViewListener(){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = (Item)lvItems.getItemAtPosition(position);
                items.remove(item);
                ItemDBHelper.getInstance(MainActivity.this).deleteItem(item);
                itemAdapter.notifyDataSetChanged();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                Item item = (Item)lvItems.getItemAtPosition(position);
                i.putExtra("ItemName", item.getName());
                i.putExtra("ItemNote", item.getNote());
                i.putExtra("ItemDescription", item.getDescription());
                i.putExtra("ItemPos", position);
                startActivityForResult(i, REQUEST_CODE_EDIT); // brings up the second activity
            }
        });
    }

    public void onAddItem(View view) {
        Intent i = new Intent(MainActivity.this, AddItemActivity.class);
        startActivityForResult(i, REQUEST_CODE_ADD); // brings up the second activity
    }

    // ActivityOne.java, time to handle the result of the sub-activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        ItemDBHelper itemDBHelper = ItemDBHelper.getInstance(this);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_EDIT) {
            // Extract name value from result extras
            String itemName = data.getExtras().getString("ItemName");
            String itemNote = data.getExtras().getString("ItemNote");
            String itemDescription = data.getExtras().getString("ItemDescription");
            int pos = data.getExtras().getInt("ItemPos", 0);
            // Toast the name to display temporarily on screen
            Item item = (Item)lvItems.getItemAtPosition(pos);
            item.setName(itemName);
            item.setNote(itemNote);
            item.setDescription(itemDescription);
            items.set(pos, item);
            itemDBHelper.updateItem(item);
            itemAdapter.notifyDataSetChanged();
        }

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_ADD) {
            // Extract name value from result extras
            String itemName = data.getExtras().getString("ItemName");
            String itemNote = data.getExtras().getString("ItemNote");
            String itemDescription = data.getExtras().getString("ItemDescription");
            // Toast the name to display temporarily on screen
            Item item = new Item(itemName,itemNote,itemDescription);
            items.add(item);
            itemDBHelper.addItem(item);
            itemAdapter.notifyDataSetChanged();
        }
    }
}

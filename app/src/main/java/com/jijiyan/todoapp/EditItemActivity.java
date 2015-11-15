package com.jijiyan.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    private EditText etEditItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        initData();
    }

    private void initData() {
        etEditItem = (EditText)findViewById(R.id.etEditItem);
        String itemName = getIntent().getStringExtra("ItemName");
        etEditItem.setText(itemName);
        etEditItem.setSelection(itemName.length());
    }

    public void onEditItem(View view) {
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("ItemName", etEditItem.getText().toString());
        data.putExtra("ItemPos", getIntent().getIntExtra("ItemPos", 0)); // ints work too
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
}

package com.jijiyan.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    private EditText etEditItemName;
    private EditText etEditItemNote;
    private EditText etEditItemDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        initData();
    }

    private void initData() {
        etEditItemName = (EditText)findViewById(R.id.etEditItemName);
        etEditItemNote = (EditText)findViewById(R.id.etEditItemNote);
        etEditItemDescription = (EditText)findViewById(R.id.etEditItemDescription);
        String itemName = getIntent().getStringExtra("ItemName");
        String itemNote = getIntent().getStringExtra("ItemNote");
        String itemDescription = getIntent().getStringExtra("ItemDescription");
        etEditItemName.setText(itemName);
        etEditItemName.setSelection(itemName.length());
        etEditItemNote.setText(itemNote);
        etEditItemNote.setSelection(itemNote.length());
        etEditItemDescription.setText(itemDescription);
        etEditItemDescription.setSelection(itemDescription.length());
    }

    public void onEditItem(View view) {
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("ItemName", etEditItemName.getText().toString());
        data.putExtra("ItemNote", etEditItemNote.getText().toString());
        data.putExtra("ItemDescription", etEditItemDescription.getText().toString());
        data.putExtra("ItemPos", getIntent().getIntExtra("ItemPos", 0)); // ints work too
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
}

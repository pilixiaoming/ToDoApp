package com.jijiyan.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddItemActivity extends AppCompatActivity {
    private EditText etAddItemName;
    private EditText etAddItemNote;
    private EditText etAddItemDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        initData();
    }

    private void initData() {
        etAddItemName = (EditText)findViewById(R.id.etAddItemName);
        etAddItemNote = (EditText)findViewById(R.id.etAddItemNote);
        etAddItemDescription = (EditText)findViewById(R.id.etAddItemDescription);
    }

    public void addItemSubmit(View view) {
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("ItemName", etAddItemName.getText().toString());
        data.putExtra("ItemNote", etAddItemNote.getText().toString());
        data.putExtra("ItemDescription", etAddItemDescription.getText().toString());
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
}

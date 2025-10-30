package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText etNumber;
    Button btnGenerate, btnHistory;
    ListView listView;

    ArrayAdapter<String> adapter;
    List<String> tableList = new ArrayList<>();
    int currentNumber = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumber = findViewById(R.id.etNumber);
        btnGenerate = findViewById(R.id.btnGenerate);
        btnHistory = findViewById(R.id.btnHistory);
        listView = findViewById(R.id.listView);

        if (!NumberStorage.lastTable.isEmpty()) {
            tableList.addAll(NumberStorage.lastTable);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tableList);
        listView.setAdapter(adapter);

        btnGenerate.setOnClickListener(v -> generateTable());
        btnHistory.setOnClickListener(v -> openHistory());

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = tableList.get(position);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Delete Row")
                    .setMessage("Do you want to delete this row?\n" + selectedItem)
                    .setPositiveButton("Yes", (dialog, which) -> {
                        tableList.remove(position);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Deleted: " + selectedItem, Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    private void generateTable() {
        String input = etNumber.getText().toString().trim();
        if (input.isEmpty()) {
            Toast.makeText(this, "Enter a number", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int number = Integer.parseInt(input);
            currentNumber = number;
            tableList.clear();

            for (int i = 1; i <= 10; i++) {
                tableList.add(number + " × " + i + " = " + (number * i));
            }

            adapter.notifyDataSetChanged();

            NumberStorage.lastTable.clear();
            NumberStorage.lastTable.addAll(tableList);

            if (!NumberStorage.historyNumbers.contains(number)) {
                NumberStorage.historyNumbers.add(number);
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid number", Toast.LENGTH_SHORT).show();
        }
    }

    private void openHistory() {
        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
        startActivity(intent);
    }
}
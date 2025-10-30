package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HistoryActivity extends AppCompatActivity {

    ListView listViewHistory;
    Button btnClearAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listViewHistory = findViewById(R.id.listViewHistory);
        btnClearAll = findViewById(R.id.btnClearAll);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, NumberStorage.historyNumbers);
        listViewHistory.setAdapter(adapter);

        btnClearAll.setOnClickListener(v -> {
            if (!NumberStorage.historyNumbers.isEmpty()) {
                NumberStorage.historyNumbers.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "All history cleared", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No history to clear", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
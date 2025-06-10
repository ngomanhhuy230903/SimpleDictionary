package com.example.simpledictionary.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.simpledictionary.R;
import com.example.simpledictionary.adapter.HistoryAdapter;
import com.example.simpledictionary.database.HistoryDAO;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;
    private HistoryDAO historyDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.rv_history_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        historyDAO = new HistoryDAO(this);
        historyDAO.open();

        List<String> historyList = historyDAO.getAllHistory();
        historyDAO.close();

        historyAdapter = new HistoryAdapter(historyList);
        recyclerView.setAdapter(historyAdapter);
    }
}
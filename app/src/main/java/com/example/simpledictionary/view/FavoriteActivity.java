package com.example.simpledictionary.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpledictionary.R;
import com.example.simpledictionary.adapter.HistoryAdapter; // Tái sử dụng adapter
import com.example.simpledictionary.database.FavoriteDAO;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private HistoryAdapter favoriteAdapter; // Dùng HistoryAdapter
    private FavoriteDAO favoriteDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite); // Đảm bảo layout có tên đúng

        recyclerView = findViewById(R.id.rv_favorite_list); // Đảm bảo ID đúng
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        favoriteDAO = new FavoriteDAO(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Lấy dữ liệu mỗi khi màn hình được hiển thị
        loadFavorites();
    }

    private void loadFavorites() {
        favoriteDAO.open();
        List<String> favoritesList = favoriteDAO.getAllFavorites();
        favoriteDAO.close();

        // Dùng HistoryAdapter để hiển thị danh sách từ yêu thích
        favoriteAdapter = new HistoryAdapter(favoritesList);
        recyclerView.setAdapter(favoriteAdapter);
    }
}
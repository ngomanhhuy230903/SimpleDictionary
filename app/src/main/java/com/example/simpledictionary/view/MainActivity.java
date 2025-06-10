package com.example.simpledictionary.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.simpledictionary.adapter.WordAdapter;
import com.example.simpledictionary.api.DictionaryAPI;
import com.example.simpledictionary.api.RetrofitClient;
import com.example.simpledictionary.databinding.ActivityMainBinding;
import com.example.simpledictionary.model.Word;
import com.example.simpledictionary.database.HistoryDAO;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private WordAdapter wordAdapter;
    private HistoryDAO historyDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Khởi tạo DAO cho lịch sử
        historyDAO = new HistoryDAO(this);
        historyDAO.open();
        // 1. Cài đặt RecyclerView và Adapter

        setupRecyclerView();

        // 2. Thiết lập sự kiện click cho nút tìm kiếm
        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wordToSearch = binding.etSearchWord.getText().toString().trim();
                if (!wordToSearch.isEmpty()) {
                    searchWord(wordToSearch);
                } else {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập từ khóa", Toast.LENGTH_SHORT).show();
                }
            }

        });
        binding.btnHistory.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });
    }

    private void setupRecyclerView() {
        // Khởi tạo adapter với một danh sách rỗng
        wordAdapter = new WordAdapter(new ArrayList<>());
        // Thiết lập LayoutManager để RecyclerView biết cách sắp xếp item (dọc)
        binding.rvResultList.setLayoutManager(new LinearLayoutManager(this));
        // Gắn adapter vào RecyclerView
        binding.rvResultList.setAdapter(wordAdapter);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        historyDAO.close();
    }

    private void searchWord(String word) {
        // Lấy đối tượng dịch vụ API từ RetrofitClient
        DictionaryAPI apiService = RetrofitClient.getClient().create(DictionaryAPI.class);

        // Tạo cuộc gọi API
        Call<List<Word>> call = apiService.searchWord(word);

        // Thực hiện cuộc gọi bất đồng bộ (trên một luồng khác để không treo giao diện)
        call.enqueue(new Callback<List<Word>>() {
            // Được gọi khi có phản hồi từ server (thành công hoặc thất bại)
            @Override
            public void onResponse(Call<List<Word>> call, Response<List<Word>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Nếu thành công, cập nhật dữ liệu cho adapter
                    wordAdapter.updateData(response.body());
                    historyDAO.addHistory(word);
                } else {
                    // Nếu không tìm thấy từ hoặc có lỗi từ server
                    Toast.makeText(MainActivity.this, "Không tìm thấy từ hoặc có lỗi", Toast.LENGTH_SHORT).show();
                    wordAdapter.updateData(null); // Xóa kết quả cũ
                }
            }

            // Được gọi khi có lỗi kết nối mạng
            @Override
            public void onFailure(Call<List<Word>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("MainActivity", "API Call Failed: ", t);
            }
        });
    }
}
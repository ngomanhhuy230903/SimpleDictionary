package com.example.simpledictionary.view;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast; // Thêm Toast để thông báo cho người dùng

import androidx.appcompat.app.AppCompatActivity;

import com.example.simpledictionary.R;
import com.example.simpledictionary.database.FavoriteDAO;
import com.example.simpledictionary.databinding.ActivityWordDetailBinding;
import com.example.simpledictionary.model.Definition;
import com.example.simpledictionary.model.Word;
import com.example.simpledictionary.model.WordMeaning;

public class WordDetailActivity extends AppCompatActivity {

    public static final String EXTRA_WORD = "EXTRA_WORD"; // Key để gửi và nhận dữ liệu

    private ActivityWordDetailBinding binding;
    private FavoriteDAO favoriteDAO;
    private Word currentWord;
    private boolean isCurrentlyFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWordDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Khởi tạo và mở kết nối DAO
        favoriteDAO = new FavoriteDAO(this);
        favoriteDAO.open();

        // Lấy dữ liệu Word được gửi từ MainActivity
        currentWord = (Word) getIntent().getSerializableExtra(EXTRA_WORD);

        // Kiểm tra xem dữ liệu có tồn tại không
        if (currentWord != null) {
            displayWordDetails(currentWord);
            setupFavoriteButton();
        }
    }

    private void displayWordDetails(Word word) {
        binding.tvDetailWord.setText(word.getWord());
        binding.tvDetailPhonetic.setText(word.getPhonetic());
        binding.layoutMeaningsContainer.removeAllViews();

        if (word.getMeanings() == null || word.getMeanings().isEmpty()) return;

        for (WordMeaning meaning : word.getMeanings()) {
            TextView partOfSpeechTv = new TextView(this);
            partOfSpeechTv.setText(meaning.getPartOfSpeech());
            partOfSpeechTv.setTextSize(20);
            partOfSpeechTv.setTextColor(getResources().getColor(android.R.color.black));
            partOfSpeechTv.setTypeface(null, android.graphics.Typeface.BOLD_ITALIC);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 24, 0, 8);
            partOfSpeechTv.setLayoutParams(params);
            binding.layoutMeaningsContainer.addView(partOfSpeechTv);

            if (meaning.getDefinitions() == null || meaning.getDefinitions().isEmpty()) continue;

            for (int i = 0; i < meaning.getDefinitions().size(); i++) {
                Definition definition = meaning.getDefinitions().get(i);
                if (definition == null) continue;

                TextView definitionTv = new TextView(this);
                definitionTv.setText((i + 1) + ". " + definition.getDefinition());
                definitionTv.setTextSize(16);
                definitionTv.setPadding(16, 8, 0, 8);
                binding.layoutMeaningsContainer.addView(definitionTv);

                if (definition.getExample() != null && !definition.getExample().isEmpty()) {
                    TextView exampleTv = new TextView(this);
                    exampleTv.setText("Ví dụ: \"" + definition.getExample() + "\"");
                    exampleTv.setTextSize(16);
                    exampleTv.setPadding(32, 0, 0, 8);
                    exampleTv.setTypeface(null, android.graphics.Typeface.ITALIC);
                    binding.layoutMeaningsContainer.addView(exampleTv);
                }
            }
        }
    }

    // Hàm cài đặt cho nút yêu thích
    private void setupFavoriteButton() {
        // 1. Kiểm tra trạng thái yêu thích ban đầu từ CSDL
        isCurrentlyFavorite = favoriteDAO.isFavorite(currentWord.getWord());
        updateFavoriteIcon();

        // 2. Thiết lập sự kiện click
        binding.ibFavorite.setOnClickListener(v -> {
            // Đảo ngược trạng thái hiện tại
            isCurrentlyFavorite = !isCurrentlyFavorite;

            if (isCurrentlyFavorite) {
                // Nếu giờ là yêu thích -> thêm vào DB
                favoriteDAO.addFavorite(currentWord.getWord());
                Toast.makeText(this, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
            } else {
                // Nếu không còn yêu thích -> xóa khỏi DB
                favoriteDAO.removeFavorite(currentWord.getWord());
                Toast.makeText(this, "Đã xóa khỏi yêu thích", Toast.LENGTH_SHORT).show();
            }

            // Cập nhật lại icon trái tim để phản ánh thay đổi
            updateFavoriteIcon();
        });
    }

    // Hàm cập nhật icon dựa trên trạng thái isCurrentlyFavorite
    private void updateFavoriteIcon() {
        if (isCurrentlyFavorite) {
            binding.ibFavorite.setImageResource(R.drawable.ic_favorite_filled);
        } else {
            binding.ibFavorite.setImageResource(R.drawable.ic_favorite_border);
        }
    }

    // Đóng kết nối DAO khi activity bị hủy để tránh rò rỉ tài nguyên
    @Override
    protected void onDestroy() {
        super.onDestroy();
        favoriteDAO.close();
    }
}
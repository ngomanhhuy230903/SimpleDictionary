package com.example.simpledictionary.view;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simpledictionary.R;
import com.example.simpledictionary.databinding.ActivityWordDetailBinding;
import com.example.simpledictionary.model.Definition;
import com.example.simpledictionary.model.Word;
import com.example.simpledictionary.model.WordMeaning;

public class WordDetailActivity extends AppCompatActivity {

    private ActivityWordDetailBinding binding;
    public static final String EXTRA_WORD = "EXTRA_WORD"; // Key để gửi và nhận dữ liệu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWordDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Lấy dữ liệu Word được gửi từ MainActivity
        Word word = (Word) getIntent().getSerializableExtra(EXTRA_WORD);

        // Kiểm tra xem dữ liệu có tồn tại không
        if (word != null) {
            displayWordDetails(word);
        }
    }

    private void displayWordDetails(Word word) {
        // Hiển thị từ và phiên âm
        binding.tvDetailWord.setText(word.getWord());
        binding.tvDetailPhonetic.setText(word.getPhonetic());

        // Xóa các view cũ trong container (phòng trường hợp tái sử dụng)
        binding.layoutMeaningsContainer.removeAllViews();

        // Lặp qua từng loại nghĩa (danh từ, động từ,...)
        for (WordMeaning meaning : word.getMeanings()) {
            // 1. Tạo TextView cho loại từ (Part of Speech)
            TextView partOfSpeechTv = new TextView(this);
            partOfSpeechTv.setText(meaning.getPartOfSpeech());
            partOfSpeechTv.setTextSize(20); // Kích thước 20sp
            partOfSpeechTv.setTextColor(getResources().getColor(R.color.black)); // Màu đen
            partOfSpeechTv.setTypeface(null, android.graphics.Typeface.BOLD_ITALIC); // In đậm nghiêng
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 24, 0, 8); // Lề trên 24px, lề dưới 8px
            partOfSpeechTv.setLayoutParams(params);

            binding.layoutMeaningsContainer.addView(partOfSpeechTv); // Thêm vào layout

            // 2. Lặp qua từng định nghĩa trong loại từ đó
            for (int i = 0; i < meaning.getDefinitions().size(); i++) {
                Definition definition = meaning.getDefinitions().get(i);

                // Tạo TextView cho định nghĩa
                TextView definitionTv = new TextView(this);
                definitionTv.setText((i + 1) + ". " + definition.getDefinition());
                definitionTv.setTextSize(16);
                definitionTv.setPadding(16, 8, 0, 8); // Thụt vào một chút cho đẹp
                binding.layoutMeaningsContainer.addView(definitionTv);

                // Tạo TextView cho ví dụ (nếu có)
                if (definition.getExample() != null && !definition.getExample().isEmpty()) {
                    TextView exampleTv = new TextView(this);
                    exampleTv.setText("Ví dụ: \"" + definition.getExample() + "\"");
                    exampleTv.setTextSize(16);
                    exampleTv.setPadding(32, 0, 0, 8); // Thụt vào sâu hơn
                    exampleTv.setTypeface(null, android.graphics.Typeface.ITALIC);
                    binding.layoutMeaningsContainer.addView(exampleTv);
                }
            }
        }
    }
}
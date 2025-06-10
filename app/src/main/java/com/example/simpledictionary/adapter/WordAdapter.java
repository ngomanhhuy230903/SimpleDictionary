package com.example.simpledictionary.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpledictionary.R;
import com.example.simpledictionary.model.Word;

import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {

    private List<Word> wordList; // Danh sách dữ liệu để hiển thị

    // Constructor để nhận dữ liệu từ bên ngoài
    public WordAdapter(List<Word> wordList) {
        this.wordList = wordList;
    }

    // Lớp nội (inner class) này đại diện cho mỗi item view trong danh sách
    public static class WordViewHolder extends RecyclerView.ViewHolder {
        TextView tvWord;
        TextView tvPhonetic;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ các view từ layout item_word.xml
            tvWord = itemView.findViewById(R.id.tv_word);
            tvPhonetic = itemView.findViewById(R.id.tv_phonetic);
        }
    }

    // Được gọi khi RecyclerView cần một ViewHolder mới (một item view mới)
    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tạo view từ file layout item_word.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word, parent, false);
        return new WordViewHolder(view);
    }

    // Được gọi khi RecyclerView muốn hiển thị dữ liệu tại một vị trí cụ thể
    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        // Lấy dữ liệu từ danh sách tại vị trí 'position'
        Word currentWord = wordList.get(position);

        // Gán dữ liệu lên các view của ViewHolder
        holder.tvWord.setText(currentWord.getWord());
        holder.tvPhonetic.setText(currentWord.getPhonetic());
    }

    // Trả về tổng số item trong danh sách
    @Override
    public int getItemCount() {
        return wordList.size();
    }

    // Hàm tiện ích để cập nhật dữ liệu cho adapter và báo cho RecyclerView biết
    public void updateData(List<Word> newWordList) {
        this.wordList.clear();
        if (newWordList != null) {
            this.wordList.addAll(newWordList);
        }
        notifyDataSetChanged(); // Yêu cầu RecyclerView vẽ lại toàn bộ danh sách
    }
}
package com.example.simpledictionary.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.simpledictionary.R;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<String> historyList;

    public HistoryAdapter(List<String> historyList) {
        this.historyList = historyList;
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvHistoryWord;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHistoryWord = itemView.findViewById(android.R.id.text1);
        }
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Sử dụng layout có sẵn của Android cho đơn giản
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.tvHistoryWord.setText(historyList.get(position));
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }
}
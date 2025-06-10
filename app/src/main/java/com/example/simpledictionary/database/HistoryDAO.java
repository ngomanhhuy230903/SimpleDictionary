package com.example.simpledictionary.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class HistoryDAO {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = {
            DatabaseHelper.COLUMN_HISTORY_ID,
            DatabaseHelper.COLUMN_HISTORY_WORD,
            DatabaseHelper.COLUMN_HISTORY_TIMESTAMP
    };

    public HistoryDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Thêm một từ vào lịch sử
    public void addHistory(String word) {
        // ContentValues hoạt động giống như một Map để chứa cặp key-value
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_HISTORY_WORD, word);

        // Lệnh insertOnConflict sẽ thay thế bản ghi cũ nếu từ đã tồn tại
        // Điều này giúp mỗi từ chỉ xuất hiện một lần và được cập nhật timestamp
        database.insertWithOnConflict(DatabaseHelper.TABLE_HISTORY, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        Log.d("HistoryDAO", "Word added to history: " + word);
    }

    // Lấy tất cả các từ trong lịch sử, sắp xếp theo thời gian mới nhất
    // Lấy tất cả các từ trong lịch sử, sắp xếp theo thời gian mới nhất
    public List<String> getAllHistory() {
        List<String> historyList = new ArrayList<>();

        // Cursor là một con trỏ trỏ đến kết quả của câu truy vấn
        // "query" là phương thức để thực hiện câu lệnh SELECT
        // Sắp xếp theo timestamp giảm dần (DESC)

        // SỬA LỖI Ở ĐÂY: Dùng DatabaseHelper thay vì Database
        Cursor cursor = database.query(DatabaseHelper.TABLE_HISTORY, allColumns,
                null, null, null, null, DatabaseHelper.COLUMN_HISTORY_TIMESTAMP + " DESC");

        // Di chuyển con trỏ đến bản ghi đầu tiên
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            // Lấy từ ở cột có index là 1 (vì cột ID có index 0)
            String word = cursor.getString(1);
            historyList.add(word);
            cursor.moveToNext(); // Di chuyển đến bản ghi tiếp theo
        }

        // Rất quan trọng: Luôn đóng cursor sau khi dùng xong để tránh rò rỉ bộ nhớ
        cursor.close();
        return historyList;
    }
}
package com.example.simpledictionary.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class FavoriteDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = {
            DatabaseHelper.COLUMN_FAVORITES_ID,
            DatabaseHelper.COLUMN_FAVORITES_WORD
    };

    public FavoriteDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Thêm một từ vào danh sách yêu thích
    public void addFavorite(String word) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_FAVORITES_WORD, word);
        database.insert(DatabaseHelper.TABLE_FAVORITES, null, values);
    }

    // Xóa một từ khỏi danh sách yêu thích
    public void removeFavorite(String word) {
        database.delete(DatabaseHelper.TABLE_FAVORITES,
                DatabaseHelper.COLUMN_FAVORITES_WORD + " = ?",
                new String[]{word});
    }

    // Kiểm tra xem một từ đã có trong danh sách yêu thích chưa
    public boolean isFavorite(String word) {
        Cursor cursor = database.query(DatabaseHelper.TABLE_FAVORITES, allColumns,
                DatabaseHelper.COLUMN_FAVORITES_WORD + " = ?",
                new String[]{word}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Lấy tất cả các từ yêu thích
    public List<String> getAllFavorites() {
        List<String> favoritesList = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_FAVORITES, allColumns,
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String favoriteWord = cursor.getString(1);
            favoritesList.add(favoriteWord);
            cursor.moveToNext();
        }
        cursor.close();
        return favoritesList;
    }
}
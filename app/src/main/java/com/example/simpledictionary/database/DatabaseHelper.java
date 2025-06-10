package com.example.simpledictionary.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Tên và phiên bản của Database
    private static final String DATABASE_NAME = "SimpleDictionary.db";
    private static final int DATABASE_VERSION = 1;

    // Định nghĩa tên bảng và các cột cho bảng LỊCH SỬ
    public static final String TABLE_HISTORY = "history_table";
    public static final String COLUMN_HISTORY_ID = "id";
    public static final String COLUMN_HISTORY_WORD = "word";
    public static final String COLUMN_HISTORY_TIMESTAMP = "timestamp";

    // Định nghĩa tên bảng và các cột cho bảng YÊU THÍCH
    public static final String TABLE_FAVORITES = "favorites_table";
    public static final String COLUMN_FAVORITES_ID = "id";
    public static final String COLUMN_FAVORITES_WORD = "word";

    // Câu lệnh SQL để tạo bảng LỊCH SỬ
    private static final String SQL_CREATE_TABLE_HISTORY =
            "CREATE TABLE " + TABLE_HISTORY + " (" +
                    COLUMN_HISTORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_HISTORY_WORD + " TEXT UNIQUE," +
                    COLUMN_HISTORY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP)";

    // Câu lệnh SQL để tạo bảng YÊU THÍCH
    private static final String SQL_CREATE_TABLE_FAVORITES =
            "CREATE TABLE " + TABLE_FAVORITES + " (" +
                    COLUMN_FAVORITES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_FAVORITES_WORD + " TEXT UNIQUE)";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Phương thức này chỉ được gọi MỘT LẦN khi database được tạo lần đầu tiên
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_HISTORY);
        db.execSQL(SQL_CREATE_TABLE_FAVORITES);
    }

    // Phương thức này được gọi khi bạn tăng DATABASE_VERSION, dùng để nâng cấp database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Cách đơn giản nhất cho phát triển: xóa bảng cũ và tạo lại
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);
    }
}
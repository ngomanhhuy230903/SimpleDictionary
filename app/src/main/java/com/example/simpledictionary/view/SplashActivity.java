package com.example.simpledictionary.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // Người dùng đã đăng nhập, chuyển đến MainActivity
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        } else {
            // Người dùng chưa đăng nhập, chuyển đến LoginActivity
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }
        // Kết thúc SplashActivity để người dùng không thể quay lại màn hình này
        finish();
    }
}
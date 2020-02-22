package com.example.bangbingbo.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bangbingbo.R;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }
}
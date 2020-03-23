package com.fillr.example.integration.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fillr.example.integration.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnHeadful = findViewById(R.id.btnHeadful);
        Button btnHeadless = findViewById(R.id.btnHeadless);
        btnHeadful.setOnClickListener(this);
        btnHeadless.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnHeadful:
                startActivity(new Intent(this, ExampleWebViewHeadfulActivity.class));
                break;
            case R.id.btnHeadless:
                startActivity(new Intent(this, ExampleWebViewHeadlessActivity.class));
                break;
        }
    }
}

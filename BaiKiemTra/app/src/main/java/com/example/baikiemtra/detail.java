package com.example.baikiemtra;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class detail extends AppCompatActivity {
    ImageView detailImg;
    TextView detailName, detailPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailImg = findViewById(R.id.imageView4);
        detailName = findViewById(R.id.textView3);
        detailPrice = findViewById(R.id.textView4);

        String productName = getIntent().getStringExtra("name");
        int productPrice = getIntent().getIntExtra("price", 0);
        int productImage = getIntent().getIntExtra("image", 0);

        detailName.setText(productName);
        detailPrice.setText(String.valueOf(productPrice));
        detailImg.setImageResource(productImage);

    }
}
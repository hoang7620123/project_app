package com.example.baikiemtra;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Product> products;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            recyclerView = findViewById(R.id.recyclerView);
            products = new ArrayList<>();
            products.add(new Product("sp1",50000,"chatlieu1",R.drawable.sp1));
            products.add(new Product("sp2",50000,"chatlieu2",R.drawable.sp2));
            products.add(new Product("sp3",50000,"chatlieu3",R.drawable.sp3));

            adapter = new ProductAdapter(this, products);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);

        }
    }


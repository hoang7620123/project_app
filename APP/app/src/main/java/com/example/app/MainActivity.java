package com.example.app;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app.SanPhamAdapter.SanPhamAdapter;
import com.example.app.SanPham.SanPham;
import com.example.app.API.APItheloai;
import com.example.app.API.APIsanpham;
import com.example.app.utils.ImageSlider;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SanPhamAdapter adapter;
    List<SanPham> sanPhamList;
    Spinner mySpinner;
    TextView idten;
    ImageView imageView;
    ImageSlider imageSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        mySpinner = findViewById(R.id.mySpinner);
        imageView = findViewById(R.id.id1);
        idten = findViewById(R.id.idten);

        sanPhamList = new ArrayList<>();
        adapter = new SanPhamAdapter(this, sanPhamList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        imageSlider = new ImageSlider(imageView, idten);
        imageSlider.start();

        APIsanpham.loadProducts(this, sanPhamList, adapter);
        APItheloai.loadSpinner(this, mySpinner);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = parent.getItemAtPosition(position).toString();
                APItheloai.filterByCategory(sanPhamList, adapter, selectedCategory);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
}
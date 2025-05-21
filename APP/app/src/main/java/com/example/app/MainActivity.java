package com.example.app;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.SanPham.SanPham;
import com.example.app.SanPhamAdapter.SanPhamAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Khai báo các thành phần giao diện và dữ liệu
    RecyclerView recyclerView;
    SanPhamAdapter adapter;
    List<SanPham> sanPhamList;

    // URL để lấy dữ liệu từ API (localhost cho trình giả lập Android)
    String url = "http://10.0.2.2:3000/api/getProductApi";
    String spinnerUrl = "http://10.0.2.2:3000/api/getTheLoaiApi";

    // Thành phần hiển thị ảnh và văn bản tự động thay đổi
    private ImageView imageView;
    private int[] imageIds = {R.drawable.bg, R.drawable.buy, R.drawable.timkiem}; // Danh sách ảnh
    private String[] texts = {"Áo", "Quần", "hào ngu như bò"}; // Danh sách văn bản tương ứng
    private int currentIndex = 0; // Chỉ số hiện tại cho ảnh và văn bản

    TextView idten;
    Spinner mySpinner;

    // Handler để xử lý ảnh tự động thay đổi
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            currentIndex++;
            if (currentIndex >= imageIds.length) {
                currentIndex = 0;
            }
            updateImageAndText(); // Cập nhật ảnh và văn bản
            handler.postDelayed(this, 5000); // Gọi lại sau 10 giây
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.id1);
        idten = findViewById(R.id.idten);
        mySpinner = findViewById(R.id.mySpinner);
        updateImageAndText();
        // Thiết lập RecyclerView để hiển thị danh sách sản phẩm
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sanPhamList = new ArrayList<>();
        adapter = new SanPhamAdapter(this, sanPhamList);
        recyclerView.setAdapter(adapter);

        loadProducts(); // Tải dữ liệu sản phẩm từ API
        loadSpinnerData(); // Tải danh sách thể loại cho Spinner

        // Xử lý khi chọn mục trong Spinner
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = parent.getItemAtPosition(position).toString();
                filterProductsByCategory(selectedCategory); // Lọc sản phẩm theo thể loại
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        startAutoSlide(); // Bắt đầu tự động chuyển ảnh
    }
    // Hàm tải danh sách sản phẩm từ API
    private void loadProducts() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        sanPhamList.clear(); // Xóa danh sách cũ
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                String tenSanPham = obj.getString("tenHang");
                                int gia = obj.getInt("DonGia");
                                String hinhAnh = obj.getString("hinhAnh");
                                String theLoai = obj.getString("TenTheLoai");

                                // Thêm sản phẩm vào danh sách
                                sanPhamList.add(new SanPham(tenSanPham, gia, hinhAnh, theLoai));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this, "Lỗi đọc dữ liệu sản phẩm", Toast.LENGTH_SHORT).show();
                            }
                        }
                        adapter.notifyDataSetChanged(); // Cập nhật RecyclerView
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Lỗi kết nối API: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(jsonArrayRequest); // Thêm request vào hàng đợi
    }

    // Hàm tải dữ liệu thể loại cho Spinner
    private void loadSpinnerData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest spinnerRequest = new JsonArrayRequest(Request.Method.GET, spinnerUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<String> spinnerItems = new ArrayList<>();
                        spinnerItems.add("Tất cả"); // Mục mặc định để hiển thị tất cả sản phẩm
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                String item = obj.getString("tenTheLoai");
                                spinnerItems.add(item);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this, "Lỗi đọc dữ liệu cho Spinner", Toast.LENGTH_SHORT).show();
                            }
                        }
                        // Tạo adapter cho Spinner
                        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, spinnerItems);
                        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mySpinner.setAdapter(spinnerAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Lỗi kết nối API cho Spinner: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(spinnerRequest); // Thêm request vào hàng đợi
    }

    // Cập nhật ảnh và văn bản theo chỉ số hiện tại
    private void updateImageAndText() {
        imageView.setImageResource(imageIds[currentIndex]);
        idten.setText(texts[currentIndex]);
    }
    // Bắt đầu auto slide ảnh
    private void startAutoSlide() {
        handler.postDelayed(runnable, 3000); // Chạy lần đầu sau 3 giây
    }
    // Lọc sản phẩm theo thể loại từ Spinner
    private void filterProductsByCategory(String category) {
        List<SanPham> filteredList = new ArrayList<>();
        for (SanPham sanPham : sanPhamList) {
            if (sanPham.getTheLoai().equals(category) || category.equals("Tất cả")) {
                filteredList.add(sanPham);
            }
        }
        adapter.updateList(filteredList); // Cập nhật danh sách hiển thị
    }
}
package com.example.app.API;

import android.content.Context;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.example.app.SanPham.SanPham;
import com.example.app.SanPhamAdapter.SanPhamAdapter;
import org.json.*;
import java.util.*;

public class APIsanpham {
    private static final String URL = "http://10.0.2.2:3000/api/getProductApi";

    public static void loadProducts(Context context, List<SanPham> list, SanPhamAdapter adapter) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null,
                response -> {
                    list.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            list.add(new SanPham(
                                    obj.getString("tenHang"),
                                    obj.getInt("DonGia"),
                                    obj.getString("hinhAnh"),
                                    obj.getString("TenTheLoai")
                            ));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                },
                error -> Toast.makeText(context, "Lỗi API: " + error, Toast.LENGTH_SHORT).show()
        );
        queue.add(request);
    }


}
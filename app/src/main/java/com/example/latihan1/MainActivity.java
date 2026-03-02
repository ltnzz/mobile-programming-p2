package com.example.latihan1;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.latihan1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.btToast.setOnClickListener(v ->
                Toast.makeText(getApplicationContext(), R.string.how_are_you, Toast.LENGTH_SHORT).show()
        );
        setSupportActionBar(binding.appBar);
    }

    //memasukkan menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);

        // Deteksi keberadaan SearchView pada menu
        MenuItem searchItem = menu.findItem(R.id.m_cari);
        SearchView searchView = (SearchView) searchItem.getActionView();

        // Tambahkan listener untuk menangkap input
        assert searchView != null;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, "Mencari: " + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Aksi saat teks berubah (misalnya untuk live search)
                return false;
            }
        });

        // deteksi edit text pada search view
        EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);

        // Atur warna teks
        searchEditText.setTextColor(Color.WHITE);
        searchEditText.setHintTextColor(Color.LTGRAY);

        return true;
    }

    // berfungi untuk menangani menu yang diklik
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.m_fragment) {
            Toast.makeText(this, R.string.fragment, Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.m_recyclerView) {
            Toast.makeText(this, R.string.recyclerview, Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
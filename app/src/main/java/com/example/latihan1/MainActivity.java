package com.example.latihan1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.latihan1.databinding.ActivityMainBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

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
        binding.btDialog.setOnClickListener(v -> {
            //menampilkan dialog (Material3)
            new MaterialAlertDialogBuilder(this)
                    .setIcon(R.mipmap.ic_launcher)
                    .setTitle(R.string.warn)
                    .setMessage(R.string.how_are_you)
                    .setCancelable(true)
                    .setPositiveButton(R.string.ok, (dialog, which) -> Toast.makeText(getApplicationContext(),
                            R.string.how_are_you,
                            Toast.LENGTH_SHORT).show())
                    .setNegativeButton(R.string.cancel, (dialog, which) -> Toast.makeText(getApplicationContext(),
                            R.string.close,
                            Toast.LENGTH_SHORT).show())
                    .setNeutralButton(R.string.neutral, (dialogInterface, i) -> Toast.makeText(getApplicationContext(),
                            R.string.hello,
                            Toast.LENGTH_SHORT).show())
                    .show();
        });
        binding.btKeluar.setOnClickListener(v -> {
            finish(); // tutup aplikasi
        });
        binding.btSnack.setOnClickListener(v -> {
            View snackbarContainer = binding.getRoot(); // Menggunakan root view dari binding
            int duration = Snackbar.LENGTH_SHORT;

            Snackbar snackbar = Snackbar.make(snackbarContainer, R.string.hello, duration);
            snackbar.setAction(R.string.close, vw -> {
                snackbar.dismiss();
            });

            snackbar.show();
        });
        binding.btNotifikasi.setOnClickListener(v -> {
            // apakah permission sudah diaktifkan pada ponsel?
            if (NotificationManagerCompat.from(this).areNotificationsEnabled()) {
                //notifikasi
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(getApplicationContext(), "notify_001");
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, i, PendingIntent.FLAG_IMMUTABLE);

                NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
                bigText.setBigContentTitle(getApplicationContext().getResources().getString(R.string.app_name));
                bigText.setSummaryText(getApplicationContext().getResources().getString(R.string.hello));

                mBuilder.setContentIntent(pendingIntent);
                mBuilder.setSmallIcon(R.mipmap.ic_launcher);
                mBuilder.setContentTitle(getApplicationContext().getResources().getString(R.string.warn));
                mBuilder.setContentText(getApplicationContext().getResources().getString(R.string.how_are_you));
                mBuilder.setPriority(Notification.PRIORITY_MAX);
                mBuilder.setStyle(bigText);
                mBuilder.setDefaults(Notification.DEFAULT_SOUND); //suara
                mBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000, 1000}); //getar

                NotificationManager mNotificationManager =
                        (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("notify_001",
                            "channelku",
                            NotificationManager.IMPORTANCE_DEFAULT);
                    mNotificationManager.createNotificationChannel(channel);
                }

                mNotificationManager.notify(0, mBuilder.build());
            } else {
                new MaterialAlertDialogBuilder(this)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle(R.string.warn)
                        .setMessage(R.string.permission)
                        .setCancelable(true)
                        .setPositiveButton(R.string.ok, (dialog, which) -> {
                            // buka notification setting
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        })
                        .setNegativeButton(R.string.cancel, (dialog, which) -> {
                        })
                        .show();
            }
        });
        binding.btDetil.setOnClickListener(v -> {
            String nama, alamat, prodi, domisili;
            Boolean teknologi, kuliner;

            //ambil nilai
            nama = binding.etNama.getText().toString();
            alamat = binding.etAlamat.getText().toString();
            prodi = binding.spProdi.getSelectedItem().toString();
            teknologi = binding.cbTeknologi.isChecked();
            kuliner = binding.cbKuliner.isChecked();
            domisili = binding.rgDomisili.toString();

            /*
            Selipkan data yang ingin dikirim ke detail activity
            dengan putExtra
            */
            Intent i = new Intent(MainActivity.this, DetilActivity.class);
            i.putExtra("x_nama", nama);
            i.putExtra("x_alamat", alamat);
            i.putExtra("x_prodi", prodi);
            i.putExtra("x_teknologi", teknologi); //boolean
            i.putExtra("x_kuliner", kuliner); //boolean
            i.putExtra("x_domisili", domisili);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(i);
        });

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
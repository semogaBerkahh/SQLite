package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class LihatData extends AppCompatActivity {

    TextView tvNama, tvTelpon;

    String nama, telpon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitytampildata);

        tvNama = findViewById(R.id.tvDtlNama);
        tvTelpon = findViewById(R.id.tvDtlTelepon);

        Bundle bundle = getIntent().getExtras();

        nama = bundle.getString("nama");
        telpon = bundle.getString("telpon");

        tvNama.setText(nama);
        tvTelpon.setText(telpon);
    }
}



package com.example.oficinadobolo.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.oficinadobolo.adapters.BoloAdapter;
import com.example.oficinadobolo.database.LocalDatabase;
import com.example.oficinadobolo.databinding.ActivityBoloListBinding;
import com.example.oficinadobolo.entities.Bolo;
import java.util.List;

public class BoloList extends AppCompatActivity {

    private ActivityBoloListBinding binding;

    private LocalDatabase db;

    private List<Bolo> bolos;

    private ListView listViewBolos;

    private Intent edtIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoloListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = LocalDatabase.getDatabase(getApplicationContext());
        listViewBolos = binding.listBolos;

        binding.btnAddBolos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BoloList.this, RegistrarBoloActivity.class));
            }
        });

        binding.btnHomeBolos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        edtIntent = new Intent(this, RegistrarBoloActivity.class);
        preencherBolos();
    }

    private void preencherBolos() {
        bolos = db.boloModel().getAll();
        BoloAdapter boloAdapter = new BoloAdapter(this, bolos);
        listViewBolos.setAdapter(boloAdapter);
        listViewBolos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Bolo boloselecionado = bolos.get(position);
                edtIntent.putExtra("BOLO_SELECIONADO_ID", boloselecionado.getBoloID());
                startActivity(edtIntent);
            }
        });
    }
}
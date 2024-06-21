package com.example.oficinadobolo.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.oficinadobolo.adapters.OficinaAdapter;
import com.example.oficinadobolo.database.LocalDatabase;
import com.example.oficinadobolo.databinding.ActivityOficinaListBinding;
import com.example.oficinadobolo.entities.Oficina;
import java.util.List;

public class OficinaList extends AppCompatActivity {

    private ActivityOficinaListBinding binding;

    private LocalDatabase db;

    private List<Oficina> oficinas;

    private ListView listViewOficinas;

    private Intent edtIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOficinaListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = LocalDatabase.getDatabase(getApplicationContext());
        listViewOficinas = binding.listOficinas;

        binding.btnAddOficinas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OficinaList.this, RegistrarOficinaActivity.class));
            }
        });

        binding.btnHomeOficinas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        edtIntent = new Intent(this, RegistrarOficinaActivity.class);
        preencherOficinas();
    }

    private void preencherOficinas() {
        oficinas = db.oficinaModel().getAll();
        OficinaAdapter boloAdapter = new OficinaAdapter(this, oficinas);
        listViewOficinas.setAdapter(boloAdapter);
        listViewOficinas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Oficina oficinaselecionado = oficinas.get(position);
                edtIntent.putExtra("OFICINA_SELECIONADA_ID", oficinaselecionado.getOficinaID());
                startActivity(edtIntent);
            }
        });
    }
}
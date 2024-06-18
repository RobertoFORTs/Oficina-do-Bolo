package com.example.oficinadobolo.view;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.oficinadobolo.database.LocalDatabase;
import com.example.oficinadobolo.databinding.ActivityRegistrarOficinaBinding;
import com.example.oficinadobolo.entities.Oficina;

import java.util.Date;

public class RegistrarOficinaActivity extends AppCompatActivity {

    private LocalDatabase db;

    private ActivityRegistrarOficinaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrarOficinaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = LocalDatabase.getDatabase(getApplicationContext());

        binding.btnVoltarOficina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // finaliza a atividade atual, voltando para a main automaticamente.
                finish();
            }
        });

        binding.btnCadastrarOficina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOficina(v);
            }
        });

    }

    public void saveOficina(View view){
        String nomeOficina = binding.edtNomeOficina.getText().toString();
        if (nomeOficina.equals("")) {
            Toast.makeText(this, "Adicione um nome.", Toast.LENGTH_SHORT).show();
            return;
        }
        String nomeDescricao = binding.edtDescricaoOficina.getText().toString();
        if (nomeDescricao.equals("")) {
            Toast.makeText(this, "Adicione uma descrição.", Toast.LENGTH_SHORT).show();
            return;
        }
        Date dataOficina = (Date) binding.edtDataOficina.getText();
        if (dataOficina.equals("")) {
            Toast.makeText(this, "Adicione data.", Toast.LENGTH_SHORT).show();
            return;
        }

        Oficina thisOficina = new Oficina();
        thisOficina.setNomeOficina(nomeOficina);
        thisOficina.setDescOficina(nomeDescricao);
        thisOficina.setDataOficina(dataOficina);

        db.oficinaModel().insertAll(thisOficina);
        Toast.makeText(this, "Oficina criada com sucesso.", Toast.LENGTH_SHORT).show();

        Intent it = new Intent(RegistrarOficinaActivity.this, LoginActivity.class);
        startActivity(it);
        finish();
    }
}
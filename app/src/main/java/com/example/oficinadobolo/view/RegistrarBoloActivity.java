package com.example.oficinadobolo.view;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.oficinadobolo.database.LocalDatabase;
import com.example.oficinadobolo.databinding.ActivityRegistrarBoloBinding;
import com.example.oficinadobolo.entities.Bolo;

public class RegistrarBoloActivity extends AppCompatActivity {

    private LocalDatabase db;

    private ActivityRegistrarBoloBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrarBoloBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = LocalDatabase.getDatabase(getApplicationContext());

        binding.btnVoltarBolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // finaliza a atividade atual, voltando para a main automaticamente.
                finish();
            }
        });

        binding.btnCadastrarBolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBolo(v);
            }
        });

    }

    public void saveBolo(View view){
        String nomeBolo = binding.edtNomeBolo.getText().toString();
        if (nomeBolo.equals("")) {
            Toast.makeText(this, "Adicione um nome.", Toast.LENGTH_SHORT).show();
            return;
        }
        String nomeDescricao = binding.edtDescricaoBolo.getText().toString();
        if (nomeDescricao.equals("")) {
            Toast.makeText(this, "Adicione uma descrição.", Toast.LENGTH_SHORT).show();
            return;
        }
        String nomeIngredientes = binding.edtIngredientesBolo.getText().toString();
        if (nomeIngredientes.equals("")) {
            Toast.makeText(this, "Adicione ingrediente(s).", Toast.LENGTH_SHORT).show();
            return;
        }

        Bolo thisBolo = new Bolo();
        thisBolo.setNomeBolo(nomeBolo);
        thisBolo.setDescBolo(nomeDescricao);
        thisBolo.setIngredientes(nomeIngredientes);

        db.boloModel().insertAll(thisBolo);
        Toast.makeText(this, "Bolo criado com sucesso.", Toast.LENGTH_SHORT).show();

        Intent it = new Intent(RegistrarBoloActivity.this, MainActivity.class);
        startActivity(it);
        finish();
    }
}
package com.example.oficinadobolo.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
    private Bolo dbBolo;
    private int dbBoloID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrarBoloBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = LocalDatabase.getDatabase(getApplicationContext());

        binding.btnVoltarBolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnCadastrarBolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBolo(v);
            }
        });

        dbBoloID = getIntent().getIntExtra(
                "BOLO_SELECIONADO_ID", -1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dbBoloID >= 0) {
            getDBBolo();
            binding.btnCadastrarBolo.setText("Atualizar");
            binding.btnExcluirBolo.setVisibility(View.VISIBLE);
        } else {
            binding.btnExcluirBolo.setVisibility(View.GONE);
        }
    }



    private void getDBBolo(){
        dbBolo = db.boloModel().getBolo(dbBoloID);
        if (dbBolo != null) {
            binding.edtNomeBolo.setText(dbBolo.getNomeBolo());
            binding.edtIngredientesBolo.setText(dbBolo.getIngredientes());
            binding.edtDescricaoBolo.setText(dbBolo.getDescBolo());
        } else {
            Toast.makeText(this, "Bolo não encontrado.", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveBolo(View view) {
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

        if (dbBolo != null) {
            thisBolo.setBoloID(dbBoloID);
            db.boloModel().update(thisBolo);
            Toast.makeText(this, "Bolo atualizado com sucesso.", Toast.LENGTH_SHORT).show();
        } else {
            db.boloModel().insertAll(thisBolo);
            Toast.makeText(this, "Bolo criado com sucesso.", Toast.LENGTH_SHORT).show();
        }

        Intent it = new Intent(RegistrarBoloActivity.this, BoloList.class);
        startActivity(it);
        finish();
    }



    public void deleteBolo(View view) {
        if (dbBolo != null) {
            new AlertDialog.Builder(this)
                    .setTitle("Exclusão de Bolo")
                    .setMessage("Deseja excluir esse bolo?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            excluir();
                        }
                    })
                    .setNegativeButton("Não", null)
                    .show();
        } else {
            Toast.makeText(this, "Não há bolo para excluir.", Toast.LENGTH_SHORT).show();
        }
    }

    private void excluir() {
        db.boloModel().delete(dbBolo);
        Toast.makeText(this, "Bolo excluído com sucesso", Toast.LENGTH_SHORT).show();
        finish();
    }

}
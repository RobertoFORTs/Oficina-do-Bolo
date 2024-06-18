package com.example.oficinadobolo.view;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;
import com.example.oficinadobolo.database.LocalDatabase;
import com.example.oficinadobolo.databinding.ActivityRegistrarUsuarioBinding;
import com.example.oficinadobolo.entities.Usuario;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    private LocalDatabase db;

    private ActivityRegistrarUsuarioBinding binding;

    private Usuario dbUsuarios;

    private int dbUsuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrarUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = LocalDatabase.getDatabase(getApplicationContext());
        dbUsuarioID = getIntent().getIntExtra("USUARIO_SELECIONADO_ID", -1);

        binding.btnVoltarRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // finaliza a atividade atual, voltando para a main automaticamente.
                finish();
            }
        });

        binding.btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUsuario(v);
            }
        });

    }


    public void saveUsuario(View view){
        String nomeUsuario = binding.edtNomeRegistrar.getText().toString();
        if (nomeUsuario.equals("")) {
            Toast.makeText(this, "Adicione um nome.", Toast.LENGTH_SHORT).show();
            return;
        }
        String nomeEmail = binding.edtEmailRegistrar.getText().toString();
        if (nomeEmail.equals("")) {
            Toast.makeText(this, "Adicione um email.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(nomeEmail).matches()) {
            Toast.makeText(this, "Formato de email inválido.", Toast.LENGTH_SHORT).show();
            return;
        }

        String nomeSenha = binding.edtSenhaRegistrar.getText().toString();
        if (nomeSenha.equals("")) {
            Toast.makeText(this, "Adicione uma senha.", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario thisUsuario = new Usuario();
        thisUsuario.setNome(nomeUsuario);
        thisUsuario.setEmail(nomeEmail);
        thisUsuario.setSenha(nomeSenha);

        db.usuarioModel().insertAll(thisUsuario);
        Toast.makeText(this, "Usuário criado com sucesso.", Toast.LENGTH_SHORT).show();

        Intent it = new Intent(RegistrarUsuarioActivity.this, MainActivity.class);
        startActivity(it);
        finish();

    }

    private void getDBUsuarios(){
        dbUsuarios = db.usuarioModel().getUsuario(dbUsuarioID);
        binding.edtEmailRegistrar.setText(dbUsuarios.getEmail());
        binding.edtSenhaRegistrar.setText(dbUsuarios.getSenha());
        binding.edtNomeRegistrar.setText(dbUsuarios.getNome());
    }

    private void deleteUsuario(View view){
        new AlertDialog.Builder(this)
                .setTitle("Exclusão de Usuário")
                .setMessage("Deseja excluir esse Usuário?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        excluir();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    private void excluir() {
        db.usuarioModel().delete(dbUsuarios);
        Toast.makeText(this, "Usuário excluído com sucesso", Toast.LENGTH_SHORT).show();
        finish();
    }


}
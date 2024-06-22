package com.example.oficinadobolo.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.oficinadobolo.adapters.UsuarioAdapter;
import com.example.oficinadobolo.database.LocalDatabase;
import com.example.oficinadobolo.databinding.ActivityUsuariosListBinding;
import com.example.oficinadobolo.entities.Usuario;

import java.util.List;

public class UsuariosList extends AppCompatActivity {

    private ActivityUsuariosListBinding binding;
    private LocalDatabase db;
    private List<Usuario> usuarios;
    private ListView listViewUsuarios;
    private Intent edtIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsuariosListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = LocalDatabase.getDatabase(getApplicationContext());
        listViewUsuarios = binding.listUsuarios;

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UsuariosList.this, RegistrarUsuarioActivity.class));
            }
        });

        binding.btnHomeUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        edtIntent = new Intent(this, RegistrarUsuarioActivity.class);
        preencherUsuarios();
    }


    private void preencherUsuarios() {
        usuarios = db.usuarioModel().getAll();
        UsuarioAdapter usuarioAdapter = new UsuarioAdapter(this, usuarios);
        listViewUsuarios.setAdapter(usuarioAdapter);
        listViewUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Usuario usuarioselecionado = usuarios.get(position);
                edtIntent.putExtra("USUARIO_SELECIONADO_ID", usuarioselecionado.getUsuarioID());
                startActivity(edtIntent);
            }
        });
    }
}
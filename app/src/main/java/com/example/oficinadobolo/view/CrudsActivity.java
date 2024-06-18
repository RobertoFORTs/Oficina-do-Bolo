package com.example.oficinadobolo.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.oficinadobolo.databinding.ActivityCrudsBinding;

public class CrudsActivity extends AppCompatActivity {

    private ActivityCrudsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCrudsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCrudUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(CrudsActivity.this, UsuariosList.class);
                startActivity(it);
            }
        });
        binding.btnCrudBolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(CrudsActivity.this, RegistrarBoloActivity.class);
                startActivity(it);
            }
        });
    }





}
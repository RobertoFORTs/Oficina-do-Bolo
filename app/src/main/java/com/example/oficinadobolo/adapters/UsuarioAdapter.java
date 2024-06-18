package com.example.oficinadobolo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.oficinadobolo.R;
import com.example.oficinadobolo.entities.Usuario;

import java.util.List;


public class UsuarioAdapter extends ArrayAdapter<Usuario> {

    private Context context;
    private List<Usuario> usuarios;

    public UsuarioAdapter(@NonNull Context context, @NonNull List<Usuario> usuarios) {
        super(context, 0, usuarios);
        this.context = context;
        this.usuarios = usuarios;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.item_usuario, parent, false);
        }

        Usuario currentUser = usuarios.get(position);

        TextView txtNome = listItem.findViewById(R.id.txtDescUsuario);
        TextView txtEmail = listItem.findViewById(R.id.txtEmailUsuario);

        txtNome.setText(currentUser.getNome());
        txtEmail.setText(currentUser.getEmail());

        return listItem;
    }
}
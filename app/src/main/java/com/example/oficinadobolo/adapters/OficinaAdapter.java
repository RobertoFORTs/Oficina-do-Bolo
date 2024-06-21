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
import com.example.oficinadobolo.entities.Oficina;
import java.util.List;

public class OficinaAdapter extends ArrayAdapter<Oficina> {

    private Context context;

    private List<Oficina> oficinas;

    public OficinaAdapter(@NonNull Context context, @NonNull List<Oficina> oficinas){
        super(context, 0, oficinas);
        this.context = context;
        this.oficinas = oficinas;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.item_bolo, parent, false);
        }

        Oficina currentOficina = oficinas.get(position);

        TextView txtNomeOficina = listItem.findViewById(R.id.txtNomeOficina);
        TextView txtDescOficina = listItem.findViewById(R.id.txtDescOficina);

        txtNomeOficina.setText(currentOficina.getNomeOficina());
        txtDescOficina.setText(currentOficina.getDescOficina());

        return listItem;
    }

}

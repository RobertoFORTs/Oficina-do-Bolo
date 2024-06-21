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
import com.example.oficinadobolo.entities.Bolo;
import java.util.List;

public class BoloAdapter extends ArrayAdapter<Bolo> {

    private Context context;

    private List<Bolo> bolos;

    public BoloAdapter(@NonNull Context context, @NonNull List<Bolo> bolos){
        super(context, 0, bolos);
        this.context = context;
        this.bolos = bolos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.item_bolo, parent, false);
        }

        Bolo currentBolo = bolos.get(position);

        TextView txtNomeBolo = listItem.findViewById(R.id.txtNomeBolo);
        TextView txtDescBolo = listItem.findViewById(R.id.txtDescBolo);

        txtNomeBolo.setText(currentBolo.getNomeBolo());
        txtDescBolo.setText(currentBolo.getDescBolo());

        return listItem;
    }

}

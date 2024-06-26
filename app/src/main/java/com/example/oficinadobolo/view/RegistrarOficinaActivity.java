package com.example.oficinadobolo.view;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.oficinadobolo.R;
import com.example.oficinadobolo.database.LocalDatabase;
import com.example.oficinadobolo.databinding.ActivityRegistrarOficinaBinding;
import com.example.oficinadobolo.entities.Oficina;
import com.example.oficinadobolo.entities.Usuario;
import com.example.oficinadobolo.utils.NotificationReceiver;

import android.widget.Spinner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RegistrarOficinaActivity extends AppCompatActivity {

    private LocalDatabase db;

    private ActivityRegistrarOficinaBinding binding;
    private Oficina dbOficina;
    private int dbOficinaID;
    private Spinner spinner;
    private List<Usuario> usuarios;
    private ArrayAdapter<Usuario> usuarioArrayAdapter;
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrarOficinaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = LocalDatabase.getDatabase(getApplicationContext());
        spinner = binding.spinner;
        dbOficinaID = getIntent().getIntExtra("OFICINA_SELECIONADA_ID", -1);

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


        binding.btnAbrirMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLocationInMaps();
            }
        });

        binding.btnTocarMusica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tocarMusica();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(dbOficinaID >= 0){
            fillOficina();
        } else {
            binding.btnExcluirOficina.setVisibility(View.GONE);
        }
        fillUsers();
    }

    private void fillOficina() {
        dbOficina = db.oficinaModel().getOficina(dbOficinaID);
        if (dbOficina != null) {
            binding.edtDescricaoOficina.setText(dbOficina.getDescOficina());
            binding.edtNomeOficina.setText(dbOficina.getNomeOficina());

            Date dataOficina = dbOficina.getDataOficina();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String dataOficinaString = sdf.format(dataOficina);
            binding.edtDataOficina.setText(dataOficinaString);

            binding.edtLatitude.setText(String.valueOf(dbOficina.getLatitude()));
            binding.edtLongitude.setText(String.valueOf(dbOficina.getLongitude()));
        }
    }


    private void fillUsers(){
        usuarios = db.usuarioModel().getAll();
        usuarioArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usuarios);
        spinner.setAdapter(usuarioArrayAdapter);
        if(dbOficina != null){
            spinner.setSelection(dbOficina.getUsuarioID() - 1);
        }
    }

    public void saveOficina(View view) {
        String nomeOficina = binding.edtNomeOficina.getText().toString();
        String newUser = "";

        if (spinner.getSelectedItem() != null) {
            newUser = spinner.getSelectedItem().toString();
        }
        if (nomeOficina.equals("")) {
            Toast.makeText(this, "Adicione um nome.", Toast.LENGTH_SHORT).show();
            return;
        }
        String nomeDescricao = binding.edtDescricaoOficina.getText().toString();
        if (nomeDescricao.equals("")) {
            Toast.makeText(this, "Adicione uma descrição.", Toast.LENGTH_SHORT).show();
            return;
        }
        String dataOficinaString = binding.edtDataOficina.getText().toString();
        if (dataOficinaString.equals("")) {
            Toast.makeText(this, "Adicione uma data.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (newUser.isEmpty()) {
            Toast.makeText(this, "Entre com um Usuário.", Toast.LENGTH_SHORT).show();
            return;
        }

        double latitude;
        double longitude;
        try {
            latitude = Double.parseDouble(binding.edtLatitude.getText().toString());
            longitude = Double.parseDouble(binding.edtLongitude.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Latitude e Longitude inválidas.", Toast.LENGTH_SHORT).show();
            return;
        }

        Date dataOficina;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            dataOficina = sdf.parse(dataOficinaString);
        } catch (ParseException e) {
            Toast.makeText(this, "Data inválida.", Toast.LENGTH_SHORT).show();
            return;
        }

        Oficina thisOficina = new Oficina();
        thisOficina.setNomeOficina(nomeOficina);
        thisOficina.setDescOficina(nomeDescricao);
        thisOficina.setDataOficina(dataOficina);
        thisOficina.setUsuarioID(usuarios.get(spinner.getSelectedItemPosition()).getUsuarioID());
        thisOficina.setLatitude(latitude);
        thisOficina.setLongitude(longitude);

        if (dbOficina != null) {
            thisOficina.setOficinaID(dbOficinaID);
            db.oficinaModel().update(thisOficina);
            Toast.makeText(this, "Oficina atualizada com sucesso.", Toast.LENGTH_SHORT).show();
        } else {
            db.oficinaModel().insertAll(thisOficina);
            Toast.makeText(this, "Oficina criada com sucesso.", Toast.LENGTH_SHORT).show();
        }

        scheduleNotification(thisOficina.getDataOficina());

        Intent it = new Intent(RegistrarOficinaActivity.this, OficinaList.class);
        startActivity(it);
        finish();
    }

    public void deleteOficina(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Exclusão de Oficina")
                .setMessage("Deseja excluir essa Oficina?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        excluir();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    public void excluir() {
        db.oficinaModel().delete(dbOficina);
        Toast.makeText(this, "Oficina excluída com sucesso.", Toast.LENGTH_SHORT).show();
        finish();
    }

    @SuppressLint("ScheduleExactAlarm")
    private void scheduleNotification(Date oficinaDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(oficinaDate);
        calendar.add(Calendar.DAY_OF_YEAR, -1); // 1 dia antes da oficina

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void openLocationInMaps() {
        String latitudeText = binding.edtLatitude.getText().toString();
        String longitudeText = binding.edtLongitude.getText().toString();

        if (latitudeText.isEmpty() || longitudeText.isEmpty()) {
            Toast.makeText(this, "Latitude e Longitude são obrigatórios", Toast.LENGTH_SHORT).show();
            return;
        }

        double latitude;
        double longitude;
        try {
            latitude = Double.parseDouble(latitudeText);
            longitude = Double.parseDouble(longitudeText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Latitude e Longitude devem ser números válidos", Toast.LENGTH_SHORT).show();
            return;
        }

        int zoomLevel = 15; // Ajuste o nível de zoom conforme necessário
        String uri = String.format("geo:%s,%s?q=%s,%s&z=%d", latitude, longitude, latitude, longitude, zoomLevel);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Tenta abrir no Maps se a aplicação não estiver instalada
            Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            try {
                startActivity(unrestrictedIntent);
            } catch (ActivityNotFoundException innerEx) {
                Toast.makeText(this, "Nenhuma aplicação de mapas encontrada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void tocarMusica() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.musica0);
        }
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            Toast.makeText(this, "Música pausada", Toast.LENGTH_SHORT).show();
        } else {
            mediaPlayer.start();
            Toast.makeText(this, "Música tocando", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }

}
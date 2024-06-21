package com.example.oficinadobolo.view;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.oficinadobolo.dao.UsuarioDao;
import com.example.oficinadobolo.database.LocalDatabase;
import com.example.oficinadobolo.databinding.ActivityRegistrarUsuarioBinding;
import com.example.oficinadobolo.entities.Usuario;
import com.example.oficinadobolo.utils.ImageUtils;
import com.example.oficinadobolo.utils.PermissionManager;

import java.util.Objects;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    private static final String[] REQUIRED_PERMISSIONS_BELOW_33 = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @SuppressLint("InlinedApi")
    private static final String[] REQUIRED_PERMISSIONS_33_AND_ABOVE = new String[]{
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.CAMERA
    };
    private ActivityRegistrarUsuarioBinding binding;
    private Intent intent;
    private UsuarioDao usuarioDao;
    private LocalDatabase db;
    private static final String TAG = "RegistrarUsuarioActivity";
    private Uri contactPhotoUri;
    private PermissionManager permissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegistrarUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        permissionManager = new PermissionManager(this, REQUIRED_PERMISSIONS_BELOW_33, REQUIRED_PERMISSIONS_33_AND_ABOVE);
        // Configuração do texto clicável
        String text = Objects.requireNonNull(binding.txtTelaLogin.getText()).toString();
        SpannableString spannableString = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(RegistrarUsuarioActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        };
        int start = text.indexOf("Entre aqui!");
        int end = start + "Entre aqui!".length();
        spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.txtTelaLogin.setText(spannableString);
        binding.txtTelaLogin.setMovementMethod(LinkMovementMethod.getInstance());

        // Inicialização do banco de dados e DAO
        db = LocalDatabase.getDatabase(getApplicationContext());
        usuarioDao = db.usuarioModel();

        // Configuração do botão de registrar
        binding.btRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });

        // Configuração do clique na imagem para abrir a câmera
        binding.imageViewFoto.setOnClickListener(v -> {
            try {
                if (permissionManager.allPermissionsGranted()) {
                    Log.d(TAG, "Permissões já concedidas");
                    showPhotoDialog();
                } else {
                    Log.d(TAG, "Solicitando permissões necessárias");
                    permissionManager.requestPermissions(requestPermissionsLauncher);
                }
            } catch (Exception e) {
                Log.e(TAG, "Erro ao verificar permissões ou exibir diálogo de fotos: ", e);
            }
        });

        // Restaurar estado após a rotação da tela
        if (savedInstanceState != null) {
            binding.editNome.setText(savedInstanceState.getString("nome"));
            binding.editEmail.setText(savedInstanceState.getString("email"));
            binding.editSenha.setText(savedInstanceState.getString("senha"));
        }
    }

    private final ActivityResultLauncher<String[]> requestPermissionsLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                boolean allGranted = true;
                for (Boolean granted : result.values()) {
                    if (!granted) {
                        allGranted = false;
                        break;
                    }
                }
                if (allGranted) {
                    Log.d(TAG, "Todas as permissões foram concedidas");
                    showPhotoDialog();
                } else {
                    Log.d(TAG, "Nem todas as permissões foram concedidas");
                }
            });

    private final ActivityResultLauncher<Intent> takePictureLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle extras = result.getData().getExtras();
                    assert extras != null;
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    binding.imageViewFoto.setImageBitmap(imageBitmap); // Corrigir o uso da ImageView correta
                    contactPhotoUri = ImageUtils.saveImageToExternalStorage(this, imageBitmap);
                    if (contactPhotoUri != null) {
                        Toast.makeText(this, "Imagem salva com sucesso!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Erro ao salvar a imagem", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    // Método para registrar o usuário
    private void registrarUsuario() {
        String nome = binding.editNome.getText().toString().trim();
        String email = binding.editEmail.getText().toString().trim();
        String senha = binding.editSenha.getText().toString().trim();

        if (TextUtils.isEmpty(nome)) {
            binding.editNome.setError("Nome é obrigatório");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            binding.editEmail.setError("Email é obrigatório");
            return;
        }

        if (TextUtils.isEmpty(senha)) {
            binding.editSenha.setError("Senha é obrigatória");
            return;
        }

        // Verificar se a foto foi tirada
        if (contactPhotoUri == null) {
            Toast.makeText(this, "A foto é obrigatória", Toast.LENGTH_SHORT).show();
            return;
        }

        // Converter a URI da foto para bytes
        byte[] fotoBytes;
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contactPhotoUri);
            fotoBytes = ImageUtils.bitmapToBytes(bitmap);
        } catch (Exception e) {
            Log.e(TAG, "Erro ao converter a imagem para bytes: ", e);
            Toast.makeText(this, "Erro ao processar a foto", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setFoto(fotoBytes); // Supondo que o campo `foto` existe na entidade Usuario

        // Exemplo de inserção assíncrona no banco de dados
        new Thread(new Runnable() {
            @Override
            public void run() {
                usuarioDao.insertAll(usuario);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegistrarUsuarioActivity.this, "Usuário registrado com sucesso!", Toast.LENGTH_SHORT).show();
                        // Após o sucesso no registro, vá para a tela de login
                        Intent intent = new Intent(RegistrarUsuarioActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish(); // Finalizar a Activity atual para evitar retorno com o botão "voltar"
                    }
                });
            }
        }).start();
    }


    private void showPhotoDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Adicione Foto")
                .setItems(new String[]{"Acesse sua câmera"}, (dialog, which) -> {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    takePictureLauncher.launch(takePictureIntent);
                })
                .show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("nome", binding.editNome.getText().toString());
        outState.putString("email", binding.editEmail.getText().toString());
        outState.putString("senha", binding.editSenha.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        binding.editNome.setText(savedInstanceState.getString("nome"));
        binding.editEmail.setText(savedInstanceState.getString("email"));
        binding.editSenha.setText(savedInstanceState.getString("senha"));
    }
}

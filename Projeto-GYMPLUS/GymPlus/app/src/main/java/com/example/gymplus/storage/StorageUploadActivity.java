package com.example.gymplus.storage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.gymplus.R;
import com.example.gymplus.util.DialogAlerta;
import com.example.gymplus.util.DialogProgress;
import com.example.gymplus.util.Permissao;
import com.example.gymplus.util.Util;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class StorageUploadActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private Button button_enviar;
    private Uri uri_imagem;
    public static final int PICK_IMAGE = 10;
    public static final int PICK_IMAGE_CAMERA = 20;
    private FirebaseStorage storage;
    boolean status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_upload);

        imageView = (ImageView) findViewById(R.id.imagem_storage_upload);
        button_enviar = (Button) findViewById(R.id.button_enviar_storage_upload);
        button_enviar.setOnClickListener(this);
        storage = FirebaseStorage.getInstance();

        permissao();
    }

    // --------------------------------------------------- TRATAMENTO DE CLICKS ---------------------------------------------------

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_enviar_storage_upload:
                /*if(status == false){
                    Toast.makeText(getBaseContext(), "Insira uma imagem!", Toast.LENGTH_SHORT).show();
                }else if(status != false){
                    //upload_imagem_forma_1();
                }*/
                button_upload();
                break;
        }
    }

    private void button_upload(){

        if(Util.verificarInternet(getBaseContext())){
            if(imageView.getDrawable() != null){
                upload_imagem_forma_2();
            }else{
                Toast.makeText(getBaseContext(), "Insira uma Imagem!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getBaseContext(), "Error de Conexão - Verifique se seu WIFI ou 3G está funcionando", Toast.LENGTH_SHORT).show();
        }
    }

    // --------------------------------------------------- CRIAR MENU ---------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_storage_upload, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_galeria:
                obterImagem_Galeria();
                break;
            case R.id.item_camera:
                item_camera();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void item_camera(){
        if(ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_DENIED){
            DialogAlerta alerta = new DialogAlerta("Permissão necessária", "Acesso suas configurações para permitir a utilização da câmera no nosso aplicativo");
            alerta.show(getSupportFragmentManager(), "44");
        }else{
            obterImagem_Camera();
        }
    }

    // --------------------------------------------------- OBTER IMAGEM ---------------------------------------------------

    private void obterImagem_Galeria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Ecolha uma Imagem"), PICK_IMAGE);
    }


    private void obterImagem_Camera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File diretorio = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String nomeImagem = diretorio.getPath()+"/"+"CursoImagem"+System.currentTimeMillis()+".jpg";
        File file = new File(nomeImagem);
        String autorizacao = "com.example.gymplus";
        uri_imagem = FileProvider.getUriForFile(getBaseContext(), autorizacao, file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri_imagem);
        startActivityForResult(intent, PICK_IMAGE_CAMERA);
    }


    // ------------------------------------------------------------ UPLOAD IMAGEM ----------------------------------------------------------------

    private void upload_imagem_forma_1(){

        final DialogProgress dialogProgress = new DialogProgress();
        dialogProgress.show(getSupportFragmentManager(),"");
        StorageReference reference = storage.getReference().child("upload").child("imagens");
        StorageReference nome_imagem = reference.child("IMG"+System.currentTimeMillis()+".jpg");
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        UploadTask uploadTask = nome_imagem.putBytes(bytes.toByteArray());
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    dialogProgress.dismiss();
                    Toast.makeText(getBaseContext(), "Sucesso ao realizar Upload", Toast.LENGTH_SHORT).show();
                }else{
                    dialogProgress.dismiss();
                    Toast.makeText(getBaseContext(), "Error ao realizar Upload", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void upload_imagem_forma_2(){

        final DialogProgress dialogProgress = new DialogProgress();
        dialogProgress.show(getSupportFragmentManager(),"");
        StorageReference reference = storage.getReference().child("upload").child("imagens");
        final StorageReference nome_imagem = reference.child("IMG"+System.currentTimeMillis()+".jpg");

        Glide.with(getBaseContext()).asBitmap().load(uri_imagem).apply(new RequestOptions().override(1024, 768)).listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                dialogProgress.dismiss();
                DialogAlerta alerta = new DialogAlerta("Error", "Error ao Transformar Imagem. Tente Novamente!");
                alerta.show(getSupportFragmentManager(), "4");
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                resource.compress(Bitmap.CompressFormat.JPEG, 70, bytes);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes.toByteArray());
                try {
                    bytes.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                UploadTask uploadTask = nome_imagem.putStream(inputStream);

                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>(){

                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        return nome_imagem.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            dialogProgress.dismiss();
                            Uri uri = task.getResult();
                            String url_imagem = uri.toString();
                            DialogAlerta alerta = new DialogAlerta("URL IMAGEM", "Sucesso! Conseguimos gerar a URL da Imagem");
                            alerta.show(getSupportFragmentManager(), "3");
                            Toast.makeText(getBaseContext(), "Sucesso ao realizar Upload", Toast.LENGTH_SHORT).show();
                        }else{
                            dialogProgress.dismiss();
                            Toast.makeText(getBaseContext(), "Error ao realizar Upload", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                return false;
            }
        }).submit();

    }

    // --------------------------------------------------- RESPOSTAS DE COMUNICAÇÃO  -----------------------------------------------------------------------

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PICK_IMAGE:
                if(resultCode == RESULT_OK){
                    if (data != null) {
                        uri_imagem = data.getData();
                        Glide.with(getBaseContext()).asBitmap().load(uri_imagem).listener(new RequestListener<Bitmap>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                Toast.makeText(getBaseContext(), "Error ao Carregar Imagem", Toast.LENGTH_SHORT).show();
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        }).into(imageView);
                    } else {
                        Toast.makeText(getBaseContext(), "Falha ao selecionar Imagem", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case PICK_IMAGE_CAMERA:
                if(resultCode == RESULT_OK){
                    if(uri_imagem != null){
                        Glide.with(getBaseContext()).asBitmap().load(uri_imagem).listener(new RequestListener<Bitmap>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                Toast.makeText(getBaseContext(), "Error ao Carregar Imagem", Toast.LENGTH_SHORT).show();
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        }).into(imageView);
                    } else {
                        Toast.makeText(getBaseContext(), "Falha ao capturar Imagem", Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }


    // ------------------------------------------------------------ PERMISSÕES USUÁRIO ----------------------------------------------------------------

    private void permissao() {

        String permissoes[] = {Manifest.permission.CAMERA};
        Permissao.permissao(this, 0, permissoes);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "Aceite as permissões para o aplicativo acessar sua câmera", Toast.LENGTH_LONG).show();
                finish();
                break;
            }
        }
    }

}



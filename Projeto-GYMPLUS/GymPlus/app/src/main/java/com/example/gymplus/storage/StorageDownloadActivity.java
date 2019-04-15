package com.example.gymplus.storage;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.gymplus.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class StorageDownloadActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private ProgressBar progressBar;
    private Button botao_download, botao_remover;
    private FirebaseStorage storage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_download);


        imageView = (ImageView) findViewById(R.id.image_storage_download);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_storage_download);
        botao_download = (Button) findViewById(R.id.button_download_storage_download);
        botao_remover = (Button) findViewById(R.id.button_remover_storage_download);

        botao_download.setOnClickListener(this);
        botao_remover.setOnClickListener(this);

        progressBar.setVisibility(View.GONE);

        storage = FirebaseStorage.getInstance();

    }

    // --------------------------------------------------- TRATAMENTO DE CLICKS ---------------------------------------------------

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_download_storage_download:
                //download_imagem();
                download_imagem_nome();
                break;
            case R.id.button_remover_storage_download:
                // remover_url();
                remover_nome();
                break;
        }
    }

    // --------------------------------------------------- CRIAR MENU ---------------------------------------------------


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_storage_download, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_compartilhar:
                Toast.makeText(getBaseContext(), "Compartilhar", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_criar_pdf:
                Toast.makeText(getBaseContext(), "Criar PDF", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // --------------------------------------------------- DOWNLOAD URL ---------------------------------------------------

    private void download_imagem() {

        progressBar.setVisibility(View.VISIBLE);

        String url = "https://firebasestorage.googleapis.com/v0/b/gumplus-9d995.appspot.com/o/imagem%2Fdownload.jpg?alt=media&token=d4906c54-62e8-43da-b9f4-c94acd3bc1e4";

        /*Picasso.with(getBaseContext()).load(url).into(imageView, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                progressBar.setVisibility(View.GONE);
            }
        });*/

        Glide.with(getBaseContext()).asBitmap().load(url).listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(imageView);

    }

    // --------------------------------------------------- DOWNLOAD NOME ---------------------------------------------------

    private void download_imagem_nome() {
        progressBar.setVisibility(View.VISIBLE);

        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference reference = storage.getReference().child("imagem").child("download.jpg");

        reference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    String url = task.getResult().toString();

                    Picasso.with(getBaseContext()).load(url).into(imageView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            Toast.makeText(getBaseContext(), "Error ao Remover Imagem", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });

                    /*Glide.with(getBaseContext()).asBitmap().load(url).listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    }).into(imageView);*/
                }
            }
        });

    }

    // --------------------------------------------------- REMOVER URL ---------------------------------------------------

    private void remover_url() {

        String url = "https://firebasestorage.googleapis.com/v0/b/gumplus-9d995.appspot.com/o/imagem%2Fdownload.jpg?alt=media&token=d4906c54-62e8-43da-b9f4-c94acd3bc1e4";

        StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(url);

        reference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    imageView.setImageDrawable(null);
                    Toast.makeText(getBaseContext(), "Sucesso ao Remover Imagem", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getBaseContext(), "Error ao Remover Imagem", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    // --------------------------------------------------- REMOVER NOME ---------------------------------------------------


    private void remover_nome() {

        String nome = "download.jpg";

        StorageReference reference = storage.getReference().child("imagem").child(nome);

        reference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    imageView.setImageDrawable(null);
                    Toast.makeText(getBaseContext(), "Sucesso ao Remover Imagem", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getBaseContext(), "Error ao Remover Imagem", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}

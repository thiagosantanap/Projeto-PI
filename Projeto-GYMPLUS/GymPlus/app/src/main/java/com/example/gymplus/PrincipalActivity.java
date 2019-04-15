package com.example.gymplus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gymplus.storage.StorageDownloadActivity;
import com.example.gymplus.storage.StorageUploadActivity;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener{

    private Button button_Deslogar, botao_storage_download,botao_storage_upload, botao_database_ler, botao_database_alterar, botao_usuarios;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);


        button_Deslogar = (Button) findViewById(R.id.button_Deslogar);
        button_Deslogar.setOnClickListener(this);

        botao_storage_download = (Button) findViewById(R.id.button_storage_download);
        botao_storage_download.setOnClickListener(this);

        botao_storage_upload = (Button) findViewById(R.id.button_storage_upload);
        botao_storage_upload.setOnClickListener(this);

        botao_database_ler = (Button) findViewById(R.id.button_database_ler);
        botao_database_ler.setOnClickListener(this);

        botao_database_alterar = (Button) findViewById(R.id.button_database_alterar);
        botao_database_alterar.setOnClickListener(this);

        botao_usuarios = (Button) findViewById(R.id.button_usuarios);
        botao_usuarios.setOnClickListener(this);

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.button_Deslogar:

                // Deslogando Firebase

                FirebaseAuth.getInstance().signOut();

                // Deslogango Facebook

                LoginManager.getInstance().logOut();

                // Deslogando Google

                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                googleSignInClient = GoogleSignIn.getClient(this, gso);
                googleSignInClient.signOut();

                // Finish

                finish();
                Toast.makeText(this, "Deslogado", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getBaseContext(),MainActivity.class));

                break;

            case R.id.button_storage_download:
                Toast.makeText(this, "Storage Download", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getBaseContext(), StorageDownloadActivity.class));
                break;
            case R.id.button_storage_upload:
                Toast.makeText(this, "Storage Upload", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getBaseContext(), StorageUploadActivity.class));
                break;
            case R.id.button_database_ler:
                Toast.makeText(this, "DataBase Ler", Toast.LENGTH_LONG).show();
                break;
            case R.id.button_database_alterar:
                Toast.makeText(this, "DataBase Alterar", Toast.LENGTH_LONG).show();
                break;
            case R.id.button_usuarios:
                Toast.makeText(this, "Usuários", Toast.LENGTH_LONG).show();
                break;
        }
    }
}

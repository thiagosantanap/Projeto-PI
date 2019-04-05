package com.example.gymplus;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Criando Botões.

    private Button button_Login, button_Cadastrar;
    private CardView cardView_LoginFacebook, cardView_LoginGoogle;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth.AuthStateListener authStateListener;
    private CallbackManager callbackManager; // Gerenciador de resposta de chamadas - Armazena a resposta do facebook

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Desenvolvendo Botões de Login - "Pegando Botões".

        button_Login = (Button)findViewById(R.id.button_login);                         // Pegando botão de Login - (Button) é um casting.
        button_Cadastrar = (Button)findViewById(R.id.button_cadastrar );                // Pegando botão de Cadastro

        cardView_LoginGoogle = (CardView)findViewById(R.id.cardView_LoginGoogle);
        cardView_LoginFacebook = (CardView)findViewById(R.id.cardView_LogiFacebook);

        // Implementando o Click no botão - Existem duas formas de fazer isso.


        /*

        Primeira forma. Não é tão aconselhável...

        button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Comando assim que clickar no botão de login.
                // Todo código colocado aqui vai acontecer quando vocÊ executar o botão de Login.
            }
        });

        button_Cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se tivermos que fazer o mesmo para o nosso botão de Cadastrar o nosso código vai ficar confuso.
            }
        });
        */

        // Segunda Forma. Mais indicada.

        button_Login.setOnClickListener(this);
        button_Cadastrar.setOnClickListener(this);
        cardView_LoginFacebook.setOnClickListener(this);
        cardView_LoginGoogle.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();

        servicosAutenticacao();
        servicosFacebook();
        servicosGoogle();

    }

    // ------------------------------------------------------------ SERVIÇOS LOGIN ------------------------------------------------------------

    private void servicosFacebook(){

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken count = loginResult.getAccessToken();
                adicionarContaFacebookFirebase(count);
            }

            @Override
            public void onCancel() {
                Toast.makeText(getBaseContext(), "Cancelado.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getBaseContext(), "Error ao fazer login com Facebook.", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void servicosGoogle(){

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    // Quando clickarmos no botão essa função vai ser chamada

    private void servicosAutenticacao(){
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Toast.makeText(getBaseContext(), "Usuário " + user.getEmail() + " está logado.", Toast.LENGTH_LONG).show();
                }else{
                }
            }
        };
    }

    // ------------------------------------------------------------ TRATAMENTO DE CLICKS ------------------------------------------------------------

    @Override
    public void onClick(View view) {
        // Pegando o Id da view e para cada Id estamos implementando algo.
        switch (view.getId()){
            // Caso a view seja o botão de login
            case R.id.cardView_LogiFacebook:
                signInFacebook();
                break;
            case R.id.cardView_LoginGoogle:
                signInGoogle();
                break;
            case R.id.button_login:
                signInEmail();
                break;
            // Caso a view seja o botão de cadastrar
            case R.id.button_cadastrar:
                // Execute um Comando
                startActivity(new Intent(this, CadastrarActivity.class));
                break;

        }
    }

    // ------------------------------------------------------------ MÉTODOS DE LOGIN ------------------------------------------------------------

    private void signInFacebook(){
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
    }

    private void signInGoogle(){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if(account == null){
            Intent intent = googleSignInClient.getSignInIntent();
            startActivityForResult(intent, 555);
        }else{
            // Já existe alguém conectado pelo Google
            Toast.makeText(getBaseContext(), "Usuário já logado.", Toast.LENGTH_LONG).show();

            startActivity(new Intent(getBaseContext(), PrincipalActivity.class));

            // googleSignInClient.signOut();

        }
    }

    private void signInEmail(){
        user = auth.getCurrentUser();
        if(user == null){
            // Execute um Comando
            startActivity(new Intent(this, LoginEmailActivity.class));
        }
        else {
            // Execute um Comando
            startActivity(new Intent(this, PrincipalActivity.class));
        }
    }

    // ------------------------------------------------------------ AUTENTICAÇÃO NO FIREBASE ------------------------------------------------------------

    private void adicionarContaFacebookFirebase(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(getBaseContext(), PrincipalActivity.class));
                        } else {
                            Toast.makeText(getBaseContext(), "Error ao Criar Conta Facebook.", Toast.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });
    }

    private void adicionarContaGoogleFirebase(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(getBaseContext(), PrincipalActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getBaseContext(), "Error ao Criar Conta Google.", Toast.LENGTH_LONG).show();
                        }
                        // ...
                    }
                });
    }

    // ------------------------------------------------------------ MÉTODOS DA ACTIVITY ----------------------------------------------------------------

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 555){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                adicionarContaGoogleFirebase(account);

            }catch (ApiException e){
                Toast.makeText(getBaseContext(), "Error ao Logar com Conta no Google.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener != null){
            auth.removeAuthStateListener(authStateListener);
        }
    }
}

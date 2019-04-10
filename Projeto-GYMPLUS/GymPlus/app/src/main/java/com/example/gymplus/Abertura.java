package com.example.gymplus;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// Primeira tela aberta no nosso aplicativo.

// Ela será exibida enquanto o aplicativo estiver sendo carregado. Depois dela vamos direto para nossa MainActivity.

// A MainActivity é onde o usuário irá escolher se ele deseja realizar o login ou cadastrar.

// Caso ele faça o Login correte, então vai para a PrincipalActivity.

public class Abertura extends AppCompatActivity implements Runnable{
    private ProgressBar progressBar;
    private Thread thread;
    private Handler handler;
    private int contador;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abertura);
        auth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar)findViewById(R.id.progressBar_Abertura);
        handler = new Handler(); // Handler significa manipulador - Ele ficará responsável por entregar as mensagens para dentro da Thread
        thread = new Thread(this);
        // Iniciando Thread
        thread.start();
    }

    @Override
    public void run() {
        // Inicializando contador
        contador = 1;
        // Tratar erros...
        try{
            while(contador <= 100){
                Thread.sleep(30); // Pausando por um determinado tempo
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // Incrementando contador
                        contador += 1; // Fila de mensagens
                        progressBar.setProgress(contador);
                    }
                });
            }
            FirebaseUser user = auth.getCurrentUser();
            if(user == null){
                finish();
                startActivity(new Intent(getBaseContext(), MainActivity.class));
            }else{
                finish();
                startActivity(new Intent(getBaseContext(), PrincipalActivity.class));
            }
        }
        catch (InterruptedException e){
        }
    }
}

package com.example.gymplus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

// Primeira tela aberta no nosso aplicativo.

// Ela será exibida enquanto o aplicativo estiver sendo carregado. Depois dela vamos direto para nossa MainActivity.

// A MainActivity é onde o usuário irá escolher se ele deseja realizar o login ou cadastrar.

// Caso ele faça o Login correte, então vai para a PrincipalActivity.

public class Abertura extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abertura);
    }
}

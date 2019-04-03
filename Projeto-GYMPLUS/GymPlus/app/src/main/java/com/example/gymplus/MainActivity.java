package com.example.gymplus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Criando Botões.

    private Button button_Login, button_Cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Desenvolvendo Botões de Login - "Pegando Botões".

        button_Login = (Button)findViewById(R.id.button_login);                         // Pegando botão de Login - (Button) é um casting.
        button_Cadastrar = (Button)findViewById(R.id.button_cadastrar );                // Pegando botão de Cadastro


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

    }

    // Quando clickarmos no botão essa função vai ser chamada

    @Override
    public void onClick(View view) {
        // Pegando o Id da view e para cada Id estamos implementando algo.
        switch (view.getId()){

            // Caso a view seja o botão de login

            case R.id.button_login:
                // Execute um Comando
                break;

            // Caso a view seja o botão de cadastrar

            case R.id.button_cadastrar:
                // Execute um Comando

                startActivity(new Intent(this, CadastrarActivity.class));

                break;

        }
    }
}

package com.example.gymplus;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CadastrarActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editText_Email, editText_Senha, editText_SenhaRepetir;
    private Button cardView_CadastrarUser, cardViewCancelar;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
        editText_Email = (EditText)findViewById(R.id.editText_EmailCadastro);
        editText_Senha = (EditText)findViewById(R.id.editText_SenhaCadastro);
        editText_SenhaRepetir = (EditText)findViewById(R.id.editText_SenhaRepetirCadastro);
        cardView_CadastrarUser = (Button) findViewById(R.id.cardView_CadastrarUsuario);
        cardViewCancelar = (Button)findViewById(R.id.cardView_Cancelar);
        cardView_CadastrarUser.setOnClickListener(this);
        cardViewCancelar.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cardView_CadastrarUsuario:
                // Execute um Comando
                cadastrar();
                break;
            case R.id.cardView_Cancelar:
                Intent voltar = new Intent(CadastrarActivity.this, MainActivity.class);
                startActivity(voltar);
                break;
        }
    }

    private void cadastrar(){
        // Recuperando valores dos botões
        String email = editText_Email.getText().toString().trim();
        String senha = editText_Senha.getText().toString().trim();
        String confirmando = editText_SenhaRepetir.getText().toString().trim();
        // Tratamentos de preenchimento.
        if(email.isEmpty() || senha.isEmpty() || confirmando.isEmpty()){
            // Essa condição verifica se algum campo está nulo. Se estiver nulo apresenta uma mensagem.
            Toast.makeText(getBaseContext(), "Error - Preencha os campos.", Toast.LENGTH_LONG).show();
        }else{
            // Caso a condição de estar nulo seja False, então podemos verificar se as senhas são iguais.
            if(email.contains(" ")){
                Toast.makeText(getBaseContext(), "Digite um E-mail sem espaços.", Toast.LENGTH_LONG).show();
            }else if(email.contains("-") || email.contains(";") || email.contains("-") || email.contains(";")){
                Toast.makeText(getBaseContext(), "Seu E-mail possui caracter inválido.", Toast.LENGTH_LONG).show();

            }else if (senha.contains(" ")){
                Toast.makeText(getBaseContext(), "Digite uma Senha sem espaços.", Toast.LENGTH_LONG).show();
            }else{
                // Verificando se as senhas são iguais.
                if(senha.contentEquals(confirmando)){
                    // Varificando força da senha
                    int count_caractere = senha.length();
                    int pontuacao_count_caractere = count_caractere * 6;
                    // System.out.println(pontuacao_count_caractere);
                    if(pontuacao_count_caractere > 60){
                        pontuacao_count_caractere = 60;
                    }
                    // System.out.println(pontuacao_count_caractere);
                    int count_minuscula = validatePassword_minuscula(senha);
                    int count_maiuscula = validatePassword_maiuscula(senha);
                    int count_numeros = validatePassword_numeros(senha);
                    int count_outros = validatePassword_outros(senha);
                    int count_final = 0;
                    count_final += pontuacao_count_caractere;
                    if(count_minuscula != 0){
                        count_final += 5;
                    }
                    if(count_maiuscula != 0){
                        count_final += 5;
                    }
                    if(count_numeros != 0){
                        count_final += 5;
                    }
                    if(count_outros != 0){
                        count_final += 5;
                        if(count_outros >= 2){
                            count_final += 10;
                        }
                    }
                    if(count_minuscula > count_maiuscula && count_minuscula > count_numeros && count_minuscula > count_outros){
                        count_final += 10;
                    }
                    else if(count_maiuscula > count_minuscula && count_maiuscula > count_numeros && count_maiuscula > count_outros){
                        count_final += 10;
                    }
                    else if(count_numeros > count_minuscula && count_numeros > count_maiuscula && count_numeros > count_outros){
                        count_final += 10;
                    }
                    // System.out.println(count_final);
                    if(count_final >= 0 && count_final <= 59) {
                        Toast.makeText(getBaseContext(), "Senha Fraca! Escolha uma mais Forte", Toast.LENGTH_LONG).show();
                    }else {
                        // Se tudo estiver okay criamos o usuário.
                        if(Util.verificarInternet(this)) {
                            criarUsuario(email, senha);
                        }else{
                            Toast.makeText(getBaseContext(), "Error - Verifique se seu WIFI ou 3G está funcionando.", Toast.LENGTH_LONG).show();
                        }
                    }
                }else{
                    // Se não forem iguais exibe um Toast de erro.
                    Toast.makeText(getBaseContext(), "Error - Senhas diferentes.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void criarUsuario(String email, String senha){
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // Se o usuáiro preencheu todos os campos.
                if(task.isSuccessful()){
                    Toast.makeText(getBaseContext(), "Cadastro efetuado com Sucesso.", Toast.LENGTH_LONG).show();
                    Intent ir = new Intent(CadastrarActivity.this, PrincipalActivity.class);
                    startActivity(ir);
                }else{
                    String resposta = task.getException().toString(); // A variável task é onde o firebase nos informa os erros que ocasionaram na hora de criar um usuário.
                    // Pegando string de erro do firebase e armazenando na variável resposta.
                    Util.opcError(getBaseContext(), resposta);
                }
            }
        });
    }

    public static int validatePassword_minuscula(final String password){
        Pattern pattern = Pattern.compile("[a-z]+");
        Matcher matcher = pattern.matcher(password);
        String resultado;
        int tamanho = 0;
        while (matcher.find()) {
            resultado = matcher.group();
            for(int i = 0; i < resultado.length(); i++){
                tamanho += 1;
            }
        }
        return tamanho;
    }

    public static int validatePassword_maiuscula(final String password){
        Pattern pattern = Pattern.compile("[A-Z]+");
        Matcher matcher = pattern.matcher(password);
        String resultado;
        int tamanho = 0;
        while (matcher.find()) {
            resultado = matcher.group();
            for(int i = 0; i < resultado.length(); i++){
                tamanho += 1;
            }
        }
        return tamanho;
    }

    public static int validatePassword_numeros(final String password){
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(password);
        String resultado;
        int tamanho = 0;
        while (matcher.find()) {
            resultado = matcher.group();
            for(int i = 0; i < resultado.length(); i++){
                tamanho += 1;
            }
        }
        return tamanho;
    }

    public static int validatePassword_outros(final String password){
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]+");
        Matcher matcher = pattern.matcher(password);
        String resultado;
        int tamanho = 0;
        while (matcher.find()) {
            resultado = matcher.group();
            for(int i = 0; i < resultado.length(); i++){
                tamanho += 1;
            }
        }
        return tamanho;
    }
}


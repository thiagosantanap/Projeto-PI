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
    private CardView cardView_CadastrarUser, cardViewCancelar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        editText_Email = (EditText)findViewById(R.id.editText_EmailCadastro);
        editText_Senha = (EditText)findViewById(R.id.editText_SenhaCadastro);
        editText_SenhaRepetir = (EditText)findViewById(R.id.editText_SenhaRepetirCadastro);

        cardView_CadastrarUser = (CardView) findViewById(R.id.cardView_CadastrarUsuario);
        cardViewCancelar = (CardView)findViewById(R.id.cardView_Cancelar);

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
            } else{
                // Verificando se as senhas são iguais.
                if(senha.contentEquals(confirmando)){
                    // Se tudo estiver okay criamos o usuário.
                    int count = 0;
                    for (int i=0;i<senha.length();i++){
                        if (validatePassword(senha)){
                            count++;
                        }
                    }

                    if(count == 6) {
                        Toast.makeText(getBaseContext(), "Senha Fraca!", Toast.LENGTH_LONG).show();

                    }else {
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
                }else{
                    String resposta = task.getException().toString(); // A variável task é onde o firebase nos informa os erros que ocasionaram na hora de criar um usuário.
                    // Pegando string de erro do firebase e armazenando na variável resposta.
                    Util.opcError(getBaseContext(), resposta);
                }
            }
        });
    }

    public static boolean validatePassword(final String password){
        Pattern p = Pattern.compile("^(?=.[0-9])(?=.[a-z])(?=.[A-Z])(?=.[@#$%^&+=])(?=\\S+$).{8,}$");
        Matcher m = p.matcher(password);
        return m.matches();
    }
}


package com.example.gymplus;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gymplus.util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginEmailActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editText_EmailLogin, editText_SenhaLogin;
    private Button button_OkLogin, button_RecuperarSenha, button_Cadastrar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText_EmailLogin = (EditText)findViewById(R.id.editText_EmailLogin);
        editText_SenhaLogin = (EditText)findViewById(R.id.editText_SenhaLogin);

        button_OkLogin = (Button)findViewById(R.id.button_OkLogin);
        button_RecuperarSenha = (Button)findViewById(R.id.button_RecuperarSenha);
        button_Cadastrar = (Button)findViewById(R.id.button_okCadastrar) ;

        button_OkLogin.setOnClickListener(this);
        button_RecuperarSenha.setOnClickListener(this);
        button_Cadastrar.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_OkLogin:
                loginEmail();
                break;
             case R.id.button_okCadastrar:
                startActivity(new Intent(this, CadastrarActivity.class));
                finish();
                break;
            case R.id.button_RecuperarSenha:
                startActivity(new Intent(this, RecuperarActivity.class));
                finish();
                break;
        }
    }

    private void loginEmail(){
        String email = editText_EmailLogin.getText().toString().trim();
        String senha = editText_SenhaLogin.getText().toString().trim();
        if(email.isEmpty() || senha.isEmpty()){
            // Essa condição verifica se algum campo está nulo. Se estiver nulo apresenta uma mensagem.
            Toast.makeText(getBaseContext(), "Error - Insira os campos obrigatórios.", Toast.LENGTH_LONG).show();
        }else{
            if(Util.verificarInternet(this)) {
                ConnectivityManager conexao = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                confirmarLoginEmail(email, senha);
            }else{
                Toast.makeText(getBaseContext(), "Error - Verifique se seu WIFI ou 3G está funcionando.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void confirmarLoginEmail(String email, String senha){
        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(getBaseContext(), PrincipalActivity.class));
                    Toast.makeText(getBaseContext(), "Usuário Logado com sucesso.", Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    String resposta = task.getException().toString();
                    Util.opcError(getBaseContext(), resposta);
                    // Toast.makeText(getBaseContext(), "Error ao Logar usuário", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
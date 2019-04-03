package com.example.gymplus;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CadastrarActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText_Email, editText_Senha, editText_SenhaRepetir;

    private Button button_Cadastrar, button_Cancelar;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        editText_Email = (EditText)findViewById(R.id.editText_EmailCadastro);
        editText_Senha = (EditText)findViewById(R.id.editText_SenhaCadastro);
        editText_SenhaRepetir = (EditText)findViewById(R.id.editText_SenhaRepetirCadastro);

        button_Cadastrar = (Button)findViewById(R.id.button_CadastrarUsuario);
        button_Cancelar = (Button)findViewById(R.id.button_Cancelar);

        button_Cadastrar.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.button_CadastrarUsuario:
                // Execute um Comando
                cadastrar();
                break;

        }

    }

    private void cadastrar(){

        String email = editText_Email.getText().toString().trim();
        String senha = editText_Senha.getText().toString().trim();
        String confirmando = editText_SenhaRepetir.getText().toString().trim();

        if(email.isEmpty() || senha.isEmpty() || confirmando.isEmpty()){
            Toast.makeText(getBaseContext(), "Error - Preencha os campos.", Toast.LENGTH_LONG).show();
        }else{

            // Verificando se as senhas são iguais.

            if(senha.contentEquals(confirmando)){

                criarUsuario(email, senha);

            }else{
                Toast.makeText(getBaseContext(), "Error - Senhas diferentes.", Toast.LENGTH_LONG).show();
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

                    String resposta = task.getException().toString();

                    opcError(resposta);
                }

            }
        });
    }

    private void opcError(String resposta){
        if(resposta.contains("least 6 characters")){
            Toast.makeText(getBaseContext(), "Digite uma senha maior que 5 caracteres.", Toast.LENGTH_LONG).show();
        }
        else if(resposta.contains("address is badly")){
            Toast.makeText(getBaseContext(), "E-mail inválido.", Toast.LENGTH_LONG).show();
        }
        else if(resposta.contains("address is already")){
            Toast.makeText(getBaseContext(), "E-mail já cadastrado.", Toast.LENGTH_LONG).show();
        }
        else if(resposta.contains("interrupted connection")){
            Toast.makeText(getBaseContext(), "Sem conexão com a internet.", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getBaseContext(), resposta, Toast.LENGTH_LONG).show();
        }
    }

}

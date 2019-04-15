package com.example.gymplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gymplus.util.Util;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperarActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editor_texto_email_recuperar;
    private Button botao_recuperar, botao_cancelar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);
        editor_texto_email_recuperar = (EditText)findViewById(R.id.editText_EmailRecuperar);
        botao_recuperar = (Button) findViewById(R.id.button_enviar_email_recuperar);
        botao_cancelar = (Button)findViewById(R.id.button_cancelar_recuperacao);

        botao_recuperar.setOnClickListener(this);
        botao_cancelar.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_enviar_email_recuperar:
                recuperarSenha();
                break;
            case R.id.button_cancelar_recuperacao:
                Intent rec = new Intent(RecuperarActivity.this, LoginEmailActivity.class);
                startActivity(rec);
                break;
        }
    }

    private void recuperarSenha() {
        String email = editor_texto_email_recuperar.getText().toString().trim();
        if(email.isEmpty()){
            // Essa condição verifica se algum campo está nulo. Se estiver nulo apresenta uma mensagem.
            Toast.makeText(getBaseContext(), "Insira pelo menos seu E-mail para poder Recuperar sua senha.", Toast.LENGTH_LONG).show();
        }else{
            enviarEmail(email);
        }
    }

    private void enviarEmail(String email){
        auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getBaseContext(), "Enviamos uma mensagem para seu Email de redefinição de senha.", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String error = e.toString();
                Util.opcError(getBaseContext(), error);
            }
        });
    }

}

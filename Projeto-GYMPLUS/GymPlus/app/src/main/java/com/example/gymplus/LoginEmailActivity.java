package com.example.gymplus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginEmailActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText_EmailLogin, editText_SenhaLogin;
    private Button button_OkLogin, button_RecuperarSenha;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText_EmailLogin = (EditText)findViewById(R.id.editText_EmailLogin);
        editText_SenhaLogin = (EditText)findViewById(R.id.editText_SenhaLogin);

        button_OkLogin = (Button)findViewById(R.id.button_OkLogin);
        button_RecuperarSenha = (Button)findViewById(R.id.button_RecuperarSenha);

        button_OkLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_OkLogin:

                break;
            case R.id.button_RecuperarSenha:

                break;
        }
    }
}

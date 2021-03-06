package com.example.gymplus.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class Util {


    public static boolean verificarInternet(Context context){

        ConnectivityManager conexao = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo informacao = conexao.getActiveNetworkInfo();

        if(informacao != null && informacao.isConnected()){
            return true;
        }
        else {
            return false;
        }
    }

    public static void opcError(Context context, String resposta){

        if(resposta.contains("least 6 characters")){
            Toast.makeText(context, "Digite uma senha maior que 5 caracteres.", Toast.LENGTH_LONG).show();
        }
        else if(resposta.contains("address is badly")){
            Toast.makeText(context, "E-mail inválido.", Toast.LENGTH_LONG).show();
        }
        else if(resposta.contains("interrupted connection")){
            Toast.makeText(context, "Sem conexão com o Firebase.", Toast.LENGTH_LONG).show();
        }
        else if(resposta.contains("password is invalid")){
            Toast.makeText(context, "Senha Incorreta.", Toast.LENGTH_LONG).show();
        }
        else if(resposta.contains("There is no user")){
            Toast.makeText(context, "Este E-mail não está cadastrado.", Toast.LENGTH_LONG).show();
        }

        // -----------------------------------------------------------------------------------------------------

        else if(resposta.contains("address is already")){
            Toast.makeText(context, "E-mail já cadastrado.", Toast.LENGTH_LONG).show();
        }
        else if(resposta.contains("INVALID_EMAIL")){
            Toast.makeText(context, "E-mail Inválido.", Toast.LENGTH_LONG).show();
        }
        else if(resposta.contains("EMAIL_NOT_FOUND")){
            Toast.makeText(context, "Este E-mail não está cadastrado ainda.", Toast.LENGTH_LONG).show();
        }
        // -----------------------------------------------------------------------------------------------------
        else if(resposta.contains("exists with the same email address")){
            Toast.makeText(context, "Error - E-mail já cadastrado, porém com credenciais diferentes.", Toast.LENGTH_LONG).show();
        }
        else if(resposta.contains("CONNECTION_FAILURE")){
            Toast.makeText(context, "Error Facebook - Sem internet", Toast.LENGTH_LONG).show();
        }
        else if(resposta.contains("7:")){
            Toast.makeText(context, "Error Google - Sem internet", Toast.LENGTH_LONG).show();
        }
        else if(resposta.contains("12501:")){
            Toast.makeText(context, "Cancelado", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
        }
    }

    public static boolean verificarCampo(Context context, String texto_1, String texto_2){

        if(texto_1.isEmpty() && texto_2.isEmpty()){
            if(verificarInternet(context)){
                return false;
            }else{
                Toast.makeText(context, "Sem conexão com a Internet", Toast.LENGTH_LONG).show();
            }

        }else{

        }
            Toast.makeText(context, "Preencha os campos", Toast.LENGTH_LONG).show();
            return false;
    }

}

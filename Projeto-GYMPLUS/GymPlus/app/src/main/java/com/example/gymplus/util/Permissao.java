package com.example.gymplus.util;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;



public class Permissao {


    public static boolean permissao(Activity activity, int requestCode, String[] permissoes){

        List<String> list=new ArrayList<String>();

        //No nosso for a variavel permissao irá receber de uma em uma as strings do array da variavel permissoes
        for(String permissao : permissoes){
            
            //caso o usuario NÃO tenha permitido ao aplicativo certos privilegios de acesso
            //a variavel ok recebera false
            boolean ok= ContextCompat.checkSelfPermission(activity,permissao)== PackageManager.PERMISSION_GRANTED;
            
            
            if(!ok){// caso a variavel ok tenha recebido false, então cairá dentro desse if
                
                
                // a variavel list irá receber a nossa string do for "que simboliza a permissao que
                // o aplicativo está querendo"
                list.add(permissao);
            }
            
            
        }// fim do nosso for - caso a variavel permissoes tenha mais de uma string,o for é executado novamente
        
        
        //se a nossa variavel list for vazia quer dizer que o usuario já permitiu que o nosso aplicativo
        //tenha certos privilegios e então entrará dentro no nosso if
        
        if(list.isEmpty()){
            return true; // caso entre dentro do nosso if o metodo acaba aqui
        }

        
        // caso a nossa variavel list não for vazia executa esse trecho de código abaixo
        String[] newPermissions= new String[list.size()];
        list.toArray(newPermissions);

        //solicita a permissao
        ActivityCompat.requestPermissions(activity,newPermissions,requestCode);
        return false;
    }



}

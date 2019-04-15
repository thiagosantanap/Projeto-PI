package com.example.gymplus.util;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.example.gymplus.R;


@SuppressLint("ValidFragment")
public class DialogAlerta extends DialogFragment {
    private String title;
    private String msg;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @SuppressLint("ValidFragment")
    public DialogAlerta(String title, String msg){
        this.title = title;
        this.msg = msg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.dialog_alerta,container);
        TextView title = (TextView) view.findViewById(R.id.textView_DialogAlerta_Title);
        TextView msg = (TextView) view.findViewById(R.id.textView_DialogAlerta_MSG);
        Button button = (Button) view.findViewById(R.id.button_DialogAlerta_OK);
        setRetainInstance(true);

        title.setText(this.title);
        msg.setText(this.msg);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                        dismiss();
                    }
                });

        return view;

    }



}

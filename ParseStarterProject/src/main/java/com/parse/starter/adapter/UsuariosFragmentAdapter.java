package com.parse.starter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.starter.R;

import java.util.ArrayList;

public class UsuariosFragmentAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<ParseUser> usuarios;

    public UsuariosFragmentAdapter(@NonNull Context c, ArrayList<ParseUser> objects) {
        super(c, 0, objects);
        this.context = c;
        this.usuarios = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){

            //Inicializa objeto para montagem do layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //monta a view a partir do xml
            view = inflater.inflate(R.layout.lista_usuarios, parent, false);

        }

        //recuperar elementos para exibicao
        TextView username = (TextView) view.findViewById(R.id.nome_usuario);

        //configurar o textview para exibir os usuarios
        ParseUser parseUser = usuarios.get( position );
        username.setText( parseUser.getUsername() );

        return view;
    }
}

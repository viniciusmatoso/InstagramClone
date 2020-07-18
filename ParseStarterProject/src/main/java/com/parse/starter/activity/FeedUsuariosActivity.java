package com.parse.starter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.starter.R;
import com.parse.starter.adapter.HomeFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class FeedUsuariosActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private String username;
    private ArrayAdapter<ParseObject> adapter;
    private ArrayList<ParseObject> postagens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_usuarios);

        //recupera dados enviados da intent
        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        toolbar = (Toolbar) findViewById(R.id.toolbar_feed_usuario);
        toolbar.setTitle( username );
        toolbar.setTitleTextColor( R.color.preto );
        toolbar.setNavigationIcon( R.drawable.ic_keyboard_arrow_left );
        setSupportActionBar( toolbar );

        //configura listview e adapter
        postagens = new ArrayList<>();
        listView = (ListView) findViewById(R.id.lista_feed_usuario);
        adapter = new HomeFragmentAdapter( getApplicationContext(), postagens);
        listView.setAdapter( adapter );

        //recuperar postagens
        getPostagens();

    }

    private void getPostagens() {

        //recupera imagens das postagens
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Imagem");
        query.whereEqualTo("username", username);
        query.orderByAscending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e == null){

                    if(objects.size() > 0){

                        postagens.clear();
                        for(ParseObject parseObject: objects){
                            postagens.add( parseObject );
                        }
                        adapter.notifyDataSetChanged();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Erro ao recuperar o feed!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

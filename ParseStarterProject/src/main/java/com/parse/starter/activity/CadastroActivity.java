package com.parse.starter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.starter.R;
import com.parse.starter.util.ParseErros;

public class CadastroActivity extends AppCompatActivity {

    private EditText etUsuario;
    private EditText etEmail;
    private EditText etSenha;
    private Button btCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        ParseUser currentUser = new ParseUser();
        currentUser.getSessionToken();
        if (currentUser != null) {
            ParseUser.logOut();
        }

        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etSenha = (EditText) findViewById(R.id.etSenha);

        btCadastrar = (Button) findViewById(R.id.btCadastrar);
        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarUsuario();
            }
        });
    }

    private void cadastrarUsuario() {

        //cria objeto usuario
        ParseUser usuario = new ParseUser();
        usuario.setUsername( etUsuario.getText().toString() );
        usuario.setEmail( etEmail.getText().toString() );
        usuario.setPassword( etSenha.getText().toString() );

        //salva dados do usuario
        usuario.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if( e == null){
                    Toast.makeText(CadastroActivity.this, "Cadastro feito com sucesso!", Toast.LENGTH_LONG).show();
                    abrirLoginUsuario();
                }else{
                    ParseErros parseErros = new ParseErros();
                    String erro = parseErros.getErro( e.getCode() );
                    Toast.makeText(CadastroActivity.this, erro, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void abrirLoginUsuario(View view){
        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity( intent );
    }

    private void abrirLoginUsuario(){
        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity( intent );
        finish();
    }

}

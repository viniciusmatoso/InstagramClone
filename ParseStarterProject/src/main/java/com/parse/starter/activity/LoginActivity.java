package com.parse.starter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.starter.R;
import com.parse.starter.util.ParseErros;

public class LoginActivity extends AppCompatActivity {

    private EditText usuario;
    private EditText senha;
    private Button logar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario = (EditText) findViewById(R.id.etUsuario);
        senha = (EditText) findViewById(R.id.etSenha);
        logar = (Button) findViewById(R.id.btLogar);


        verificaUsuarioLogado();

        logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginUsuario = usuario.getText().toString();
                String loginSenha = senha.getText().toString();

                verificarLogin(loginUsuario, loginSenha);
            }
        });

    }

    private void verificarLogin(String usuario, String senha){
        ParseUser.logInInBackground(usuario, senha, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {

                if(e == null){
                    Toast.makeText(LoginActivity.this, "Login realizado com sucesso!", Toast.LENGTH_LONG).show();
                    abrirAreaPrincipal();
                }else{
                    ParseErros parseErros = new ParseErros();
                    String erro = parseErros.getErro( e.getCode() );
                    Toast.makeText(LoginActivity.this, "Erro ao realizar login, "
                            + erro, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void verificaUsuarioLogado(){

        if(ParseUser.getCurrentUser() != null){
            //envia o usuario para area principal do app
            abrirAreaPrincipal();
        }
    }

    private void abrirAreaPrincipal(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity( intent );
        finish();
    }

    public void abrirCadastroUsuario(View view){
        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity( intent );
    }

}

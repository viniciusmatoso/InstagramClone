/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.R;
import com.parse.starter.adapter.TabAdapter;
import com.parse.starter.fragments.HomeFragment;
import com.parse.starter.util.SlidingTabLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

  private Toolbar toolbarPrincipal;
  private SlidingTabLayout slidingTabLayout;
  private ViewPager viewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //configura toolbar
    toolbarPrincipal = (Toolbar) findViewById(R.id.toolbar_principal);
    toolbarPrincipal.setLogo( R.drawable.instagramlogo);
    setSupportActionBar( toolbarPrincipal );

    //configura as abas
    slidingTabLayout = (SlidingTabLayout) findViewById(R.id.slidingTabMain);
    viewPager = (ViewPager) findViewById(R.id.viewPagerMain);

    //configurar adapter
    TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager(), this);
    viewPager.setAdapter( tabAdapter );
    slidingTabLayout.setDistributeEvenly(true);
    slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.cinzaEscuro));
    slidingTabLayout.setCustomTabView(R.layout.tab_view, R.id.text_item_tab);
    slidingTabLayout.setViewPager( viewPager );


  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {

    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    switch (item.getItemId() ){
      case R.id.action_sair:
        deslogarUsuario();
        return true;
      case R.id.action_configuracoes:
        return true;
      case R.id.action_compartilhar:
          compartilharFoto();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

    private void compartilharFoto() {

      Intent intent = new Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
      startActivityForResult( intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //testar processo de retorno dos dados
        if(requestCode == 1 && resultCode == RESULT_OK && data != null){

            //recuperar local do recurso
            Uri localImagemSelecionada = data.getData();

            //recupera a imagem do local que foi selecionada
            try {
                Bitmap imagem = MediaStore.Images.Media.getBitmap( getContentResolver(), localImagemSelecionada );

                //comprimir no formato PNG
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imagem.compress(Bitmap.CompressFormat.PNG, 75, stream);

                //cria um array de bytes da imagem
                byte[] byteArray = stream.toByteArray();

                //criar um arquivo com formato proprio do parse
                SimpleDateFormat dateFormat = new SimpleDateFormat("ddmmaaaahhmmss");
                String nomeImagem = dateFormat.format( new Date() );
                ParseFile arquivoParse = new ParseFile(nomeImagem + "imagem.png", byteArray);

                //monta o objeto para salvar no parse
                ParseObject parseObject = new ParseObject("Imagem");
                parseObject.put("username", ParseUser.getCurrentUser().getUsername() );
                parseObject.put("imagem", arquivoParse);

                //salvar os dados
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if(e == null){

                            TabAdapter adapterNovo = (TabAdapter) viewPager.getAdapter();
                            HomeFragment homeFragmentNovo = (HomeFragment) adapterNovo.getFragment(0);
                            homeFragmentNovo.atualizaPostagens();

                        }else{

                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void deslogarUsuario(){
      ParseUser.logOut();
      Intent intent = new Intent(this, LoginActivity.class);
      startActivity( intent );
  }
}

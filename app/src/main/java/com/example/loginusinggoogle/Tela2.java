package com.example.loginusinggoogle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Tela2 extends AppCompatActivity {

    ImageView foto;
    TextView nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela2);

        foto = findViewById(R.id.usuario_imagem);
        nome = findViewById(R.id.usuario_nome);

        String nomeUsuario = getIntent().getStringExtra("username");
        String fotoPerfil = getIntent().getStringExtra("FotoPerfil");

        nome.setText(nomeUsuario);
        Picasso.get().load(fotoPerfil).into(foto);
    }
}
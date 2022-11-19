package com.example.loginusinggoogle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.loginusinggoogle.Models.Users;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button botao;
    private GoogleSignInClient client;
    FirebaseAuth auth;
    FirebaseDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botao = findViewById(R.id.botao_google);
        auth = FirebaseAuth.getInstance();
        //por ter sido configurado com banco de testes ele ficará disponível durante 30 dias a partir do dia 19/11/2022
        bd = FirebaseDatabase.getInstance("https://loginusing-a8af5-default-rtdb.firebaseio.com/");

        GoogleSignInOptions opcoes = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        client = GoogleSignIn.getClient(this, opcoes);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = client.getSignInIntent();

                startActivityForResult(i, 123);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount conta = task.getResult(ApiException.class);

                AuthCredential credential = GoogleAuthProvider.getCredential(conta.getIdToken(), null);

                auth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        if (task.isSuccessful()){
                            FirebaseUser user = auth.getCurrentUser();

                            Users usuarios1 = new Users();

                            assert usuarios1 != null;
                            usuarios1.setUserId(user.getUid());
                            usuarios1.setUserName(user.getDisplayName());
                            usuarios1.setProfilePic(user.getPhotoUrl().toString());

                            bd.getReference().child("Users").child(user.getUid()).setValue(usuarios1);

                            Intent it = new Intent(MainActivity.this, Tela2.class);
                            it.putExtra("username",user.getDisplayName());
                            it.putExtra("FotoPerfil",user.getPhotoUrl().toString());

                            startActivity(it);
                        }else{

                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } catch (ApiException e) {
                e.printStackTrace();
            }

        }
    }
}
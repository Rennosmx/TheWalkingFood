package imd.ufrn.br.thewalkingfood;



import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private ProgressBar barraProgresso;
    private int tempoProgresso = 0;

    private VideoView video;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this.getApplicationContext());

        Log.d("Auau", "Ligou");
        printHashKey();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            FirebaseAuth.getInstance().signOut();
        }


        RelativeLayout activitymain = (RelativeLayout) findViewById(R.id.activity_main);

        video = (VideoView) findViewById(R.id.opening_video);

        Uri videouri = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.logo);

        video.setVideoURI(videouri);

        video.setMediaController(new MediaController(this));
        video.setVideoURI(Uri.parse("android.resource://" +getPackageName()+ "/" +R.raw.logo));
        video.requestFocus();



        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                video.setBackgroundColor(Color.TRANSPARENT);
                video.start();
            }
        });

        activitymain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressoFinalizado();
            }
        });

        //carregar();
    }

    //Simulando o carregamento da Tela de Perfil
    public void carregar(){
           final Thread thread = new Thread(){

            @Override
            public void run() {
                Random rand = new Random();
                try {
                    while( tempoProgresso < barraProgresso.getMax() ){
                        sleep(40);
                        tempoProgresso += rand.nextInt(5) + 1;
                        atualizarProgresso(tempoProgresso);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progressoFinalizado();
            }
        };
        thread.start();
    }

    public void atualizarProgresso(int valor){
        barraProgresso.setProgress(valor);
    }

    public void progressoFinalizado(){
        Intent intent = new Intent(MainActivity.this, SelecaoPerfilActivity.class);
        startActivity(intent);
        finish();
    }

    public void printHashKey() {

        try {

            PackageInfo info = getPackageManager().getPackageInfo("pit.feat", PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("Key", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

}
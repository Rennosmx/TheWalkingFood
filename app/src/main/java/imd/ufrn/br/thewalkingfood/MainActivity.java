package imd.ufrn.br.thewalkingfood;



import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private ProgressBar barraProgresso;
    private int tempoProgresso = 0;

    private VideoView video;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout activitymain = (RelativeLayout) findViewById(R.id.activity_main);

        video = (VideoView) findViewById(R.id.opening_video);

        Uri videouri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.logo);

        video.setVideoURI(videouri);


        video.setBackgroundColor(Color.parseColor("#ffe26f"));
        video.setZOrderOnTop(true);
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

}
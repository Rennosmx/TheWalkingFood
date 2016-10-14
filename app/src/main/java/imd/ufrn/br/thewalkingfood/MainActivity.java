package imd.ufrn.br.thewalkingfood;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private ProgressBar barraProgresso;
    private int tempoProgresso = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        barraProgresso = (ProgressBar)findViewById(R.id.barraDeProgresso);
        carregar();
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
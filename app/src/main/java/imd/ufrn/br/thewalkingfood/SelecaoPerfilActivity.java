package imd.ufrn.br.thewalkingfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SelecaoPerfilActivity extends AppCompatActivity {

    ImageButton imgConsumidor, imgAmbulante;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecao_perfil);

        imgConsumidor = (ImageButton)findViewById(R.id.imgConsumidor);
        imgAmbulante = (ImageButton)findViewById(R.id.imgAmbulante);
    }

    public void cadastroAmbulante(View view) {
        Intent intent = new Intent(SelecaoPerfilActivity.this, CadastroAmbulanteActivity.class);
        startActivity(intent);
    }

    public void cadastroConsumidor(View view) {
        Intent intent = new Intent(SelecaoPerfilActivity.this, CadastroConsumidorActivity.class);
        startActivity(intent);
    }


}

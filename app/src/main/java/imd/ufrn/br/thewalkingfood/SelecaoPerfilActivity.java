package imd.ufrn.br.thewalkingfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;

public class SelecaoPerfilActivity extends AppCompatActivity {


    RadioButton rbVendedor, rbConsumidor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecao_perfil);

        rbVendedor = (RadioButton)findViewById(R.id.rBtnVendedor);
        rbConsumidor = (RadioButton)findViewById(R.id.rBtnConsumidor);
    }


    public void telaCadastro(View view) {

        if(rbVendedor.isChecked()){
            Intent intent = new Intent(SelecaoPerfilActivity.this, CadastroVendedorActivity.class);
            startActivity(intent);
            finish();
        }
        if(rbConsumidor.isChecked()){
            Intent intent = new Intent(SelecaoPerfilActivity.this, CadastroConsumidorActivity.class);
            startActivity(intent);
            finish();
        }
    }
}

package imd.ufrn.br.thewalkingfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CadastroAmbulanteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_ambulante);
    }


    public void adicionarProduto(View view) {
        Intent intent = new Intent(this, CadastroProdutoActivity.class);
        startActivity(intent);
    }

    public void finalizarCadastro(View view) {
        Intent intent = new Intent(this, TelaInicialAmbulanteActivity.class);
        startActivity(intent);
    }
}

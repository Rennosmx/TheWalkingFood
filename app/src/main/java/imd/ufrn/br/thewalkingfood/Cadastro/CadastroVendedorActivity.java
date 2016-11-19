package imd.ufrn.br.thewalkingfood.Cadastro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import imd.ufrn.br.thewalkingfood.R;
import imd.ufrn.br.thewalkingfood.VendedorActivity;

public class CadastroVendedorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_vendedor);
    }


    public void adicionarProduto(View view) {
        Intent intent = new Intent(this, CadastroProdutoActivity.class);
        startActivity(intent);
    }

    public void finalizarCadastro(View view) {
        Intent intent = new Intent(this, VendedorActivity.class);
        startActivity(intent);
        finish();
    }
}
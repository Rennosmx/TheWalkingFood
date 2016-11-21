package imd.ufrn.br.thewalkingfood.Cliente;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import imd.ufrn.br.thewalkingfood.Cliente.Fragments.DetalhesVendedorFeedFragment;
import imd.ufrn.br.thewalkingfood.Cliente.Fragments.DetalhesVendedorProdutosFragment;
import imd.ufrn.br.thewalkingfood.R;

public class TelaDetalhesVendedorActivity extends AppCompatActivity {

    BottomBar bottomBar;

    String idVendedor;
    String idConsumidor;

    DetalhesVendedorProdutosFragment detalhesVendedorProdutosFragment;
    DetalhesVendedorFeedFragment detalhesVendedorFeedFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_detalhes_vendedor);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        idVendedor = bundle.get("idVendedor").toString();
        idConsumidor = bundle.get("idConsumidor").toString();

        detalhesVendedorProdutosFragment = new DetalhesVendedorProdutosFragment();
        detalhesVendedorFeedFragment = new DetalhesVendedorFeedFragment();

        Toast.makeText(TelaDetalhesVendedorActivity.this, idVendedor,
                Toast.LENGTH_LONG).show();
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.tela_detalhes_vendedor_BottomBarContainer, detalhesVendedorProdutosFragment);
        fragmentTransaction.commit();


        bottomBar = (BottomBar) findViewById(R.id.tela_detalhes_vendedor_BottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();

                if (tabId == R.id.bottombar_tabs_detalhes_vendedor_produtos) {
                    fragmentTransaction1.replace(R.id.tela_detalhes_vendedor_BottomBarContainer, detalhesVendedorProdutosFragment);

                }
                else if(tabId == R.id.bottombar_tabs_detalhes_vendedor_chats){
                    //fragmentTransaction1.replace(R.id.tela_detalhes_vendedor_BottomBarContainer, vendedoresFragment);

                }
                else if(tabId == R.id.bottombar_tabs_detalhes_vendedor_feed){
                    fragmentTransaction1.replace(R.id.tela_detalhes_vendedor_BottomBarContainer, detalhesVendedorFeedFragment);
                }

                fragmentTransaction1.addToBackStack(null);
                fragmentTransaction1.commit();
            }
        });


    }

    @Override
    public void onBackPressed()
    {
        // super.onBackPressed(); Do not call me!
        goToTelaInicialVendedor();
        // Go to the previous web page.
    }

    public void goToTelaInicialVendedor(){
        Intent intent = new Intent(TelaDetalhesVendedorActivity.this, TelaInicialConsumidorActivity.class);
        startActivity(intent);
        finish();
    }

    public String getIdVendedor() {
        return idVendedor;
    }

    public String getIdConsumidor() {
        return idConsumidor;
    }
}

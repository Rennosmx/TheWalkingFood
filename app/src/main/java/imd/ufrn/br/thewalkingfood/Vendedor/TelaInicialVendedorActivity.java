package imd.ufrn.br.thewalkingfood.Vendedor;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import imd.ufrn.br.thewalkingfood.Cliente.Fragments.ConsumidorFeedFragment;
import imd.ufrn.br.thewalkingfood.Cliente.Fragments.ListaVendedoresFragment;
import imd.ufrn.br.thewalkingfood.Cliente.TelaInicialConsumidorActivity;
import imd.ufrn.br.thewalkingfood.R;
import imd.ufrn.br.thewalkingfood.Vendedor.Fragments.VendedorFeedFragment;
import imd.ufrn.br.thewalkingfood.Vendedor.Fragments.VendedorProdutosFragment;

public class TelaInicialVendedorActivity extends AppCompatActivity {

    BottomBar bottomBar;

    String idVendedor;

    VendedorFeedFragment vendedorFeedFragment;

    VendedorProdutosFragment vendedorProdutosFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial_vendedor);

        vendedorFeedFragment = new VendedorFeedFragment();
        vendedorProdutosFragment = new VendedorProdutosFragment();


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        idVendedor = firebaseUser.getUid();



        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.tela_inicial_vendedor_BottomBarContainer, vendedorProdutosFragment);
        fragmentTransaction.commit();


        bottomBar = (BottomBar) findViewById(R.id.tela_inicial_vendedor_BottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();

                if (tabId == R.id.bottombar_tabs_tela_inicial_vendedor_produtos) {
                   fragmentTransaction1.replace(R.id.tela_inicial_vendedor_BottomBarContainer, vendedorProdutosFragment);

                }
                else if(tabId == R.id.bottombar_tabs_tela_inicial_vendedor_chats){
                    //fragmentTransaction1.replace(R.id.tela_inicial_vendedor_BottomBarContainer, vendedoresFragment);

                }
                else if(tabId == R.id.bottombar_tabs_tela_inicial_vendedor_feed){
                    fragmentTransaction1.replace(R.id.tela_inicial_vendedor_BottomBarContainer, vendedorFeedFragment);
                }
                fragmentTransaction1.addToBackStack(null);
                fragmentTransaction1.commit();
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();



    }





    public void goToTelaInicialConsumidor(){
        Intent intent = new Intent(TelaInicialVendedorActivity.this, TelaInicialConsumidorActivity.class);
        startActivity(intent);
        finish();
    }
}

package imd.ufrn.br.thewalkingfood.Vendedor;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import imd.ufrn.br.thewalkingfood.Cadastro.CadastroVendedorActivity;
import imd.ufrn.br.thewalkingfood.Cliente.Fragments.ConsumidorFeedFragment;
import imd.ufrn.br.thewalkingfood.Cliente.Fragments.ListaVendedoresFragment;
import imd.ufrn.br.thewalkingfood.Cliente.TelaInicialConsumidorActivity;
import imd.ufrn.br.thewalkingfood.R;
import imd.ufrn.br.thewalkingfood.Vendedor.Fragments.VendedorFeedFragment;
import imd.ufrn.br.thewalkingfood.Vendedor.Fragments.VendedorProdutosFragment;

public class TelaInicialVendedorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    BottomBar bottomBar;

    String idVendedor;

    VendedorFeedFragment vendedorFeedFragment;

    VendedorProdutosFragment vendedorProdutosFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_lateral_vendedor);

        //Inicialização da ToolBar da Tela Inicial Vendedor com o icone do menu lateral
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_vendedor);
        setSupportActionBar(toolbar);

        //Inicializando Layout do Menu Lateral
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.menu_lateral_vendedor);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_vendedor);
        navigationView.setNavigationItemSelectedListener(this);


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

    //Metodo para Fechar Menu Lateral em caso de apertar botão de voltar
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.menu_lateral_vendedor);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //Metodo que trata evento de clique e redireciona para a tela
    //que corresponde ao item do menu clicado
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_produtos) {
            bottomBar.selectTabAtPosition(0);
        } else if (id == R.id.nav_chat) {
            bottomBar.selectTabAtPosition(1);
        } else if (id == R.id.nav_feed) {
            bottomBar.selectTabAtPosition(2);
        } else if (id == R.id.nav_edit_perfil) {
            Intent intent = new Intent(this, CadastroVendedorActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_add_produto) {
            Intent intent = new Intent(this, VendedorAddProdutoActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_add_feed) {
            Intent intent = new Intent(this, VendedorAddFeedActivity.class);
            startActivity(intent);
        }

        //Após item ser escolhido menu lateral é fechado
        DrawerLayout drawer1 = (DrawerLayout) findViewById(R.id.menu_lateral_vendedor);
        drawer1.closeDrawer(GravityCompat.START);
        return true;
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
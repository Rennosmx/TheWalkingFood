package imd.ufrn.br.thewalkingfood.Cliente;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import imd.ufrn.br.thewalkingfood.ChatScreenActivity;
import imd.ufrn.br.thewalkingfood.Cliente.Fragments.ConsumidorFeedFragment;
import imd.ufrn.br.thewalkingfood.Cliente.Fragments.ListaVendedoresFragment;
import imd.ufrn.br.thewalkingfood.Manifest;
import imd.ufrn.br.thewalkingfood.R;
import imd.ufrn.br.thewalkingfood.Vendedor.TelaInicialVendedorActivity;

public class TelaInicialConsumidorActivity extends AppCompatActivity implements
        OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener{


    private BottomBar bottomBar;

    private String idConsumidor;

    private ImageView imgBicycle;

    private ListaVendedoresFragment vendedoresFragment;

    private SupportMapFragment mapFragment;

    private ConsumidorFeedFragment consumidorFeedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_lateral_consumidor);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.menu_lateral_consumidor);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_consumidor);
        navigationView.setNavigationItemSelectedListener(this);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        idConsumidor = firebaseUser.getUid();

        //Create the fragments that will be used in this activity
        vendedoresFragment = new ListaVendedoresFragment();
        mapFragment = SupportMapFragment.newInstance();
        consumidorFeedFragment = new ConsumidorFeedFragment();

        //code from : https://github.com/roughike/BottomBar
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //Initialize with this fragment
        fragmentTransaction.add(R.id.tela_inicial_consumidor_BottomBarContainer, mapFragment);
        fragmentTransaction.commit();



        bottomBar = (BottomBar) findViewById(R.id.tela_inicial_consumidor_BottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();

                if (tabId == R.id.tab_mapa_nearby) {
                    fragmentTransaction1.replace(R.id.tela_inicial_consumidor_BottomBarContainer, mapFragment);

                }
                else if(tabId == R.id.tab_vendedores){
                    fragmentTransaction1.replace(R.id.tela_inicial_consumidor_BottomBarContainer, vendedoresFragment);

                }
                else if(tabId == R.id.tab_chats){

                }
                else if(tabId == R.id.tab_feed){
                    fragmentTransaction1.replace(R.id.tela_inicial_consumidor_BottomBarContainer, consumidorFeedFragment);
                }
                fragmentTransaction1.addToBackStack(null);
                fragmentTransaction1.commit();
            }
        });

        mapFragment.getMapAsync(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.menu_lateral_consumidor);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_map) {
            bottomBar.selectTabAtPosition(0);
        } else if (id == R.id.nav_vendedores) {
            bottomBar.selectTabAtPosition(1);
        } else if (id == R.id.nav_chat) {
            bottomBar.selectTabAtPosition(2);
        } else if (id == R.id.nav_feed) {
            bottomBar.selectTabAtPosition(3);
        }

        DrawerLayout drawer1 = (DrawerLayout) findViewById(R.id.menu_lateral_consumidor);
        drawer1.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    public void goToMamaProfile(){
        Intent intent = new Intent(TelaInicialConsumidorActivity.this, TelaInicialVendedorActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToJimmyChat(){
        Intent intent = new Intent(TelaInicialConsumidorActivity.this, ChatScreenActivity.class);
        startActivity(intent);
        finish();
    }

    // Everything that has to be done with the map fragment, is done in this function onMapReady
    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }
    }

    public void goToDetalhesVendedor(String id, String idC){
        Intent intent = new Intent(TelaInicialConsumidorActivity.this, TelaDetalhesVendedorActivity.class);
        intent.putExtra("idVendedor", id);
        intent.putExtra("idConsumidor", idC);
        startActivity(intent);
        finish();
    }

}

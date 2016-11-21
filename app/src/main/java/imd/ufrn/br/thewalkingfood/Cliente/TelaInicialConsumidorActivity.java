package imd.ufrn.br.thewalkingfood.Cliente;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import imd.ufrn.br.thewalkingfood.ChatScreenActivity;
import imd.ufrn.br.thewalkingfood.Cliente.Fragments.ConsumidorFeedFragment;
import imd.ufrn.br.thewalkingfood.Cliente.Fragments.ListaVendedoresFragment;
import imd.ufrn.br.thewalkingfood.R;
import imd.ufrn.br.thewalkingfood.Vendedor.TelaInicialVendedorActivity;

public class TelaInicialConsumidorActivity extends AppCompatActivity implements OnMapReadyCallback{

    private BottomBar bottomBar;


    private String idConsumidor;

    private ListaVendedoresFragment vendedoresFragment;

    private SupportMapFragment mapFragment;

    private ConsumidorFeedFragment consumidorFeedFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial_consumidor);

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

    }

    public void goToDetalhesVendedor(String id, String idC){
        Intent intent = new Intent(TelaInicialConsumidorActivity.this, TelaDetalhesVendedorActivity.class);
        intent.putExtra("idVendedor", id);
        intent.putExtra("idConsumidor", idC);
        startActivity(intent);
        finish();
    }

}

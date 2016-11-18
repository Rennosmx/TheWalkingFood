package imd.ufrn.br.thewalkingfood;

import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class TelaInicialConsumidorActivity extends AppCompatActivity implements OnMapReadyCallback{

    private BottomBar bottomBar;



    private TelaInicialConsumidorMapaFragment mapaFragment;
    private ListaVendedoresFragment vendedoresFragment;

    private SupportMapFragment mapFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial_consumidor);

        mapaFragment = new TelaInicialConsumidorMapaFragment();
        vendedoresFragment = new ListaVendedoresFragment();


        mapFragment = SupportMapFragment.newInstance();



        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.tela_inicial_consumidor_BottomBarContainer, mapaFragment);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}

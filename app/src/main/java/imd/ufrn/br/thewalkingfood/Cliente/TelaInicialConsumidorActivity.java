package imd.ufrn.br.thewalkingfood.Cliente;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
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
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import imd.ufrn.br.thewalkingfood.ChatScreenActivity;
import imd.ufrn.br.thewalkingfood.Cliente.Fragments.ConsumidorFeedFragment;
import imd.ufrn.br.thewalkingfood.Cliente.Fragments.ListaVendedoresFragment;
import imd.ufrn.br.thewalkingfood.Common.SelecaoPerfilActivity;
import imd.ufrn.br.thewalkingfood.Manifest;
import imd.ufrn.br.thewalkingfood.R;
import imd.ufrn.br.thewalkingfood.Vendedor.TelaInicialVendedorActivity;

public class TelaInicialConsumidorActivity
        extends AppCompatActivity
        implements
        OnMapReadyCallback,
        NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    private BottomBar bottomBar;

    private String idConsumidor;

    private ImageView imgBicycle;

    private ListaVendedoresFragment vendedoresFragment;

    private SupportMapFragment mapFragment;

    private ConsumidorFeedFragment consumidorFeedFragment;

    //Variáveis necessárias para setar marcador na localização do dispositivo
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private MarkerOptions markerOptionsCurrent;
    private Marker markerCurrent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_lateral_consumidor);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Inicialização da Aba Lateral
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

        // Instanciando o GoogleApiClient, caso seja nulo
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this) // Interface ConnectionCallbacks
                    .addOnConnectionFailedListener(this) //Interface OnConnectionFailedListener
                    .addApi(LocationServices.API) // Vamos a API do LocationServices
                    .build();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.menu_lateral_consumidor);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            FirebaseAuth.getInstance().signOut();
            goToSelecaoPerfil();
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
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
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

            mMap = googleMap;
            mMap.setMyLocationEnabled(true);

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

    public void goToSelecaoPerfil(){
        Intent intent = new Intent(TelaInicialConsumidorActivity.this, SelecaoPerfilActivity.class);

        startActivity(intent);
        finish();
    }

    public Bitmap resizeMapIcons(String iconName,int width, int height) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;

        if (markerCurrent != null){
            markerCurrent.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        markerOptionsCurrent = new MarkerOptions();
        markerOptionsCurrent.position(latLng);
        markerOptionsCurrent.icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("mapa_consumidor",125,125)));

        markerCurrent = mMap.addMarker(markerOptionsCurrent);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
// pegamos a ultima localização
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            if(mMap != null){

                // Criamos o LatLng através do Location
                LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                // Adicionamos um Marker com a posição...
                markerCurrent = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory
                                .fromBitmap(resizeMapIcons("mapa_consumidor",125,125))));
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

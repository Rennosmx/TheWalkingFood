package imd.ufrn.br.thewalkingfood;

import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class TelaInicialConsumidorActivity extends AppCompatActivity {

    TabHost tabelas;
    TabHost.TabSpec tb;
    TextView textoVinho, textoBranco;

    ImageView chatSim;
    ImageView mamaProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial_consumidor);


        chatSim = (ImageView) findViewById(R.id.tela_inicial_consumidor_chat1);
        chatSim.setClickable(true);
        chatSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToJimmyChat();
            }
        });

        mamaProfile = (ImageView) findViewById(R.id.mama_ImageView);
        mamaProfile.setClickable(true);
        mamaProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    goToMamaProfile();
            }
        });

        tabelas = (TabHost) findViewById(R.id.tabelasConsumidor);
        tabelas.setup();

        tb = tabelas.newTabSpec("MAPA");
        tb.setContent(R.id.tab1);
        tb.setIndicator("MAPA");
        tabelas.addTab(tb);
        ViewGroup.LayoutParams params2 = tabelas.getTabWidget().getChildAt(0).getLayoutParams();
        params2.width = 20;

        tabelas.getTabWidget().getChildAt(0).setLayoutParams(params2);

        tb = tabelas.newTabSpec("VENDEDORES");

        tb.setContent(R.id.tab2);
        tb.setIndicator("VENDEDORES");

        tabelas.addTab(tb);
        ViewGroup.LayoutParams params = tabelas.getTabWidget().getChildAt(1).getLayoutParams();
        params.width = 140;

        tabelas.getTabWidget().getChildAt(1).setLayoutParams(params);


        tb = tabelas.newTabSpec("CHATS");
        tb.setContent(R.id.tab3);
        tb.setIndicator("CHATS");
        tabelas.addTab(tb);

        ViewGroup.LayoutParams params3 = tabelas.getTabWidget().getChildAt(2).getLayoutParams();
        params3.width = 30;

        tabelas.getTabWidget().getChildAt(2).setLayoutParams(params3);

        tb = tabelas.newTabSpec("FEED");
        tb.setContent(R.id.tab4);
        tb.setIndicator("FEED");
        tabelas.addTab(tb);

        tabelas.getTabWidget().getChildAt(tabelas.getCurrentTab()).setBackgroundColor(Color.parseColor("#FFFFFF"));

        textoVinho = (TextView) tabelas.getTabWidget().getChildAt(tabelas.getCurrentTab()).findViewById(android.R.id.title);
        textoVinho.setTextColor(Color.parseColor("#A7425C"));
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Metodo para atualizar coloração de texto e background das abas
        tabelas.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            public void onTabChanged(String arg0) {
                for (int i = 0; i < tabelas.getTabWidget().getChildCount(); i++) {
                    tabelas.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#A7425C"));

                    textoBranco = (TextView) tabelas.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                    textoBranco.setTextColor(Color.parseColor("#FFFFFF"));
                }
                tabelas.getTabWidget().getChildAt(tabelas.getCurrentTab()).setBackgroundColor(Color.parseColor("#FFFFFF"));

                textoVinho = (TextView) tabelas.getTabWidget().getChildAt(tabelas.getCurrentTab()).findViewById(android.R.id.title);
                textoVinho.setTextColor(Color.parseColor("#A7425C"));
            }
        });
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
}

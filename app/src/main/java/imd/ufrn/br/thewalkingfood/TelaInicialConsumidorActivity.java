package imd.ufrn.br.thewalkingfood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

public class TelaInicialConsumidorActivity extends AppCompatActivity {

    TabHost tabelas;
    TabHost.TabSpec tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial_consumidor);

        tabelas = (TabHost) findViewById(R.id.tabelasConsumidor);
        tabelas.setup();

        tb = tabelas.newTabSpec("MAPA");
        tb.setContent(R.id.tab1);
        tb.setIndicator("MAPA");
        tabelas.addTab(tb);

        tb = tabelas.newTabSpec("VENDEDORES");
        tb.setContent(R.id.tab2);
        tb.setIndicator("VENDEDORES");
        tabelas.addTab(tb);

        tb = tabelas.newTabSpec("CHAT");
        tb.setContent(R.id.tab3);
        tb.setIndicator("CHAT");
        tabelas.addTab(tb);

        tb = tabelas.newTabSpec("FEED");
        tb.setContent(R.id.tab4);
        tb.setIndicator("FEED");
        tabelas.addTab(tb);

    }


}

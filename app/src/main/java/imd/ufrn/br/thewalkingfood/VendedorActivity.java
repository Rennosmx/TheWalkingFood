package imd.ufrn.br.thewalkingfood;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

public class VendedorActivity extends AppCompatActivity {

    TabHost tabelas;
    TabHost.TabSpec tb;
    TextView textoVinho, textoBranco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendedor);

        tabelas = (TabHost) findViewById(R.id.tabelasVendedor);
        tabelas.setup();

        tb = tabelas.newTabSpec("PEDIDOS");
        tb.setContent(R.id.tab1);
        tb.setIndicator("PEDIDOS");
        tabelas.addTab(tb);
        ViewGroup.LayoutParams params2 = tabelas.getTabWidget().getChildAt(0).getLayoutParams();
        params2.width = 50;

        tabelas.getTabWidget().getChildAt(0).setLayoutParams(params2);

        tb = tabelas.newTabSpec("PRODUTOS");

        tb.setContent(R.id.tab2);
        tb.setIndicator("PRODUTOS");

        tabelas.addTab(tb);
        ViewGroup.LayoutParams params = tabelas.getTabWidget().getChildAt(1).getLayoutParams();
        params.width = 100;

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
}

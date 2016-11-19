package imd.ufrn.br.thewalkingfood;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

import imd.ufrn.br.thewalkingfood.Cliente.TelaInicialConsumidorActivity;

public class TelaInformacoesVendedorActivity extends AppCompatActivity {

    TabHost tabelas;
    TabHost.TabSpec tb;
    TextView textoVinho, textoBranco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_informacoes_vendedor);

        tabelas = (TabHost) findViewById(R.id.tabelasAmbulante);
        tabelas.setup();

        //Inclusão de abas no TabHost
        tb = tabelas.newTabSpec("PRODUTOS");
        tb.setContent(R.id.tab1);
        tb.setIndicator("PRODUTOS");
        tabelas.addTab(tb);

        tb = tabelas.newTabSpec("CHAT");
        tb.setContent(R.id.tab2);
        tb.setIndicator("CHAT");
        tabelas.addTab(tb);

        tb = tabelas.newTabSpec("FEED");
        tb.setContent(R.id.tab3);
        tb.setIndicator("FEED");
        tabelas.addTab(tb);

        //Coloração Inicial da Aba selecionada por padrão(MAPA)
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

    @Override
    public void onBackPressed()
    {
        // super.onBackPressed(); Do not call me!
        goToTelaInicialConsumidor();
        // Go to the previous web page.
    }

    public void goToTelaInicialConsumidor(){
        Intent intent = new Intent(TelaInformacoesVendedorActivity.this, TelaInicialConsumidorActivity.class);
        startActivity(intent);
        finish();

    }

}
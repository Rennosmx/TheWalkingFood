package imd.ufrn.br.thewalkingfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ChatScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
    }

    @Override
    public void onBackPressed()
    {
        // super.onBackPressed(); Do not call me!
        goToTelaInicialConsumidor();
        // Go to the previous web page.
    }

    public void goToTelaInicialConsumidor(){
        Intent intent = new Intent(ChatScreenActivity.this, TelaInicialConsumidorActivity.class);
        startActivity(intent);
        finish();

    }
}

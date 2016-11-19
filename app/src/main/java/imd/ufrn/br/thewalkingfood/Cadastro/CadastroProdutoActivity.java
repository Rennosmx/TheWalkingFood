package imd.ufrn.br.thewalkingfood.Cadastro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import imd.ufrn.br.thewalkingfood.R;

public class CadastroProdutoActivity extends AppCompatActivity {

    ImageButton fotoProduto;
    private static final int FOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);

        fotoProduto = (ImageButton) findViewById(R.id.imgProduto);
        fotoProduto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, FOTO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==FOTO) {
            Bitmap foto = (Bitmap) data.getExtras().get("data");
            fotoProduto.setImageBitmap(foto);
        }
    }

    public void cadastrarProduto(View view) {
        finish();
    }
}

package imd.ufrn.br.thewalkingfood.Vendedor;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import imd.ufrn.br.thewalkingfood.Cliente.TelaDetalhesVendedorActivity;
import imd.ufrn.br.thewalkingfood.Cliente.TelaInicialConsumidorActivity;
import imd.ufrn.br.thewalkingfood.R;
import mehdi.sakout.fancybuttons.FancyButton;

public class VendedorAddProdutoActivity extends AppCompatActivity {

    private static int IMG_RESULT = 1;

    String ImageDecode;

    private FirebaseStorage firebaseStorage;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser firebaseUser;

    private DatabaseReference novoProduto;
    private Intent intent;

    StorageReference vendedorProdutosStorageReference;

    private ImageView imageView;
    private EditText nomeTextView;
    private EditText precoTextView;
    private EditText categoriaTextView;
    private EditText descricaoTextView;

    private Button cadastrarProdutoButton;
    private FancyButton editPhoto;

    private ProgressBar loadingCircle;

    private String idProduto;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendedor_add_produto);


        firebaseStorage = FirebaseStorage.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference produtos = firebaseDatabase.getReference().child("Users").child("Vendedor").child(firebaseUser.getUid()).child("produtos");

        StorageReference root = firebaseStorage.getReference();



        idProduto = produtos.push().getKey();

        novoProduto = firebaseDatabase.getReference().child("Users").child("Vendedor").child(firebaseUser.getUid()).child("produtos").child(idProduto);


        vendedorProdutosStorageReference = root.child("Users").child("Vendedor").child(firebaseUser.getUid()).child("produtos").child(idProduto);


        imageView = (ImageView) findViewById(R.id.vendedor_add_produto_ImageView);
        nomeTextView = (EditText) findViewById(R.id.vendedor_add_produto_EditText_Nome);
        precoTextView = (EditText) findViewById(R.id.vendedor_add_produto_EditText_Preco);
        categoriaTextView = (EditText) findViewById(R.id.vendedor_add_produto_EditText_Categoria);
        descricaoTextView = (EditText) findViewById(R.id.vendedor_add_produto_descricaoEditText);
        cadastrarProdutoButton = (Button) findViewById(R.id.vendedor_add_produto_Button);
        editPhoto = (FancyButton) findViewById(R.id.vendedor_add_produto_editPhotoButton);
        loadingCircle = (ProgressBar) findViewById(R.id.vendedor_add_produto_ProgressBar);
        loadingCircle.setVisibility(View.INVISIBLE);

        editPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, IMG_RESULT);
            }
        });

        cadastrarProdutoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingCircle.setVisibility(View.VISIBLE);
                novoProduto.child("nome").setValue(nomeTextView.getText().toString());
                novoProduto.child("preco").setValue(precoTextView.getText().toString());
                novoProduto.child("categoria").setValue(categoriaTextView.getText().toString());
                novoProduto.child("descricao").setValue(descricaoTextView.getText().toString());



                Uri file = Uri.fromFile(new File(ImageDecode));
                fileName = getFileName(file);

                UploadTask uploadTask = vendedorProdutosStorageReference.putFile(file);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("STORAGE TRY", "DEU MERDA");
                        novoProduto.child("photo-url").setValue("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSDs0jc74KLJuKswxa1lAfEba6sPxIjRtF3EDI4SCD5xzHrn6YH");
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("STORAGE TRY", "DEU CERTOOOOOO");
                        novoProduto.child("photo-url").setValue(taskSnapshot.getDownloadUrl().toString());
                        goToTelaInicialVendedor();
                    }
                });





            }
        });








    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == IMG_RESULT && resultCode == RESULT_OK
                    && null != data) {


                Uri URI = data.getData();
                String[] FILE = { MediaStore.Images.Media.DATA };


                Cursor cursor = getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                ImageDecode = cursor.getString(columnIndex);
                cursor.close();


                Glide.with(this.getApplicationContext()).load(ImageDecode).centerCrop().into(imageView);
                Log.d("IMAGE DECODE", ImageDecode);

            }
        } catch (Exception e) {
            Toast.makeText(this, "Please try again", Toast.LENGTH_LONG)
                    .show();
        }

    }


    @Override
    public void onBackPressed()
    {
        // super.onBackPressed(); Do not call me!
        novoProduto.removeValue();
        goToTelaInicialVendedor();
        // Go to the previous web page.
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public void goToTelaInicialVendedor(){
        Intent intent = new Intent(VendedorAddProdutoActivity.this, TelaInicialVendedorActivity.class);
        startActivity(intent);
        finish();
    }




}

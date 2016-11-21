package imd.ufrn.br.thewalkingfood.Vendedor;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import imd.ufrn.br.thewalkingfood.R;
import mehdi.sakout.fancybuttons.FancyButton;

public class VendedorEditProdutoActivity extends AppCompatActivity {

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
    private Button removerProdutoButton;
    private FancyButton editPhoto;

    private ProgressBar loadingCircle;

    private String idProduto;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendedor_edit_produto);

        firebaseStorage = FirebaseStorage.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference produtos = firebaseDatabase.getReference().child("Users").child("Vendedor").child(firebaseUser.getUid()).child("produtos");

        StorageReference root = firebaseStorage.getReference();

        Intent intent1 = getIntent();
        Bundle bundle = intent1.getExtras();


        idProduto = bundle.get("idProduto").toString();

        novoProduto = firebaseDatabase.getReference().child("Users").child("Vendedor").child(firebaseUser.getUid()).child("produtos").child(idProduto);


        vendedorProdutosStorageReference = root.child("Users").child("Vendedor").child(firebaseUser.getUid()).child("produtos").child(idProduto);


        imageView = (ImageView) findViewById(R.id.vendedor_edit_produto_ImageView);
        nomeTextView = (EditText) findViewById(R.id.vendedor_edit_produto_EditText_Nome);
        precoTextView = (EditText) findViewById(R.id.vendedor_edit_produto_EditText_Preco);
        categoriaTextView = (EditText) findViewById(R.id.vendedor_edit_produto_EditText_Categoria);
        descricaoTextView = (EditText) findViewById(R.id.vendedor_edit_produto_descricaoEditText);
        cadastrarProdutoButton = (Button) findViewById(R.id.vendedor_edit_produto_Button);
        editPhoto = (FancyButton) findViewById(R.id.vendedor_edit_produto_editPhotoButton);
        removerProdutoButton = (Button) findViewById(R.id.vendedor_edit_produto_removerButton);
        loadingCircle = (ProgressBar) findViewById(R.id.vendedor_edit_produto_ProgressBar);
        loadingCircle.setVisibility(View.INVISIBLE);

        editPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, IMG_RESULT);
            }
        });

        removerProdutoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingCircle.setVisibility(View.VISIBLE);
                novoProduto.removeValue();
                vendedorProdutosStorageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        goToTelaInicialVendedor();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("STORAGE DELETE", "DELETE FALHOU");
                    }
                });
            }
        });


        ValueEventListener novoProdutoListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Glide.with(getApplicationContext()).load(dataSnapshot.child("photo-url").getValue().toString()).centerCrop().into(imageView);
                nomeTextView.setText(dataSnapshot.child("nome").getValue().toString());
                precoTextView.setText(dataSnapshot.child("preco").getValue().toString());
                categoriaTextView.setText(dataSnapshot.child("categoria").getValue().toString());
                descricaoTextView.setText(dataSnapshot.child("descricao").getValue().toString());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        novoProduto.addListenerForSingleValueEvent(novoProdutoListener);


        cadastrarProdutoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingCircle.setVisibility(View.VISIBLE);
                novoProduto.child("nome").setValue(nomeTextView.getText().toString());
                novoProduto.child("preco").setValue(precoTextView.getText().toString());
                novoProduto.child("categoria").setValue(categoriaTextView.getText().toString());
                novoProduto.child("descricao").setValue(descricaoTextView.getText().toString());

                vendedorProdutosStorageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Uri file = Uri.fromFile(new File(ImageDecode));
                        //fileName = getFileName(file);

                        UploadTask uploadTask = vendedorProdutosStorageReference.putFile(file);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("STORAGE TRY", "DEU MERDA");
                                //novoProduto.child("photo-url").setValue("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSDs0jc74KLJuKswxa1lAfEba6sPxIjRtF3EDI4SCD5xzHrn6YH");
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
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("STORAGE", "NAO DEU PRA DELETAR");
                    }
                });



            }
        });


        //DatabaseReference produtoEditReference = firebaseDatabase.getReference().child("Users").child("Vendedor").child(firebaseUser.getUid()).child("produtos").child(idProduto);

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

    public void goToTelaInicialVendedor(){
        Intent intent = new Intent(VendedorEditProdutoActivity.this, TelaInicialVendedorActivity.class);
        startActivity(intent);
        finish();
    }

}

package imd.ufrn.br.thewalkingfood.Vendedor;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;

import imd.ufrn.br.thewalkingfood.R;
import mehdi.sakout.fancybuttons.FancyButton;

import static android.R.attr.data;

public class VendedorEditFeedActivity extends AppCompatActivity {

    private static int IMG_RESULT = 1;
    DatabaseReference novoFeed;

    String ImageDecode;

    private FirebaseStorage firebaseStorage;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser firebaseUser;

    private Intent intent;

    StorageReference vendedorFeedsStorageReference;

    private ImageView imageView;
    private EditText descricaoTextView;
    private EditText dataTextView;

    private Button cadastrarFeedButton;
    private Button removerFeedButton;
    private FancyButton editPhoto;

    private ProgressBar loadingCircle;

    private String idFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendedor_edit_feed);

        firebaseStorage = FirebaseStorage.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        ImageDecode = null;

        DatabaseReference feeds = firebaseDatabase.getReference().child("Users").child("Vendedor").child(firebaseUser.getUid()).child("feed");

        StorageReference root = firebaseStorage.getReference();

        Intent intent1 = getIntent();
        Bundle bundle = intent1.getExtras();



        idFeed = bundle.get("idFeed").toString();

        novoFeed = firebaseDatabase.getReference().child("Users").child("Vendedor").child(firebaseUser.getUid()).child("feed").child(idFeed);


        vendedorFeedsStorageReference = root.child("Users").child("Vendedor").child(firebaseUser.getUid()).child("feed").child(idFeed);


        imageView = (ImageView) findViewById(R.id.vendedor_edit_feed_ImageView);
        descricaoTextView = (EditText) findViewById(R.id.vendedor_edit_feed_descricaoEditText);
        cadastrarFeedButton = (Button) findViewById(R.id.vendedor_edit_feed_Button);
        editPhoto = (FancyButton) findViewById(R.id.vendedor_edit_feed_editPhotoButton);
        removerFeedButton = (Button) findViewById(R.id.vendedor_edit_feed_removerButton);
        loadingCircle.setVisibility(View.INVISIBLE);

        editPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, IMG_RESULT);
            }
        });

        removerFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingCircle.setVisibility(View.VISIBLE);
                novoFeed.removeValue();
                vendedorFeedsStorageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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

        ValueEventListener editFeedListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Glide.with(getApplicationContext()).load(dataSnapshot.child("photo-url").getValue().toString()).centerCrop().into(imageView);
                descricaoTextView.setText(dataSnapshot.child("descricao").getValue().toString());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        novoFeed.addListenerForSingleValueEvent(editFeedListener);

        cadastrarFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ///GET DATA
                loadingCircle.setVisibility(View.VISIBLE);
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
                String data = myFormat.format(calendar.getTime());

                novoFeed.child("descricao").setValue(descricaoTextView.getText().toString());
                novoFeed.child("data").setValue(data);

                if(ImageDecode != null){
                    vendedorFeedsStorageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Uri file = Uri.fromFile(new File(ImageDecode));
                            //fileName = getFileName(file);
                            UploadTask uploadTask = vendedorFeedsStorageReference.putFile(file);
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("STORAGE TRY", "DEU MERDA");
                                    //novoFeed.child("photo-url").setValue("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSDs0jc74KLJuKswxa1lAfEba6sPxIjRtF3EDI4SCD5xzHrn6YH");
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Log.d("STORAGE TRY", "DEU CERTOOOOOO");
                                    novoFeed.child("photo-url").setValue(taskSnapshot.getDownloadUrl().toString());
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
        novoFeed.removeValue();
        goToTelaInicialVendedor();
        // Go to the previous web page.
    }

    public void goToTelaInicialVendedor(){
        Intent intent = new Intent(VendedorEditFeedActivity.this, TelaInicialVendedorActivity.class);
        startActivity(intent);
        finish();
    }

}

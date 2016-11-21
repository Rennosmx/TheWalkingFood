package imd.ufrn.br.thewalkingfood.Cadastro;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import imd.ufrn.br.thewalkingfood.R;
import imd.ufrn.br.thewalkingfood.Cliente.TelaInicialConsumidorActivity;


public class CadastroConsumidorActivity extends AppCompatActivity {

    private EditText userNameEditText;
    private EditText userNumberEditText;
    private ImageView userImageView;
    private Button cadastrarButton;



    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;


    private String name;
    private String uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_consumidor);

        name = "";

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        firebaseUser = firebaseAuth.getCurrentUser();


        //Create a reference to the user Node, in this case the user will be a Consumidor, then its data will
        //be stored under Users/Consumidor Node. And its Identifier will be the Unique Id provided by FirebaseUser
        databaseReference = firebaseDatabase.getReference().child("Users").child("Consumidor").child(firebaseUser.getUid());

        userNameEditText = (EditText) findViewById(R.id.cadastro_consumidor_EditText_Nome);

        userNumberEditText = (EditText) findViewById(R.id.cadastro_consumidor_EditText_Numero);

        userImageView = (ImageView) findViewById(R.id.cadastro_consumidor_ImageView);

        cadastrarButton = (Button) findViewById(R.id.cadastro_consumidor_Button);

        cadastrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCadastrarClick();
            }
        });

        //Create a ValueEventListener. Will be added to the databaseReference. dataShapshot corresponds to the user Node
        // and with that we can write and read from it. In this case we are reading, since its name and photo-url was already
        //set on the previous Activity (SelecaoPerfilActivity)
        ValueEventListener dataListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                name = dataSnapshot.child("nome").getValue().toString();
                uri = dataSnapshot.child("photo-url").getValue().toString();



                userNameEditText.setText(name);
                setUserImage(uri);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        //Adding Listener to the reference. If this isn't done, nothing happens
        databaseReference.addListenerForSingleValueEvent(dataListener);




    }


    public void onCadastrarClick(){
        //Check if Number is empty asks for user to put it
        if(userNumberEditText.getText().toString() == null){
            Toast.makeText(CadastroConsumidorActivity.this, "Insira Seu Número",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            String username;
            String usernumber;

            username = userNameEditText.getText().toString();
            usernumber = userNumberEditText.getText().toString();

            //Set values of nome and numero at the databaseReference
            databaseReference.child("nome").setValue(username);
            databaseReference.child("numero").setValue(usernumber);

            goToTelaInicialConsumidor();
        }
    }

    public void setUserImage(String url){
        Uri uri1 = Uri.parse(url);

        //Picasso.with(this).load("\"https://lh6.googleusercontent.com/-t9kWaYYhDTs/AAAAAAAAAAI/AAAAAAAAAAA/AKTaeK-TFvX7boI-PhySCesDH24bISiatQ/s96-c/photo.jpg\"").into(userImageView);
        //Loads the image from the url provided into the ImageView
        Glide.with(this).load(uri1).centerCrop().into(userImageView);

    }


    public void goToTelaInicialConsumidor() {
        Intent intent = new Intent(this, TelaInicialConsumidorActivity.class);
        startActivity(intent);
        finish();
    }


}

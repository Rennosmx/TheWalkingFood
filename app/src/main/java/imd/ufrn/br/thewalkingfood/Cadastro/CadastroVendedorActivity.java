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

import imd.ufrn.br.thewalkingfood.Cliente.TelaInicialConsumidorActivity;
import imd.ufrn.br.thewalkingfood.R;
import imd.ufrn.br.thewalkingfood.Vendedor.TelaInicialVendedorActivity;

public class CadastroVendedorActivity extends AppCompatActivity {

    private EditText vendedorNameEditText;
    private EditText vendedorNumberEditText;
    private EditText vendedorDescricaoEditText;
    private ImageView vendedorImageView;
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
        setContentView(R.layout.activity_cadastro_vendedor);

        name = "";

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        firebaseUser = firebaseAuth.getCurrentUser();


        //Create a reference to the user Node, in this case the user will be a Vendedor, then its data will
        //be stored under Users/Vendedor Node. And its Identifier will be the Unique Id provided by FirebaseUser
        databaseReference = firebaseDatabase.getReference().child("Users").child("Vendedor").child(firebaseUser.getUid());


        vendedorNameEditText = (EditText) findViewById(R.id.cadastro_vendedor_EditText_Nome);

        vendedorNumberEditText = (EditText) findViewById(R.id.cadastro_vendedor_EditText_Numero);

        vendedorDescricaoEditText = (EditText) findViewById(R.id.cadastro_vendedor_descricaoEditText);

        vendedorImageView = (ImageView) findViewById(R.id.cadastro_vendedor_ImageView);

        cadastrarButton = (Button) findViewById(R.id.cadastro_vendedor_Button);

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

                if(dataSnapshot.child("descricao").exists() == true){
                    vendedorDescricaoEditText.setText(dataSnapshot.child("descricao").getValue().toString());
                }
                if(dataSnapshot.child("numero").exists() == true){
                    vendedorNumberEditText.setText(dataSnapshot.child("numero").getValue().toString());
                }



                vendedorNameEditText.setText(name);
                setVendedorImage(uri);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        databaseReference.addListenerForSingleValueEvent(dataListener);





    }

    public void setVendedorImage(String url){
        Uri uri1 = Uri.parse(url);

        //Picasso.with(this).load("\"https://lh6.googleusercontent.com/-t9kWaYYhDTs/AAAAAAAAAAI/AAAAAAAAAAA/AKTaeK-TFvX7boI-PhySCesDH24bISiatQ/s96-c/photo.jpg\"").into(userImageView);
        Glide.with(this).load(uri1).centerCrop().into(vendedorImageView);

    }

    public void onCadastrarClick(){
        if(vendedorNumberEditText.getText().toString() == null){
            Toast.makeText(CadastroVendedorActivity.this, "Insira Seu Número",
                    Toast.LENGTH_SHORT).show();
        }
        else if(vendedorDescricaoEditText.getText().toString() == null){
            Toast.makeText(CadastroVendedorActivity.this, "Insira Sua Descrição",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            String username;
            String usernumber;
            String userdescricao;

            username = vendedorNameEditText.getText().toString();
            usernumber = vendedorNumberEditText.getText().toString();
            userdescricao = vendedorDescricaoEditText.getText().toString();

            databaseReference.child("nome").setValue(username);
            databaseReference.child("numero").setValue(usernumber);
            databaseReference.child("descricao").setValue(userdescricao);

            goToTelaInicialVendedor();
        }
    }

    public void goToTelaInicialVendedor() {
        Intent intent = new Intent(this, TelaInicialVendedorActivity.class);
        startActivity(intent);
        finish();
    }
}

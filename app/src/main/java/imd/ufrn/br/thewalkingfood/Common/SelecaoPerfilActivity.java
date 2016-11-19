package imd.ufrn.br.thewalkingfood.Common;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import imd.ufrn.br.thewalkingfood.Cadastro.CadastroConsumidorActivity;
import imd.ufrn.br.thewalkingfood.Cadastro.CadastroVendedorActivity;
import imd.ufrn.br.thewalkingfood.R;


public class SelecaoPerfilActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 007;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseDatabase firebaseDatabase;


    RadioButton rbVendedor, rbConsumidor;
    SignInButton signInButton;
    GoogleApiClient mGoogleApiClient;
    GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
*/
        setContentView(R.layout.activity_selecao_perfil);

        rbVendedor = (RadioButton)findViewById(R.id.rBtnVendedor);
        rbConsumidor = (RadioButton)findViewById(R.id.rBtnConsumidor);
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                DatabaseReference root = firebaseDatabase.getReference().child("Users");

                if (user != null) {
                    // User is signed in
                    Log.d("Firebase", "onAuthStateChanged:signed_in:" + user.getUid());

                    if(isVendedor() == true){
                        root.child("Vendedor").child(firebaseAuth.getCurrentUser().getUid()).child("nome").setValue(user.getDisplayName());
                        root.child("Vendedor").child(firebaseAuth.getCurrentUser().getUid()).child("e-mail").setValue(user.getEmail());
                        root.child("Vendedor").child(firebaseAuth.getCurrentUser().getUid()).child("photo-url").setValue(user.getPhotoUrl().toString());

                    }
                    else {
                        root.child("Consumidor").child(firebaseAuth.getCurrentUser().getUid()).child("nome").setValue(user.getDisplayName());
                        root.child("Consumidor").child(firebaseAuth.getCurrentUser().getUid()).child("e-mail").setValue(user.getEmail());
                        root.child("Consumidor").child(firebaseAuth.getCurrentUser().getUid()).child("photo-url").setValue(user.getPhotoUrl().toString());
                    }

                    Toast.makeText(SelecaoPerfilActivity.this, "LOGADO E SALVO NO DB",
                            Toast.LENGTH_SHORT).show();

                    goToNextScreen();
                } else {
                    // User is signed out
                    Log.d("Firebase", "onAuthStateChanged:signed_out");

                }
                // ...
            }
        };






        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();

            }
        });

        onConnectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Log.d("Google", "Connection Failed");
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("880890803658-1vfki0so2ovngg51bf2od26a4a105cp3.apps.googleusercontent.com").requestEmail().build();

        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this /* FragmentActivity */, onConnectionFailedListener).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                int statusCode = result.getStatus().getStatusCode();
            Log.d("ERRO DE SIGN IN", "ERROR CODE:" + String.valueOf(statusCode));
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("Google", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            firebaseAuthWithGoogle(acct);



        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(SelecaoPerfilActivity.this, "Authentication failed.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("Firebase", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Firebase", "signInWithCredential:onComplete:" + task.isSuccessful());


                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("Firebase", "signInWithCredential", task.getException());
                            Toast.makeText(SelecaoPerfilActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }



    public void telaCadastro(View view) {

        if(rbVendedor.isChecked()){
            Intent intent = new Intent(SelecaoPerfilActivity.this, CadastroVendedorActivity.class);
            startActivity(intent);
            //finish();
        }
        if(rbConsumidor.isChecked()){
            Intent intent = new Intent(SelecaoPerfilActivity.this, CadastroConsumidorActivity.class);
            startActivity(intent);
            //finish();
        }
    }

    public boolean isVendedor(){
        if(rbVendedor.isChecked()){
            return true;
            //finish();
        }
        else{
            return false;
        }
    }

    public void goToNextScreen(){
        if(rbVendedor.isChecked()){
            goToCadastroVendedor();
            //finish();
        }
        if(rbConsumidor.isChecked()){
            goToCadastroCliente();
            //finish();
        }
    }

    public void goToCadastroVendedor(){
        Intent intent = new Intent(SelecaoPerfilActivity.this, CadastroVendedorActivity.class);
        startActivity(intent);
    }

    public void goToCadastroCliente(){
        Intent intent = new Intent(SelecaoPerfilActivity.this, CadastroConsumidorActivity.class);
        startActivity(intent);
    }

    public void goToTelaInicial(){
        Intent intent = new Intent(SelecaoPerfilActivity.this, MainActivity.class);
        startActivity(intent);
    }
}

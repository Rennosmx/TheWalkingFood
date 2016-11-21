package imd.ufrn.br.thewalkingfood.Vendedor.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


import de.hdodenhof.circleimageview.CircleImageView;
import imd.ufrn.br.thewalkingfood.Cadastro.CadastroConsumidorActivity;
import imd.ufrn.br.thewalkingfood.Cadastro.CadastroVendedorActivity;
import imd.ufrn.br.thewalkingfood.Cliente.TelaDetalhesVendedorActivity;
import imd.ufrn.br.thewalkingfood.Cliente.TelaInicialConsumidorActivity;
import imd.ufrn.br.thewalkingfood.Common.SelecaoPerfilActivity;
import imd.ufrn.br.thewalkingfood.ListObject.ListAdapterProdutos;
import imd.ufrn.br.thewalkingfood.ListObject.Produto;
import imd.ufrn.br.thewalkingfood.R;
import imd.ufrn.br.thewalkingfood.Vendedor.VendedorAddProdutoActivity;
import imd.ufrn.br.thewalkingfood.Vendedor.VendedorEditProdutoActivity;
import mehdi.sakout.fancybuttons.FancyButton;


public class VendedorProdutosFragment extends Fragment {

    FancyButton editDescricaoButton;
    FancyButton addProdutoButton;

    FirebaseDatabase firebaseDatabase;

    FirebaseStorage firebaseStorage;

    StorageReference produtosStorageReference;
    ListAdapterProdutos listAdapterProdutos;

    CircleImageView imageView;
    TextView vendedorTextView;
    TextView descricaoTextView;

    ListView listView;

    String idVendedor;
    String fileUrl;


    public VendedorProdutosFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View frag = inflater.inflate(R.layout.fragment_vendedor_produtos, container, false);

        idVendedor = FirebaseAuth.getInstance().getCurrentUser().getUid();

        firebaseStorage = FirebaseStorage.getInstance();
        produtosStorageReference = firebaseStorage.getReference().child("Users").child("Vendedor").child(idVendedor).child("produtos");


        imageView = (CircleImageView) frag.findViewById(R.id.fragment_vendedor_produtos_imageView);
        vendedorTextView = (TextView) frag.findViewById(R.id.fragment_vendedor_produtos_vendedorTextView);
        descricaoTextView = (TextView) frag.findViewById(R.id.fragment_vendedor_produtos_descricaoTextView);
        listView = (ListView) frag.findViewById(R.id.fragment_vendedor_produtos_listView);

        addProdutoButton = (FancyButton) frag.findViewById(R.id.fragment_vendedor_produtos_addProdutoButton);
        editDescricaoButton = (FancyButton) frag.findViewById(R.id.fragment_vendedor_produtos_editDescricaoButton);


        addProdutoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "ADD PRODUTO",
                        Toast.LENGTH_SHORT).show();
                goToVendedorAddProduto();
            }
        });

        editDescricaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "EDIT PERFIL",
                        Toast.LENGTH_SHORT).show();
                goToCadastroVendedor();
            }
        });



        firebaseDatabase = FirebaseDatabase.getInstance();

        final DatabaseReference vendedorNode = firebaseDatabase.getReference().child("Users").child("Vendedor").child(idVendedor);

        ValueEventListener vendedorListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<Produto> produtos = new ArrayList<Produto>();

                Glide.with(getContext()).load(dataSnapshot.child("photo-url").getValue().toString()).centerCrop().into(imageView);
                vendedorTextView.setText(dataSnapshot.child("nome").getValue().toString());
                descricaoTextView.setText(dataSnapshot.child("descricao").getValue().toString());

                for (DataSnapshot dsp : dataSnapshot.child("produtos").getChildren()) {

                    Produto p = new Produto();

                    p.setId(dsp.getKey());
                    p.setNome(dsp.child("nome").getValue().toString());
                    p.setCategoria(dsp.child("categoria").getValue().toString());
                    p.setDescricao(dsp.child("descricao").getValue().toString());
                    p.setPreco(dsp.child("preco").getValue().toString());
                    if(dsp.child("photo-url").exists() == true) {
                        p.setPhoto_url(dsp.child("photo-url").getValue().toString());
                    }
                    else {
                        produtosStorageReference.child(dsp.getKey()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                fileUrl = uri.toString();
                                Log.d("FILEURL", fileUrl);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("STORAGE RETRIEVE", "DEU MERDA");
                            }
                        });

                        p.setPhoto_url(fileUrl);


                    }
                    produtos.add(p);

                }

                listAdapterProdutos = new ListAdapterProdutos(getContext(), produtos);

                listView.setAdapter(listAdapterProdutos);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        vendedorNode.addListenerForSingleValueEvent(vendedorListener);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Produto p = (Produto) listAdapterProdutos.getItem(i);

                goToVendedorEditProduto(p.getId());

            }
        });



        return frag;
    }


    public void goToCadastroVendedor(){
        Intent intent = new Intent(getActivity(), CadastroVendedorActivity.class);
        startActivity(intent);

    }

    public void goToVendedorAddProduto(){
        Intent intent = new Intent(getActivity(), VendedorAddProdutoActivity.class);
        startActivity(intent);

    }

    public void goToVendedorEditProduto(String id){
        Intent intent = new Intent(getActivity(), VendedorEditProdutoActivity.class);
        intent.putExtra("idProduto", id);
        startActivity(intent);

    }

}

package imd.ufrn.br.thewalkingfood.Vendedor.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import imd.ufrn.br.thewalkingfood.Cliente.TelaDetalhesVendedorActivity;
import imd.ufrn.br.thewalkingfood.ListObject.Feed;
import imd.ufrn.br.thewalkingfood.ListObject.ListAdapterFeed;
import imd.ufrn.br.thewalkingfood.ListObject.Produto;
import imd.ufrn.br.thewalkingfood.R;
import imd.ufrn.br.thewalkingfood.Vendedor.TelaInicialVendedorActivity;
import imd.ufrn.br.thewalkingfood.Vendedor.VendedorAddFeedActivity;
import imd.ufrn.br.thewalkingfood.Vendedor.VendedorAddProdutoActivity;
import imd.ufrn.br.thewalkingfood.Vendedor.VendedorEditFeedActivity;
import imd.ufrn.br.thewalkingfood.Vendedor.VendedorEditProdutoActivity;
import mehdi.sakout.fancybuttons.FancyButton;


public class VendedorFeedFragment extends Fragment {


    ListView listView;
    FancyButton addFeedButton;

    FirebaseDatabase firebaseDatabase;
    ListAdapterFeed listAdapterFeed;

    String idVendedor;
    String idFeed;


    public VendedorFeedFragment() {
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
        View frag = inflater.inflate(R.layout.fragment_vendedor_feed, container, false);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        idVendedor = firebaseUser.getUid();


        listView = (ListView) frag.findViewById(R.id.fragment_vendedor_feed_listView);
        addFeedButton = (FancyButton) frag.findViewById(R.id.fragment_vendedor_feed_addFeedButton);



        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference vendedorNode = firebaseDatabase.getReference().child("Users").child("Vendedor").child(idVendedor);



        ValueEventListener feedListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<Feed> feeds = new ArrayList<Feed>();

                for (DataSnapshot dsp : dataSnapshot.child("feed").getChildren()) {

                    Feed f = new Feed();

                    f.setId(dsp.getKey());
                    f.setFeedText(dsp.child("descricao").getValue().toString());
                    f.setFeedDate(dsp.child("data").getValue().toString());
                    f.setPhotourl(dsp.child("photo-url").getValue().toString());

                    feeds.add(f);

                }

                Collections.sort(feeds);
                Collections.reverse(feeds);

                listAdapterFeed = new ListAdapterFeed(getContext(), feeds);

                listView.setAdapter(listAdapterFeed);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        vendedorNode.addListenerForSingleValueEvent(feedListener);


        addFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToVendedorAddFeed();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Feed p = (Feed) listAdapterFeed.getItem(i);

                goToVendedorEditFeed(p.getId());

            }
        });



        return frag;



    }

    public String getIdFeed() {
        return idFeed;
    }

    public void goToVendedorAddFeed(){
        Intent intent = new Intent(getActivity(), VendedorAddFeedActivity.class);
        startActivity(intent);

    }

    public void goToVendedorEditFeed(String id){
        Intent intent = new Intent(getActivity(), VendedorEditFeedActivity.class);
        intent.putExtra("idFeed", id);
        startActivity(intent);

    }
}

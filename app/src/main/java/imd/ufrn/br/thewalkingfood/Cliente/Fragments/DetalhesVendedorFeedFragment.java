package imd.ufrn.br.thewalkingfood.Cliente.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.facebook.internal.CollectionMapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import imd.ufrn.br.thewalkingfood.Cliente.TelaDetalhesVendedorActivity;
import imd.ufrn.br.thewalkingfood.ListObject.Feed;
import imd.ufrn.br.thewalkingfood.ListObject.ListAdapterFeed;
import imd.ufrn.br.thewalkingfood.R;


public class DetalhesVendedorFeedFragment extends Fragment {


    FirebaseDatabase firebaseDatabase;

    ListView listView;
    Button subscribeButton;

    String idVendedor;
    String idConsumidor;

    DatabaseReference subscribeReference;

    public DetalhesVendedorFeedFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View frag = inflater.inflate(R.layout.fragment_detalhes_vendedor_feed, container, false);


        TelaDetalhesVendedorActivity activity = (TelaDetalhesVendedorActivity) getActivity();
        idVendedor = activity.getIdVendedor();
        idConsumidor = activity.getIdConsumidor();

        listView = (ListView) frag.findViewById(R.id.fragment_detalhes_vendedor_feed_listView);

        subscribeButton = (Button) frag.findViewById(R.id.fragment_detalhes_vendedor_feed_subscribeButton);



        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference vendedorNode = firebaseDatabase.getReference().child("Users").child("Vendedor").child(idVendedor);

        DatabaseReference userFeed = firebaseDatabase.getReference().child("Users").child("Consumidor").child(idConsumidor);

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

                ListAdapterFeed listAdapterFeed = new ListAdapterFeed(getContext(), feeds);

                listView.setAdapter(listAdapterFeed);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        vendedorNode.addListenerForSingleValueEvent(feedListener);


        ValueEventListener userFeedListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("feed").child(idVendedor).exists() == true) {
                    subscribeButton.setText("SEGUINDO");
                }
                else {
                    subscribeButton.setText("SEGUIR");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        userFeed.addListenerForSingleValueEvent(userFeedListener);

        final String subscribedText = subscribeButton.getText().toString();

        subscribeReference = firebaseDatabase.getReference().child("Users").child("Consumidor").child(idConsumidor);



        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(subscribeButton.getText().toString().equalsIgnoreCase("SEGUIR")){
                    subscribeReference.child("feed").child(idVendedor).setValue(true);
                    subscribeButton.setText("SEGUINDO");
                }
                else {
                    subscribeReference.child("feed").child(idVendedor).removeValue();
                    subscribeButton.setText("SEGUIR");
                }

            }
        });


        return frag;
    }

    public ArrayList<Feed> ordenarFeeds(ArrayList<Feed> feeds){


        return null;
    }



}

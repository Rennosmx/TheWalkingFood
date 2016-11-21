package imd.ufrn.br.thewalkingfood.Cliente.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import imd.ufrn.br.thewalkingfood.ListObject.Feed;
import imd.ufrn.br.thewalkingfood.ListObject.ListAdapterFeed;
import imd.ufrn.br.thewalkingfood.R;


public class ConsumidorFeedFragment extends Fragment {

    ListView listView;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;

    FirebaseUser firebaseUser;

    public ConsumidorFeedFragment() {
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
        View frag = inflater.inflate(R.layout.fragment_consumidor_feed, container, false);

        listView = (ListView) frag.findViewById(R.id.consumidor_feed_fragment_ListView);




        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseUser = firebaseAuth.getCurrentUser();

        DatabaseReference userFeedReference = firebaseDatabase.getReference().child("Users");

        ValueEventListener userFeedListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<Feed> feeds = new ArrayList<Feed>();
                ArrayList<String> idVendedores = new ArrayList<String>();

                if (dataSnapshot.child("Consumidor").child(firebaseUser.getUid()).child("feed").exists() == true){

                    for (DataSnapshot dsp : dataSnapshot.child("Consumidor").child(firebaseUser.getUid()).child("feed").getChildren()) {

                        idVendedores.add(dsp.getKey());



                        if(dataSnapshot.child("Vendedor").child(dsp.getKey()).child("feed").exists() == true) {

                            for(DataSnapshot dspV : dataSnapshot.child("Vendedor").child(dsp.getKey()).child("feed").getChildren()){
                                Feed f = new Feed();

                                f.setFeedText(dspV.child("descricao").getValue().toString());
                                f.setFeedDate(dspV.child("data").getValue().toString());
                                f.setPhotourl(dspV.child("photo-url").getValue().toString());

                                feeds.add(f);

                            }
                        }
                    }

                }
                else {

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

        userFeedReference.addListenerForSingleValueEvent(userFeedListener);







        return frag;
    }


}

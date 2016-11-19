package imd.ufrn.br.thewalkingfood.Cliente.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import imd.ufrn.br.thewalkingfood.ListObject.Feed;
import imd.ufrn.br.thewalkingfood.ListObject.ListAdapterFeed;
import imd.ufrn.br.thewalkingfood.R;


public class ConsumidorFeedFragment extends Fragment {

    ListView listView;

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
        ArrayList<Feed> feedsText = new ArrayList<Feed>();
        Feed f = new Feed("http://pngimg.com/upload/small/dog_PNG2438.png", "Descrição do feed", "10/12/4851");
        feedsText.add(f);
        f = new Feed("http://pngimg.com/upload/small/dog_PNG2438.png", "AUAUAU auauauauaauauauau auauauaauauauaau auaauauauaau AUAUA AU AU AU", "10/12/1000");
        feedsText.add(f);

        ListAdapterFeed listAdapterFeed = new ListAdapterFeed(getContext(), feedsText);

        View frag = inflater.inflate(R.layout.fragment_consumidor_feed, container, false);

        listView = (ListView) frag.findViewById(R.id.consumidor_feed_fragment_ListView);

        listView.setAdapter(listAdapterFeed);

        return frag;
    }


}

package imd.ufrn.br.thewalkingfood;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class ListaVendedoresFragment extends Fragment {





    public ListaVendedoresFragment() {
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
        ArrayList<String> idsList = new ArrayList<String>();
        idsList.add("1");
        idsList.add("2");
        ArrayList<String> photourlList = new ArrayList<String>();
        photourlList.add("http://pngimg.com/upload/small/dog_PNG2438.png");
        photourlList.add("https://s-media-cache-ak0.pinimg.com/236x/9b/cb/b3/9bcbb308affbafe820a0c8190191c1cb.jpg");
        ArrayList<String> numbersList = new ArrayList<String>();
        numbersList.add("99999999");
        numbersList.add("00000000");
        ArrayList<String> distanceList = new ArrayList<String>();
        distanceList.add("5");
        distanceList.add("10");



        View frag = inflater.inflate(R.layout.fragment_lista_vendedores, container, false);

        ListAdapterVendedores adapter = new ListAdapterVendedores(frag.getContext(), idsList, photourlList, numbersList, distanceList );



        ListView listView = (ListView) frag.findViewById(R.id.fragment_lista_vendedores_ListView);

        listView.setAdapter(adapter);

        return frag;
    }


}

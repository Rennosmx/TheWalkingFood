package imd.ufrn.br.thewalkingfood;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class FragmentChat extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        ImageView chatSim = (ImageView) view.findViewById(R.id.tela_inicial_consumidor_chat1);

        // Inflate the layout for this fragment
        return view;
    }

}

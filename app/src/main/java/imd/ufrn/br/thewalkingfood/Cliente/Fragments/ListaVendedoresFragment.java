package imd.ufrn.br.thewalkingfood.Cliente.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import imd.ufrn.br.thewalkingfood.Cliente.TelaDetalhesVendedorActivity;
import imd.ufrn.br.thewalkingfood.ListObject.ListAdapterVendedores;
import imd.ufrn.br.thewalkingfood.ListObject.DuplaVendedor;
import imd.ufrn.br.thewalkingfood.ListObject.Vendedor;
import imd.ufrn.br.thewalkingfood.R;


public class ListaVendedoresFragment extends Fragment {


    FirebaseDatabase firebaseDatabase;

    private ArrayList<Vendedor> vendedor;
    private ArrayList<DuplaVendedor> listaDuplaVendedores;

    private ListAdapterVendedores adapter;
    private ListView listView;

    private Context context;
/*
    public ListaVendedoresFragment() {
        // Required empty public constructor
    }
*/

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

        context = this.getContext();

        listView = (ListView) frag.findViewById(R.id.fragment_lista_vendedores_ListView);



        //GET DATA FROM FIREBASE AND POPULATE THE FREAKING LIST
        //////////////////////////////////////////////////////////////////////
        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference vendedorNode = firebaseDatabase.getReference().child("Users").child("Vendedor");

        ValueEventListener listaVendedores = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<Vendedor> listaVendedorFB = new ArrayList<Vendedor>();

                Long XARALHO = dataSnapshot.getChildrenCount();
                boolean a = dataSnapshot.child("vendedor1").exists();

                for (DataSnapshot dsp : dataSnapshot.getChildren()){

                    Vendedor aux = new Vendedor();

                    aux.setId(dsp.getKey());
                    aux.setPhotourl(dsp.child("photo-url").getValue().toString());
                    aux.setNumber(dsp.child("numero").getValue().toString());
                    if(dsp.child("distance").exists() == true) {
                        aux.setDistance(dsp.child("distance").getValue().toString());
                    }
                    else {
                        aux.setDistance("10");
                    }
                    listaVendedorFB.add(aux);

                }

                listaDuplaVendedores = getFinalList(listaVendedorFB);
                adapter = new ListAdapterVendedores(getContext(),listaDuplaVendedores );
                listView.setAdapter(adapter);
                Log.d("FIREBASE", "ENTROU NO VALUE EVENT LISTENER");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        vendedorNode.addListenerForSingleValueEvent(listaVendedores);




        ///////////////////////////////////////////////////////////////////
        //DuplaVendedor v = new DuplaVendedor(idsList.get(0), photourlList.get(0), numbersList.get(0), distanceList.get(0), idsList.get(1), photourlList.get(1), numbersList.get(1), distanceList.get(1));









        return frag;
    }



    public ArrayList<DuplaVendedor> getFinalList(ArrayList<Vendedor> listaVendedor){

        ArrayList<DuplaVendedor> vendedores = new ArrayList<DuplaVendedor>();
        int listaSize = listaVendedor.size();

        if (modulo(listaVendedor.size(), 2) == 1) {
            listaSize = listaVendedor.size() - 1;
        }

        for(int i = 0; i < (listaSize - 1); i+=2){
            DuplaVendedor DPaux = new DuplaVendedor(listaVendedor.get(i), listaVendedor.get(i+1));

            vendedores.add(DPaux);
        }

        if (listaSize < listaVendedor.size()){
            Vendedor emptyVendedor = new Vendedor(null, null, null, null);
            DuplaVendedor DPaux1 = new DuplaVendedor(listaVendedor.get(listaVendedor.size() - 1), emptyVendedor);
            vendedores.add(DPaux1);
        }

        return vendedores;
    }


    public int modulo( int m, int n ){
        int mod =  m % n ;
        return ( mod < 0 ) ? mod + n : mod;
    }

    public void goToDetalhesVendedor(String id, String idC){
        Intent intent = new Intent(getActivity(), TelaDetalhesVendedorActivity.class);
        intent.putExtra("idVendedor", id);
        intent.putExtra("idConsumidor", idC);
        startActivity(intent);
        getActivity().finish();
    }

}

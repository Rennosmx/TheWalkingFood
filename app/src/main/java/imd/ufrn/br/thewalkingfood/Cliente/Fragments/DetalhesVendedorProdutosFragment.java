package imd.ufrn.br.thewalkingfood.Cliente.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import imd.ufrn.br.thewalkingfood.Cliente.TelaDetalhesVendedorActivity;
import imd.ufrn.br.thewalkingfood.ListObject.ListAdapterProdutos;
import imd.ufrn.br.thewalkingfood.ListObject.Produto;
import imd.ufrn.br.thewalkingfood.R;


public class DetalhesVendedorProdutosFragment extends Fragment {


    FirebaseDatabase firebaseDatabase;

    CircleImageView imageView;
    TextView vendedorTextView;
    TextView descricaoTextView;

    ListView listView;

    String idVendedor;
    String idConsumidor;


    public DetalhesVendedorProdutosFragment() {
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
        View frag = inflater.inflate(R.layout.fragment_detalhes_vendedor_produtos, container, false);

        TelaDetalhesVendedorActivity activity = (TelaDetalhesVendedorActivity) getActivity();
        idVendedor = activity.getIdVendedor();
        idConsumidor = activity.getIdConsumidor();

        imageView = (CircleImageView) frag.findViewById(R.id.fragment_detalhes_vendedor_produtos_imageView);
        vendedorTextView = (TextView) frag.findViewById(R.id.fragment_detalhes_vendedor_produtos_vendedorTextView);
        descricaoTextView = (TextView) frag.findViewById(R.id.fragment_detalhes_vendedor_produtos_descricaoTextView);
        listView = (ListView) frag.findViewById(R.id.fragment_detalhes_vendedor_produtos_listView);


        firebaseDatabase = FirebaseDatabase.getInstance();

        final DatabaseReference vendedorNode = firebaseDatabase.getReference().child("Users").child("Vendedor").child(idVendedor);

        ValueEventListener vendedorListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<Produto> produtos = new ArrayList<Produto>();

                Glide.with(getContext()).load(dataSnapshot.child("photo-url").getValue().toString()).centerCrop().into(imageView);
                vendedorTextView.setText(dataSnapshot.getKey());
                descricaoTextView.setText(dataSnapshot.child("descricao").getValue().toString());

                for (DataSnapshot dsp : dataSnapshot.child("produtos").getChildren()) {

                    Produto p = new Produto();

                    p.setId(dsp.getKey());
                    p.setNome(dsp.child("nome").getValue().toString());
                    p.setCategoria(dsp.child("categoria").getValue().toString());
                    p.setDescricao(dsp.child("descricao").getValue().toString());
                    p.setPreco(dsp.child("preco").getValue().toString());
                    p.setPhoto_url(dsp.child("photo-url").getValue().toString());

                    produtos.add(p);

                }

                ListAdapterProdutos listAdapterProdutos = new ListAdapterProdutos(getContext(), produtos);

                listView.setAdapter(listAdapterProdutos);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        vendedorNode.addListenerForSingleValueEvent(vendedorListener);



        return frag;
    }


}

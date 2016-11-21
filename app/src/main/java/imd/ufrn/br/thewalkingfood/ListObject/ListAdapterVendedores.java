package imd.ufrn.br.thewalkingfood.ListObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import imd.ufrn.br.thewalkingfood.Cliente.TelaDetalhesVendedorActivity;
import imd.ufrn.br.thewalkingfood.ListObject.DuplaVendedor;
import imd.ufrn.br.thewalkingfood.ListObject.Vendedor;
import imd.ufrn.br.thewalkingfood.R;

/**
 * Created by Kamilla on 11/18/2016.
 */

public class ListAdapterVendedores extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<DuplaVendedor>  vendedores;
    String extra;
    String idConsumidor;

    int auxi;

    public ListAdapterVendedores(Context _context, ArrayList<DuplaVendedor> _vendedores ) {
        super();
        this.context = _context;

        vendedores = new ArrayList<DuplaVendedor>(_vendedores);

    }

    private class ViewHolder {
        RelativeLayout itemA;
        CircleImageView imageViewA;
        TextView numberTextViewA;
        TextView distanceTextViewA;
        Button buttonA;

        RelativeLayout itemB;
        CircleImageView imageViewB;
        TextView numberTextViewB;
        TextView distanceTextViewB;
        Button buttonB;
    }



    @Override
    public int getCount() {
        return vendedores.size();
    }

    @Override
    public Object getItem(int i) {
        return vendedores.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Long.parseLong(vendedores.get(i).getIdA());
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        idConsumidor = firebaseUser.getUid();

        if(view == null){
            view = mInflater.inflate(R.layout.item_lista_vendedores, null);

            holder = new ViewHolder();
            holder.imageViewA = (CircleImageView) view.findViewById(R.id.item_lista_vendedores_ImageViewA);
            holder.numberTextViewA = (TextView) view.findViewById(R.id.item_lista_vendedores_number_TextViewA);
            holder.distanceTextViewA = (TextView) view.findViewById(R.id.item_lista_vendedores_distance_TextViewA);
            holder.buttonA = (Button) view.findViewById(R.id.item_lista_vendedores_buttonA);


            holder.imageViewB = (CircleImageView) view.findViewById(R.id.item_lista_vendedores_ImageViewB);
            holder.numberTextViewB = (TextView) view.findViewById(R.id.item_lista_vendedores_number_TextViewB);
            holder.distanceTextViewB = (TextView) view.findViewById(R.id.item_lista_vendedores_distance_TextViewB);
            holder.buttonB = (Button) view.findViewById(R.id.item_lista_vendedores_buttonB);

            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }

        auxi = i;
        extra = vendedores.get(i).getIdA();
        Glide.with(context).load(vendedores.get(i).getPhotourlA()).centerCrop().into(holder.imageViewA);
        holder.numberTextViewA.setText(vendedores.get(i).getNumberA());
        holder.distanceTextViewA.setText(vendedores.get(i).getDistanceA());
        holder.buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListView listView = (ListView) view.getParent().getParent().getParent();
                int position = listView.getPositionForView(view);

                Log.d("VIEW", view.getParent().getParent().getParent().toString());
                extra = vendedores.get(position).getIdA();

                goToDetalhesVendedor(extra, idConsumidor);
            }
        });




        auxi = i;
        extra = vendedores.get(i).getIdB();
        if(vendedores.get(i).getIdB() == null){
            holder.buttonB.setVisibility(View.INVISIBLE);
        }
        else {
            Glide.with(context).load(vendedores.get(i).getPhotourlB()).centerCrop().into(holder.imageViewB);
            holder.numberTextViewB.setText(vendedores.get(i).getNumberB());
            holder.distanceTextViewB.setText(vendedores.get(i).getDistanceB());
            holder.buttonB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ListView listView = (ListView) view.getParent().getParent().getParent();
                    int position = listView.getPositionForView(view);

                    Log.d("VIEW", view.getParent().getParent().getParent().toString());

                    extra = vendedores.get(position).getIdB();
                    goToDetalhesVendedor(extra, idConsumidor);
                }
            });
        }
        /*

        RelativeLayout itemA = (RelativeLayout) item.findViewById(R.id.item_lista_vendedores_RelativeLayoutA);
        ImageView imageViewA = (ImageView) item.findViewById(R.id.item_lista_vendedores_ImageViewA);
        TextView numberTextViewA = (TextView) item.findViewById(R.id.item_lista_vendedores_number_TextViewA);
        TextView distanceTextViewA = (TextView) item.findViewById(R.id.item_lista_vendedores_distance_TextViewA);
        Button buttonA = (Button) item.findViewById(R.id.item_lista_vendedores_buttonA);

        RelativeLayout itemB = (RelativeLayout) item.findViewById(R.id.item_lista_vendedores_RelativeLayoutB);
        ImageView imageViewB = (ImageView) item.findViewById(R.id.item_lista_vendedores_ImageViewB);
        TextView numberTextViewB = (TextView) item.findViewById(R.id.item_lista_vendedores_number_TextViewB);
        TextView distanceTextViewB = (TextView) item.findViewById(R.id.item_lista_vendedores_distance_TextViewB);
        Button buttonB = (Button) item.findViewById(R.id.item_lista_vendedores_buttonB);

        ///



        //Glide.with(this).load(uri1).centerCrop().into(userImageView);

        Glide.with(context).load(urlPhotoVendedor.get(i)).centerCrop().into(imageViewA);
        numberTextViewA.setText(numeroVendedor.get(i));
        distanceTextViewA.setText(distanceVendedor.get(i));
        auxi = i;
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, idVendedor.get(auxi) + " CLICADO",
                        Toast.LENGTH_LONG).show();
            }
        });

        //////////////////
        itemB.setVisibility(View.INVISIBLE);
        imageViewB.setVisibility(View.INVISIBLE);
        numberTextViewB.setVisibility(View.INVISIBLE);
        distanceTextViewB.setVisibility(View.INVISIBLE);
        buttonB.setVisibility(View.INVISIBLE);

        */

        return view;
    }

    public void goToDetalhesVendedor(String id, String idC){
        Intent intent = new Intent(context, TelaDetalhesVendedorActivity.class);
        intent.putExtra("idVendedor", id);
        intent.putExtra("idConsumidor", idC);
        context.startActivity(intent);
    }


}

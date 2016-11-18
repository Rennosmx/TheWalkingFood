package imd.ufrn.br.thewalkingfood;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

import imd.ufrn.br.thewalkingfood.ListObject.DuplaVendedor;
import imd.ufrn.br.thewalkingfood.ListObject.Vendedor;

/**
 * Created by Kamilla on 11/18/2016.
 */

public class ListAdapterVendedores extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<DuplaVendedor>  vendedores;
    int auxi;

    public ListAdapterVendedores(Context _context, ArrayList<DuplaVendedor> _vendedores ) {
        super();
        this.context = _context;

        this.vendedores = _vendedores;

    }

    private class ViewHolder {
        RelativeLayout itemA;
        ImageView imageViewA;
        TextView numberTextViewA;
        TextView distanceTextViewA;
        Button buttonA;

        RelativeLayout itemB;
        ImageView imageViewB;
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

        if(view == null){
            view = mInflater.inflate(R.layout.item_lista_vendedores, null);

            holder = new ViewHolder();
            holder.imageViewA = (ImageView) view.findViewById(R.id.item_lista_vendedores_ImageViewA);
            holder.numberTextViewA = (TextView) view.findViewById(R.id.item_lista_vendedores_number_TextViewA);
            holder.distanceTextViewA = (TextView) view.findViewById(R.id.item_lista_vendedores_distance_TextViewA);
            holder.buttonA = (Button) view.findViewById(R.id.item_lista_vendedores_buttonA);


            holder.imageViewB = (ImageView) view.findViewById(R.id.item_lista_vendedores_ImageViewB);
            holder.numberTextViewB = (TextView) view.findViewById(R.id.item_lista_vendedores_number_TextViewB);
            holder.distanceTextViewB = (TextView) view.findViewById(R.id.item_lista_vendedores_distance_TextViewB);
            holder.buttonB = (Button) view.findViewById(R.id.item_lista_vendedores_buttonB);

            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }


        Glide.with(context).load(vendedores.get(i).getPhotourlA()).centerCrop().into(holder.imageViewA);
        holder.numberTextViewA.setText(vendedores.get(i).getNumberA());
        holder.distanceTextViewA.setText(vendedores.get(i).getNumberB());

        Glide.with(context).load(vendedores.get(i).getPhotourlB()).centerCrop().into(holder.imageViewB);
        holder.numberTextViewA.setText(vendedores.get(i).getNumberB());
        holder.distanceTextViewA.setText(vendedores.get(i).getNumberB());

        if(vendedores.get(i).getIdB() == null){
            holder.buttonB.setVisibility(View.INVISIBLE);
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
}

package imd.ufrn.br.thewalkingfood;

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

/**
 * Created by Kamilla on 11/18/2016.
 */

public class ListAdapterVendedores extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<String> idVendedor;
    ArrayList<String> urlPhotoVendedor;
    ArrayList<String> numeroVendedor;
    ArrayList<String> distanceVendedor;
    int auxi;

    public ListAdapterVendedores(Context _context, ArrayList<String> _idVendedor, ArrayList<String> _urlPhotoVendedor, ArrayList<String> _numeroVendedor, ArrayList<String> _distanceVendedor ) {
        super();
        this.context = _context;

        this.idVendedor = _idVendedor;
        this.urlPhotoVendedor = _urlPhotoVendedor;
        this.numeroVendedor = _numeroVendedor;
        this.distanceVendedor = _distanceVendedor;
        layoutInflater = LayoutInflater.from(context);

    }





    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View item = layoutInflater.inflate(R.layout.item_lista_vendedores, viewGroup);

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



        return null;
    }
}

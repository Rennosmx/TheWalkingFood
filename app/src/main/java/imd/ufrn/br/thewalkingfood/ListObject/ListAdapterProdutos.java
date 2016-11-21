package imd.ufrn.br.thewalkingfood.ListObject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import imd.ufrn.br.thewalkingfood.R;

/**
 * Created by Kamilla on 11/19/2016.
 */

public class ListAdapterProdutos extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Produto> produtos;


    public ListAdapterProdutos(){

    }

    public ListAdapterProdutos(Context _context, ArrayList<Produto> _produtos){

        this.context = _context;
        this.produtos = new ArrayList<Produto>(_produtos);

    }

    private class ViewHolder{
        CircleImageView imageView;
        TextView nomeTextView;
        TextView categoriaTextView;
        TextView descricaoTextView;
        TextView precoTextView;
    }



    @Override
    public int getCount() {
        return produtos.size();
    }

    @Override
    public Object getItem(int i) {
        return produtos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;
        layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(view == null){

            view = layoutInflater.inflate(R.layout.item_lista_produtos, null);

            holder = new ViewHolder();

            holder.imageView = (CircleImageView) view.findViewById(R.id.item_lista_produtos_imageView);
            holder.nomeTextView = (TextView) view.findViewById(R.id.item_lista_produtos_tituloTextView);
            holder.categoriaTextView = (TextView) view.findViewById(R.id.item_lista_produtos_categoriaTextView);
            holder.descricaoTextView = (TextView) view.findViewById(R.id.item_lista_produtos_descricaoTextView);
            holder.precoTextView = (TextView) view.findViewById(R.id.item_lista_produtos_precoTextView);

            view.setTag(holder);

        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        Glide.with(context).load(produtos.get(i).getPhoto_url()).override(400,400).centerCrop().into(holder.imageView);
        holder.nomeTextView.setText(produtos.get(i).getNome());
        holder.categoriaTextView.setText("Categoria: " + produtos.get(i).getCategoria());
        holder.descricaoTextView.setText(produtos.get(i).getDescricao());
        holder.precoTextView.setText("Preço: " + produtos.get(i).getPreco());



        return view;

    }
}

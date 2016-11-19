package imd.ufrn.br.thewalkingfood.ListObject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import imd.ufrn.br.thewalkingfood.R;

/**
 * Created by Kamilla on 11/19/2016.
 */

public class ListAdapterFeed extends BaseAdapter {




    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Feed> feeds;

    public ListAdapterFeed(Context _context, ArrayList<Feed> _feeds){
        super();
        this.context = _context;
        this.feeds = new ArrayList<Feed>(_feeds);

    }

    private class ViewHolder{
        CircleImageView imageView;
        TextView feedTextView;
        TextView dateTextView;
    }


    @Override
    public int getCount() {
        return feeds.size();
    }

    @Override
    public Object getItem(int i) {
        return feeds.get(i);
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

            view = layoutInflater.inflate(R.layout.item_lista_feed, null);

            holder = new ViewHolder();

            holder.imageView = (CircleImageView) view.findViewById(R.id.item_lista_feed_ImageView);
            holder.feedTextView = (TextView) view.findViewById(R.id.item_lista_feed_feedTextView);
            holder.dateTextView = (TextView) view.findViewById(R.id.item_lista_feed_dateTextView);

            view.setTag(holder);

        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        Glide.with(context).load(feeds.get(i).getPhotourl()).centerCrop().into(holder.imageView);
        holder.feedTextView.setText(feeds.get(i).getFeedText());
        holder.dateTextView.setText(feeds.get(i).getFeedDate());



        return view;
    }
}

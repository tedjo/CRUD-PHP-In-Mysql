package com.e.menumakanki.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.menumakanki.Model.DataModel;
import com.e.menumakanki.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MenuAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<DataModel> mPost;

    public MenuAdapter(Context context, ArrayList<DataModel> mPost) {
        this.context = context;
        this.mPost = mPost;
    }

    @Override
    public int getCount() {
        return mPost.size();
    }

    @Override
    public Object getItem(int position) {
        return mPost.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convetView, ViewGroup parent) {
        Myholder mHolder;
        if(convetView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convetView = inflater.inflate(R.layout.item_row,parent,false);
            mHolder = new Myholder();
            mHolder.id = (TextView) convetView.findViewById(R.id.viewId);
            mHolder.name = (TextView) convetView.findViewById(R.id.viewName);
            mHolder.price = (TextView) convetView.findViewById(R.id.viewPrice);
            mHolder.stock = (TextView) convetView.findViewById(R.id.viewStock);
            mHolder.description = (TextView) convetView.findViewById(R.id.viewDescription);
            convetView.setTag(mHolder);
        }else {
            mHolder = (Myholder) convetView.getTag();
        }

        mHolder.id.setText(mPost.get(position).getId());
        mHolder.name.setText("Nama Makan   : " + mPost.get(position).getName());
        mHolder.price.setText("Harga Makan   : Rp " +mPost.get(position).getPrice() +",00");
        mHolder.stock.setText("Ketersediaan  : " +mPost.get(position).getStock());
        mHolder.description.setText("Desc Makan    : " +mPost.get(position).getDescription());

        return convetView;
    }

    static class Myholder{
        TextView id;
        TextView name;
        TextView price;
        TextView stock;
        TextView description;
    }
}

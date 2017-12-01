package com.example.mike.e_ventory.ListAdapters;

import com.example.mike.e_ventory.*;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ListAdapterPopUpShop extends ArrayAdapter<ListItemProd> {

    private Context context;
    private ViewHolder holder;

    public ListAdapterPopUpShop(Context c, int id, List<ListItemProd> items){
        super(c, id, items);
        this.context = c;
    }

    private class ViewHolder{
        TextView nameView;
        TextView priceView;
        TextView qtyView;
        ImageButton addQty;
        ImageButton subQty;
    }

    public View getView(final int pos, View convertView, final ViewGroup parent){
        ListItemProd prod = getItem(pos);

        LayoutInflater inFlater = (LayoutInflater) context.getSystemService(
                Activity.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            convertView = inFlater.inflate(R.layout.item_product, null);

            holder = new ViewHolder();

            holder.nameView = (TextView) convertView.findViewById(R.id.name);
            holder.priceView = (TextView) convertView.findViewById(R.id.price);
            holder.qtyView = (TextView) convertView.findViewById(R.id.qtyTV);
            holder.addQty = (ImageButton) convertView.findViewById(R.id.addIBTN);
            holder.subQty = (ImageButton) convertView.findViewById(R.id.subtractIBTN);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nameView.setText("Product Name : " + prod.getProdName());
        holder.priceView.setText("Price : $" + String.valueOf(prod.getPrice()));
        holder.qtyView.setText("0");

        holder.addQty.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                View view = (View) v.getParent();
                TextView qtyView = (TextView)view.findViewById(R.id.qtyTV);
                int qty = Integer.parseInt(qtyView.getText().toString());

                if(qty < getItem(pos).getQty()) {
                    qty += 1;
                    qtyView.setText(String.valueOf(qty));
                }
                else{
                    Toast.makeText( getContext(), "No more products in stock.",
                            Toast.LENGTH_LONG ).show();

                }
            }
        });
        holder.subQty.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                View view = (View) v.getParent();
                TextView qtyView = (TextView)view.findViewById(R.id.qtyTV);
                int qty = Integer.parseInt(qtyView.getText().toString());

                if(qty > 0) {
                    qty -= 1;
                    qtyView.setText(String.valueOf(qty));
                }
            }
        });

        return convertView;
    }

}

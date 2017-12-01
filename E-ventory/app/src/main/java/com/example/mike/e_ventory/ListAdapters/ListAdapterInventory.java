package com.example.mike.e_ventory.ListAdapters;

import com.example.mike.e_ventory.*;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class ListAdapterInventory extends ArrayAdapter<ListItemProd> {

    private Context context;
    private ViewHolder holder;

    public ListAdapterInventory(Context c, int id, List<ListItemProd> items){
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
        holder.qtyView.setText(String.valueOf(prod.getQty()));

        holder.addQty.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                View view = (View) v.getParent();
                TextView qtyView = (TextView)view.findViewById(R.id.qtyTV);
                TextView title = (TextView)view.findViewById(R.id.name);
                int qty = Integer.parseInt(qtyView.getText().toString());
                String name = title.getText().toString().substring(15);
                DBAdapter db = new DBAdapter(getContext());
                Cursor c;

                qty += 1;
                getItem(pos).setQty(qty);
                qtyView.setText(String.valueOf(qty));
                db.open();
                c = db.getAllInventory();
                if(c.moveToFirst()){
                    do{
                        if(c.getString(1).equals(name)){
                            int gtyDB = Integer.parseInt(c.getString(4));
                            if(qty != gtyDB){
                                db.updateInventoryItem(c.getLong(0), c.getString(1),
                                        c.getFloat(2), c.getString(3), qty, c.getString(5),
                                        c.getString(6), c.getString(7), c.getString(8));
                            }

                        }
                    }while(c.moveToNext());
                    db.close();
                }
            }
        });
        holder.subQty.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                View view = (View) v.getParent();
                TextView qtyView = (TextView)view.findViewById(R.id.qtyTV);
                TextView title = (TextView)view.findViewById(R.id.name);
                int qty = Integer.parseInt(qtyView.getText().toString());
                String name = title.getText().toString().substring(15);
                Cursor c;
                DBAdapter db = new DBAdapter(getContext());


                if(qty > 0) {
                    qty -= 1;
                    getItem(pos).setQty(qty);
                    db.open();
                    c = db.getAllInventory();
                    if (c.moveToFirst()) {
                        do {
                            if (c.getString(1).equals(name)) {
                                int gtyDB = Integer.parseInt(c.getString(4));
                                if (qty != gtyDB) {
                                    db.updateInventoryItem(c.getLong(0), c.getString(1),
                                            c.getFloat(2), c.getString(3), qty, c.getString(5),
                                            c.getString(6), c.getString(7), c.getString(8));
                                }

                            }
                        } while (c.moveToNext());
                        db.close();
                    }
                    qtyView.setText(String.valueOf(qty));
                }
            }
        });

        return convertView;
    }

}

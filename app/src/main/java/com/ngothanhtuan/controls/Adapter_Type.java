package com.ngothanhtuan.controls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ngothanhtuan.model.Products_Type;
import com.ngothanhtuan.productfarmmanager.R;

import java.util.ArrayList;

/**
 * Created by MyPC on 11/3/2016.
 */

public class Adapter_Type extends BaseAdapter {

    private Context context;
    private ArrayList<Products_Type> list;
    private LayoutInflater inflater;
    private View row;

    private TextView txtvID, txtvNameType;

    public Adapter_Type(Context context, ArrayList<Products_Type> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        addControls();
        addViews(position);
        return row;
    }

    private void addViews(int position) {
        Products_Type productsType = (Products_Type) getItem(position);
        txtvID.setText(productsType.getID_Type());
        txtvNameType.setText(productsType.getName_type());

    }

    private void addControls() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.custom_lv_type,null);

        txtvID = (TextView) row.findViewById(R.id.txtvID);
        txtvNameType = (TextView) row.findViewById(R.id.txtvNameType);
    }


}

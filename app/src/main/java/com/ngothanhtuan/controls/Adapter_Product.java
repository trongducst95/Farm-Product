package com.ngothanhtuan.controls;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ngothanhtuan.model.Products;
import com.ngothanhtuan.productfarmmanager.R;

import java.util.ArrayList;

/**
 * Created by MyPC on 10/29/2016.
 */

public class Adapter_Product extends BaseAdapter {

    private Context context;
    private ArrayList<Products> list;
    private View row;
    SQLite db;

    private ImageView imgvAvatar;
    private TextView txtvID,txtvType,txtvName_P,txtvPrice;

    public Adapter_Product(Context context, ArrayList<Products> list) {
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

    private void addControls() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.custom_lv_product,null);

        imgvAvatar = (ImageView) row.findViewById(R.id.imgvAvatar);

        txtvID = (TextView) row.findViewById(R.id.txtvID_P);
        txtvName_P = (TextView) row.findViewById(R.id.txtvName_P);
        txtvType = (TextView) row.findViewById(R.id.txtvType);
        txtvPrice = (TextView) row.findViewById(R.id.txtvPrice);

        db = new SQLite(context);
    }

    private void addViews(int position) {
        Products product = list.get(position);

        txtvID.setText(product.getID_Prodc());
        txtvName_P.setText(product.getName_Prodc());
        txtvType.setText(db.getType(product.getID_Type()));
        txtvPrice.setText(product.getPrice().toString());

        Bitmap bmAvatar = BitmapFactory.decodeByteArray(product.getImg(),0,product.getImg().length);
        imgvAvatar.setImageBitmap(bmAvatar);
    }
}

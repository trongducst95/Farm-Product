package com.ngothanhtuan.productfarmmanager;


import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ngothanhtuan.controls.Adapter_Product;
import com.ngothanhtuan.controls.SQLite;
import com.ngothanhtuan.model.Products;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    TextView txtvID,txtvName,txtvType,txtvCount,txtvPrice;
    ImageView imgD;

    SQLite db;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        addControls(view);
        addViews();
        return view;
    }

    private void addViews() {
        if (getArguments().getParcelable("product") != null) {
            Products products = getArguments().getParcelable("product");
            Bitmap bmAvatar = BitmapFactory.decodeByteArray(products.getImg(), 0, products.getImg().length);
            imgD.setImageBitmap(bmAvatar);
            txtvID.setText(products.getID_Prodc());
            txtvName.setText(products.getName_Prodc());
            txtvType.setText(db.getType(products.getID_Type()));
            txtvCount.setText(String.valueOf(products.getCount()));
            txtvPrice.setText(products.getPrice().toString());
        }
    }

    private void addControls(View view) {
        txtvID = (TextView) view.findViewById(R.id.txtvID);

        txtvName = (TextView) view.findViewById(R.id.txtvName);
        txtvType = (TextView) view.findViewById(R.id.txtvType);
        txtvCount = (TextView) view.findViewById(R.id.txtvCount);
        txtvPrice = (TextView) view.findViewById(R.id.txtvPrice);

        imgD = (ImageView) view.findViewById(R.id.imgD);

        db = new SQLite(getActivity());
    }
}

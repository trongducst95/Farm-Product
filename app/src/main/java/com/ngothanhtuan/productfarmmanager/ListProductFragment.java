package com.ngothanhtuan.productfarmmanager;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ngothanhtuan.controls.Adapter_Product;
import com.ngothanhtuan.controls.Adapter_Type;
import com.ngothanhtuan.controls.SQLite;
import com.ngothanhtuan.model.Products;
import com.ngothanhtuan.model.Products_Type;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListProductFragment extends Fragment {


    ListView lvProduct;
    ArrayList<Products> list;
    Adapter_Product adapter;

    int pos;
    int ID;
    SQLite db;

    public ListProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_product, container, false);

        ID = getArguments().getInt("ID");
        addControls(view);
        addEvents();

        return view;
    }

    private void addEvents() {
        list = db.getAllProduct(ID);
        adapter = new Adapter_Product(getActivity(),list);
        lvProduct.setAdapter(adapter);

        lvProduct.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                registerForContextMenu(lvProduct);
                getActivity().openContextMenu(lvProduct);
                return true;
            }
        });

    }

    private void addControls(View view) {
        lvProduct = (ListView) view.findViewById(R.id.lvProduct);
        list = new ArrayList<>();
        adapter = new Adapter_Product(getActivity(),list);
        lvProduct.setAdapter(adapter);

        db = new SQLite(getActivity());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Menu");
        menu.add(0,0,0,"View");
        menu.add(0,1,1,"Update");
        menu.add(0,2,2,"Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 0: break;
            case 1:
                Products products = list.get(pos);
                AddPrFragment addPrFragment = new AddPrFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("product",products);
                addPrFragment.setArguments(bundle);

                addPrFragment.setArguments(bundle);
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                transaction.replace(R.id.FrgList, addPrFragment);
                transaction.commit();
                break;
            case 2:
                Notication();
                break;

        }
        return super.onContextItemSelected(item);
    }

    private void Notication() {
        final Products products = list.get(pos);
        final AlertDialog.Builder b = new AlertDialog.Builder(getActivity());

        b.setTitle("Delete");
        b.setMessage("Are you sure?");
        b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            int i = 0;
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteProduct(products);
                list = db.getAllProduct(ID);
                adapter = new Adapter_Product(getActivity(),list);
                lvProduct.setAdapter(adapter);
            }
        });

        b.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        b.create().show();
    }
}

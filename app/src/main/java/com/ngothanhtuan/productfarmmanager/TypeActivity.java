package com.ngothanhtuan.productfarmmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.ngothanhtuan.controls.Adapter_Type;
import com.ngothanhtuan.controls.CustomActivity;
import com.ngothanhtuan.controls.SQLite;
import com.ngothanhtuan.model.Products_Type;

import java.util.ArrayList;

public class TypeActivity extends CustomActivity {

    private static final int REQUEST_CODE = 888;
    EditText edtIDType,edtNameT;
    Button btnSave,btnCancel;
    FloatingActionButton fab;
    SQLite db;

    ListView lvType;
    ArrayList<Products_Type> list;
    Adapter_Type adapter;

    int ID;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ID = bundle.getInt("ID");

        addLeft_menu();

        addControls();
        addEvents();

    }

    private void addEvents() {
        final Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();

        // set listview
        list = db.getAllType(bundle.getInt("ID"));
        adapter = new Adapter_Type(this,list);
        lvType.setAdapter(adapter);

        //set Button Save
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Products_Type type = new Products_Type();
                type.setID_Type(edtIDType.getText().toString());
                type.setName_type(edtNameT.getText().toString());
                if ("Save".equals(btnSave.getText()) == true || "Lưu".equals(btnSave.getText()) == true){
                    db.addType(type,ID);
                }

                else {
                    db.updateType(type);
                    btnSave.setText("Save");
                }
                edtIDType.setText("");
                edtNameT.setText("");
                list = db.getAllType(ID);
                adapter = new Adapter_Type(TypeActivity.this,list);
                lvType.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }
        });

        //set Button Cancel
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(TypeActivity.this,ProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ID",ID);
                intent1.putExtras(bundle);
                startActivityForResult(intent1,REQUEST_CODE);
                startActivity(intent1);
            }
        });

        lvType.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                registerForContextMenu(lvType);
                openContextMenu(lvType);
                return true;
            }
        });

    }

    private void addControls() {
        edtIDType = (EditText) findViewById(R.id.edtIDType);
        edtNameT = (EditText) findViewById(R.id.edtNameT);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        lvType = (ListView) findViewById(R.id.lvType);

        db = new SQLite(this);
    }

    private void addLeft_menu() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_type) {
        } else if (id == R.id.nav_product) {
            Intent intent = new Intent(this,ProductActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("ID",ID);
            intent.putExtras(bundle);
            startActivity(intent);

        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(this,MapsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("ID",ID);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Menu");
        menu.add(0,0,0,"Update");
        menu.add(0,1,1,"Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 0:
                Products_Type productsType = list.get(pos);
                edtIDType.setText(productsType.getID_Type());
                edtIDType.setEnabled(false);
                edtNameT.setText(productsType.getName_type());
                btnSave.setText("Update");
                break;
            case 1:
                Notication();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void Notication() {
        final Products_Type productsType = list.get(pos);
        final AlertDialog.Builder b = new AlertDialog.Builder(TypeActivity.this);

        b.setTitle("Delete");
        b.setMessage("Are you sure?");
        b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            int i = 0;
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteType(productsType);
                list = db.getAllType(ID);
                adapter = new Adapter_Type(TypeActivity.this,list);
                lvType.setAdapter(adapter);
            }
        });

        b.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        b.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        MenuItem add = menu.findItem(R.id.ItemAdd);
        add.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }
}

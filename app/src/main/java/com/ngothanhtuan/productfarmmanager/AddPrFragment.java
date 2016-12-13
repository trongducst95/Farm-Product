package com.ngothanhtuan.productfarmmanager;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ngothanhtuan.controls.SQLite;
import com.ngothanhtuan.model.Products;
import com.ngothanhtuan.model.Products_Type;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddPrFragment extends Fragment {
    ImageView imgView;
    Spinner spType;
    EditText edtName, edtCount, edtPrice;
    Button btnAddPr,btnCancel, btnChooseImg, btnTakeImg;

    ArrayList<Products_Type> arrayList_Type;
    ArrayAdapter<Products_Type> arrayAdapter_Type;

    SQLite db;
    int ID;
    String ID_pr;
    int pos;

    final int RESQUEST_TAKE_PHOTO = 123;
    final int RESQUEST_CHOOSE_PHOTO = 321;

    public AddPrFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        addControls(view);
        addEvents();
        return view;
    }

    private void addEvents() {
        ID = getArguments().getInt("ID");
        if (getArguments().getParcelable("product") != null){
            btnAddPr.setText("Save");
            Products products = getArguments().getParcelable("product");
            ID = products.getID_User();
            ID_pr = products.getID_Prodc();
            Bitmap bmAvatar = BitmapFactory.decodeByteArray(products.getImg(),0,products.getImg().length);
            imgView.setImageBitmap(bmAvatar);
            edtName.setText(products.getName_Prodc());
            edtCount.setText(String.valueOf(products.getCount()));
            edtPrice.setText(products.getPrice().toString());
        }


        arrayList_Type = db.getAllType(ID);
        Products_Type a = new Products_Type("test","Chọn Type....");
        arrayList_Type.add(0,a);
        arrayAdapter_Type = new ArrayAdapter<Products_Type>(getActivity(),android.R.layout.simple_spinner_item, arrayList_Type);

        arrayAdapter_Type.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spType.setAdapter(arrayAdapter_Type);

        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               pos= position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnTakeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        btnChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });

        btnAddPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String random;
                do {
                    random = random();
                }while (db.Test_ID(random));
                Products products = new Products();
                products.setID_Prodc(random);
                products.setID_Type(arrayList_Type.get(pos).getID_Type());
                products.setName_Prodc(edtName.getText().toString());
                products.setImg(ImageView_To_Byte(imgView));
                products.setCount(Integer.parseInt(edtCount.getText().toString()));
                products.setPrice(Long.parseLong(edtPrice.getText().toString()));
                if ("Save".equals(btnAddPr.getText())){
                    db.updateDataProduct(products,ID_pr);
                    btnAddPr.setText("Add");
                    Toast.makeText(getActivity(), "Update thành công", Toast.LENGTH_SHORT).show();
                }
                else {
                    db.addProduct(products,ID);
                    Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                }

                Cancel();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Cancel();
            }
        });
    }

    private void addControls(View view) {
        imgView = (ImageView) view.findViewById(R.id.imgView);

        spType = (Spinner) view.findViewById(R.id.spType);

        edtName = (EditText) view.findViewById(R.id.edtName);
        edtCount = (EditText) view.findViewById(R.id.edtCount);
        edtPrice = (EditText) view.findViewById(R.id.edtPrice);

        btnAddPr = (Button) view.findViewById(R.id.btnAddP);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnChooseImg = (Button) view.findViewById(R.id.btnUpImg);
        btnTakeImg = (Button) view.findViewById(R.id.btnCam);

        db = new SQLite(getActivity());
    }

    private void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,RESQUEST_TAKE_PHOTO);
    }

    private void choosePhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, RESQUEST_CHOOSE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESQUEST_CHOOSE_PHOTO && resultCode == getActivity().RESULT_OK){
                Uri imageUri = data.getData();
            InputStream is = null;
            try {
                is = getActivity().getContentResolver().openInputStream(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(is);
                imgView.setImageBitmap(bitmap);
        }
        else if (requestCode == RESQUEST_TAKE_PHOTO && resultCode == getActivity().RESULT_OK){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgView.setImageBitmap(bitmap);
        }
    }

    public String random(){
        int passwordSize = 5;
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < passwordSize; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    public byte[] ImageView_To_Byte(ImageView imgv){

        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public void Cancel(){
        ListProductFragment listProductFragment = new ListProductFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("ID",ID);

        listProductFragment.setArguments(bundle);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.FrgList, listProductFragment);
        transaction.commit();
    }

}

package com.ngothanhtuan.productfarmmanager;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.ngothanhtuan.controls.CustomActivity;
import com.ngothanhtuan.controls.SQLite;
import com.ngothanhtuan.model.User;

public class MainActivity extends CustomActivity {
    private static final int REQUEST_CODE = 1;
    EditText edtUser,edtPass;
    Button btnLogin;
    TextView txtvSignUp;

    SQLite db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvent();
    }


    private void addEvent() {
        txtvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.animation_in_right,R.anim.animation_out_left);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = db.getUser(edtUser.getText().toString());
                if (user.getIP() == null)
                {
                    Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
                    edtUser.startAnimation(shake);
                    Toast.makeText(MainActivity.this, "Incorrect account!!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (edtPass.getText().toString().equals(user.getPassword()))
                    {
                        Intent intent = new Intent(MainActivity.this,ProductActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("ID",db.getID(user.getIP()));
                        intent.putExtras(bundle);
                        startActivityForResult(intent,REQUEST_CODE);
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
                        edtPass.startAnimation(shake);
                    }
                }
            }
        });

    }

    private void addControls() {
        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPass = (EditText) findViewById(R.id.edtPass);

        btnLogin = (Button) findViewById(R.id.btnLogin);

        txtvSignUp = (TextView) findViewById(R.id.txtvSignUp);

        db = new SQLite(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        MenuItem add = menu.findItem(R.id.ItemAdd);
        add.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }
}

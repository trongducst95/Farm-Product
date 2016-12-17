package com.ngothanhtuan.productfarmmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ngothanhtuan.controls.SQLite;
import com.ngothanhtuan.model.User;

public class SignUpActivity extends AppCompatActivity {

    Button btnBack,btnSignUp;
    EditText edtUser,edtPass,edtPassAgain;
    SQLite db;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        addControls();
        addEvents();

    }

    private void addEvents() {
        //Event Button SignUp
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (test_null() == true)
                    test_exists();
            }
        });

        // Event Button Back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.animation_in_left,R.anim.animation_out_right);
            }
        });
    }

    private void addControls() {
        btnBack = (Button) findViewById(R.id.btnBack);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPass = (EditText) findViewById(R.id.edtPass);
        edtPassAgain = (EditText) findViewById(R.id.edtPassAgain);

        db = new SQLite(this);
    }

    private Boolean test_null() {
        if (edtUser.length() == 0){
            Toast.makeText(SignUpActivity.this, "Username can't null!!!", Toast.LENGTH_SHORT).show();
            Animation shake = AnimationUtils.loadAnimation(SignUpActivity.this, R.anim.shake);
            edtUser.startAnimation(shake);
            return false;
        }
        else {
            if (edtPass.length() == 0){
                Toast.makeText(SignUpActivity.this, "Password can't null!!!", Toast.LENGTH_SHORT).show();
                Animation shake = AnimationUtils.loadAnimation(SignUpActivity.this, R.anim.shake);
                edtPass.startAnimation(shake);
                return false;
            }
            else {
                if (!edtPass.getText().toString().equals(edtPassAgain.getText().toString())){
                    Toast.makeText(SignUpActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                    Animation shake = AnimationUtils.loadAnimation(SignUpActivity.this, R.anim.shake);
                    edtPassAgain.startAnimation(shake);
                    return false;
                }
            }
        }
        return true;
    }

    private void test_exists() {
        user = new User();
        if (db.Test_IP(edtUser.getText().toString())) {
            Toast.makeText(SignUpActivity.this, "Username exists!!!", Toast.LENGTH_SHORT).show();
            Animation shake = AnimationUtils.loadAnimation(SignUpActivity.this, R.anim.shake);
            edtUser.startAnimation(shake);
        }
        else {
            user.setIP(edtUser.getText().toString());
            user.setPassword(edtPass.getText().toString());
            db.addUsers(user);
            Toast.makeText(this, "Sign Up complete!!!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        MenuItem add = menu.findItem(R.id.ItemAdd);
        add.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

     @Override
    public boolean sua(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        MenuItem add = menu.findItem(R.id.ItemAdd);
        add.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }
}

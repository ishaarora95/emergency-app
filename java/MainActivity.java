package com.example.isha.emergency;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    DBAdapter db;
    static String user="";
    String pass="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        db=new DBAdapter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void login(View v){
        user=username.getText().toString().trim();
        pass=password.getText().toString().trim();
        if(user.equals("") || pass.equals("")){
            Toast.makeText(getApplicationContext(),"Enter both username & password",Toast.LENGTH_LONG).show();
        }
        else{
            if(db.usercheck(user)){
                Toast.makeText(getApplicationContext(),"Login ",Toast.LENGTH_LONG).show();
                if(db.logincheck(user,pass)){
                    Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_LONG).show();
                   Intent intent=new Intent(getApplicationContext(),Home_Page.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Login Failure",Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(getApplicationContext(),"Invalid Username",Toast.LENGTH_LONG).show();
            }
        }
    }
    public void signup(View v){
        user=username.getText().toString().trim();
        pass=password.getText().toString().trim();
        if(user.equals("") || pass.equals("")){
            Toast.makeText(getApplicationContext(),"Enter both username & password",Toast.LENGTH_LONG).show();
        }
        else{
            if(!db.usercheck(user)){
                db.insert(user,pass);
                Toast.makeText(getApplicationContext(),"Signup Success",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"Username already in user", Toast.LENGTH_LONG).show();
            }
        }
    }
}

package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import java.util.*;

public class log_in extends AppCompatActivity {

    private EditText userName,password;

    private Button logIn;

    private TextView signUp;

    public static int userId = 0;

    String Username ,passWord,conPass,Email_Address,phone;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        logIn = findViewById(R.id.button3);
        signUp = findViewById(R.id.signUpLink);

        loadCurrentUser();
        if (Username!=null)
        {
            userName.setText(Username);
        }


        //adding testing accounts
        Account acc1 = new Account("hady","1234","0123456789","hady@gmail.com");
        Account acc2 = new Account("joe","1234","012387789","joe@gmail.com");
        Account acc3 = new Account("seka","1234","01234566789","seka@gmail.com");
        Account.users.add(acc1);
        Account.users.add(acc2);
        Account.users.add(acc3);


        Account adm = new Account("admin","12345","0123456789","admin@gmail.com");

        Account.admins.add(adm);
        //end of adding testing accounts

        loadCurrentUser();

        logIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Activity_Navigation(0);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Activity_Navigation(1);
            }
        });
    }
    public void Activity_Navigation(int i)
    {
        if (i==1)
        {
            Intent intent = new Intent(this , SignUpActivity.class);
            startActivity(intent);
        }
        else
        {
            int login = login();
            switch (login)
            {
                case 1:
                {
                    Toast.makeText(log_in.this, "logged in succesfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("email", Account.users.get(userId).getE_mail());
                    intent.putExtra("phone", Account.users.get(userId).getPhone());
                    startActivity(intent);
                    break;
                }
                case 2:
                {
                    Toast.makeText(this, "Admin logged in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, Admin.class);
                    startActivity(intent);
                    break;
                }
                case 0:
                    Toast.makeText(this, "wrong username or password try Again", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }
    public int login()
    {

        String userText = userName.getText().toString();
        String passText = password.getText().toString();

        for (int i =0 ; i<Account.users.size() ; ++i)
        {
            if (userText.equals(Account.users.get(i).getName())&&passText.equals(Account.users.get(i).getPass()))
            {
                userId = i ;
                return 1;
            }
        }
        for (int i =0 ; i<Account.admins.size() ; ++i)
        {
            if (userText.equals(Account.admins.get(i).getName())&&passText.equals(Account.admins.get(i).getPass()))
            {
                userId = i ;
                return 2;
            }
        }
        return 0;
    }



    private void loadCurrentUser()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Username      = sharedPreferences.getString("username", null);
        passWord      = sharedPreferences.getString("password", null);
        phone         = sharedPreferences.getString("phone", null);
        Email_Address = sharedPreferences.getString("email", null);

        if(Username != null)
        {
            Account current =  new Account(Username,passWord,phone,Email_Address);
            Account.users.add(current);
        }

        Log.e( "loadData: ", Account.users.toString());
    }




}
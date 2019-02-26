package com.example.sqlliteapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sqlliteapp.models.User;
import com.squareup.picasso.Picasso;

public class data_user extends AppCompatActivity {
    User usuario;
    TextView name,last,postcode,address,email,userName,birthday,phone,cell;
    ImageView imageView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_user);
        int  position = getIntent().getIntExtra("position",-1);
        if (  position != -1  ) {
             usuario = User.dataDB.get(position);
        }else{
            finish();
        }

        name = findViewById(R.id.nameTextView_data);
        last = findViewById(R.id.lastTextView_data);
        address = findViewById(R.id.addressTextView_data);
        email = findViewById(R.id.emailTextView_data);
        userName = findViewById(R.id.userTextView_data);
        birthday = findViewById(R.id.birthdayTextView_data);
        phone = findViewById(R.id.phoneTextView_data);
        cell = findViewById(R.id.cellTextView_data);

        name.setText(usuario.getName());
        last.setText(usuario.getLast());
        address.setText(usuario.getStreet() + " " +
                usuario.getCity() + " " +
                usuario.getState() + " " +
                usuario.getPostcode() + " " );
        email.setText(usuario.getEmail());
        userName.setText(usuario.getUser());
        birthday.setText(usuario.getBirthday());
        phone.setText(usuario.getPhone());
        cell.setText(usuario.getCell());

        imageView = findViewById(R.id.imageUser_data);
        Picasso.get().load(usuario.getPicture()).into(imageView);
    }
}

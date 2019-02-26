package com.example.sqlliteapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sqlliteapp.DatabaseSQLlite.MySQLLiteHelper;
import com.example.sqlliteapp.api.RandomAPI;
import com.example.sqlliteapp.models.Dob;
import com.example.sqlliteapp.models.Info;
import com.example.sqlliteapp.models.Location;
import com.example.sqlliteapp.models.Login;
import com.example.sqlliteapp.models.Name;
import com.example.sqlliteapp.models.Picture;
import com.example.sqlliteapp.models.User;
import com.example.sqlliteapp.models.UserEntry;
import com.example.sqlliteapp.utils.NetworkHelper;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private OkHttpClient okHttpClient;
    private RandomAPI randomAPI;
    private TextView nameTextView,lastTextView,addressTextview;

    private Name nameO = new Name();
    private Picture pictureO = new Picture();
    private Location locationO = new Location();
    private Dob dobO = new Dob();
    private Login loginO = new Login();
    private Info infoO = new Info();
    private String phone,cell,email;

    private MySQLLiteHelper sqlHelper;
    private SQLiteDatabase database;
    private ArrayList<User> userBD = new ArrayList<User>();

    public static final String TAG="MainActivity_TAG";

    private Button buttonNext,buttonSave,buttonShowDataBase ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createView();
        createMethodsDefault();
        GetUser();
    }

    private void createMethodsDefault() {
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performNetworkCall(view);
            }
        });
        buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewUser();
            }
        });
        buttonShowDataBase = findViewById(R.id.button_show_data);
        buttonShowDataBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userBD = getUser();
                Intent openActivity = new Intent(MainActivity.this, list_data.class);
                User.dataDB = getUser();
                startActivity(openActivity);
            }
        });

        Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView);

        sqlHelper = new MySQLLiteHelper(getApplicationContext());
        database = sqlHelper.getWritableDatabase();

    }

    private void createView() {
        imageView = findViewById(R.id.imageUser);
        nameTextView = findViewById(R.id.nameTextView);
        lastTextView = findViewById(R.id.lastTextView);
        addressTextview = findViewById(R.id.addressTextView);
        buttonNext = findViewById(R.id.button_next);
    }


    private void GetUser() {
        okHttpClient = new OkHttpClient();
        randomAPI = NetworkHelper.createRandomAPI();
    }

    public void performNetworkCall(View view) {
        Request request = new Request.Builder().url(NetworkHelper.BASE_URL).build();
        buttonNext.setEnabled(false);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.body() != null) {
                    final String json = response.body().string();
                    try {
                        JSONObject apiResponse = new JSONObject(json);
                        JSONArray nameArray = apiResponse.getJSONArray("results");

                        JSONObject nameObject = nameArray.getJSONObject(0).getJSONObject("name");
                        JSONObject pictureObject = nameArray.getJSONObject(0).getJSONObject("picture");
                        JSONObject locationObject = nameArray.getJSONObject(0).getJSONObject("location");
                        JSONObject dobObject = nameArray.getJSONObject(0).getJSONObject("location");
                        JSONObject loginObject = nameArray.getJSONObject(0).getJSONObject("login");
                        email  = nameArray.getJSONObject(0).getString("email");
                        phone  = nameArray.getJSONObject(0).getString("phone");
                        cell= nameArray.getJSONObject(0).getString("cell");
                        JSONObject infoObject = apiResponse.getJSONObject("info");

                        Gson gson = new Gson();
                        nameO = gson.fromJson(nameObject.toString(), Name.class);
                        pictureO = gson.fromJson(pictureObject.toString(), Picture.class);
                        locationO = gson.fromJson(locationObject.toString(), Location.class);
                        dobO = gson.fromJson(dobObject.toString(), Dob.class);
                        loginO = gson.fromJson(loginObject.toString(), Login.class);
                        infoO = gson.fromJson(infoObject.toString(), Info.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    final Picture objectPicture = pictureO;
                    final Name objectName = nameO;
                    final Location objectLocation = locationO;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           try {
                               Picasso.get().load(objectPicture.getLarge()).into(imageView);
                               nameTextView.setText( objectName.getFirst());
                               lastTextView.setText(objectName.getLast());
                               addressTextview.setText(objectLocation.getStreet() + " " +
                                       objectLocation.getCity() + " " +
                                       objectLocation.getState() + " " +
                                       objectLocation.getPostcode() + " " );
                               buttonNext.setEnabled(true);
                           }catch (Exception ex){

                           }

                        }
                    });
                }
            }
        });
    }

    private void addNewUser(){
        try {

            long result =database.insert(UserEntry.Tablename , null, preparedNewUser(infoO.getSeed(),
                    nameO.getFirst(),
                    nameO.getLast(),
                    locationO.getPostcode(),
                    locationO.getStreet(),
                    locationO.getState(),
                    locationO.getCity(),
                    email,
                    loginO.getUsername(),
                    dobO.getDate()== null ? "": dobO.getDate(),
                    phone,
                    cell,
                    pictureO.getLarge()));
            if (result>0){
                Log.d(TAG, "addNewUser: add");
            }else  {
                Log.d(TAG, "addNewUser: No add");
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private ArrayList<User> getUser () {
        String[] columns = {UserEntry._ID,
                UserEntry.columnname,
                UserEntry.lastColumn,
                UserEntry.postCodeColumn,
                UserEntry.streetColumn,
                UserEntry.stateColumn,
                UserEntry.cityColumn,
                UserEntry.emailColumn,
                UserEntry.userNameColumn,
                UserEntry.birthdayColumn,
                UserEntry.phoneColumn,
                UserEntry.cellPhoneColumn,
                UserEntry.pictureColumn};
        Cursor cursor = database.query(UserEntry.Tablename, columns, null, null, null, null, null);
        ArrayList<User> users = new ArrayList<User>();


        Log.d(TAG, "getUser: " + cursor.getCount());

        for (int  i=0; i< cursor.getCount();i++){
            cursor.moveToPosition(i);
            User user = new User(cursor.getString(cursor.getColumnIndexOrThrow(UserEntry._ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(UserEntry.columnname)),
                    cursor.getString(cursor.getColumnIndexOrThrow(UserEntry.lastColumn)),
                    cursor.getString(cursor.getColumnIndexOrThrow(UserEntry.postCodeColumn)),
                    cursor.getString(cursor.getColumnIndexOrThrow(UserEntry.streetColumn)),
                    cursor.getString(cursor.getColumnIndexOrThrow(UserEntry.stateColumn)),
                    cursor.getString(cursor.getColumnIndexOrThrow(UserEntry.cityColumn)),
                    cursor.getString(cursor.getColumnIndexOrThrow(UserEntry.emailColumn)),
                    cursor.getString(cursor.getColumnIndexOrThrow(UserEntry.userNameColumn)),
                    cursor.getString(cursor.getColumnIndexOrThrow(UserEntry.birthdayColumn)),
                    cursor.getString(cursor.getColumnIndexOrThrow(UserEntry.phoneColumn)),
                    cursor.getString(cursor.getColumnIndexOrThrow(UserEntry.cellPhoneColumn)),
                    cursor.getString(cursor.getColumnIndexOrThrow(UserEntry.pictureColumn)) );
            users.add(user);
        }

        cursor.close();

        return users;

    }

    private ContentValues preparedNewUser(String ...arg) {
        ContentValues  cv= new ContentValues();
        cv.put(UserEntry._ID, arg[0]);
        cv.put(UserEntry.columnname, arg[1]);
        cv.put(UserEntry.lastColumn, arg[2]);
        cv.put(UserEntry.postCodeColumn, arg[3]);
        cv.put(UserEntry.streetColumn, arg[4]);
        cv.put(UserEntry.stateColumn, arg[5]);
        cv.put(UserEntry.cityColumn, arg[6]);
        cv.put(UserEntry.emailColumn, arg[7]);
        cv.put(UserEntry.userNameColumn, arg[8]);
        cv.put(UserEntry.birthdayColumn, arg[9]);
        cv.put(UserEntry.phoneColumn, arg[10]);
        cv.put(UserEntry.cellPhoneColumn, arg[11]);
        cv.put(UserEntry.pictureColumn, arg[12]);

        return cv;
    }


}

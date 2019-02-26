package com.example.sqlliteapp.models;

import android.provider.BaseColumns;

public final  class UserEntry  implements BaseColumns {
    public static final String  Tablename = "users";
    //Name
    public static final String  columnname = "name";
    public static final String  lastColumn = "last";
    //Address
    public static final String  streetColumn = "street";
    public static final String  postCodeColumn = "postcode";
    public static final String  stateColumn = "state";
    public static final String  cityColumn = "city";
    //dataLogin
    public static final String  emailColumn = "email";
    public static final String  userNameColumn = "user";
    //dataUser
    public static final String  birthdayColumn = "birthday";
    public static final String  phoneColumn = "phone";
    public static final String  cellPhoneColumn = "cell";
    public static final String pictureColumn ="picture";


}
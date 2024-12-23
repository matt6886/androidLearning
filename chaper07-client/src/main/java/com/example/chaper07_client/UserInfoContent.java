package com.example.chaper07_client;

import android.net.Uri;
import android.provider.BaseColumns;

public class UserInfoContent implements BaseColumns {
    public static final String AUTHORITIES = "com.example.chaper07_server.provider.UserInfoProvider";

    //content://com.example.chaper07_server.provider.UserInfoProvider/user

    //access to the uri of content provider
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITIES + "/user");

    public static final String USER_NAME = "name";
    public static final String USER_AGE = "age";
    public static final String USER_HEIGHT = "height";
    public static final String USER_WEIGHT = "weight";
    public static final String USER_MARRIED = "married";

}


package com.example.chaper07_server.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.chaper07_server.UserInfoContent;
import com.example.chaper07_server.database.UserDbHelper;

public class UserInfoProvider extends ContentProvider {
    private UserDbHelper mDBHelper;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int USERS = 1;
    private static final int USER = 2;

    static {
        // Add specific data path in Uri matcher
        URI_MATCHER.addURI(UserInfoContent.AUTHORITIES, "/user", USERS);
        URI_MATCHER.addURI(UserInfoContent.AUTHORITIES, "user/#", USER);
    }

    public UserInfoProvider() {
    }

    @Override
    public boolean onCreate() {
        Log.d("Matt", "UserInfoProvider: onCreate");
        mDBHelper = UserDbHelper.getInstance(getContext());
        return true;
    }

    // content://com.example.chaper07_server.provider.UserInfoProvider/user
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d("Matt", "UserInfoProvider: insert");
        if (URI_MATCHER.match(uri) == USERS) {
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            db.insert(UserDbHelper.TABLE_NAME, null, values);
        }
        return uri;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.d("Matt", "UserInfoProvider: query");
        if (URI_MATCHER.match(uri) == USERS) {
            SQLiteDatabase db = mDBHelper.getReadableDatabase();
            return db.query(UserDbHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d("Matt", "UserInfoProvider: delete");
        int count = 0;
        if (URI_MATCHER.match(uri) == USERS) {
            // content://com.example.chaper07_server.provider.UserInfoProvider/user
            // delete multiple rows
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            count  = db.delete(UserDbHelper.TABLE_NAME, selection, selectionArgs);
            db.close();
        } else if (URI_MATCHER.match(uri) == USER) {
            // content://com.example.chaper07_server.provider.UserInfoProvider/user/2
            String id = uri.getLastPathSegment();
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            count = db.delete(UserDbHelper.TABLE_NAME, "_id=?", new String[]{id});
            db.close();
        }
        return count;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
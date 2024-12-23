package com.example.chaper07_client;

import android.annotation.SuppressLint;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chaper07_client.entity.Contact;

import java.util.ArrayList;
import java.util.Locale;

public class ContactAddActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_phone;
    private EditText et_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        et_email = findViewById(R.id.et_email);

        findViewById(R.id.btn_add_contacts).setOnClickListener(this);
        findViewById(R.id.btn_retrieve_contacts).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_add_contacts) {
            Contact contact = new Contact();
            contact.name = et_name.getText().toString();
            contact.phone = et_phone.getText().toString();
            contact.email = et_email.getText().toString();

            // method 1: use ContentResolver to write, one field fpr one time
            // addContacts(getContentResolver(), contact);
            // method 2: use ContentProviderOperation to add all data with batch way
            addFullContact(getContentResolver(), contact);

        } else if (v.getId() == R.id.btn_retrieve_contacts) {
            readPhoneContacts(getContentResolver());
        }
    }

    @SuppressLint("Range")
    private void readPhoneContacts(ContentResolver contentResolver) {
        // 先查询raw_contacts 表，再根据raw_contacts_id去查data表
        Cursor cursor = contentResolver.query(ContactsContract.RawContacts.CONTENT_URI, new String[]{ContactsContract.RawContacts._ID}, null, null, null);
        while (cursor.moveToNext()) {
            int rawContactId = cursor.getInt(0);
            Uri uri = Uri.parse("content://com.android.contacts/contacts/" + rawContactId + "/data");
            Cursor dataCursor = contentResolver.query(uri, new String[]{
                    ContactsContract.Contacts.Data.MIMETYPE,
                    ContactsContract.Contacts.Data.DATA1,
                    ContactsContract.Contacts.Data.DATA2
            }, null, null, null);
            Contact contact = new Contact();
            while (dataCursor.moveToNext()) {
                String data1 = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.Contacts.Data.DATA1));
                String mimeType = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.Contacts.Data.MIMETYPE));
                switch (mimeType) {
                    case ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE:
                        contact.name = data1;
                        break;
                    case ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE:
                        contact.phone = data1;
                        break;
                    case ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE:
                        contact.email = data1;
                        break;
                }
            }
            dataCursor.close();
            if (contact.name != null) {
                Log.d("Matt", contact.toString());
            }
        }
        cursor.close();
    }

    private void addFullContact(ContentResolver contentResolver, Contact contact) {
        // 创建一个插入联系人主记录的内容操作器
        ContentProviderOperation cp_main = ContentProviderOperation
                .newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build();

        // 创建一个插入联系人姓名记录的操作器
        ContentProviderOperation cp_name = ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                // 将第0个操作的id，即raw_contacts的id作为data表中的raw_contact_id
                .withValueBackReference(ContactsContract.Contacts.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.Contacts.Data.DATA2, contact.name)
                .build();

        // 创建一个插入联系人电话号码记录的操作器
        ContentProviderOperation cp_phone = ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                // 将第0个操作的id，即raw_contacts的id作为data表中的raw_contact_id
                .withValueBackReference(ContactsContract.Contacts.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.Contacts.Data.DATA1, contact.phone)
                .withValue(ContactsContract.Contacts.Data.DATA2, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .build();

        // 创建一个插入联系人邮箱记录的操作器
        ContentProviderOperation cp_email = ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                // 将第0个操作的id，即raw_contacts的id作为data表中的raw_contact_id
                .withValueBackReference(ContactsContract.Contacts.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.Contacts.Data.DATA1, contact.email)
                .withValue(ContactsContract.Contacts.Data.DATA2, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                .build();

        ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        operations.add(cp_main);
        operations.add(cp_name);
        operations.add(cp_phone);
        operations.add(cp_email);

        try {
            contentResolver.applyBatch(ContactsContract.AUTHORITY, operations);
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }

    private void addContacts(ContentResolver contentResolver, Contact contact) {
        ContentValues values = new ContentValues();
        Uri uri = contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI, values);
        assert uri != null;
        long rawContactId = ContentUris.parseId(uri);

        ContentValues name = new ContentValues();
        // 关联联系人编号
        name.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
        // 姓名的数据类型
        name.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        // 联系人的姓名
        name.put(ContactsContract.Contacts.Data.DATA2, contact.name);
        // 往提供器里面添加联系人姓名记录
        contentResolver.insert(ContactsContract.Data.CONTENT_URI, name);

        ContentValues phone = new ContentValues();
        // 关联电话号码编号
        phone.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
        // 电话号码的数据类型
        phone.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        // 电话号码
        phone.put(ContactsContract.Contacts.Data.DATA1, contact.phone);
        // 联系人类型 1表示家庭， 2表示工作
        phone.put(ContactsContract.Contacts.Data.DATA2, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        // 往提供器里面添加电话号码记录
        contentResolver.insert(ContactsContract.Data.CONTENT_URI, phone);

        ContentValues email = new ContentValues();
        // 关联电子邮箱
        email.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
        // 电子邮箱类型
        email.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
        // 联系人的电子邮箱
        email.put(ContactsContract.Contacts.Data.DATA1, contact.email);
        // 电子邮箱类型 1家庭，2工作
        email.put(ContactsContract.Contacts.Data.DATA2, ContactsContract.CommonDataKinds.Email.TYPE_WORK);
        // 往提供器里面添加联系人姓名记录
        contentResolver.insert(ContactsContract.Data.CONTENT_URI, email);
    }
}
package com.itel.smartkey.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

import com.itel.smartkey.bean.ContactsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取联系人数据库中的联系人数据的工具类
 * Created by huorong.liang on 2017/1/6.
 */

public class ContactsUtils {

    private static Uri contactsUri = ContactsContract.Contacts.CONTENT_URI;
    private static Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

    public static List<ContactsBean> getContacts(Context context, List<ContactsBean> list){
        ArrayList<ContactsBean> mList = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();
//        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions((Activity) context, new String[] {Manifest.permission.WRITE_CONTACTS}, 1);
//        }else{
//            Log.d("LHRTAG", "还没有授予权限");
//        }
        Cursor contactsCursor = resolver.query(contactsUri, null, null, null, null);

        while(contactsCursor.moveToNext()){
            ContactsBean bean = new ContactsBean();
            int _id = contactsCursor.getInt(contactsCursor.getColumnIndex(ContactsContract.Contacts._ID));//获取联系人的id
            String name = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));//获取联系人的姓名
            bean.setName(name);
            bean.setContactsBitmap(getContactPhotoAsBitmap(resolver, _id + ""));
            bean.setContactsPhotoBytes(getContactPhotoAsbytes(resolver, _id + ""));
//            bean.setContactsPhotoString(new String(getContactPhotoAsbytes(resolver, _id + "")));

            //根据联系人的_id(此处获取的_id和raw_contact_id相同，因为contacts表就是通过这两个字段和raw_contacts表取得联系)查询电话
            //查询电话
            Cursor phoneCursor = resolver.query(phoneUri, new String[]{ContactsContract.CommonDataKinds.Phone.DATA1}, "raw_contact_id = ?", new String[]{_id+""}, null);
            ArrayList<String> phones = new ArrayList<>();
            while(phoneCursor.moveToNext()){
                String phone = "";
                phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA1));
                phones.add(phone);
            }
            bean.setPhones(phones);
            phoneCursor.close();
            phoneCursor = null;
            mList.add(bean);
        }
        for (int i = 0; i < mList.size(); i++){
            list.add(mList.get(i));
        }
        return list;
    }


    /**
     * 根据联系人id获取联系人的头像,返回bitmap形式
     */
    public static Bitmap getContactPhotoAsBitmap(final ContentResolver contentResolver, String contactId) {
        Bitmap photo = null;
        Cursor dataCursor = contentResolver.query(ContactsContract.Data.CONTENT_URI,
                new String[]{"data15"},
                ContactsContract.Data.CONTACT_ID + "=?" + " AND "
                        + ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE + "'",
                new String[]{String.valueOf(contactId)}, null);
        if (dataCursor != null) {
            if (dataCursor.getCount() > 0) {
                dataCursor.moveToFirst();
                byte[] bytes = dataCursor.getBlob(dataCursor.getColumnIndex("data15"));
                if (bytes != null) {
                    photo = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                }
            }
            dataCursor.close();
        }
        return photo;
    }



    /**
     * 根据联系人id获取联系人的头像,返回bytes[]形式
     */
    public static byte[] getContactPhotoAsbytes(final ContentResolver contentResolver, String contactId) {
        Bitmap photo = null;
        Cursor dataCursor = contentResolver.query(ContactsContract.Data.CONTENT_URI,
                new String[]{"data15"},
                ContactsContract.Data.CONTACT_ID + "=?" + " AND "
                        + ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE + "'",
                new String[]{String.valueOf(contactId)}, null);
        if (dataCursor != null) {
            if (dataCursor.getCount() > 0) {
                dataCursor.moveToFirst();
                byte[] bytes = dataCursor.getBlob(dataCursor.getColumnIndex("data15"));
                if (bytes != null) {
                    return bytes;
                }
            }
            dataCursor.close();
        }
        return null;
    }


}

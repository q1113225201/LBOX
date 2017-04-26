package com.sjl.lbox.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.sjl.lbox.app.ui.CustomView.contact.bean.PhoneContact;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by yanfa on 2016/7/18.
 */
public class ContactUtil {
    private static final String[] PHONES_PROJECTION = {
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };
    /**
     * 联系人显示名称
     **/
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;
    /**
     * 电话号码
     **/
    private static final int PHONES_NUMBER_INDEX = 1;

    /**
     * 得到所有的手机号码联系人信息
     **/
    public static List<PhoneContact> getAllPhoneContacts(Context context) {
        List<PhoneContact> phoneContactList = getPhoneContact(context);
        List<PhoneContact> simContactList = getSIMContacts(context);
        for (PhoneContact contact : simContactList) {
            if (!isContainObject(phoneContactList, contact)) {
                phoneContactList.add(contact);
            }
        }
        return phoneContactList;
    }

    /**
     * 某个联系人是否已经存在于联系人列表中
     */
    public static boolean isContainObject(List<PhoneContact> phoneContactList, PhoneContact contact) {
        for (int i = 0; i < phoneContactList.size(); i++) {
            PhoneContact curContact = phoneContactList.get(i);
            if (curContact.getMobile().equals(contact.getMobile()) && curContact.getName().equals(contact.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取手机通讯录联系人
     *
     * @param context
     * @return
     */
    public static List<PhoneContact> getPhoneContact(Context context) {
        List<PhoneContact> list = new ArrayList<PhoneContact>();
        try {
            ContentResolver contentResolver = context.getContentResolver();
            Uri contentUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            Cursor cursor = contentResolver.query(contentUri, PHONES_PROJECTION, null, null, null);

            while (cursor != null ? cursor.moveToNext() : false) {
                String name = cursor.getString(0);
                String mobile = cursor.getString(1).replace("+86", "").replace(" ", "").replace("-", "");
                String namePinYin = PinYinUtil.getPinYin(name).toUpperCase(Locale.US);
                PhoneContact contactInfo = new PhoneContact(name, mobile, namePinYin);
                list.add(contactInfo);
            }
            cursor.close();
        } catch (Exception e) {
        }
        return list;
    }

    /**
     * 得到手机SIM卡联系人人信息
     **/
    private static List<PhoneContact> getSIMContacts(Context context) {
        List<PhoneContact> list = new ArrayList<PhoneContact>();
        try {
            ContentResolver contentResolver = context.getContentResolver();
            Uri uri = Uri.parse("content://icc/adn");
            Cursor cursor = contentResolver.query(uri, PHONES_PROJECTION, null, null, null);
            while (cursor != null ? cursor.moveToNext() : false) {
                String name = cursor.getString(PHONES_DISPLAY_NAME_INDEX);
                String mobile = cursor.getString(PHONES_NUMBER_INDEX).replace("+86", "").replace(" ", "").replace("-", "");
                String namePinYin = PinYinUtil.getPinYin(name).toUpperCase(Locale.US);
                PhoneContact contactInfo = new PhoneContact(name, mobile, namePinYin);
                list.add(contactInfo);
            }
            cursor.close();
        } catch (Exception ex) {

        }
        return list;
    }

    public static boolean addContacts(Context context, String name, String mobile) {
        ContentValues contentValues = new ContentValues();

        // 向RawContacts.CONTENT_URI空值插入，
        // 先获取Android系统返回的rawContactId
        // 后面要基于此id插入值
        Uri rawContactUri = context.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, contentValues);
        long rawContactId = ContentUris.parseId(rawContactUri);
        contentValues.clear();

        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        // 内容类型
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        // 联系人名字
        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name);
        context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, contentValues);
        contentValues.clear();

        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        // 联系人的电话号码
        contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, mobile);
        // 电话类型
        contentValues.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        // 向联系人URI添加联系人名字
        context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, contentValues);
        contentValues.clear();

        return true;
    }
}

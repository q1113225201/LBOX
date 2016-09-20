package com.sjl.lbox.app.contact;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sjl.lbox.R;
import com.sjl.lbox.adapter.ContactAdapter;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.bean.PhoneContact;
import com.sjl.lbox.listener.BaseListener;
import com.sjl.lbox.util.ContactUtil;
import com.sjl.lbox.util.DialogUtil;
import com.sjl.lbox.util.LogUtil;
import com.sjl.lbox.util.PermisstionsUtil;
import com.sjl.lbox.util.ToastUtil;

import java.util.Collections;
import java.util.List;

/**
 * 读取联系人
 *
 * @author SJL
 * @date 2016/8/11 23:31
 */
public class ContactActivity extends BaseActivity {
    private String tag = ContactActivity.class.getSimpleName();
    private ListView lv;
    private ContactAdapter adapter;
    private List<PhoneContact> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        initView();
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.lv);

        PermisstionsUtil.checkSelfPermission(mContext, PermisstionsUtil.READ_CONTACTS, PermisstionsUtil.READ_CONTACTS_CODE, "读取联系人权限", new PermisstionsUtil.PermissionResult() {
            @Override
            public void granted(int requestCode) {
                initData();
            }

            @Override
            public void denied(int requestCode) {
                ToastUtil.showToast(mContext, "读取联系人权限被拒绝");
            }
        });
    }

    private void initData() {
        list = ContactUtil.getAllPhoneContacts(mContext);
        //排序
        Collections.sort(list, new PhoneContact());
        adapter = new ContactAdapter(mContext, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final PhoneContact phoneContact = list.get(position);
                DialogUtil.showConfirm(mContext, "提示", phoneContact.getName() + ":" + phoneContact.getMobile(), "呼叫", null, new BaseListener() {
                    @Override
                    public void baseListener(View v, String msg) {
                        callPerson(phoneContact.getMobile());
                    }
                }, null, false);
            }
        });
    }

    private void callPerson(final String mobile) {
        LogUtil.i(tag,"mobile:"+mobile);
        PermisstionsUtil.checkSelfPermission(mContext, PermisstionsUtil.CALL_PHONE, PermisstionsUtil.CALL_PHONE_CODE, "拨号权限", new PermisstionsUtil.PermissionResult() {
            @Override
            public void granted(int requestCode) {
                try {
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        LogUtil.i(tag,"call:"+Uri.parse("tel:" + mobile).toString());
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobile));
                        startActivity(intent);
                    }else{
                        LogUtil.i(tag,"无拨号权限");
                    }
                } catch (Exception e) {
                    LogUtil.i(tag,"e:"+e.getMessage());
                }
            }

            @Override
            public void denied(int requestCode) {
                LogUtil.i(tag,"拨号权限被拒绝");
                ToastUtil.showToast(mContext, "拨号权限被拒绝");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermisstionsUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

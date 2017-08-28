package com.sjl.lbox.app.ui.CustomView.contact;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.ui.CustomView.contact.adapter.ContactAdapter;
import com.sjl.lbox.app.ui.CustomView.contact.bean.PhoneContact;
import com.sjl.lbox.app.ui.CustomView.contact.view.SectionIndexBar;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.ContactUtil;
import com.sjl.lbox.util.PermisstionUtil;
import com.sjl.lbox.util.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 读取联系人
 *
 * @author SJL
 * @date 2016/8/11 23:31
 */
public class ContactActivity extends BaseActivity implements View.OnClickListener {
    private String tag = ContactActivity.class.getSimpleName();
    private EditText etKeyword;
    private EditText etName;
    private EditText etMobile;
    private Button btnAdd;
    private ListView lv;
    private TextView tvCenter;

    private SectionIndexBar sectionIndexBar;

    private List<PhoneContact> allList;

    private List<PhoneContact> list;

    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_contact);

        initView();
    }

    private void initView() {
        etKeyword = (EditText) findViewById(R.id.etKeyword);
        etName = (EditText) findViewById(R.id.etName);
        etMobile = (EditText) findViewById(R.id.etMobile);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        lv = (ListView) findViewById(R.id.lv);
        sectionIndexBar = (SectionIndexBar) findViewById(R.id.sectionIndexBar);
        tvCenter = (TextView) findViewById(R.id.tvCenter);

        etKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //根据关键字查询
                String keyword = etKeyword.getText().toString();
                list.clear();
                PhoneContact phoneContact;
                for (int i = 0; i < allList.size(); i++) {
                    phoneContact = allList.get(i);
                    if (phoneContact.getNamePinYin().contains(keyword.toUpperCase()) || phoneContact.getName().contains(keyword)) {
                        list.add(phoneContact);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });

        btnAdd.setOnClickListener(this);
        PermisstionUtil.requestPermissions(mContext, PermisstionUtil.CONTACTS, 1003, "正在请求联系人读写权限", new PermisstionUtil.OnPermissionResult() {
            @Override
            public void granted(int requestCode) {
                initData();
            }

            @Override
            public void denied(int requestCode) {
                ToastUtil.showToast(mContext, "联系人权限被拒绝");
            }
        });
    }

    private void initData() {
        allList = ContactUtil.getAllPhoneContacts(this);
        initPhoneContact();
        adapter = new ContactAdapter(this, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtil.showToast(mContext, list.get(position).getName() + "——" + list.get(position).getMobile());
            }
        });
        sectionIndexBar.setOnIndexListener(new SectionIndexBar.OnIndexListener() {
            @Override
            public void onIndexSelect(String str) {
                tvCenter.setVisibility(View.GONE);
            }

            @Override
            public void onIndexChange(String str) {
                tvCenter.setText(str);
                tvCenter.setVisibility(View.VISIBLE);
                for (int i = 0; i < list.size(); i++) {
                    if (str.charAt(0) <= list.get(i).getNameFirstChar().charAt(0)) {
                        lv.setSelection(i);
                        break;
                    }
                }
            }
        });
    }

    private void initPhoneContact() {
        list = new ArrayList<>();
        for (PhoneContact item : allList) {
            //不能转换为字母的都用#
            String nameFirstChar = (item.getNamePinYin() == null || item.getNamePinYin().length() == 0 || !(item.getNamePinYin().charAt(0) >= 'A' && item.getNamePinYin().charAt(0) <= 'Z')) ? "#" : item.getNamePinYin().substring(0, 1);
            item.setNameFirstChar(nameFirstChar);
            list.add(item);
        }
        //按字母排序
        Collections.sort(allList, new PhoneContact());
        Collections.sort(list, new PhoneContact());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                addUser();
                break;
        }
    }

    private void addUser() {
        PermisstionUtil.requestPermissions(mContext, PermisstionUtil.CONTACTS, 1005, "写联系人需要联系人读写权限", new PermisstionUtil.OnPermissionResult() {
            @Override
            public void granted(int requestCode) {
                if (ContactUtil.addContacts(mContext, etName.getText().toString(), etMobile.getText().toString())) {
                    ToastUtil.showToast(mContext, "添加成功");
                    initData();
                }
                /*for (int i=1000;i<3000;i++){
                    ContactUtil.addContacts(mContext,etName.getText().toString()+i,etMobile.getText().toString()+i);
                    ToastUtil.showToast(mContext,"添加成功"+i);
                }
                initData();*/
            }

            @Override
            public void denied(int requestCode) {
                ToastUtil.showToast(mContext, "联系人权限被拒绝");
            }
        });
    }
}

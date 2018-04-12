package com.sjl.lbox.app.ui.CustomView.repeat.recyclerview;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.sjl.lbox.R;
import com.sjl.lbox.app.ui.CustomView.contact.bean.PhoneContact;
import com.sjl.lbox.app.ui.CustomView.repeat.recyclerview.StickyHeader.GroupInfo;
import com.sjl.lbox.app.ui.CustomView.repeat.recyclerview.StickyHeader.SectionDecoration;
import com.sjl.lbox.app.ui.CustomView.repeat.recyclerview.adapter.RVAdapter;
import com.sjl.lbox.base.BaseActivity;
import com.sjl.lbox.util.ContactUtil;
import com.sjl.lbox.util.PermisstionUtil;
import com.sjl.lbox.util.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 悬停头部
 *
 * @author SJL
 * @date 2018/4/11
 */
public class RecyclerViewStickyHeaderActivity extends BaseActivity {
    private RVAdapter adapter;
    private List<String> list = new ArrayList<>();
    private List<GroupInfo> groupInfoList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_sticky_header);

        initView();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new RVAdapter(this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SectionDecoration(this, new SectionDecoration.GroupInfoCallback() {
            @Override
            public GroupInfo getGroupInfo(int position) {
                return groupInfoList.get(position);
            }

            @Override
            public View getHeaderView() {
                return LayoutInflater.from(mContext).inflate(R.layout.layout_sticky_header,null);
            }
        }));

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
        initContact();
        adapter.notifyDataSetChanged();
    }

    private void initContact() {
        List<PhoneContact> phoneContactList = new ArrayList<>();
        for (PhoneContact item : ContactUtil.getAllPhoneContacts(this)) {
            //不能转换为字母的都用#
            String nameFirstChar = (item.getNamePinYin() == null || item.getNamePinYin().length() == 0 || !(item.getNamePinYin().charAt(0) >= 'A' && item.getNamePinYin().charAt(0) <= 'Z')) ? "#" : item.getNamePinYin().substring(0, 1);
            item.setNameFirstChar(nameFirstChar);
            phoneContactList.add(item);
        }
        //按字母排序
        Collections.sort(phoneContactList, new PhoneContact());
        int groupId = 0;
        int count = 0;
        String lastTitle = phoneContactList.get(0).getNameFirstChar();
        groupInfoList.add(new GroupInfo(groupId,count,lastTitle));
        list.add(phoneContactList.get(0).getName());
        for (int i=1;i<phoneContactList.size();i++){
            if(lastTitle.equalsIgnoreCase(phoneContactList.get(i).getNameFirstChar())){
                count++;
            }else{
                groupId++;
                count = 0;
                lastTitle = phoneContactList.get(i).getNameFirstChar();
            }
            groupInfoList.add(new GroupInfo(groupId,count,lastTitle));
            list.add(phoneContactList.get(i).getName());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

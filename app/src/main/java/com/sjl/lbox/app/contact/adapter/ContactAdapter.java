package com.sjl.lbox.app.contact.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.contact.bean.PhoneContact;

import java.util.List;

/**
 * 联系人适配器
 *
 * @author SJL
 * @date 2016/8/11 22:54
 */
public class ContactAdapter extends BaseAdapter {
    private Context context;
    private List<PhoneContact> list;

    public ContactAdapter(Context context, List<PhoneContact> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView = View.inflate(context, R.layout.item_contact,null);
            holder=new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvPhone = (TextView) convertView.findViewById(R.id.tvPhone);
            holder.tvFirstChar = (TextView) convertView.findViewById(R.id.tvFirstChar);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        PhoneContact phoneContact=list.get(position);
        holder.tvName.setText(phoneContact.getName());
        holder.tvPhone.setText(phoneContact.getMobile());
        holder.tvFirstChar.setText(phoneContact.getNameFirstChar());
        if(isFirst(position)){
            holder.tvFirstChar.setVisibility(View.VISIBLE);
        }else{
            holder.tvFirstChar.setVisibility(View.GONE);
        }
        return convertView;
    }
    private boolean isFirst(int position) {
        if(position==0){
            return true;
        }
        if(list.get(position).getNameFirstChar().equals(list.get(position-1).getNameFirstChar())) {
            return false;
        }else{
            return true;
        }
    }
    class ViewHolder{
        TextView tvFirstChar;
        TextView tvName;
        TextView tvPhone;
    }
}

package com.sjl.lbox.app.mobile.signature.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sjl.lbox.R;
import com.sjl.lbox.app.mobile.signature.bean.AppInfo;

import java.util.List;

/**
 * App信息适配器
 *
 * @author SJL
 * @date 2016/8/17 22:39
 */
public class AppInfoAdapter extends BaseAdapter {
    private Context context;
    private List<AppInfo> list;

    public AppInfoAdapter(Context context, List<AppInfo> list) {
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
            convertView=View.inflate(context, R.layout.item_app_info,null);
            holder=new ViewHolder();
            holder.ivIcon= (ImageView) convertView.findViewById(R.id.ivIcon);
            holder.tvName= (TextView) convertView.findViewById(R.id.tvName);
            holder.tvPkg= (TextView) convertView.findViewById(R.id.tvPkg);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        AppInfo appInfo=list.get(position);
        holder.ivIcon.setImageDrawable(appInfo.getIcon());
        String name = appInfo.getName()+"("+appInfo.getVersion()+")";
        if(appInfo.getSystemApp()){
            name+="(系统应用)";
        }
        holder.tvName.setText(name);
        holder.tvPkg.setText(appInfo.getPackageName());
        return convertView;
    }
    class ViewHolder{
        ImageView ivIcon;
        TextView tvName;
        TextView tvPkg;
    }
}

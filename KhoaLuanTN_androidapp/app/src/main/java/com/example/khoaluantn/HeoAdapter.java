package com.example.khoaluantn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class HeoAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Heo> heoList;

    public HeoAdapter(Context context, int layout, List<Heo> heoList) {
        this.context = context;
        this.layout = layout;
        this.heoList = heoList;
    }

    @Override
    public int getCount() {
        return heoList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder{
        TextView tvGiong, tvId, tvGioitinh, tvNgaytuoi, tvCannang;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.tvGiong = (TextView) view.findViewById(R.id.giong);
            holder.tvId = (TextView) view.findViewById(R.id.id);
            holder.tvGioitinh = (TextView) view.findViewById(R.id.gioitinh);
            holder.tvNgaytuoi = (TextView) view.findViewById(R.id.ngaytuoi);
            holder.tvCannang = (TextView) view.findViewById(R.id.cannang);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        Heo heo = heoList.get(i);
        holder.tvGiong.setText(heo.getGiong());
        holder.tvId.setText(heo.getId());
        holder.tvGioitinh.setText(heo.getGiong());
        holder.tvNgaytuoi.setText(heo.getNgayTuoi());
        holder.tvCannang.setText(heo.getCanNang());
        return view;
    }
}

package com.mbds.hotelnfc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class RoomAdapter extends BaseAdapter {

    private List<Room> mRooms;
    private LayoutInflater mInflater;

    public RoomAdapter(Context context, List<Room> rooms) {
        mRooms = rooms;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mRooms.size();
    }

    @Override
    public Object getItem(int position) {
        return mRooms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_room, parent, false);
            holder = new ViewHolder();
            holder.tvRoomCode = convertView.findViewById(R.id.tv_room_code);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Room room = mRooms.get(position);
        holder.tvRoomCode.setText(room.getCode());

        return convertView;
    }

    static class ViewHolder {
        TextView tvRoomCode;
    }
}

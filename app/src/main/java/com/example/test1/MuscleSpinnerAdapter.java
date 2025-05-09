package com.example.test1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MuscleSpinnerAdapter extends ArrayAdapter<MuscleItem> {

    private Context mContext;
    private List<MuscleItem> muscleList;

    public MuscleSpinnerAdapter(@NonNull Context context, List<MuscleItem> list) {
        super(context, 0, list);
        mContext = context;
        muscleList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.spinner_item_with_icon, parent, false);

        ImageView icon = view.findViewById(R.id.muscleIcon);
        TextView name = view.findViewById(R.id.muscleName);

        MuscleItem item = muscleList.get(position);

        icon.setImageResource(item.getIconResId());
        name.setText(item.getName());

        return view;
    }
}

package com.glory.customspinner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CompanyAdapter extends ArrayAdapter<CompanyItem> {

    public CompanyAdapter(Context context, ArrayList<CompanyItem> companyItems){
        super(context, 0, companyItems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.spinner_item_row, parent, false
            );
        }

        ImageView imageViewlogo = convertView.findViewById(R.id.image_view_logo);
        TextView textViewName = convertView.findViewById(R.id.text_view_name);

        CompanyItem currentItem = getItem(position);

        if(currentItem !=null){
            imageViewlogo.setImageResource(currentItem.getmCompanyImage());
            textViewName.setText(currentItem.getmCompanyName());
        }
        return convertView;

    }
}

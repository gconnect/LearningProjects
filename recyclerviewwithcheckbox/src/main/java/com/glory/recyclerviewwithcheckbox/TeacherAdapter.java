package com.glory.recyclerviewwithcheckbox;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter <TeacherAdapter.TeacherVieHolder> implements Filterable {

    List<TeachersModel> teachersModels ;
    List<TeachersModel> teacherIsCheck = new ArrayList<>();
    List<TeachersModel> teacherIsFull;
    private boolean [] checkstate;
    private Context mContext;
    private setOnItemClickListener mListener;
    @NonNull
    @Override
    public TeacherVieHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model, parent, false);
        return new TeacherVieHolder(view);

    }

    public TeacherAdapter(Context context, List<TeachersModel> teachersModels) {
        this.teachersModels = teachersModels;
        this.mContext = context;
        teacherIsFull = new ArrayList<>();

        checkstate = new boolean[teachersModels.size()];

    }

    public void updateList(ArrayList<TeachersModel> newList) {
        teachersModels = new ArrayList<>();
        teachersModels.addAll(newList);
        notifyDataSetChanged();
    }

    // Interface to implement click on the checkbox
    public interface setOnItemClickListener{
        void onCheckboxClick(int position);

    }

    public void setOnItemClickListener(setOnItemClickListener listener){
        this.mListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull final TeacherVieHolder holder, final int position) {

        TeachersModel currentItem = teachersModels.get(position);

        int profile = currentItem.getProfilePix();
        String name = currentItem.getName();
        String mDescription = currentItem.getDescription();
//        boolean mIscheck = currentItem.isChecked();

        holder.profilePix.setImageResource(profile);
        holder.name.setText(name);
        holder.description.setText(mDescription);
//        holder.ischeck.setChecked(mIscheck);

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext,  holder.more);
                popupMenu.inflate(R.menu.options_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){
                            case R.id.edit:
                                Toast.makeText(mContext, "Edit clicked", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.delete:
                                Toast.makeText(mContext, "delete clicked", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.disable:
                                Toast.makeText(mContext, "disabled clicked", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        holder.ischeck.setChecked(false);

        if(checkstate[position]){
            holder.ischeck.setChecked(true);
        }else {
            holder.ischeck.setChecked(false);
        }

        holder.ischeck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkstate[position] = false;
                }else{
                    checkstate[position] = true;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return teachersModels.size();
    }

    public class TeacherVieHolder extends RecyclerView.ViewHolder{

        private ImageView profilePix;
        private TextView name;
        private  TextView description;
        private CheckBox ischeck;
        private ImageView more;

        public TeacherVieHolder(@NonNull final View itemView) {
            super(itemView);

            profilePix = itemView.findViewById(R.id.teacherImageview);
            name = itemView.findViewById(R.id.teacherName);
            description = itemView.findViewById(R.id.description);
            ischeck = itemView.findViewById(R.id.checkbox);
            more = itemView.findViewById(R.id.more);
/*
            ischeck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onCheckboxClick(position);
                        }
                        if (ischeck.isChecked()){
                            teacherIsCheck.add(teachersModels.get(position));
                        }else if (!ischeck.isChecked()){
                               teacherIsCheck.remove(teachersModels.get(position));
                        }
                    }
                }
            });
*/


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (mListener != null) {
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onCheckboxClick(position);
                        }
                        if (ischeck.isChecked()) {
                            teacherIsCheck.add(teachersModels.get(position));
                        } else if (!ischeck.isChecked()) {
                            teacherIsCheck.remove(teachersModels.get(position));
                        }
                        if (ischeck != null) {
                            ischeck.setChecked(true);
                            if (ischeck.isChecked()) {
                                ischeck.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    return true;
                }
            });

        }
    }

    //Implementing search in recyclerview

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter;

    {
        exampleFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<TeachersModel> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(teacherIsFull);
                } else {
                    String filteredPattern = constraint.toString().toLowerCase().trim();

                    for (TeachersModel item : teacherIsFull) {
                        if (item.getName().toLowerCase().contains(filteredPattern)) {
                            filteredList.add(item);
                        } else if (item.getDescription().toLowerCase().contains(filteredPattern)) {
                            filteredList.add(item);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                teachersModels.clear();
                teacherIsCheck.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };

    }

    }

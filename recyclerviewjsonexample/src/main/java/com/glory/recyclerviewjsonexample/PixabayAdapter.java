package com.glory.recyclerviewjsonexample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class PixabayAdapter extends RecyclerView.Adapter<PixabayAdapter.PixabayViewHolder>{
    private ArrayList<PixabayItem> pixabayItems;
    private Context context;
    private OnItemClickListener mlistener;

    public PixabayAdapter(Context context, ArrayList<PixabayItem> pixabayItem){
        this. pixabayItems = pixabayItem;
        this.context = context;
    }

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mlistener = listener;

    }


    @NonNull
    @Override
    public PixabayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pixaby_item, parent, false);
        return new PixabayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PixabayViewHolder holder, int position) {

        PixabayItem currentItem = pixabayItems.get(position);
        String image = currentItem.getmImageUrl();
        String name = currentItem.getmCreator();
        int likes = currentItem.getmLikes();

        holder.creatorName.setText(name);
        holder.creatorLikes.setText("Likes: " + likes);

        Picasso.with(context).load(image).fit().centerInside().into(holder.creatorImage);
    }

    @Override
    public int getItemCount() {
        return pixabayItems.size();
    }

    public class PixabayViewHolder extends RecyclerView.ViewHolder{
        private ImageView creatorImage;
        private TextView creatorName;
        private TextView creatorLikes;

        public PixabayViewHolder(@NonNull View itemView) {
            super(itemView);

            creatorImage = itemView.findViewById(R.id.image_view);
            creatorName = itemView.findViewById(R.id.text_view_creator);
            creatorLikes = itemView.findViewById(R.id.text_view_likes);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mlistener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mlistener.OnItemClick(position);
                        }
                    }
                }
            });
        }
    }
}

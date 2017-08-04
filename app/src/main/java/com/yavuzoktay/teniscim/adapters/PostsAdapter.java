package com.yavuzoktay.teniscim.adapters;

import android.content.Context;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yavuzoktay.teniscim.NewsDetails;
import com.yavuzoktay.teniscim.R;
import com.yavuzoktay.teniscim.models.Post;

import java.util.List;


public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.MyViewHolder> {

    private String TAG = getClass().getSimpleName();

    private Context context;
    private List<Post> postList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView category;
        public ImageView imageView;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            category = (TextView) view.findViewById(R.id.category);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            cardView=view.findViewById(R.id.card_view);
        }
    }


    public PostsAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Post post = postList.get(position);
        holder.title.setText(post.getTitle());
        holder.category.setText(post.getCategory().get(0));

        Log.i(TAG, "image url > : " + post.getImageUrl());
        Picasso.with(context).load(post.getImageUrl()).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, NewsDetails.class);
                intent.putExtra("Link",post.getLink());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}
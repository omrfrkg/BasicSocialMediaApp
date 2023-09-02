package com.omrfrkg.basicsocialmediaapp.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omrfrkg.basicsocialmediaapp.databinding.RecyclerRowBinding;
import com.omrfrkg.basicsocialmediaapp.model.Post;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {

    private ArrayList<Post> postArrayList;

    public PostAdapter(ArrayList<Post> postArrayList) {
        this.postArrayList = postArrayList;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new PostHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        String email = postArrayList.get(position).email;
        int emailIx = email.indexOf('@');
        String username = email.substring(0,emailIx);

        holder.binding.recyclerUsername.setText(postArrayList.get(position).email);
        holder.binding.recyclerViewUsername2.setText(username);
        holder.binding.recyclerViewComment.setText(postArrayList.get(position).comment);
        Picasso.get().load(postArrayList.get(position).downloadUrl).into(holder.binding.recyclerViewImage);
    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder{
        RecyclerRowBinding binding;
        public PostHolder(RecyclerRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

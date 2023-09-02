package com.omrfrkg.basicsocialmediaapp.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.omrfrkg.basicsocialmediaapp.R;
import com.omrfrkg.basicsocialmediaapp.adapter.PostAdapter;
import com.omrfrkg.basicsocialmediaapp.databinding.ActivityFeedBinding;
import com.omrfrkg.basicsocialmediaapp.model.Post;

import java.util.ArrayList;
import java.util.Map;


public class FeedActivity extends AppCompatActivity {

    private ActivityFeedBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private ArrayList<Post> postArrayList;
    private PostAdapter postAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        postArrayList = new ArrayList<>();

        auth = FirebaseAuth.getInstance();

        firestore = FirebaseFirestore.getInstance();

        getData();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(FeedActivity.this));
        postAdapter = new PostAdapter(postArrayList);
        binding.recyclerView.setAdapter(postAdapter);

    }

    public void getData(){

        firestore.collection("Posts").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Toast.makeText(FeedActivity.this,error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }

                if (value != null){
                    for (DocumentSnapshot documentSnapshot : value.getDocuments()){
                        Map<String,Object> data = documentSnapshot.getData();

                        //Casting
                        String userEmail = (String) data.get("useremail");
                        String comment = (String) data.get("comment");
                        String downloadUrl = (String) data.get("downloadUrl");

                        Post post = new Post(userEmail,downloadUrl,comment);
                        postArrayList.add(post);

                        postAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater =getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_post){
            //Upload Post
            Intent upload = new Intent(FeedActivity.this,UploadActivity.class);
            startActivity(upload);
        } else if (item.getItemId() == R.id.sign_out) {
            //Signout
            auth.signOut();
            Intent signout = new Intent(FeedActivity.this,MainActivity.class);
            startActivity(signout);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
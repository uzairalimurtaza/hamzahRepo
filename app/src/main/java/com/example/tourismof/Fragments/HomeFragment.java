package com.example.tourismof.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourismof.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment {
    private FirebaseRecyclerAdapter<Posts, PostsVIewHolder> adapter;
    private String Userrrrr;
    private RecyclerView postList;
    private FirebaseAuth mAuth;
    private DatabaseReference UserRef, PostRef;
    private Context ctx;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        mAuth = FirebaseAuth.getInstance();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        PostRef = FirebaseDatabase.getInstance().getReference().child("Posts");

        postList = (RecyclerView) view.findViewById(R.id.recycler_view);
        postList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        postList.setLayoutManager(linearLayoutManager);

//        DisplayAllUsersPosts();


        return view;


    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Posts> options = new FirebaseRecyclerOptions.Builder<Posts>()
                .setQuery(PostRef, Posts.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Posts, PostsVIewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final PostsVIewHolder holder, int position, @NonNull Posts model) {
                String Userids=getRef(position).getKey();
                 PostRef.child(Userids).addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         if (dataSnapshot.hasChild("postimage"))
                         {
                         String profileimage=dataSnapshot.child("profileimage").getValue().toString();
                             String dateee=dataSnapshot.child("date").getValue().toString();
                             String descriptionnn=dataSnapshot.child("description").getValue().toString();
                             String fullnameee=dataSnapshot.child("fullname").getValue().toString();
                             String postimageee=dataSnapshot.child("postimage").getValue().toString();
                             String time=dataSnapshot.child("time").getValue().toString();
                             String uid=dataSnapshot.child("uid").getValue().toString();

                             holder.PostDescription.setText(descriptionnn);
                         holder.PostDate.setText(dateee);
                         holder.username.setText(fullnameee);
                         Picasso.get().load(profileimage).placeholder(R.drawable.profile).into(holder.profileimage);
                         Picasso.get().load(postimageee).placeholder(R.drawable.profile).into(holder.PostImage);



//                             holder.setDate(model.getDate());
//                             holder.setTime(model.getTime());
//                             holder.setFullname(model.getFullname());
//                             holder.setDescription(model.getDescription());
//                             holder.setProfileimage(getActivity().getApplicationContext(), model.getProfileimage());
//                             holder.setPostimage(getActivity().getApplicationContext(), model.getPostimage());

                         }
                         else{
                             String dateee=dataSnapshot.child("date").getValue().toString();
                             String descriptionnn=dataSnapshot.child("description").getValue().toString();
                             String fullnameee=dataSnapshot.child("fullname").getValue().toString();
                             String time=dataSnapshot.child("time").getValue().toString();
                             String uid=dataSnapshot.child("uid").getValue().toString();
                             holder.PostDescription.setText(descriptionnn);
                             holder.PostDate.setText(dateee);
                             holder.username.setText(fullnameee);
                         }
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {

                     }
                 });
            }

            @NonNull
            @Override
            public PostsVIewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.all_posts_layout, parent, false);

                PostsVIewHolder viewHolder=new PostsVIewHolder(view);
                return viewHolder;
            }
        };

        postList.setAdapter(adapter);
           adapter.startListening();
    }

    public static class PostsVIewHolder extends RecyclerView.ViewHolder {
        TextView PostDescription,username,PostTime,PostDate;
        CircleImageView profileimage;
        ImageView PostImage;
        public PostsVIewHolder(@NonNull View itemView) {
            super(itemView);
            username= itemView.findViewById(R.id.post_username);
            profileimage = (CircleImageView) itemView.findViewById(R.id.post_profile_image);
            PostTime = (TextView) itemView.findViewById(R.id.post_Time);
            PostDate = (TextView) itemView.findViewById(R.id.post_Date);
            PostDescription = (TextView) itemView.findViewById(R.id.post_description);
            PostImage = (ImageView) itemView.findViewById(R.id.post_image);

        }
//
//        public void setFullname(String fullname) {
//            username.setText(fullname);
//        }
//
//        public void setProfileimage(Context ctx, String profileimage) {
//            Picasso.get().load(profileimage).into(image);
//        }
//
//        public void setTime(String time) {
//            PostTime.setText("    " + time);
//        }
//
//        public void setDate(String date) {
//            PostDate.setText("    " + date);
//        }
//
//        public void setDescription(String description) {
//            PostDescription.setText(description);
//        }
//
//        public void setPostimage(Context ctx, String postimage) {
//            Picasso.get().load(postimage).into(PostImage);
//        }
    }

}


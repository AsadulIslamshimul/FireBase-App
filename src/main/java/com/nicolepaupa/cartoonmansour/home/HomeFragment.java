package com.nicolepaupa.cartoonmansour.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nicolepaupa.cartoonmansour.adapter.ItemAdapter;
import com.nicolepaupa.cartoonmansour.databinding.FragmentHomeBinding;
import com.nicolepaupa.cartoonmansour.entity.Video;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nicolepaupa.cartoonmansour.repo.DataRepository;
import com.nicolepaupa.cartoonmansour.viewModel.MainViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {
String s;
FragmentHomeBinding binding;
ArrayList<Video> item;
@Inject
DataRepository myRepository;

MainViewModel mainViewModel;

    public HomeFragment(String videos) {
        this.s=videos;
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(getLayoutInflater());

        mainViewModel=new ViewModelProvider(this).get(MainViewModel.class);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        item=new ArrayList<Video>();

        mainViewModel.videoListLiveData(s).observe(getViewLifecycleOwner(), it->{
            binding.recyclerview.setAdapter(new ItemAdapter(new ArrayList<Video>(it)));
            showRecycler();
        });


        return binding.getRoot();
    }

    // coming data

    private void showRecycler() {

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference(s);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.exists()){
                    item.clear();
                    for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                    Video video=new Video(dataSnapshot.child("id").getValue().toString(),
                            dataSnapshot.child("title").getValue().toString(),
                            dataSnapshot.child("duration").getValue().toString(),s,
                            dataSnapshot.child("date").getValue().toString(),
                            dataSnapshot.child("views").getValue().toString());
                    item.add(video);
                        myRepository.insert(video);

                    }
                }
                binding.progressBar.setVisibility(View.GONE);
                Log.i("123321", "78:"+item);
                binding.recyclerview.setAdapter(new ItemAdapter(item));

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}
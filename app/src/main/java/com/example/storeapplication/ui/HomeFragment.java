package com.example.storeapplication.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.storeapplication.MainActivity;
import com.example.storeapplication.R;
import com.example.storeapplication.adapter.PopularAdapter;
import com.example.storeapplication.databinding.FragmentHomeBinding;
import com.example.storeapplication.model.PopularModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    FragmentHomeBinding fragmentHomeBinding;
    MainActivity mainActivity;
    private boolean doubleToBackButtonExit = false;
    List<PopularModel> popularModelList;
    PopularAdapter popularAdapter;
    RecyclerView popularRecycler;
    FirebaseFirestore db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentHomeBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_home, container, false);

        db = FirebaseFirestore.getInstance();
        popularModelList = new ArrayList<>();

        //popular item set adapter
        popularAdapter = new PopularAdapter(getActivity(), popularModelList);
        fragmentHomeBinding.rvPopularRecycler.setAdapter(popularAdapter);

        //read data from cloud database
        readData();


        return fragmentHomeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupNavigation(view);

        //finish fragment when we click back button
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (doubleToBackButtonExit) {
                    requireActivity().finish();
                    return;
                }
                //handle double click for exit
                doubleToBackButtonExit = true;
                Toast.makeText(requireContext(), "برای خروج دوباره کلیک کنید", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(() -> doubleToBackButtonExit = false, 2000);
            }
        });

    }

    private void setupNavigation(View view) {
        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment).setOpenableLayout(mainActivity.drawerLayout).build();

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.homeFragment) {
                    toolbar.setNavigationIcon(R.drawable.ic_menu);
                    toolbar.setTitle("Home");
                }
            }
        });
    }

    private void readData() {
        db.collection("PopularProducts")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException error) {
                        assert documentSnapshots != null;
                        for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                            if (documentChange.getType() == DocumentChange.Type.ADDED) {
                                PopularModel popularModel = documentChange.getDocument().toObject(PopularModel.class);
                                popularModelList.add(popularModel);
                                popularAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;

    }
}
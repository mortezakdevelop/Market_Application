package com.example.storeapplication.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.storeapplication.MainActivity;
import com.example.storeapplication.R;
import com.example.storeapplication.databinding.FragmentCartBinding;


public class CartFragment extends Fragment {

  FragmentCartBinding fragmentCartBinding;
  MainActivity mainActivity;
    private boolean doubleToBackButtonExit = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentCartBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.fragment_cart,container,false);
        return fragmentCartBinding.getRoot();
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

    private void setupNavigation(View view){
        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.cartFragment).setOpenableLayout(mainActivity.drawerLayout).build();

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.cartFragment){
                    toolbar.setNavigationIcon(R.drawable.ic_menu);
                    toolbar.setTitle("Offer");
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
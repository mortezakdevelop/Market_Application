package com.example.storeapplication.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.storeapplication.R;
import com.example.storeapplication.databinding.PopularItemBinding;
import com.example.storeapplication.model.PopularModel;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.viewHolder> {

    LayoutInflater layoutInflater;
    private List<PopularModel> popularModelList;

    public PopularAdapter(FragmentActivity activity, List<PopularModel> popularModelList) {
        this.popularModelList = popularModelList;
    }


    @NonNull
    @Override
    public PopularAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        PopularItemBinding popularItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.popular_item,parent,false);
        return new viewHolder(popularItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.viewHolder holder, int position) {
        holder.bind(popularModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return popularModelList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {

        PopularItemBinding popularItemBinding;
        public viewHolder(@NonNull PopularItemBinding popularItemBinding) {
            super(popularItemBinding.getRoot());
            this.popularItemBinding = popularItemBinding;
        }

        public void bind(@NonNull PopularModel popularModel) {
            Glide.with(popularItemBinding.getRoot().getContext()).load(popularModel.getImage_url()).into(popularItemBinding.rivPopularImage);
            popularItemBinding.tvDescription.setText(popularModel.getDescription());
            popularItemBinding.tvDiscountOff.setText(popularModel.getDiscount());
            popularItemBinding.tvFiveRatingbar.setText(popularModel.getRating());
            popularItemBinding.tvPopularProduct.setText(popularModel.getName());

        }
    }
}

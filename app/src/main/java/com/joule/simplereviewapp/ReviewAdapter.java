package com.joule.simplereviewapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.joule.simplereviewapp.api.ApiClient;
import com.joule.simplereviewapp.model.MainViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private Context context;
    private ArrayList<ReviewModel> listReview = new ArrayList<>();
    MainViewModel model;

    public ReviewAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<ReviewModel> getListReview() {
        return listReview;
    }

    public void setListReview(ArrayList<ReviewModel> items) {
        listReview.clear();
        listReview.addAll(items);
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);

        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ReviewViewHolder holder, final int position) {
        Glide.with(context)
                .load(listReview.get(position).getImage())
                .apply(new RequestOptions().centerCrop())
                .into(holder.imgContent);
        holder.tvName.setText(listReview.get(position).getName());
        holder.tvDate.setText(listReview.get(position).getDate());
        holder.tvContent.setText(listReview.get(position).getContent());
        holder.ratingBar.setRating(Float.parseFloat(listReview.get(position).getRatingbar()));
        holder.btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, holder.btn_menu);
                popupMenu.inflate(R.menu.card_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.edit_card :

                                break;
                            case R.id.delete_card:
                                alertDelete(listReview.get(position).getName(), listReview.get(position).getId());
                                listReview.remove(position);
                                notifyItemRemoved(position);
                                notifyItemChanged(position, listReview.size());
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listReview.size();
    }

 class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvDate,tvContent;
        RatingBar ratingBar;
        ImageView imgContent;
        ImageButton btn_menu;

        ReviewViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.profile_name);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvContent = itemView.findViewById(R.id.tv_content);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            imgContent = itemView.findViewById(R.id.img_content);
            btn_menu = itemView.findViewById(R.id.btn_menu);
        }
    }

    private void alertDelete(String name, final String id){

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setCancelable(false)
                .setTitle("Delete")
                .setMessage("Sure to Delete "+name+ "?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       DeleteData deleteData = ApiClient.getClient().create(DeleteData.class);
                        Call<ReviewModel> call = deleteData.deleting(id);
                        call.enqueue(new Callback<ReviewModel>() {
                            @Override
                            public void onResponse(Call<ReviewModel> call, Response<ReviewModel> response) {
                                try {
                                    Log.d("YYY", "onResponse: success");

                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<ReviewModel> call, Throwable t) {

                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        final AlertDialog alertDialog = alert.create();
        alertDialog.show();

    }

    interface DeleteData{
        @FormUrlEncoded
        @POST("ActionApi/deleteData")
        Call<ReviewModel> deleting (
                @Field("id") String id);
    }
}

package com.joule.simplereviewapp.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonArray;
import com.joule.simplereviewapp.ReviewModel;
import com.joule.simplereviewapp.api.ApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ArrayList<ReviewModel>> listDataItem = new MutableLiveData<>();
    public void getAllData(){
        final ArrayList<ReviewModel> listItem = new ArrayList<>();
        GetData getData = ApiClient.getClient().create(GetData.class);
        Call<ResponseBody> call = getData.getAdata();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    JSONArray resJson = new JSONArray(result);
                    Log.d("YY", "onResponse: ");

                    for (int i = 0; i < resJson.length(); i++){
                        JSONObject review = resJson.getJSONObject(i);

                        String id = review.getString("id");
                        String name = review.getString("name");
                        String content = review.getString("review");
                        String ratingBar = review.getString("rating");
                        String date = review.getString("date");
                        String image = review.getString("image");

                        ReviewModel listData = new ReviewModel(id, ratingBar, name, content, image, date);
                        listItem.add(listData);
                    }
                    listDataItem.postValue(listItem);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("MMM", "onFailure: " + t.getMessage());
            }
        });

    }

    public void pushData(String name, String rating, String content){
        InsertData insertData = ApiClient.getClient().create(InsertData.class);
        Call<ReviewModel> call = insertData.inserting(rating,name, content);
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
    public void delData(String id){
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
    public LiveData<ArrayList<ReviewModel>> getAllAdata(){ return listDataItem; }

    interface GetData{
        @GET("getAllData")
        Call<ResponseBody> getAdata();
    }

    interface InsertData{
        @FormUrlEncoded
        @POST("ActionApi/insertData")
        Call<ReviewModel> inserting (
                @Field("ratingBar") String ratingBar,
                @Field("name") String name,
                @Field("content") String content);
    }
    interface DeleteData{
        @FormUrlEncoded
        @POST("ActionApi/deleteData")
        Call<ReviewModel> deleting (
                @Field("id") String id);
    }
}

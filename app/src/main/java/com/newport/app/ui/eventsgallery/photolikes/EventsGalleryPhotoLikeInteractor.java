package com.newport.app.ui.eventsgallery.photolikes;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.perf.metrics.AddTrace;
import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.api.NewPortApiManager;
import com.newport.app.data.models.response.PhotoLikeResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsGalleryPhotoLikeInteractor {
    @AddTrace(name = "getPhotoLikes")
    static void getPhotoLikes(int photoId, final EventsGalleryPhotoLikeContract.Callback callback) {
        Call<PhotoLikeResponse> call = NewPortApiManager.apiManager().getPhotoLikes(photoId);

        call.enqueue(new Callback<PhotoLikeResponse>() {
            @Override
            public void onResponse(@NonNull Call<PhotoLikeResponse> call, @NonNull Response<PhotoLikeResponse> response) {
                if (response.isSuccessful()){
                    callback.getPhotoLikesSuccess(response.body());
                } else {
                    Log.d("onResponse", response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PhotoLikeResponse> call, @NonNull  Throwable t) {
                callback.getPhotoLikesFailure(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }

    @AddTrace(name = "setPhotoLike")
    static void setPhotoLike(int photoId, String userDni, final EventsGalleryPhotoLikeContract.Callback callback) {
        Call<PhotoLikeResponse> call = NewPortApiManager.apiManager().setPhotoLike(photoId, userDni);

        call.enqueue(new Callback<PhotoLikeResponse>() {
            @Override
            public void onResponse(@NonNull Call<PhotoLikeResponse> call, @NonNull Response<PhotoLikeResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        callback.getPhotoLikeSuccess(response.body());
                    } else {
                        Log.d("onResponse", response.message());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<PhotoLikeResponse> call, @NonNull Throwable t) {
                callback.getPhotoLikeFailure(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }

    @AddTrace(name = "getPhotoLikedBy")
    static void getPhotoLikedBy(int photoId, String userDni, final EventsGalleryPhotoLikeContract.Callback callback) {
        Call<PhotoLikeResponse> call = NewPortApiManager.apiManager().getPhotoLikedBy(photoId, userDni);

        call.enqueue(new Callback<PhotoLikeResponse>() {
            @Override
            public void onResponse(@NonNull Call<PhotoLikeResponse> call, @NonNull Response<PhotoLikeResponse> response) {
                if (response.isSuccessful()){
                    callback.getPhotoLikedBySuccess(response.body());
                } else {
                    Log.d("onResponse", response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PhotoLikeResponse> call, @NonNull Throwable t) {
                callback.getPhotoLikedByFailure(NewPortApplication.getAppContext().getString(R.string.conectivity_error));
            }
        });
    }
}

package com.newport.app.ui.eventsgallery.photolikes;

import com.newport.app.data.models.response.PhotoLikeResponse;
import com.newport.app.util.BasePresenter;

public interface EventsGalleryPhotoLikeContract {
    interface View {

        void showPhotoLikes(PhotoLikeResponse photoLikeResponse);
        void showPhotoLikesError(String error);
        void showPhotoLikesFailure(String failure);

        void showPhotoLikeError(String error);
        void showPhotoLikeSuccess(PhotoLikeResponse photoLikeResponse);

        void showPhotoLikedBySuccess(PhotoLikeResponse photoLikeResponse);
        void showPhotoLikedByError(String Error);

    }

    interface Presenter extends BasePresenter<View> {

        void getPhotoLikes(int photoId);
        void setPhotoLike(int photoId, String userDni);
        void getPhotoLikedBy(int photoId, String userDni);

    }

    interface Callback {

        void getPhotoLikesSuccess(PhotoLikeResponse photoLikeResponse);
        void getPhotoLikesError(String error);
        void getPhotoLikesFailure(String failure);

        void getPhotoLikeSuccess(PhotoLikeResponse photoLikeResponse);
        void getPhotoLikeError(String error);
        void getPhotoLikeFailure(String error);

        void getPhotoLikedBySuccess(PhotoLikeResponse photoLikeResponse);
        void getPhotoLikedByError(String error);
        void getPhotoLikedByFailure(String error);

    }
}

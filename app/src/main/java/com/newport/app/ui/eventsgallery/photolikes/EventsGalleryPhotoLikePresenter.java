package com.newport.app.ui.eventsgallery.photolikes;


import com.newport.app.data.models.response.PhotoLikeResponse;

public class EventsGalleryPhotoLikePresenter implements EventsGalleryPhotoLikeContract.Presenter, EventsGalleryPhotoLikeContract.Callback {

    private EventsGalleryPhotoLikeContract.View view;

    @Override
    public void getPhotoLikes(int photoId) {
        EventsGalleryPhotoLikeInteractor.getPhotoLikes(photoId, this);
    }

    @Override
    public void setPhotoLike(int photoId, String userDni) {
        EventsGalleryPhotoLikeInteractor.setPhotoLike(photoId, userDni, this);
    }

    @Override
    public void getPhotoLikedBy(int photoId, String userDni) {
        EventsGalleryPhotoLikeInteractor.getPhotoLikedBy(photoId, userDni, this);
    }

    @Override
    public void getPhotoLikesSuccess(PhotoLikeResponse photoLikeResponse) {
        view.showPhotoLikes(photoLikeResponse);
    }

    @Override
    public void getPhotoLikesError(String error) {
        view.showPhotoLikesError(error);
    }

    @Override
    public void getPhotoLikesFailure(String failure) {
        view.showPhotoLikesFailure(failure);
    }

    @Override
    public void getPhotoLikeSuccess(PhotoLikeResponse photoLikeResponse) {
        view.showPhotoLikeSuccess(photoLikeResponse);
    }

    @Override
    public void getPhotoLikeError(String error) {
        view.showPhotoLikeError(error);
    }

    @Override
    public void getPhotoLikeFailure(String error) {
        view.showPhotoLikeError(error);
    }

    @Override
    public void getPhotoLikedBySuccess(PhotoLikeResponse photoLikeResponse) {
        view.showPhotoLikedBySuccess(photoLikeResponse);
    }

    @Override
    public void getPhotoLikedByError(String error) {
        view.showPhotoLikedByError(error);
    }

    @Override
    public void getPhotoLikedByFailure(String error) {
        view.showPhotoLikedByError(error);
    }

    @Override
    public void attachedView(EventsGalleryPhotoLikeContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}

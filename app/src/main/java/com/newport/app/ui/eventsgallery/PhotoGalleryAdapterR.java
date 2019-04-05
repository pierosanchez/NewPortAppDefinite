package com.newport.app.ui.eventsgallery;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.models.response.PhotoGalleryEventResponse;
import com.newport.app.data.models.response.PhotoLikeResponse;
import com.newport.app.ui.eventsgallery.photolikes.EventsGalleryPhotoLikeContract;
import com.newport.app.ui.eventsgallery.photolikes.EventsGalleryPhotoLikePresenter;
import com.newport.app.util.PreferencesHeper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PhotoGalleryAdapterR extends RecyclerView.Adapter<PhotoGalleryAdapterR.PhotoGalleryViewHolder> implements EventsGalleryPhotoLikeContract.View {

    private List<PhotoGalleryEventResponse> imageUrls;

    //Listener for onCLick in Like button
    public interface OnClickLikeListener { void onLikeClick(int idPhoto); }
    private OnClickLikeListener listener;
    public void setOnLikeClickListener(OnClickLikeListener listener) { this.listener = listener; }

    //private int pos;
    private PhotoGalleryAdapterR.PhotoGalleryViewHolder holder;
    private EventsGalleryPhotoLikePresenter eventsGalleryPhotoLikePresenter;

    PhotoGalleryAdapterR() {
        this.imageUrls = new ArrayList<>();
    }

    public void addData(List<PhotoGalleryEventResponse> newsList){
        this.imageUrls.addAll(newsList);
        notifyDataSetChanged();
    }

    @Override
    public PhotoGalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_detail, parent, false);
        eventsGalleryPhotoLikePresenter = new EventsGalleryPhotoLikePresenter();
        eventsGalleryPhotoLikePresenter.attachedView(this);
        return new PhotoGalleryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final PhotoGalleryAdapterR.PhotoGalleryViewHolder holder, int position) {
        this.holder = holder;
        //pos = position;
        if (imageUrls != null) {
            Picasso.with(holder.imgNew.getContext())
                    .load(imageUrls.get(position).getImage_url())
                    .noFade()
                    .into(holder.imgNew);

            holder.lblHour.setText(imageUrls.get(position).getCreated_at());
            holder.lblName.setText(imageUrls.get(position).getNews_title());
            //holder.lblLikes.setText(imageUrls.get(position).getLikePhoto());

            if (imageUrls.get(position).getComent() != null) {
                holder.lblComent.setText(imageUrls.get(position).getComent());
                holder.lblComent.setVisibility(View.VISIBLE);
            } else {
                holder.lblComent.setVisibility(View.GONE);
            }

            holder.imgLikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventsGalleryPhotoLikePresenter.setPhotoLike(imageUrls.get(holder.getAdapterPosition()).getId(), PreferencesHeper.getDniUser(NewPortApplication.getAppContext()));
                }
            });
            eventsGalleryPhotoLikePresenter.getPhotoLikedBy(imageUrls.get(position).getId(), PreferencesHeper.getDniUser(NewPortApplication.getAppContext()));
        }
    }

    @Override
    public int getItemCount() {
        if (imageUrls == null) {
            return 0;
        } else {
            return imageUrls.size();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void showPhotoLikes(PhotoLikeResponse photoLikeResponse) {
        this.holder.lblLikes.setText(String.valueOf(photoLikeResponse.getLikes()));
    }

    @Override
    public void showPhotoLikesError(String error) {
        //Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPhotoLikesFailure(String failure) {
        Toast.makeText(NewPortApplication.getAppContext().getApplicationContext(), failure, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPhotoLikeError(String error) {
        //Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPhotoLikeSuccess(PhotoLikeResponse photoLikeResponse) {
        if (photoLikeResponse.getMessage().equals("disliked")) {
            this.holder.imgLikeButton.setImageResource(R.drawable.like_manito_de_horacio_byn);
        } else {
            this.holder.imgLikeButton.setImageResource(R.drawable.like_manito_de_horacio);
        }
        this.holder.lblLikes.setText(String.valueOf(photoLikeResponse.getLikes()));
    }

    @Override
    public void showPhotoLikedBySuccess(PhotoLikeResponse photoLikeResponse) {
        if (photoLikeResponse.getMessage().equals("liked")) {
            this.holder.imgLikeButton.setImageResource(R.drawable.like_manito_de_horacio);
        } else {
            this.holder.imgLikeButton.setImageResource(R.drawable.like_manito_de_horacio_byn);
        }

        this.holder.lblLikes.setText(String.valueOf(photoLikeResponse.getLikes()));
    }

    @Override
    public void showPhotoLikedByError(String Error) {

    }

    class PhotoGalleryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener/*, EventsGalleryPhotoLikeContract.View*/ {

        private ImageView imgNew;
        private TextView lblHour;
        private TextView lblName;
        private TextView lblLikes;
        private TextView lblComent;
        private ImageView imgLikeButton;
        private EventsGalleryPhotoLikePresenter eventsGalleryPhotoLikePresenter;


        PhotoGalleryViewHolder(View itemView) {
            super(itemView);

            /*eventsGalleryPhotoLikePresenter = new EventsGalleryPhotoLikePresenter();
            eventsGalleryPhotoLikePresenter.attachedView(this);*/

            imgNew = itemView.findViewById(R.id.imgGalleryPhoto);
            lblHour = itemView.findViewById(R.id.lblHourPhoto);
            lblName = itemView.findViewById(R.id.lblNamePhoto);
            lblLikes = itemView.findViewById(R.id.lblLikeCount);
            lblComent = itemView.findViewById(R.id.lblComent);
            imgLikeButton = itemView.findViewById(R.id.imgLikeButton);

            //imgLikeButton.setOnClickListener(this);

            //eventsGalleryPhotoLikePresenter.getPhotoLikedBy(imageUrls.get(pos).getId(), PreferencesHeper.getDniUser(NewPortApplication.getAppContext()));
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.imgLikeButton) {
                //eventsGalleryPhotoLikePresenter.setPhotoLike(imageUrls.get(pos).getId(), PreferencesHeper.getDniUser(NewPortApplication.getAppContext()));
            }
        }

        /*@Override
        public void showPhotoLikes(PhotoLikeResponse photoLikeResponse) {
            lblLikes.setText(String.valueOf(photoLikeResponse.getLikes()));
        }

        @Override
        public void showPhotoLikesError(String error) {
            //Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void showPhotoLikesFailure(String failure) {
            Toast.makeText(itemView.getContext(), failure, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void showPhotoLikeError(String error) {
            //Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void showPhotoLikeSuccess(PhotoLikeResponse photoLikeResponse) {
            if (photoLikeResponse.getMessage().equals("disliked")) {
                imgLikeButton.setImageResource(R.drawable.like_manito_de_horacio_byn);
            } else {
                imgLikeButton.setImageResource(R.drawable.like_manito_de_horacio);
            }
            lblLikes.setText(String.valueOf(photoLikeResponse.getLikes()));
        }

        @Override
        public void showPhotoLikedBySuccess(PhotoLikeResponse photoLikeResponse) {
            if (photoLikeResponse.getMessage().equals("liked")) {
                imgLikeButton.setImageResource(R.drawable.like_manito_de_horacio);
            } else {
                imgLikeButton.setImageResource(R.drawable.like_manito_de_horacio_byn);
            }

            lblLikes.setText(String.valueOf(photoLikeResponse.getLikes()));
        }

        @Override
        public void showPhotoLikedByError(String error) {
            //Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
        }*/
    }
}

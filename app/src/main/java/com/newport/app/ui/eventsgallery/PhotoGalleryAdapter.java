package com.newport.app.ui.eventsgallery;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import java.util.List;

/**
 * Created by psanchez on 02/05/2018.
 */

public class PhotoGalleryAdapter extends PagerAdapter implements EventsGalleryPhotoLikeContract.View {

    private Context context;
    private List<PhotoGalleryEventResponse> imageUrls;
    private TextView lblHour;
    private TextView lblName;
    private TextView lblLikes;
    private TextView lblComent;
    private ImageView imgLikeButton;

    private int position;

    private EventsGalleryPhotoLikePresenter eventsGalleryPhotoLikePresenter;

    PhotoGalleryAdapter(Context context, List<PhotoGalleryEventResponse> imageUrls, TextView lblHour,
                        TextView lblName, TextView lblLikes, ImageView imgLikeButton, TextView lblComent) {
        this.context = context;
        this.imageUrls = imageUrls;
        this.lblHour = lblHour;
        this.lblName = lblName;
        this.lblLikes = lblLikes;
        this.lblComent = lblComent;
        this.imgLikeButton = imgLikeButton;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);

        this.position = position;

        eventsGalleryPhotoLikePresenter = new EventsGalleryPhotoLikePresenter();
        eventsGalleryPhotoLikePresenter.attachedView(this);

        lblHour = (TextView) ((Activity)context).findViewById(R.id.lblHourPhoto);
        lblName = (TextView) ((Activity)context).findViewById(R.id.lblNamePhoto);
        lblLikes = (TextView) ((Activity)context).findViewById(R.id.lblLikeCount);
        //lblComent = (TextView) ((Activity)context).findViewById(R.id.lblComent);
        imgLikeButton = (ImageView) ((Activity)context).findViewById(R.id.imgLikeButton);

        lblHour.setText(imageUrls.get(position).getCreated_at());
        lblName.setText(imageUrls.get(position).getNews_title());


        /*if (imageUrls.get(position).getComent() != null){
            Log.d("positioninstantiateitem", String.valueOf(position));
            lblComent.setVisibility(View.VISIBLE);
            lblComent.setText(imageUrls.get(position).getComent());
        } else {
            lblComent.setVisibility(View.GONE);
        }*/

        Picasso.with(context)
                .load(imageUrls.get(position).getImage_url())
                .noFade()
                .into(imageView);

        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((View) object);
    }


    @Override
    public void showPhotoLikes(PhotoLikeResponse photoLikeResponse) {
        lblLikes.setText(String.valueOf(photoLikeResponse.getLikes()));
    }

    @Override
    public void showPhotoLikesError(String error) {
        //Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPhotoLikesFailure(String failure) {
        Toast.makeText(context, failure, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPhotoLikeError(String error) {
        //Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPhotoLikeSuccess(PhotoLikeResponse photoLikeResponse) {
        if (photoLikeResponse.getMessage().equals("disliked")) {
            imgLikeButton.setImageResource(R.drawable.circulo_sin_fondo);
        }
    }

    @Override
    public void showPhotoLikedBySuccess(PhotoLikeResponse photoLikeResponse) {
        if (photoLikeResponse.getMessage().equals("liked")) {
            imgLikeButton.setImageResource(R.drawable.circulo_con_fondo);
        } else {
            imgLikeButton.setImageResource(R.drawable.circulo_sin_fondo);
        }
    }

    @Override
    public void showPhotoLikedByError(String error) {
        //Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
    }
}

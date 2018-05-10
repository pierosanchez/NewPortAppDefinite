package com.newport.app.ui.eventsgallery;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newport.app.R;
import com.newport.app.data.models.response.PhotoGalleryEventResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by psanchez on 02/05/2018.
 */

public class PhotoGalleryAdapter extends PagerAdapter {

    private Context context;
    private List<PhotoGalleryEventResponse> imageUrls;
    private TextView lblHour;
    private TextView lblName;

    PhotoGalleryAdapter(Context context, List<PhotoGalleryEventResponse> imageUrls, TextView lblHour, TextView lblName) {
        this.context = context;
        this.imageUrls = imageUrls;
        this.lblHour = lblHour;
        this.lblName = lblName;
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

        lblHour = (TextView) ((Activity)context).findViewById(R.id.lblHourPhoto);
        lblName = (TextView) ((Activity)context).findViewById(R.id.lblNamePhoto);

        lblHour.setText(imageUrls.get(position).getCreated_at());
        lblName.setText(imageUrls.get(position).getNews_title());

        Picasso.with(context)
                .load(imageUrls.get(position).getImage_url())
                .noFade()
                .into(imageView);
        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

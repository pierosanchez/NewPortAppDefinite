package com.newport.app.ui.eventsgallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.newport.app.data.models.response.PhotoGalleryEventResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by psanchez on 02/05/2018.
 */

public class PhotoGalleryAdapter extends PagerAdapter {

    private Context context;
    private List<PhotoGalleryEventResponse> imageUrls;
    private int idPhotoGallery;

    PhotoGalleryAdapter(Context context, List<PhotoGalleryEventResponse> imageUrls, int idPhotoGallery) {
        this.context = context;
        this.imageUrls = imageUrls;
        this.idPhotoGallery = idPhotoGallery;
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

        Picasso.with(context)
                .load(imageUrls.get(position).getImage_url())
                .fit()
                .centerCrop()
                .into(imageView);
        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

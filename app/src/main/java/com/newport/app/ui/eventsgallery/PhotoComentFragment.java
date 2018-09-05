package com.newport.app.ui.eventsgallery;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.app.Fragment;
import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.bundled.BundledEmojiCompatConfig;
import android.support.text.emoji.widget.EmojiAppCompatEditText;
import android.support.text.emoji.widget.EmojiEditText;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.perf.metrics.AddTrace;
import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.models.response.PhotoGalleryEventResponse;
import com.newport.app.data.models.response.PhotoUploadedResponse;
import com.newport.app.ui.main.MainActivity;
import com.newport.app.ui.newdetail.NewDetailFragment;
import com.newport.app.util.Constant;
import com.newport.app.util.UBitmap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.newport.app.util.Constant.REQUEST_OBJECTS_CODE;


public class PhotoComentFragment extends Fragment implements EventsGalleryContract.View, View.OnClickListener {
    private static final String ARG_PARAM1 = "absolutePath";
    private static final String ARG_PARAM2 = "idDetailEvent";
    private static final String ARG_PARAM3 = "base64Data";
    private static final String ARG_PARAM4 = "uploadPhotos";
    private static final int REQUEST_OBJECTS_CODE_GALLERY = 258;

    private static final int REQUEST_PERMISSIONS_FOT_GALLERY = 201;
    private EventsGalleryPresenter eventsGalleryPresenter;

    private String mParam1;
    private String mParam2;
    private String mParam3;
    private int mParam4;

    private View rootView;

    private ImageView imgPhoto;
    private Button sendImageButton;
    private EmojiEditText txtComent;
    private ProgressBar prgSendPhoto;

    public PhotoComentFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PhotoComentFragment newInstance(String param1, String param2, String param3, int uploadPhotos) {
        PhotoComentFragment fragment = new PhotoComentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putInt(ARG_PARAM4, uploadPhotos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getInt(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EmojiCompat.Config config = new BundledEmojiCompatConfig(NewPortApplication.getAppContext());
        EmojiCompat.init(config);
        rootView = inflater.inflate(R.layout.fragment_photo_coment, container, false);
        init();
        return rootView;
    }

    private void init(){
        imgPhoto = rootView.findViewById(R.id.imgPhoto);
        sendImageButton = rootView.findViewById(R.id.sendImageButton);
        txtComent = rootView.findViewById(R.id.txtComent);

        prgSendPhoto = rootView.findViewById(R.id.prgSendPhoto);

        eventsGalleryPresenter = new EventsGalleryPresenter();
        eventsGalleryPresenter.attachedView(this);

        decodeBase64();

        sendImageButton.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void decodeBase64(){
        byte[] decodedString = Base64.decode(mParam3, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imgPhoto.setImageBitmap(decodedByte);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sendImageButton) {
            prgSendPhoto.setVisibility(View.VISIBLE);
            String coment = txtComent.getText().toString();
            eventsGalleryPresenter.savePhotoGalleryEvent(Integer.parseInt(mParam2), mParam3, coment);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showPhotosEvent(List<PhotoGalleryEventResponse> photoGalleryEventResponseList) {

    }

    @Override
    public void reloadGalleryPhotos() {

    }

    @Override
    public void showPhotosEmpty(String message) {

    }

    @Override
    public void showPhotosError(String error) {

    }

    @Override
    public void sendPhotosSuccess(PhotoUploadedResponse photo) {
        prgSendPhoto.setVisibility(View.GONE);
        Toast.makeText(NewPortApplication.getAppContext(), "Subido Correctamente.", Toast.LENGTH_SHORT).show();

        EventsGalleryFragment newFragment = EventsGalleryFragment.newInstance(Integer.parseInt(mParam2), mParam4);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.hide(this);
        transaction.add(R.id.content_fragments, newFragment, Constant.FRAGMENT_NEWS_DETAIL);
        transaction.commit();
    }
}

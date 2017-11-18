package com.niquid.personal.bakeme.fragments;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.niquid.personal.bakeme.R;
import com.niquid.personal.bakeme.models.Step;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import org.parceler.Parcels;

import timber.log.Timber;

import static com.niquid.personal.bakeme.utils.RecipeUtils.STEP_KEY;


public class StepDetailFragment extends Fragment implements com.stepstone.stepper.BlockingStep{

    private SimpleExoPlayerView exoPlayerView;
    private ImageView thumbnail;
    private SimpleExoPlayer exoPlayer;
    private TextView description;
    private Step step;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        setRetainInstance(true);
        exoPlayerView = rootView.findViewById(R.id.media_player);
        thumbnail = rootView.findViewById(R.id.media_thumbnail);
        description = rootView.findViewById(R.id.step_description);
        if(savedInstanceState != null)
            step = Parcels.unwrap(savedInstanceState.getParcelable(STEP_KEY));

        return rootView;
    }

    @Override
    public void setArguments(Bundle args) {
        step =  Parcels.unwrap(args.getParcelable(STEP_KEY));
    }

    private void setUI(){
        int orientation = getResources().getConfiguration().orientation;
        Boolean playVideo = step.hasVideo();
        if(orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (playVideo)
                setVideo(step.getVideo());
            else
                setImage(step.getThumbnailURL());

            description.setText(step.getDescription());
        } else {
            setVideoPlayer(step.getVideoURL());
        }
    }

    private void setVideo(String url){
        if(thumbnail != null) thumbnail.setVisibility(View.GONE);
        exoPlayerView.setVisibility(View.VISIBLE);
        setVideoPlayer(url);
    }

    private void setImage(String url){
        exoPlayerView.setVisibility(View.GONE);
        thumbnail.setVisibility(View.VISIBLE);

        Uri uri = Uri.parse(url);
        Picasso.with(getContext()).load(uri).placeholder(R.drawable.loading).into(thumbnail, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                thumbnail.setVisibility(View.GONE);
            }
        });
    }

    private void setVideoPlayer(String url){
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
        exoPlayerView.setPlayer(exoPlayer);

        Uri uri = Uri.parse(url);
        String userAgent = Util.getUserAgent(getContext(), "BakeMe");
        MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(getContext(), userAgent),
                new DefaultExtractorsFactory(), null, null);
        LoopingMediaSource loopingMediaSource = new LoopingMediaSource(mediaSource);
        exoPlayer.prepare(loopingMediaSource);
        exoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STEP_KEY, Parcels.wrap(step));
    }

    private void releaseMedia(){
        if(exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        releaseMedia();
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        releaseMedia();
        callback.goToNextStep();
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        releaseMedia();
        getActivity().finish();
        callback.complete();
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        releaseMedia();
        callback.goToPrevStep();
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        //User can always go to next step
        return null;
    }

    @Override
    public void onSelected() {
        setUI();
        //Do nothing on selected
    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}

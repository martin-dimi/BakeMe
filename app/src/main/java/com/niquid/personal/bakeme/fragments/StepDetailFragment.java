package com.niquid.personal.bakeme.fragments;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.niquid.personal.bakeme.utils.RecipeUtils.STEP_KEY;
import static com.niquid.personal.bakeme.utils.RecipeUtils.STEP_VIDEO_POSITION;


public class StepDetailFragment extends Fragment implements com.stepstone.stepper.BlockingStep{

    private SimpleExoPlayer mPlayer;
    private Step step;
    private boolean playWhenReady = true;
    private long playerPosition;
    private int playerWindow;

    @BindView(R.id.media_player)
    SimpleExoPlayerView mPlayerView;

    @Nullable
    @BindView(R.id.media_thumbnail)
    ImageView thumbnail;

    @Nullable
    @BindView(R.id.step_description)
    TextView description;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        ButterKnife.bind(this, rootView);
        setRetainInstance(true);

        thumbnail = rootView.findViewById(R.id.media_thumbnail);
        description = rootView.findViewById(R.id.step_description);

        if(savedInstanceState != null) {
            step = Parcels.unwrap(savedInstanceState.getParcelable(STEP_KEY));
            playerPosition = savedInstanceState.getLong(STEP_VIDEO_POSITION);
            Timber.d("CLASS: " + getActivity().getLocalClassName());
            setUI();
        }

        return rootView;
    }

    @Override
    public void setArguments(Bundle args) {
        step =  Parcels.unwrap(args.getParcelable(STEP_KEY));
    }

    private void setUI(){
        Boolean playVideo = step.hasVideo();
        //Checks if it is port mode
        if(description != null) {
            if (playVideo)
                setVideo(step.getVideo());
            else
                setImage(step.getThumbnailURL());

            description.setText(step.getDescription());
        } else {
            initPlayer(step.getVideoURL());
        }
    }

    private void setVideo(String url){
        if(thumbnail != null) thumbnail.setVisibility(View.GONE);
        mPlayerView.setVisibility(View.VISIBLE);
        initPlayer(url);
    }

    private void setImage(String url){
        mPlayerView.setVisibility(View.GONE);
        if(thumbnail != null)
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

    private void initPlayer(String url){
        //Setting up the player
        if(mPlayer == null) {
            Timber.i("Setting up the video");
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mPlayer);
            mPlayer.seekTo(playerWindow, playerPosition);
        }

        //Setting up the media
        Uri uri = Uri.parse(url);
        String userAgent = Util.getUserAgent(getContext(), "BakeMe");
        MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(getContext(), userAgent),
                new DefaultExtractorsFactory(), null, null);
        LoopingMediaSource loopingMediaSource = new LoopingMediaSource(mediaSource);
        mPlayer.prepare(loopingMediaSource);
        mPlayer.setPlayWhenReady(playWhenReady);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STEP_KEY, Parcels.wrap(step));
        outState.putLong(STEP_VIDEO_POSITION, playerPosition);
    }

    private void releasePlayer(){
        if(mPlayer != null) {
            playerPosition = mPlayer.getCurrentPosition();
            playerWindow = mPlayer.getCurrentWindowIndex();
            playWhenReady = mPlayer.getPlayWhenReady();

            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mPlayer == null && this.isVisible()){
            Timber.i("Setting up the Player from onResume");
            setUI();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        releasePlayer();
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        releasePlayer();
        callback.goToNextStep();
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        releasePlayer();
        getActivity().finish();
        callback.complete();
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        releasePlayer();
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
        if(mPlayer == null){
            Timber.i("Setting up the Player from onSelect");
            setUI();
        }
    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}

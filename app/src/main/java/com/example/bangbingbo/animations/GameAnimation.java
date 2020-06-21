package com.example.bangbingbo.animations;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import com.example.bangbingbo.game.listeners.AnimationStartStopListener;

public class GameAnimation {
    private DecelerateInterpolator decelerateInterpolator;
    private OvershootInterpolator overshootInterpolator;
    AnimationStartStopListener animationStartStopListener;

    public GameAnimation(AnimationStartStopListener animationStartStopListener) {
        this.decelerateInterpolator = new DecelerateInterpolator();
        this.overshootInterpolator = new OvershootInterpolator();
        this.animationStartStopListener = animationStartStopListener;
    }

    public void startClickAnimation(final ImageView imageClicked, final ImageView floatingPieceView) {
        animationStartStopListener.onAnimationStarted();
        imageClicked.setImageDrawable(null);
        animateClickedView(imageClicked, floatingPieceView);
    }

    private void animateClickedView(final ImageView imageClicked, final ImageView floatingPieceView) {
        final Drawable tempImgView1ClickedDrawable = imageClicked.getDrawable();
        Runnable endAction = new Runnable() {
            public void run() {
                imageClicked.animate().setInterpolator(overshootInterpolator).scaleX(1.0f).scaleY(1.0f).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        imageClicked.setImageDrawable(tempImgView1ClickedDrawable);
                        floatingPieceView.setVisibility(View.INVISIBLE);
                        animationStartStopListener.onAnimationStopped();
                    }
                });
            }
        };
    }

    public void startSlideAnimation(ImageView sourceImage, final ImageView destinationImage, final ImageView floatingPieceView) {
        animationStartStopListener.onAnimationStarted();
        sourceImage.setImageDrawable(null);
        floatingPieceView.setVisibility(View.VISIBLE);
        animateSlide(destinationImage, floatingPieceView);
    }

    private void animateSlide(final ImageView destinationImage, final ImageView floatingPieceView) {
        final Drawable tempFloatingPieceDrawable = floatingPieceView.getDrawable();
        int[] destinationLocation = new int[2];
        destinationImage.getLocationOnScreen(destinationLocation);

        floatingPieceView.animate().translationX(destinationLocation[0]).translationY(destinationLocation[1]).setInterpolator(decelerateInterpolator).withEndAction(new Runnable() {
            @Override
            public void run() {
                destinationImage.setImageDrawable(tempFloatingPieceDrawable);
                floatingPieceView.setVisibility(View.INVISIBLE);
                floatingPieceView.setTranslationX(0);
                floatingPieceView.setTranslationY(0);
                floatingPieceView.setVisibility(View.VISIBLE);
                //TODO: startNextPieceAnimation(getCalculatedBestField() - 1);
                animationStartStopListener.onAnimationStopped();
            }
        });
    }
}

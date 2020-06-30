package com.rangedroid.javoh.oasis.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Build;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

public class MovingViewAnimator {

    public static final int HORIZONTAL_MOVE = 1;
    public static final int VERTICAL_MOVE = 2;
    public static final int DIAGONAL_MOVE = 3;
    public static final int AUTO_MOVE = 0;
    public static final int NONE_MOVE = -1;

    private AnimatorSet mAnimatorSet;
    private Animator.AnimatorListener animatorListener;
    private View mView;

    private boolean isRunning;
    private int currentLoop;
    private boolean infiniteRepetition = true;
    private ArrayList<Float> pathDistances;

    private int loopCount = -1;
    private int movementType;
    private float offsetWidth, offsetHeight;
    private int mSpeed = 50;
    private long mDelay = 0;
    private AccelerateDecelerateInterpolator mInterpolator;

    private Animator.AnimatorListener repeatAnimatorListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(final Animator animation) {
            mView.post((new Runnable() {
                public void run() {
                    if (isRunning) {
                        if (infiniteRepetition) {
                            mAnimatorSet.start();
                            if (animatorListener != null)
                                animatorListener.onAnimationRepeat(animation);
                        } else {
                            currentLoop--;
                            if (currentLoop > 0) {
                                mAnimatorSet.start();
                                if (animatorListener != null)
                                    animatorListener.onAnimationRepeat(animation);
                            }
                        }
                    }
                }
            }));
        }
    };


    public MovingViewAnimator(View imgView) {
        mView = imgView;
        isRunning = false;
        mAnimatorSet = new AnimatorSet();
        pathDistances = new ArrayList<>();
        mInterpolator = new AccelerateDecelerateInterpolator();
    }

    public MovingViewAnimator(View imgView, int type, float width, float height) {
        this(imgView);
        updateValues(type, width, height);
    }

    private void init() {
        setUpAnimator();
        updateListener();
        setUpValues();
    }

    private void setUpAnimator() {
        AnimatorSet animatorSet = new AnimatorSet();
        pathDistances.clear();

        switch (movementType) {
            case HORIZONTAL_MOVE:
                animatorSet.playSequentially(createHorizontalAnimator(0, offsetWidth),
                        createHorizontalAnimator(offsetWidth, 0));
                break;
            case VERTICAL_MOVE:
                animatorSet.playSequentially(createVerticalAnimator(0, offsetHeight),
                        createVerticalAnimator(offsetHeight, 0));
                break;
            case DIAGONAL_MOVE:
                animatorSet.playSequentially(createDiagonalAnimator(0, offsetWidth, 0, offsetHeight),
                        createDiagonalAnimator(offsetWidth, 0, offsetHeight, 0));
                break;
            case AUTO_MOVE:
                animatorSet.playSequentially(
                        createVerticalAnimator(0, offsetHeight),
                        createDiagonalAnimator(0, offsetWidth, offsetHeight, 0),
                        createHorizontalAnimator(offsetWidth, 0),
                        createDiagonalAnimator(0, offsetWidth, 0, offsetHeight),
                        createHorizontalAnimator(offsetWidth, 0),
                        createVerticalAnimator(offsetHeight, 0));
        }

        if (mAnimatorSet != null) {
            mAnimatorSet.removeAllListeners();
            stop();
        }
        mAnimatorSet = animatorSet;
    }

    private void setUpValues() {
        addListener(animatorListener);
        setSpeed(mSpeed);
        setStartDelay(mDelay);
        setRepetition(loopCount);
        setInterpolator(mInterpolator);
    }

    private void updateListener() {
        mAnimatorSet.addListener(repeatAnimatorListener);
    }

    public void updateValues(int type, float w, float h) {
        this.movementType = type;
        this.offsetWidth = w;
        this.offsetHeight = h;
        init();
    }

    public void setMovementType(int type) {
        updateValues(type, offsetWidth, offsetHeight);
    }

    public void setOffsets(float w, float h) {
        updateValues(movementType, w, h);
    }

    public void start() {
        if (movementType != NONE_MOVE) {
            isRunning = true;
            if (!infiniteRepetition)
                currentLoop = loopCount;
            mAnimatorSet.start();
        }
    }

    public void cancel() {
        if(isRunning) {
            mAnimatorSet.removeListener(repeatAnimatorListener);
            mAnimatorSet.cancel();
        }
    }

    public void pause() {
        if(mAnimatorSet.isStarted())
            mAnimatorSet.pause();
    }

    public void resume() {
        if(mAnimatorSet.isPaused())
            mAnimatorSet.resume();
    }

    public void stop() {
        isRunning = false;
        mAnimatorSet.removeListener(repeatAnimatorListener);
        mAnimatorSet.end();
        mView.clearAnimation();
    }

    public void setRepetition(int repetition) {
        if (repetition < 0)
            infiniteRepetition = true;
        else {
            loopCount = repetition;
            currentLoop = loopCount;
            infiniteRepetition = false;
        }
    }

    public Builder addCustomMovement() {
        return new Builder();
    }

    public void clearCustomMovement() {
        init();
        start();
    }

    public int getMovementType() {
        return movementType;
    }

    public int getRemainingRepetitions() {
        return (infiniteRepetition) ? -1 : currentLoop;
    }

    public void setInterpolator(AccelerateDecelerateInterpolator interpolator) {
        mInterpolator = interpolator;
        mAnimatorSet.setInterpolator(interpolator);
    }

    public void setStartDelay(long time) {
        mDelay = time;
        mAnimatorSet.setStartDelay(time);
    }

    public void setSpeed(int speed) {
        mSpeed = speed;
        List<Animator> listAnimator = mAnimatorSet.getChildAnimations();
        for (int i = 0; i < listAnimator.size(); i++) {
            Animator a = listAnimator.get(i);
            a.setDuration(parseSpeed(pathDistances.get(i)));
        }
    }

    public void addListener(Animator.AnimatorListener listener) {
        clearListener();
        if (listener != null) {
            animatorListener = listener;
            mAnimatorSet.addListener(animatorListener);
        }
    }

    public void clearListener() {
        if (animatorListener != null) {
            mAnimatorSet.removeListener(animatorListener);
            animatorListener = null;
        }
    }

    private long parseSpeed(float distance) {
        return (long) ((distance / (float) mSpeed) * 1000f);
    }

    private ObjectAnimator createHorizontalAnimator(float startValue, float endValue) {
        pathDistances.add(Math.abs(startValue - endValue));
        return createObjectAnimation("scrollX", startValue, endValue);
    }

    private ObjectAnimator createVerticalAnimator(float startValue, float endValue) {
        pathDistances.add(Math.abs(startValue - endValue));
        return createObjectAnimation("scrollY", startValue, endValue);
    }

    private ObjectAnimator createDiagonalAnimator(float startW, float endW, float startH, float endH) {
        float diagonal = Pythagoras(Math.abs(startW - endW), Math.abs(startH - endH));
        pathDistances.add(diagonal);
        PropertyValuesHolder pvhX = createPropertyValuesHolder("scrollX", startW, endW);
        PropertyValuesHolder pvhY = createPropertyValuesHolder("scrollY", startH, endH);
        return ObjectAnimator.ofPropertyValuesHolder(mView, pvhX, pvhY);
    }

    private ObjectAnimator createObjectAnimation(String prop, float startValue, float endValue) {
        return ObjectAnimator.ofInt(mView, prop, (int) startValue, (int) endValue);
    }

    private PropertyValuesHolder createPropertyValuesHolder(String prop, float startValue, float endValue) {
        return PropertyValuesHolder.ofInt(prop, (int) startValue, (int) endValue);
    }

    private static float Pythagoras(float a, float b) {
        return (float) Math.sqrt((a * a) + (b * b));
    }

    public class Builder {

        private ArrayList<Animator> mList;

        private Builder() {
            mList = new ArrayList<>();
            pathDistances.clear();
        }

        public Builder addHorizontalMoveToRight() {
            mList.add(createHorizontalAnimator(0, offsetWidth));
            return this;
        }

        public Builder addHorizontalMoveToLeft() {
            mList.add(createHorizontalAnimator(offsetWidth, 0));
            return this;
        }

        public Builder addVerticalMoveToDown() {
            mList.add(createVerticalAnimator(0, offsetHeight));
            return this;
        }

        public Builder addVerticalMoveToUp() {
            mList.add(createVerticalAnimator(offsetHeight, 0));
            return this;
        }

        public Builder addDiagonalMoveToDownRight() {
            mList.add(createDiagonalAnimator(0, offsetWidth, 0, offsetHeight));
            return this;
        }

        public Builder addDiagonalMoveToDownLeft() {
            mList.add(createDiagonalAnimator(offsetWidth, 0, 0, offsetHeight));
            return this;
        }

        public Builder addDiagonalMoveToUpRight() {
            mList.add(createDiagonalAnimator(0, offsetWidth, offsetHeight, 0));
            return this;
        }

        public Builder addDiagonalMoveToUpLeft() {
            mList.add(createDiagonalAnimator(offsetWidth, 0, offsetHeight, 0));
            return this;
        }

        public void start() {
            mAnimatorSet.removeAllListeners();
            stop();
            mAnimatorSet = new AnimatorSet();
            mAnimatorSet.playSequentially(mList);
            updateListener();
            setUpValues();
            MovingViewAnimator.this.start();
        }

    }
}

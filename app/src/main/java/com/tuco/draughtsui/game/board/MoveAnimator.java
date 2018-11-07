package com.tuco.draughtsui.game.board;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.tuco.draughts.board.Chequer;
import com.tuco.draughts.board.util.Coordinate;
import com.tuco.draughts.movement.util.Movement;
import com.tuco.draughtsui.R;

import java.util.ArrayList;
import java.util.List;

public class MoveAnimator {

    private BoardView boardView;
    private PlaceView floatingChequer;
    private List<Coordinate> steps;
    private List<Coordinate> hits;

    public MoveAnimator(BoardView boardView, Movement movement) {
        this.boardView = boardView;
        this.steps = new ArrayList<>(movement.getSteps());
        this.hits = new ArrayList<>(movement.getHits());
    }

    public void start() {
        initializeFloatingChequer();

        animateSubMove();
    }

    private void initializeFloatingChequer() {
        Coordinate startCoordinate = steps.get(0);
        PlaceView startPlace = boardView.getPlaceView(startCoordinate);

        floatingChequer = ((Activity) boardView.getContext()).findViewById(R.id.floatingChecker);
        floatingChequer.setValues(startPlace);
        floatingChequer.setOnlyForeground();
        floatingChequer.setHidden(false);
        floatingChequer.bringToFront();
        floatingChequer.setVisibility(View.VISIBLE);
    }

    private void animateSubMove() {
        if (steps.size() < 2) {
            finishAllAnimation();
            return;
        }

        PlaceView placeA = getFirstCoordinate(steps);
        steps.remove(0);
        PlaceView placeB = getFirstCoordinate(steps);

        PlaceView placeHit = null;
        if (!hits.isEmpty()) {
            placeHit = getFirstCoordinate(hits);
            hits.remove(0);
        }

        AnimatorSet animatorSet = createSubAnimation(placeA, placeB, placeHit);
        animatorSet.start();
    }

    private PlaceView getFirstCoordinate(List<Coordinate> list) {
        Coordinate coordinate = list.get(0);
        return boardView.getPlaceView(coordinate);
    }

    @NonNull
    private AnimatorSet createSubAnimation(PlaceView placeA, PlaceView placeB, PlaceView placeHit) {
        AnimatorSet animatorSet = createAnimatorSet(placeA, placeB, placeHit);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                placeA.setChequer(Chequer.EMPTY);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animateSubMove();
                placeB.setChequer(floatingChequer.getChequer());
                if (placeHit != null) {
                    placeHit.setChequer(Chequer.EMPTY);
                }
            }

        });
        return animatorSet;
    }

    @NonNull
    private AnimatorSet createAnimatorSet(PlaceView placeA, PlaceView placeB, PlaceView placeHit) {
        Path path = generateMovePath(placeA, placeB);

        AnimatorSet animatorSet = new AnimatorSet();
        if (placeHit == null) {
            animatorSet.play(ObjectAnimator.ofFloat(floatingChequer, "x", "y", path));
        } else {
            animatorSet.playTogether(
                    ObjectAnimator.ofFloat(floatingChequer, "x", "y", path),
                    ObjectAnimator.ofFloat(placeHit, "alpha", 0)
            );
        }
        return animatorSet;
    }

    @NonNull
    private Path generateMovePath(PlaceView placeA, PlaceView placeB) {
        int[] startPoint = getScreenLocation(placeA);
        int[] firstPoint = getScreenLocation(placeA);
        int[] secondPoint = getJumpedLocation(placeB);
        int[] endPoint = getScreenLocation(placeB);

        Path path = new Path();
        path.moveTo(startPoint[0], startPoint[1]);
        path.cubicTo(firstPoint[0], firstPoint[1], secondPoint[0], secondPoint[1], endPoint[0], endPoint[1]);

        return path;
    }

    private void finishAllAnimation() {
        floatingChequer.setVisibility(View.GONE);
        synchronized (boardView) {
            boardView.notify();
        }
    }

    private int[] getJumpedLocation(View view) {
        int[] result = getScreenLocation(view);
        result[1] -= view.getHeight();
        return result;
    }

    private int[] getScreenLocation(View view) {
        int[] result = new int[2];
        view.getLocationOnScreen(result);
        generalizeScreenLocation(result);
        return result;
    }

    private void generalizeScreenLocation(int[] location) {
        int[] generalLocation = new int[2];
        ((View) boardView.getParent()).getLocationOnScreen(generalLocation);

        location[0] -= generalLocation[0];
        location[1] -= generalLocation[1];
    }
}

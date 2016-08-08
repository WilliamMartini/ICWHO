package ch.uzh.icu.icwho.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.InputStream;

import ch.uzh.icu.icwho.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GebaeudeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GebaeudeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GebaeudeFragment extends Fragment {
    // declarations
    ImageView i;
    ImageButton bu, bd;

    final int MAX_FLOOR = 6;
    Drawable[] floors = new Drawable[6];
    int currentFloor = 0;

    private OnFragmentInteractionListener mListener;

    public GebaeudeFragment() {
        // Required empty public constructor
    }

    public static GebaeudeFragment newInstance() {
        GebaeudeFragment fragment = new GebaeudeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gebaeude, container, false);

        i = (ImageView) view.findViewById(R.id.gebaeudeImage);
        bu = (ImageButton) view.findViewById(R.id.upButton);
        bd = (ImageButton) view.findViewById(R.id.downButton);

        // load images
        try {
            for (int i = 0; i < MAX_FLOOR; i++) {
                InputStream is = getActivity().getAssets().open("bin" + i + ".png");

                Drawable d = Drawable.createFromStream(is, null);

                floors[i] = d;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // add and set swipe listener
        final GestureDetector g = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener () {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (currentFloor == 1) {
                    doorOpener++;
                }

                if (doorOpener > 2) {
                    Intent intent= new Intent(Intent.ACTION_VIEW,Uri.parse("https://grape.icu.uzh.ch/"));
                    startActivity(intent);

                    doorOpener = 0;
                }

                return super.onDoubleTap(e);
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                final int SWIPE_MIN_DISTANCE = 120;
                final int SWIPE_MAX_OFF_PATH = 250;
                final int SWIPE_THRESHOLD_VELOCITY = 200;
                try {
                    // off path
                    if (Math.abs(e1.getX() - e2.getX()) > SWIPE_MAX_OFF_PATH)
                        return false;
                    // up
                    if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE
                            && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                        currentFloor = (currentFloor - 1) % MAX_FLOOR;
                    // down
                    } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE
                            && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                        currentFloor = (currentFloor + 1) % MAX_FLOOR;
                    }
                } catch (Exception e) {
                    // nothing
                }

                if (currentFloor < 0){
                    currentFloor = MAX_FLOOR - 1;
                }

                i.setImageDrawable(floors[currentFloor]);

                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return g.onTouchEvent(motionEvent);
            }
        });

        i.setImageDrawable(floors[0]);

        // add and set button listeners
        View.OnClickListener up = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                currentFloor = (currentFloor + 1) % MAX_FLOOR;
                i.setImageDrawable(floors[currentFloor]);
            }
        };

        View.OnClickListener down = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                currentFloor = (currentFloor - 1) % MAX_FLOOR;
                if (currentFloor < 0){
                    currentFloor = MAX_FLOOR - 1;
                }
                i.setImageDrawable(floors[currentFloor]);
            }
        };

        bu.setOnClickListener(up);
        bd.setOnClickListener(down);

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            //throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onResume();
    }

    @Override
    public void onPause() {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        super.onPause();
    }

    int doorOpener = 0;
}

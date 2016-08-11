package ch.uzh.icu.icwho.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

import ch.uzh.icu.icwho.AppState;
import ch.uzh.icu.icwho.R;
import ch.uzh.icu.icwho.models.Event;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailedEventFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailedEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailedEventFragment extends Fragment {
    // declarations
    TextView t, d;
    Button b;
    Event e;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DetailedEventFragment() {
        // Required empty public constructor
    }

    public static DetailedEventFragment newInstance(String param1, String param2) {
        DetailedEventFragment fragment = new DetailedEventFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detailed_event, container, false);

        e = AppState.currentEvent;

        t = (TextView) v.findViewById(R.id.detailedEventText);
        t.setMovementMethod(LinkMovementMethod.getInstance());
        d = (TextView) v.findViewById(R.id.descriptionText);
        d.setMovementMethod(LinkMovementMethod.getInstance());

        b = (Button) v.findViewById(R.id.addToCalendarbutton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // import event to calendar
                Intent calIntent = new Intent(Intent.ACTION_INSERT);
                calIntent.setType("vnd.android.cursor.item/event");
                calIntent.putExtra(CalendarContract.Events.TITLE, e.getName());
                calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, e.getLocation());
                calIntent.putExtra(CalendarContract.Events.DESCRIPTION, e.getDescription());

                Calendar calStart = Calendar.getInstance();
                Calendar calEnd = Calendar.getInstance();

                // (- 1) for month!! 'cuz google
                calStart.set(e.getStartDateY(), e.getStartDateM() - 1, e.getStartDateD(), e.getStartTimeH(), e.getStartTimeM());
                calEnd.set(e.getEndDateY(), e.getEndDateM() - 1, e.getEndDateD(), e.getEndTimeH(), e.getEndTimeM());
                long startTime = calStart.getTimeInMillis();
                long endTime = calEnd.getTimeInMillis();
                calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
                calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, startTime);

                calIntent.putExtra(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance().getTimeZone().getID());

                startActivity(calIntent);
            }
        });

        // add data to textview
        t.setText(Html.fromHtml("<b>" + e.getName() + "</b>"));
        t.append("\n\n");
        if (e.getStartDate().equals(e.getEndDate())) {
            t.append("Datum: " + e.getStartDate());
            t.append("\nVon: " + e.getStartTime());
            t.append("\nBis: " + e.getEndTime());
        } else {
            t.append("Startdatum: " + e.getStartDate());
            t.append("\nVon: " + e.getStartTime());
            t.append("\nEnddatum: " + e.getEndDate());
            t.append("\nBis: " + e.getEndTime());
        }

        t.append("\nOrt: " + e.getLocation());

        t.append("\n\n");
        t.append("Link: ");
        t.append(Html.fromHtml("<a href=\"" + e.getLink() + "\">" + e.getLink() + "</a>"));
        t.append("\nAnmeldung nötig? " + e.getSignUp());

        d.append("\n\n");
        d.append(e.getDescription());

        return v;
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
}

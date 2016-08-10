package ch.uzh.icu.icwho.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

import ch.uzh.icu.icwho.AppState;
import ch.uzh.icu.icwho.R;
import ch.uzh.icu.icwho.models.Event;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventsFragment extends Fragment {
    // declarations
    ListView l;
    ArrayList<Event> events = new ArrayList<Event>();
    ArrayList<String> eventsShort = new ArrayList<String>();

    private OnFragmentInteractionListener mListener;

    public EventsFragment() {
        // Required empty public constructor
    }


    public static EventsFragment newInstance() {
        EventsFragment fragment = new EventsFragment();
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
        View v = inflater.inflate(R.layout.fragment_events, container, false);

        l = (ListView) v.findViewById(R.id.eventList);

        // Test Event
        events.add(new Event("App Testing", 2016, 8, 11, 2016, 8, 11, 0, 30, 1, 5, "diese gut?", "icu.uzh.ch", "Nein"));
        events.add(new Event("App Release", 2016, 9, 1, 2016, 9, 2, 0, 0, 0, 5, "diese gut!", "icu.uzh.ch", "Ja"));

        // TODO: add JSON parsing

        // add events for display in ListView
        for (Event e : events) {
            eventsShort.add(e.getStartDate() + "\t\t" + e.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, eventsShort);
        l.setAdapter(adapter);

        //TODO: implement listener
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AppState.currentEvent = events.get(i);

                AppState.currentState = 1;

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_main, new AboutFragment()).commit();
            }
        });

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

    public Event parseEvent(JSONObject jo) {
        // TODO: add method body
        return new Event();
    }
}

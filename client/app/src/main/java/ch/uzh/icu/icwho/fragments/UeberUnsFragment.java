package ch.uzh.icu.icwho.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import ch.uzh.icu.icwho.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UeberUnsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UeberUnsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UeberUnsFragment extends Fragment {
    // declarations
    TextView t, l;
    Spinner s;

    String werSindwir, leitbild, vorstand, staff, mitgliedschaft;

    private OnFragmentInteractionListener mListener;

    public UeberUnsFragment() {
        // Required empty public constructor
    }


    public static UeberUnsFragment newInstance(String param1, String param2) {
        UeberUnsFragment fragment = new UeberUnsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_ueber_uns, container, false);

        t = (TextView) view.findViewById(R.id.ueberUnsText);
        l = (TextView) view.findViewById(R.id.linkText);
        s = (Spinner) view.findViewById(R.id.ueberUnsSpinner);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    t.setText(Html.fromHtml(werSindwir));
                } else if (i == 1) {
                    t.setText(Html.fromHtml(leitbild));
                } else if (i == 2) {
                    t.setText(Html.fromHtml(vorstand));
                } else if (i == 3) {
                    t.setText(Html.fromHtml(staff));
                } else if (i == 4) {
                    t.setText(Html.fromHtml(mitgliedschaft));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        l.setText(Html.fromHtml("<a href=\"https://www.icu.uzh.ch\">https://www.icu.uzh.ch</a>  "));
        l.setMovementMethod(LinkMovementMethod.getInstance());

        // get content from JSON
        try {
            JSONObject jo = new JSONObject(loadJSONFromAsset());

            werSindwir = jo.getString("werSindWir");
            leitbild = jo.getString("leitbild");
            vorstand = jo.getString("vorstand");
            staff = jo.getString("staff");
            mitgliedschaft = jo.getString("mitgliedschaft");
        } catch (Exception e) {
            e.printStackTrace();
        }

        t.setText(Html.fromHtml(werSindwir));
        t.setMovementMethod(new ScrollingMovementMethod());

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

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("ueberUns.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}

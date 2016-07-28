package ch.uzh.icu.icwho.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import org.json.JSONObject;

import ch.uzh.icu.icwho.R;
import ch.uzh.icu.icwho.services.HttpRequest;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link XKCDFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link XKCDFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class XKCDFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public XKCDFragment() {
        // Required empty public constructor
    }


    public static XKCDFragment newInstance() {
        XKCDFragment fragment = new XKCDFragment();
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
        View view = inflater.inflate(R.layout.fragment_xkcd, container, false);

        final TextView t = (TextView) view.findViewById(R.id.xkcdText);
        final WebView w = (WebView) view.findViewById(R.id.xkcdWeb);


        t.setText("Getting content");

        String requestURL = "http://xkcd.com/info.0.json";

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String imgurl = new String();

                try {
                    HttpRequest request = HttpRequest.get(strings[0]);
                    String s = request.body();

                    JSONObject obj = new JSONObject(s);

                    imgurl = obj.getString("img");
                } catch (Throwable t) {

                }
            return imgurl;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                //w.setInitialScale(1);
                w.loadUrl(s);
            }
        }.execute(requestURL);


        t.setText("Done getting content");



        if (getActivity().getResources().getConfiguration().orientation == 2) {
            t.setVisibility(View.GONE);
        } else {
            t.setVisibility(View.VISIBLE);
        }

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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        final TextView t = (TextView) getView().findViewById(R.id.xkcdText);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            t.setVisibility(View.GONE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            t.setVisibility(View.VISIBLE);
        }
    }
}

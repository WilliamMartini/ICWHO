package ch.uzh.icu.icwho.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

import ch.uzh.icu.icwho.R;
import ch.uzh.icu.icwho.services.HttpRequest;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {
    private String requestURL = "https://graph.facebook.com/FVICU/feed?access_token=EAACJmFennJYBALrZBfeIFpvP9X3UTgOBQXGv04gYSaw7qrTKCy5AFMp5gFro3CnL7JmE1GLU11VMW60fZAt6vjAj2UyUEncrMscirFjh3uUhbPBhlGbvBkvwY87D5QVh9RREZAvZAGgH81PVxYInrGpN6ROuJbMZD";

    ArrayList<String> postIDs = new ArrayList<String>();
    TextView t;

    private OnFragmentInteractionListener mListener;

    public NewsFragment() {
        // Required empty public constructor
    }


    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
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
        View v = inflater.inflate(R.layout.fragment_news, container, false);

        t = (TextView) v.findViewById(R.id.newsText);
        TextView l = (TextView) v.findViewById(R.id.newsIntroText);

        l.setText("Die Neuigkeiten der letzten Wochen!\nBesuche uns auf Facebook:\n");
        l.append(Html.fromHtml("<a href=\"https://www.facebook.com/FVICU/\">https://www.facebook.com/FVICU/</a>"));
        l.setMovementMethod(LinkMovementMethod.getInstance());

        t.setText("Lade die letzten News ...");
        t.setMovementMethod(LinkMovementMethod.getInstance());

        try {
            new AsyncTask<String, Void, JSONObject>() {
                @Override
                protected JSONObject doInBackground(String... strings) {
                    JSONObject jo = null;

                    try {
                        // make request and convert to JSON
                        HttpRequest request = HttpRequest.get(strings[0]);
                        String s = request.body();

                        jo = new JSONObject(s);
                    } catch (Throwable th) {

                    }

                    return jo;
                }

                @Override
                protected void onPostExecute(JSONObject jo) {
                    super.onPostExecute(jo);

                    // extract date from JSON
                    JSONArray ja = new JSONArray();

                    t.setText("");
                    try {
                        ja = jo.getJSONArray("data");

                        for (int i = 0; i < ja.length(); i++){
                            jo = ja.getJSONObject(i);

                            if (jo.has("message")){
                                String message = jo.getString("message") + "\n";
                                if (message.length() > 40) {

                                    String date = new String();
                                    String dateTime = jo.getString("created_time");
                                    // convert date
                                    String[] dateTimeArr = dateTime.split("T");
                                    String[] dateArr = dateTimeArr[0].split("-");
                                    date = dateArr[2] + "." + dateArr[1] + "." + dateArr[0];

                                    //String news = date + "\n" + message + "----------------";
                                    t.append(Html.fromHtml("<b>" + date + "</b><br/>"));// + message + "<br/><b>--------------</b><br/><br/>"));
                                    t.append(message + "--------------\n\n");
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.execute(requestURL);

        } catch (Exception e){
            e.printStackTrace();
        }

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

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("fb.json");
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

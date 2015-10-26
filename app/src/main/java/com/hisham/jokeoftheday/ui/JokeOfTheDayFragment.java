package com.hisham.jokeoftheday.ui;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hisham.jokeoftheday.R;
import com.hisham.jokeoftheday.utils.ParseDataStructure;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JokeOfTheDayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JokeOfTheDayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JokeOfTheDayFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = JokeOfTheDayFragment.class.getSimpleName().toString();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Button btnLike;
    private TextView tvTitle;
    private TextView tvJoke;
    private TextView tvSubmittedBy;
    private Button btnShare;
    private Button btnCopy;
    private FloatingActionButton floatingButton;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JokeOfTheDayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JokeOfTheDayFragment newInstance(String param1, String param2) {
        JokeOfTheDayFragment fragment = new JokeOfTheDayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public JokeOfTheDayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_joke_of_the_day, container, false);

        initViews(view);

        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseDataStructure.JokeTableName);
        // TODO this has to be changed to retreive the most liked joke
        // Retrieve the most recent ones
        query.orderByDescending("createdAt");
        // Only retrieve the last
        query.setLimit(1);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    Log.i(TAG, "Retrieved " + scoreList.size() + " jokes");
                    if(scoreList.size() > 0){
                        ParseObject parseObjectLastJoke = scoreList.get(0);
                        tvTitle.setText(parseObjectLastJoke.getString(ParseDataStructure.JokeColJokeTitle));
                        tvJoke.setText(parseObjectLastJoke.getString(ParseDataStructure.JokeColJokeText));
                        // loadLikes(scoreList.get(i));
                    }
                } else {
                    Log.e(TAG, "Error: " + e.getMessage());
                }
            }
        });




        return view;
    }

    /**
     * Initialize all the views used on this fragment
     * @param view
     */
    private void initViews(View view) {
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvJoke = (TextView) view.findViewById(R.id.tvJoke);
        tvSubmittedBy = (TextView) view.findViewById(R.id.tvSubmittedBy);
        btnLike = (Button) view.findViewById(R.id.btnLike);
        btnShare = (Button) view.findViewById(R.id.btnShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = tvJoke.getText().toString();
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(share, "Share this joke."));
            }
        });
        btnCopy = (Button) view.findViewById(R.id.btnCopy);
        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(tvJoke.getText().toString(), tvJoke.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getActivity(), "Copied.", Toast.LENGTH_SHORT).show();
            }
        });
        floatingButton = (FloatingActionButton) view.findViewById(R.id.floatingButton);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.gotoCreateAJoke();
                }
            }
        });
    }


    public void loadLikes(final ParseObject parseObject){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseDataStructure.LikeTableName);
        query.whereEqualTo(ParseDataStructure.LikeJokeID, parseObject.getObjectId());
        query.whereEqualTo(ParseDataStructure.LikeUserID, ParseUser.getCurrentUser().getObjectId());
        // Retrieve the most recent ones
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    Log.i(TAG, "Retrieved " + scoreList.size() + " like on the post : " + parseObject.getObjectId());
                    btnLike.setText("Like (" + scoreList.size() + ")");
                } else {
                    Log.e(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
        void gotoCreateAJoke();
    }

}

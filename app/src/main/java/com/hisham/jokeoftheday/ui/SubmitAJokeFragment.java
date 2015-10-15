package com.hisham.jokeoftheday.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hisham.jokeoftheday.R;
import com.hisham.jokeoftheday.utils.ParseDataStructure;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SubmitAJokeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SubmitAJokeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubmitAJokeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = SubmitAJokeFragment.class.getSimpleName().toString();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubmitAJokeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubmitAJokeFragment newInstance(String param1, String param2) {
        SubmitAJokeFragment fragment = new SubmitAJokeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SubmitAJokeFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_submit_ajoke, container, false);

        final Button btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        final EditText etJokeTitle = (EditText) view.findViewById(R.id.etJokeTitle);
        final EditText etJokeText = (EditText) view.findViewById(R.id.etJokeText);
        final ProgressBar progressBarSubmit = (ProgressBar) view.findViewById(R.id.progressBarSubmit);
        final TextInputLayout etJokeTitleLayout = (TextInputLayout)view.findViewById(R.id.etJokeTitleLayout);
        final TextInputLayout textInputLayoutJokeText = (TextInputLayout)view.findViewById(R.id.etJokeTextLayout);
        textInputLayoutJokeText.setErrorEnabled(true);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if post validation is not successful, return the user to try again.
                if(!postValidationSuccessful()){
                    return;
                }
                
                progressBarSubmit.setVisibility(View.VISIBLE);
                btnSubmit.setVisibility(View.GONE);
                ParseObject testObject = new ParseObject(ParseDataStructure.JokeTableName);
                testObject.put(ParseDataStructure.JokeColJokeTitle, etJokeTitle.getText().toString());
                testObject.put(ParseDataStructure.JokeColJokeText, etJokeText.getText().toString());

                testObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        progressBarSubmit.setVisibility(View.GONE);
                        btnSubmit.setVisibility(View.VISIBLE);
                        textInputLayoutJokeText.setError("");
                        // is e is null, means no exception, that's why successful.
                        if(e == null){
                            etJokeTitle.setText("");
                            etJokeText.setText("");
                            Log.e(TAG, "Post updated successfully");
                            Toast.makeText(getActivity().getApplicationContext(), "Post updated successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            /**
             * Validates if all fields while posting a joke are fine
             * @return true or false
             */
            private boolean postValidationSuccessful() {

                if(TextUtils.isEmpty(etJokeText.getText().toString().trim())){
                    textInputLayoutJokeText.setError("Joke can't be empty.");
                    return false;
                }

                if(etJokeText.getText().toString().trim().length() < 10){
                    textInputLayoutJokeText.setError("Joke can't be that small.");
                    return false;
                }
                return true;
            }
        });


//        TextInputLayout textInputLayoutJokeText = (TextInputLayout)view.findViewById(R.id.etJokeTitleLayout);
//        textInputLayoutJokeText.setErrorEnabled(true);
//        textInputLayoutJokeText.setError("Enter");


        return view;
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
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}

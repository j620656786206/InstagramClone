package tabian.com.instagramclone.Profile;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import tabian.com.instagramclone.Login.LoginActivity;
import tabian.com.instagramclone.R;

/**
 * Created by j on 2018/3/30.
 */

public class SignOutFragment extends Fragment {

    private static final String TAG = "SignOutFragment";

    // firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressBar mProgressBar;
    private TextView tvSignout, tvSigningout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signout, container, false);
        tvSignout = (TextView) view.findViewById(R.id.tvConfirmSignout);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        tvSigningout = (TextView) view.findViewById(R.id.tvSigningout);
        Button btnConfirmSingout = (Button) view.findViewById(R.id.btnConfirmSignout);

        mProgressBar.setVisibility(View.GONE);
        tvSigningout.setVisibility(View.GONE);

        setupFirebaseAuth();

        btnConfirmSingout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to sing out");
                mProgressBar.setVisibility(View.VISIBLE);
                tvSigningout.setVisibility(View.VISIBLE);

                mAuth.signOut();
                getActivity().finish();
            }
        });

        return view;
    }

    /*
    ----------------------------------Firebase--------------------------------------
     */

    /**
     * Setup firebase auth object
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth");

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    //user is signed in
                    Log.d(TAG, "setupFirebaseAuth: signed_in" + user.getUid());
                } else {
                    //user is signed out
                    Log.d(TAG, "setupFirebaseAuth: signed_out");

                    Log.d(TAG, "onAuthStateChanged: navigating back to login screen.");
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}

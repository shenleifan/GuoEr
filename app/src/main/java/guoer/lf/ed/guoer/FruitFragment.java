package guoer.lf.ed.guoer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import guoer.lf.ed.guoer.logUtils.LogUtils;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FruitFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FruitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FruitFragment extends Fragment {
    private static final String TAG = "FruitFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String FRUIT_NAME = "fruit_name";
    private static final String FRUIT_IMAGE_ID = "fruit_image_id";

    private static final String URL = "http://cn.bing.com/dict/search?q=%s&FORM=BDVSP2&qpvt=%s";

    // TODO: Rename and change types of parameters
    private String mFruitName;
    private int mFruitImageId;

    private OnFragmentInteractionListener mListener;

    public FruitFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param fruitName    fruit name.
     * @param fruitImageId fruit image id.
     * @return A new instance of fragment FruitFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FruitFragment newInstance(String fruitName, int fruitImageId) {
        FruitFragment fragment = new FruitFragment();
        Bundle args = new Bundle();
        args.putString(FRUIT_NAME, fruitName);
        args.putInt(FRUIT_IMAGE_ID, fruitImageId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFruitName = getArguments().getString(FRUIT_NAME);
            mFruitImageId = getArguments().getInt(FRUIT_IMAGE_ID);
            LogUtils.d(TAG, "onCreate " + mFruitName + " " + mFruitImageId);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fruit, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(toolbar);

        ActionBar actionBar = appCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(mFruitName);
//        collapsingToolbarLayout.setExpandedTitleColor();

        ImageView fruitImageView = (ImageView) view.findViewById(R.id.fruit_image_view);
        Glide.with(getActivity()).load(mFruitImageId).into(fruitImageView);

        WebView webView = (WebView) view.findViewById(R.id.web_view_item_des);
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setWebViewClient(new WebViewClient());
//        webView.setWebChromeClient(new WebChromeClient());
        LogUtils.d(TAG, "onCreateView()" + String.format(URL, mFruitName, mFruitName));
        if (!TextUtils.isEmpty(mFruitName)) {
            webView.loadUrl(String.format(URL, mFruitName, mFruitName));
        }
        return view;
    }

    private String generateFruitDes(String fruitName) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            builder.append(fruitName);
        }
        return builder.toString();
    }

    // TODO: Rename method, update argument and hook method into UI event
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
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

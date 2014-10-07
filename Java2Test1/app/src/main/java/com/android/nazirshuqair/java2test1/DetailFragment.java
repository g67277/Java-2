package com.android.nazirshuqair.java2test1;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.InjectView;


/**
 * Created by nazirshuqair on 10/6/14.
 */
public class DetailFragment extends Fragment {

    public static final String TAG = "DetailFragment.TAG";

    private static final String ARG_TITLE = "DetailFragment.ARG_TITLE";
    private static final String ARG_DESCRIPTIONG = "DetailFragment.ARG_DESCRIPTION";
    private static final String ARG_STAR = "DetailFragment.ARG_STAR";
    private static final String ARG_RATING = "DetailFragment.ARG_RATING";
    private static final String ARG_YEAR = "DetailFragment.ARG_YEAR";


    public static DetailFragment newInstance(String _title, String _description, String _star, int _rating, int _year) {
        DetailFragment frag = new DetailFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, _title);
        args.putString(ARG_DESCRIPTIONG, _description);
        args.putString(ARG_STAR, _star);
        args.putInt(ARG_RATING, _rating);
        args.putInt(ARG_YEAR, _year);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container,
                             Bundle _savedInstanceState) {
        View view = _inflater.inflate(R.layout.detail_fragment, _container, false);


        return view;
    }

    @Override
    public void onActivityCreated(Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);


        Bundle args = getArguments();
        if(args != null && args.containsKey(ARG_TITLE)) {
            setDisplayText(args.getString(ARG_TITLE),
                    args.getString(ARG_DESCRIPTIONG),
                    args.getString(ARG_STAR),
                    args.getInt(ARG_RATING),
                    args.getInt(ARG_YEAR));
        }
    }

    public void setDisplayText(String _title, String _description, String _star, int _rating, int _year) {

        TextView titleView = (TextView)getView().findViewById(R.id.title_name);
        TextView descriptionView = (TextView)getView().findViewById(R.id.descriptions_view);
        TextView starView = (TextView)getView().findViewById(R.id.star_view);
        TextView ratingView = (TextView)getView().findViewById(R.id.rating_view);
        TextView yearView = (TextView)getView().findViewById(R.id.year_view);

        titleView.setText(_title);
        descriptionView.setText(_description);
        starView.setText(_star);
        ratingView.setText(String.valueOf(_rating));
        yearView.setText(String.valueOf(_year));
    }

}

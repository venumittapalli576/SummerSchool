package com.developmentapps.summerschool.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.developmentapps.summerschool.R;
import com.developmentapps.summerschool.course.offline.AbacusDetails;
import com.developmentapps.summerschool.course.offline.BankcoachingDetails;
import com.developmentapps.summerschool.course.offline.ComputerlearningDetails;
import com.developmentapps.summerschool.course.offline.DanceDetails;
import com.developmentapps.summerschool.course.offline.DrawingDetails;
import com.developmentapps.summerschool.course.offline.DrivingDetails;
import com.developmentapps.summerschool.course.offline.EnglishDetails;
import com.developmentapps.summerschool.course.offline.HindiDetails;
import com.developmentapps.summerschool.course.offline.MusicDetails;
import com.developmentapps.summerschool.course.offline.SkettingDetails;
import com.developmentapps.summerschool.course.offline.SwimmingDetails;
import com.developmentapps.summerschool.course.offline.YogaDetails;
import com.developmentapps.summerschool.course.offline.ZimDetails;
import com.developmentapps.summerschool.course.online.JavaDetails;
import com.developmentapps.summerschool.course.online.OnlineBank;
import com.developmentapps.summerschool.course.online.OnlineComputerProgramming;
import com.developmentapps.summerschool.course.online.OnlineEnglish;
import com.developmentapps.summerschool.course.online.OnlinePHP;
import com.developmentapps.summerschool.course.online.SqlLangauage;
import com.developmentapps.summerschool.course.online.cPlusLangauge;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PhotosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PhotosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhotosFragment extends ListFragment implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PhotosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhotosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhotosFragment newInstance(String param1, String param2) {
        PhotosFragment fragment = new PhotosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    EditText inputSearch;
    static ArrayAdapter adapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.OnlineCourses, android.R.layout.simple_list_item_1);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photos, container, false);
        inputSearch = (EditText) view.findViewById(R.id.inputSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                PhotosFragment.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
        return view;
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

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
        if (position == 0) {
            Intent i = new Intent(getActivity(), JavaDetails.class);
            startActivity(i);
        } else if (position == 1) {
            Intent i = new Intent(getActivity(), OnlineBank.class);
            startActivity(i);
        } else if (position == 2) {
            Intent i = new Intent(getActivity(), OnlineComputerProgramming.class);
            startActivity(i);
        } else if (position == 3) {
            Intent i = new Intent(getActivity(), OnlineEnglish.class);
            startActivity(i);
        } else if (position == 4) {
            Intent i = new Intent(getActivity(), OnlinePHP.class);
            startActivity(i);
        } else if (position == 5) {
            Intent i = new Intent(getActivity(), cPlusLangauge.class);
            startActivity(i);
        } else if (position == 6) {
            Intent i = new Intent(getActivity(),SqlLangauage .class);
            startActivity(i);
        }
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

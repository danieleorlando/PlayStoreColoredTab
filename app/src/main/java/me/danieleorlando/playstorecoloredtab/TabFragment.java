package me.danieleorlando.playstorecoloredtab;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.danieleorlando.playstorecoloredtab.databinding.FragmentTabBinding;


public class TabFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private String mParam1;

    private FragmentTabBinding binding;

    public TabFragment() {
        // Required empty public constructor
    }

    public static TabFragment newInstance(String param1) {
        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_tab, container, false);

        View view =  binding.getRoot();

        binding.textView.setText(mParam1);

        return view;

    }

}

package com.dhairytripathi.walls.ui.Fragment;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Slide;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.dhairytripathi.walls.API.ServiceAPI;
import com.dhairytripathi.walls.Adapter.PixbayAdapter;
import com.dhairytripathi.walls.Model.Pixbay;
import com.dhairytripathi.walls.R;
import com.dhairytripathi.walls.ui.Activity.SearchableActivity;

import java.util.ArrayList;
import java.util.Objects;

public class MainFragment extends Fragment {
    private RecyclerView rvFeatured;
    private RecyclerView rvCategory;
    private RecyclerView rvLatest;
    private RecyclerView rvPopular;
    private LottieAnimationView searchView;
    private EditText searchText;
    boolean clicked = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_main, container, false);
        rvFeatured = root.findViewById(R.id.rvMainFeatured);
        rvCategory = root.findViewById(R.id.rvMainCategory);
        rvLatest = root.findViewById(R.id.rvMainLatest);
        rvPopular = root.findViewById(R.id.rvMainPopular);
        searchView = root.findViewById(R.id.searchButton);
        searchText = root.findViewById(R.id.searchText);
        rvFeatured.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvLatest.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvPopular.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        ArrayList<Pixbay> features = new ArrayList<>();
        ArrayList<Pixbay> categories = new ArrayList<>();
        ArrayList<Pixbay> latest = new ArrayList<>();
        ArrayList<Pixbay> populars = new ArrayList<>();
        PixbayAdapter featureAdapter = new PixbayAdapter(getContext(), getActivity(), features, 1, "main", "Featured");
        PixbayAdapter categoryAdapter = new PixbayAdapter(getContext(), getActivity(), categories, 2, "main", "category");
        PixbayAdapter latestAdapter = new PixbayAdapter(getContext(), getActivity(),latest, 1,"main", "Latest");
        PixbayAdapter popularAdapter = new PixbayAdapter(getContext(), getActivity(),populars, 1,"main", "Popular");
        rvFeatured.setAdapter(featureAdapter);
        rvCategory.setAdapter(categoryAdapter);
        rvLatest.setAdapter(latestAdapter);
        rvPopular.setAdapter(popularAdapter);
        searchText.setVisibility(View.GONE);

        ServiceAPI.getImages(getContext(), "" ,1, true, "", "", "", "vertical","", features, featureAdapter);
        ServiceAPI.getImages(getContext(), "" ,1, false, "latest", "", "", "vertical","", latest, latestAdapter);
        ServiceAPI.getImages(getContext(), "" ,1, false, "popular", "", "", "vertical","", populars, popularAdapter);
        int i = 12;
        while (i>0) {
            categories.add(new Pixbay("","","","","","",
                    "","","","","","",
                    "","","","","","","","","",""));
            i--;
        }
        categoryAdapter.notifyDataSetChanged();

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final float progress = 0.7f;
                if(!clicked) {
                    Log.d("BUTTON", "OPEN");
                    buttonOpen();

            } else {
                    Log.d("BUTTON", "CLOSE");
                    buttonClose(false);

                }
            }
        });

        searchText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.d("IME", event.toString());
                if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    boolean isSearch = true;
                    buttonClose(isSearch);
                }
                return false;
            }
        });

        return root;
    }

    private void buttonOpen() {
        searchView.playAnimation();
        searchText.setVisibility(View.VISIBLE);
        final Animation slide_right_in = AnimationUtils.loadAnimation(
                getContext(), R.anim.slide_right_in);
        searchText.startAnimation(slide_right_in);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                searchView.pauseAnimation();
            }
        }, searchView.getDuration()/2);
        clicked = true;
        searchText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void buttonClose(boolean isSearch) {
        searchView.resumeAnimation();
        final Animation slide_left_out = AnimationUtils.loadAnimation(
                getContext(), R.anim.slide_left_out);
        searchText.startAnimation(slide_left_out);
        clicked = false;
        searchText.clearFocus();
        String query = String.valueOf(searchText.getText());
        searchText.setText("");
        searchText.clearAnimation();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        searchText.setVisibility(View.GONE);
        if(isSearch && query.length() != 0) {
            Intent i = new Intent(getContext(), SearchableActivity.class);
            i.putExtra("query", query);
            getContext().startActivity(i);
        }
    }
}
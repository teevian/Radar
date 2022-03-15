package outspin.mvp.radar.ui.radar_inside;

import static java.lang.String.*;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import outspin.mvp.radar.R;
import outspin.mvp.radar.api.APIHandler;
import outspin.mvp.radar.api.JSONBuilder;
import outspin.mvp.radar.data.Macros;
import outspin.mvp.radar.databinding.FragmentRadarInsideBinding;
import outspin.mvp.radar.models.UserThumb;
import outspin.mvp.radar.ui.radar_outside.RadarOutsideFragment;


public class RadarInsideFragment extends Fragment implements InsideAdapter.ItemClickListener {
    private FragmentRadarInsideBinding fragmentRadarInsideBinding;
    private ArrayList<UserThumb> userThumbs = null;

    private InsideAdapter gridAdapter;
    RecyclerView rvInsideGrid;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentRadarInsideBinding = FragmentRadarInsideBinding.inflate(inflater, container, false);
        fragmentRadarInsideBinding.btLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new RadarOutsideFragment())
                        .addToBackStack("outside")
                        .commit();
            }
        });

        return fragmentRadarInsideBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvInsideGrid = fragmentRadarInsideBinding.rvInsideUsers;

        PopulateAdapter populate = new PopulateAdapter(this);
        populate.execute();
    }

    @Override
    public void onItemClick(View view, int position, Context parent) {
        ProfileBottomSheetDialog myBottomSheetDialogFragment =
                new ProfileBottomSheetDialog(getContext(), userThumbs.get(position));
        myBottomSheetDialogFragment.show(getChildFragmentManager(), myBottomSheetDialogFragment.getTag());
    }

    protected void populateInsideClubInfo(int population, int burns) {
        fragmentRadarInsideBinding.tvInsideHeaderPopulation.setText(valueOf(population));
        fragmentRadarInsideBinding.burnNumbered.tvNumberOfBurns.setText(valueOf(burns));
    }

    protected class PopulateAdapter extends AsyncTask<Void, Void, JSONObject> {
        HttpURLConnection urlConnection = null;
        String jsonString = null;
        RadarInsideFragment parent;

        PopulateAdapter(RadarInsideFragment parent) {
            this.parent = parent;
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {
            JSONObject jsonData = null;
            try {
                URL url = new URL("http://92.222.10.201:62126/users?id=21&club=2");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setDoInput(false);

                int statusCode = urlConnection.getResponseCode();

                if(statusCode == Macros.SERVER_STATUS_OK) {
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
                    StringBuilder stringBuilder = new StringBuilder();

                    String responseLineFromAPI;
                    while ((responseLineFromAPI = bufferedReader.readLine()) != null)
                        stringBuilder.append(responseLineFromAPI).append("\n");

                    bufferedReader.close();

                    jsonString = stringBuilder.toString();
                    jsonData = JSONBuilder.JSONfromString(jsonString).getJSONObject("data");
                } else {
                    Log.d("STATUS CODE", "NOT OK");
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return jsonData;
        }

        @Override
        protected void onPostExecute(JSONObject jsonData) {
            super.onPostExecute(jsonData);
            try {
                userThumbs = JSONBuilder.userThumbsInsideFromJSON(jsonData);

                Log.d("THUMBS: ", userThumbs.toString());
                gridAdapter = new InsideAdapter(parent.getContext(), userThumbs);
                gridAdapter.setClickListener(parent);

                rvInsideGrid.setLayoutManager(new GridLayoutManager(parent.getContext(),
                        Macros.CONST_RADAR_INSIDE_NUM_OF_COLUMNS));
                rvInsideGrid.setAdapter(gridAdapter);

                populateInsideClubInfo(userThumbs.size(), 20);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}



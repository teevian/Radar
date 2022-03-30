package outspin.mvp.radar.ui.fragments;

import static java.lang.String.*;

import android.content.Context;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.List;

import outspin.mvp.radar.R;
import outspin.mvp.radar.api.APIErrorResponse;
import outspin.mvp.radar.api.APIHandler;
import outspin.mvp.radar.api.JSONParser;
import outspin.mvp.radar.data.Macros;
import outspin.mvp.radar.databinding.FragmentRadarInsideBinding;
import outspin.mvp.radar.models.Thumbnail;
import outspin.mvp.radar.ui.adapters.InsideAdapter;
import outspin.mvp.radar.ui.dialogs.ProfileBottomSheetDialog;


public class RadarInsideFragment extends Fragment implements InsideAdapter.ItemClickListener, APIHandler.APIConnectionCallback {
    private FragmentRadarInsideBinding fragmentRadarInsideBinding;
    private List<Thumbnail> userThumbs = null;
    private RecyclerView rvInsideGrid;
    APIHandler.QueryAPI getUsersInClubTask;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentRadarInsideBinding = FragmentRadarInsideBinding.inflate(inflater, container, false);
        fragmentRadarInsideBinding.btLeave.setOnClickListener(view -> {

            //  TODO ??? isto dá problemas de stack overflow - AVISAR JOAO (com jeitinho)
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, new RadarOutsideFragment())
                    .addToBackStack("outside")
                    .commit();
            // TODO pensar se faz mais sentido ser replace em vez de add to backstack
            /*
            if (savedInstanceState == null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                RuntimePermissionsFragment fragment = new RuntimePermissionsFragment();
                transaction.replace(R.id.sample_content_fragment, fragment);
                transaction.commit();
            }
             */
            // TODO código como deve ser:
            // getParentFragmentManager().popBackStack();
        });


        return fragmentRadarInsideBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvInsideGrid = fragmentRadarInsideBinding.rvInsideUsers;

        getUsersInClubTask = new APIHandler.QueryAPI(this);
        getUsersInClubTask.execute();
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

    @Override
    public void onSuccess(JSONObject jsonData) {
        try {
            userThumbs = JSONParser.usersFromJSON(jsonData);

            InsideAdapter gridAdapter = new InsideAdapter(getContext(), userThumbs);
            gridAdapter.setClickListener(this);

            rvInsideGrid.setLayoutManager(new GridLayoutManager(getContext(),
                    Macros.CONST_RADAR_INSIDE_NUM_OF_COLUMNS));
            rvInsideGrid.setAdapter(gridAdapter);

            populateInsideClubInfo(userThumbs.size(), 20);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(APIErrorResponse jsonError) {

    }

    @Override
    public APIHandler.APIConnectionBundle getAPIConnectionBundle() {
        HashMap<String, String> queries = new HashMap<>();
        queries.put("id", "20");
        queries.put("club", "2");

        return new APIHandler.APIConnectionBundle("GET", new String[]{"users"}, queries, null);
    }

    @Override
    public void onStop() {
        super.onStop();
        getUsersInClubTask.cancel(true);  // to prevent memory leaks
    }
}



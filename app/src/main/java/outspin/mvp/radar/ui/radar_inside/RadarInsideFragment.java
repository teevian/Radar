package outspin.mvp.radar.ui.radar_inside;

import static java.lang.String.*;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import outspin.mvp.radar.data.DummieData;
import outspin.mvp.radar.data.Macros;
import outspin.mvp.radar.databinding.FragmentRadarInsideBinding;
import outspin.mvp.radar.models.UserThumbnail;
import outspin.mvp.radar.network.NetworkManager;


public class RadarInsideFragment extends Fragment implements InsideAdapter.ItemClickListener {
    private FragmentRadarInsideBinding fragmentRadarInsideBinding;
    private final ArrayList<UserThumbnail> userThumbnails = new ArrayList<>(DummieData.DUMMY_USERS_FULL);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentRadarInsideBinding = FragmentRadarInsideBinding.inflate(inflater, container, false);

        NetworkManager.JSONTask task = new NetworkManager.JSONTask();
        task.execute();

        return fragmentRadarInsideBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // populate club info
        populateInsideClubInfo();

        // TODO(2) get dummie data (users) from JSON http
        InsideAdapter gridAdapter = new InsideAdapter(view.getContext(), userThumbnails);
        gridAdapter.setClickListener(this);

        RecyclerView rvInsideGrid = fragmentRadarInsideBinding.rvInsideUsers;
        rvInsideGrid.setLayoutManager(new GridLayoutManager(this.getContext(), Macros.CONST_RADAR_INSIDE_NUM_OF_COLUMNS));
        rvInsideGrid.setAdapter(gridAdapter);
    }

    @Override
    public void onItemClick(View view, int position, Context parent) {
        ProfileBottomSheetDialog myBottomSheetDialogFragment = new ProfileBottomSheetDialog(getContext(), userThumbnails.get(position));
        myBottomSheetDialogFragment.show(getChildFragmentManager(), myBottomSheetDialogFragment.getTag());
    }

    protected void populateInsideClubInfo() {
        fragmentRadarInsideBinding.tvInsideHeaderPopulation.setText(valueOf(userThumbnails.size()));
        fragmentRadarInsideBinding.burnNumbered.tvNumberOfBurns.setText(valueOf(userThumbnails.size()));
    }
}



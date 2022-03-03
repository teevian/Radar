package outspin.mvp.radar.ui.radar_inside;

import static java.lang.String.*;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import outspin.mvp.radar.R;
import outspin.mvp.radar.data.DummieData;
import outspin.mvp.radar.databinding.FragmentRadarInsideBinding;
import outspin.mvp.radar.databinding.ProfileBottomSheetDialogBinding;
import outspin.mvp.radar.models.User;


public class RadarInsideFragment extends Fragment implements InsideGridAdapter.ItemClickListener {
    private FragmentRadarInsideBinding fragmentRadarInsideBinding;
    private final ArrayList<User> users = new ArrayList<>(DummieData.DUMMY_USERS_FULL);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentRadarInsideBinding = FragmentRadarInsideBinding.inflate(inflater, container, false);
        return fragmentRadarInsideBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // populate club info
        fragmentRadarInsideBinding.tvInsideHeaderPopulation.setText(valueOf(users.size()));
        fragmentRadarInsideBinding.burnNumbered.tvNumberOfBurns.setText(valueOf(users.size()));

        // TODO(2) get dummie data (users) from JSON http
        InsideGridAdapter gridAdapter = new InsideGridAdapter(view.getContext(), users);
        RecyclerView rvInsideGrid = fragmentRadarInsideBinding.rvInsideUsers;
        rvInsideGrid.setLayoutManager(new GridLayoutManager(this.getContext(), 5));
        gridAdapter.setClickListener(this);
        rvInsideGrid.setAdapter(gridAdapter);

/*
        radarInsideGridAdapter = new RadarInsideGridAdapter(view.getContext(), users);

        gridView = fragmentRadarInsideBinding.gv;
        gridView.setAdapter(radarInsideGridAdapter);

        // TODO(3) create listener when functionality becomes complex (+2 functions)
        gridView.setOnItemClickListener((adapterView, thisView, i, l) -> {
            BottomSheetDialogFragment myBottomSheetDialogFragment = new MyBottomSheetDialogFragment(getContext());
            myBottomSheetDialogFragment.show(getChildFragmentManager(), myBottomSheetDialogFragment.getTag());

            View contactView = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_notifications,
                    view.findViewById(R.id.ll_radar_inside));

            ImageView profileIcon = contactView.findViewById(R.id.profile_thumbnail_picture);
            String uriPath = users.get(i).getPhotoURL();
            Picasso.with(getContext())
                    .load(uriPath)
                    .resize(150, 150)
                    .centerCrop()
                    .into(profileIcon);
        });
*/
    }

    @Override
    public void onItemClick(View view, int position, Context parent) {
        Toast.makeText(this.getContext(), "posi: " + position, Toast.LENGTH_SHORT).show();

        BottomSheetDialogFragment myBottomSheetDialogFragment = new ProfileBottomSheetDialog(getContext());
        myBottomSheetDialogFragment.show(getChildFragmentManager(), myBottomSheetDialogFragment.getTag());

        ProfileBottomSheetDialogBinding profileDialogBinding =
                ProfileBottomSheetDialogBinding.inflate(LayoutInflater.from(parent));

        // TODO(3) image not changing
        String uriPath = users.get(position).getPhotoURL();
        //profileDialogBinding.profileThumbnail.profileThumbnailPicture.setImageResource(R.drawable.ic_outspin_burn);
        //profileDialogBinding.ivTap.setVisibility(View.INVISIBLE);
        ImageView iv = view.findViewById(R.id.profile_thumbnail_picture);
        iv.setVisibility(View.INVISIBLE);

        /*
        Picasso.with(getContext())
                .load(uriPath)
                .resize(150, 150)
                .centerCrop()
                .into(profileDialogBinding.profileThumbnail.profileThumbnailPicture);
*/
    }
}



package outspin.mvp.radar.ui.radar_inside;

import static java.lang.String.*;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import outspin.mvp.radar.R;
import outspin.mvp.radar.data.DummieData;
import outspin.mvp.radar.databinding.FragmentRadarInsideBinding;
import outspin.mvp.radar.models.User;


public class RadarInsideFragment extends Fragment {
    private FragmentRadarInsideBinding fragmentRadarInsideBinding;
    private GridView gridView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        fragmentRadarInsideBinding =
                FragmentRadarInsideBinding.inflate(inflater, container, false);
        View root = fragmentRadarInsideBinding.getRoot();

        ArrayList<User> users =  new ArrayList<>(DummieData.DUMMY_USERS_FULL);
        BaseAdapter radarInsideGridAdapter = new RadarInsideGridAdapter(root.getContext(), users);

        fragmentRadarInsideBinding.tvInsideHeaderPopulation.setText(valueOf(users.size()));

        gridView = fragmentRadarInsideBinding.gv;
        gridView.setAdapter(radarInsideGridAdapter);

        gridView.setOnItemClickListener((adapterView, view, i, l) -> {
            BottomSheetDialogFragment myBottomSheetDialogFragment =
                    new MyBottomSheetDialogFragment(getContext());
            myBottomSheetDialogFragment.show(getChildFragmentManager(),
                    myBottomSheetDialogFragment.getTag());

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
        return root;
    }

    public static class RadarInsideGridAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private ArrayList<User> users;

        RadarInsideGridAdapter(Context context, ArrayList<User> users){
                this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                this.users = users;
        }

        @Override
        public int getCount() {
            return users.size();
        }

        @Override
        public Object getItem(int i) {
            return users.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view == null){
                view = inflater.inflate(R.layout.user_profile_thumbnail_medium,viewGroup, false);

                ImageView profileIcon = view.findViewById(R.id.profile_thumbnail_picture);

                String uriPath = users.get(i).getPhotoURL();
                Picasso.with(viewGroup.getContext())
                        .load(uriPath)
                        .resize(150, 150)
                        .centerCrop()
                        .into(profileIcon);
            }

            return view;
        }
    }
}



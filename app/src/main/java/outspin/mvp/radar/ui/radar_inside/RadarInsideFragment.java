package outspin.mvp.radar.ui.radar_inside;

import static java.lang.String.*;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import outspin.mvp.radar.R;
import outspin.mvp.radar.data.DummieData;
import outspin.mvp.radar.databinding.FragmentRadarInsideBinding;
import outspin.mvp.radar.databinding.NotMyProfileBinding;
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
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                BottomSheetDialogFragment myBottomSheetDialogFragment =
                        new MyBottomSheetDialogFragment(getContext());
                myBottomSheetDialogFragment.show(getChildFragmentManager(),
                        myBottomSheetDialogFragment.getTag());


                View contactView = LayoutInflater.from(getContext()).inflate(R.layout.not_my_profile,
                        (LinearLayout) view.findViewById(R.id.ll_radar_inside));

                ImageView profileIcon = contactView.findViewById(R.id.profile_icon);
                String uriPath = users.get(i).getPhotoURL();
                Picasso.with(getContext())
                        .load(uriPath)
                        .resize(150, 150)
                        .centerCrop()
                        .into(profileIcon);
            }
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
                view = inflater.inflate(R.layout.user_profile_thumbnail,viewGroup, false);

                ImageView profileIcon = (ImageView) view.findViewById(R.id.profile_icon);

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



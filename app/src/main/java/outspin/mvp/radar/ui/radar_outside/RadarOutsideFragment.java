package outspin.mvp.radar.ui.radar_outside;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Arrays;
import java.util.List;

import outspin.mvp.radar.databinding.ClubLayoutBinding;
import outspin.mvp.radar.databinding.FragmentRadarInsideBinding;
import outspin.mvp.radar.databinding.FragmentRadarOutsideBinding;

public class RadarOutsideFragment extends Fragment {
    private FragmentRadarOutsideBinding binding;
    private static final List<double[]> positions = Arrays.asList(
            new double[]{0.1269, 0.0993},
            new double[]{0.5856, 0.1420},
            new double[]{0.2289,0.2791},
            new double[]{0.6495, 0.4253},
            new double[]{0.5235, 0.5920},
            new double[]{0.0728, 0.5571},
            new double[]{0.5111, 0.5924},
            new double[]{0.1899,0.7845}
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRadarOutsideBinding.inflate(inflater, container, false);
        RelativeLayout rl = binding.container;

        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        double pxHeight = displayMetrics.heightPixels;
        double pxWidth = displayMetrics.widthPixels;

        double dp1 = (double) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1,
                getContext().getResources().getDisplayMetrics());
        int width = (int) (115*dp1);
        int height = (int) (100*dp1);

        for (int i = 0; i < 8; ++i) {
            ClubLayoutBinding clubBinding = ClubLayoutBinding.inflate( (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE), container, false);
            View clubIcon = clubBinding.getRoot();

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);

            params.leftMargin = (int) (positions.get(i)[0]*pxWidth);
            params.topMargin = (int) (positions.get(i)[1]*pxHeight);
            rl.addView(clubIcon, params);
        }
        return binding.getRoot();
    }


}

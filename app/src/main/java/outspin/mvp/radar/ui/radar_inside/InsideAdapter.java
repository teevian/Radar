package outspin.mvp.radar.ui.radar_inside;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import outspin.mvp.radar.R;
import outspin.mvp.radar.models.UserThumbnail;

public class InsideAdapter extends RecyclerView.Adapter<InsideAdapter.ProfileThumbViewHolder> {
    private final ArrayList<UserThumbnail> data;
    private final LayoutInflater inflater;
    private ItemClickListener mClickListener;

    public InsideAdapter(Context context, ArrayList<UserThumbnail> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public ProfileThumbViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.inflater.inflate(R.layout.user_profile_thumbnail_medium, parent, false);
        return new ProfileThumbViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileThumbViewHolder holder, int position) {
        ImageView profileIcon = holder.itemView.findViewById(R.id.profile_thumbnail_picture);

        String uriPath = data.get(position).getPhotoURL();
        Picasso.with(holder.itemView.getContext())
                .load(uriPath)
                .resize(150, 150)
                .centerCrop()
                .into(profileIcon);
    }

    @Override
    public int getItemCount() { return data.size(); }


    /*
     * inner class
     */
    public class ProfileThumbViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public ProfileThumbViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition(), view.getContext());
        }
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position, Context parent);
    }
}

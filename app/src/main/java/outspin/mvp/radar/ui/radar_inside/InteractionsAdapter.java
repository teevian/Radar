package outspin.mvp.radar.ui.radar_inside;

import android.graphics.drawable.shapes.Shape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import outspin.mvp.radar.R;
import outspin.mvp.radar.models.Interaction;

public class InteractionsAdapter extends RecyclerView.Adapter<InteractionsAdapter.InteractionViewHolder>{

    String interactionTextDummie = "Ricardo waved @ you!";
    ArrayList<Interaction> interactions;

    InteractionsAdapter(ArrayList<Interaction> interactions) {
        this.interactions = interactions;
    }

    @NonNull
    @Override
    public InteractionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View item = inflater.inflate(R.layout.notification_item, parent, false);

        return new InteractionViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull InteractionViewHolder holder, int position) {
        final Interaction interaction = interactions.get(position);
        holder.textViewMessage.setText(interaction.getMessage());

        Picasso.with(holder.imageView.getContext())
                .load(interactions.get(position).getSenderPhotoURI())
                .resize(150, 150)
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return interactions.size();
    }

    protected static class InteractionViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewMessage = null;
        public ShapeableImageView imageView = null;

        public InteractionViewHolder(@NonNull View itemView) {
            super(itemView);

            this.textViewMessage = (TextView) itemView.findViewById(R.id.tv_interaction_message);
            this.imageView = (ShapeableImageView) itemView.findViewById(R.id.profile_thumbnail_picture);
        }
    } //profile_thumbnail_picture
}

package outspin.mvp.radar.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import outspin.mvp.radar.R;
import outspin.mvp.radar.models.Interaction;

public class InteractionsAdapter extends RecyclerView.Adapter<InteractionsAdapter.InteractionViewHolder>{
    ArrayList<Interaction> notifications;
    InteractionClickListener notificationClickListener;

    InteractionsAdapter(ArrayList<Interaction> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public InteractionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View item = inflater.inflate(R.layout.interaction_item, parent, false);

        item.setOnClickListener(view -> {

        });
        return new InteractionViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull InteractionViewHolder holder, int position) {
        final Interaction notification = notifications.get(position);
        holder.textViewMessage.setText(notification.getMessage());

        Picasso.with(holder.imageView.getContext())
                .load(notifications.get(position).getSenderPhotoURI())
                .resize(150, 150)
                .centerCrop()
                .into(holder.imageView);

        if(position % 7 == 0) {
            holder.btWaveBack.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    protected static class InteractionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textViewMessage;
        public ShapeableImageView imageView;
        public Button btWaveBack;

        public InteractionViewHolder(@NonNull View itemView) {
            super(itemView);

            this.textViewMessage = itemView.findViewById(R.id.tv_interaction_message);
            this.imageView = itemView.findViewById(R.id.profile_thumbnail_picture);
            this.btWaveBack = itemView.findViewById(R.id.bt_wave_back);
        }

        @Override
        public void onClick(View view) {
           // if(this.no != null) mClickListener.onItemClick(view, getAdapterPosition(), view.getContext());
        }
    } //profile_thumbnail_picture


    void setNotificationClickListener(InteractionClickListener clickListener) {
        this.notificationClickListener = clickListener;
    }

    public interface InteractionClickListener {
        void onItemClick(View view, int position, Context parent);
    }
}

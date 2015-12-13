package com.example.janikagahalot.authorstorydetails;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.janikagahalot.authorstorydetails.model.AuthorDetails;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by janikagahalot on 12/13/15.
 */
public class AuthorDetailsAdapter  extends RecyclerView.Adapter<AuthorDetailsAdapter.RowViewHolder>{
    private static AuthorDetails[] mDetails;
    private static Context mContext;
    private static onFollowButtonClickListener mListener;
    private static HashMap<String, HashSet<Integer>> commonItems = new HashMap<>();
    public AuthorDetailsAdapter(Context context, AuthorDetails[] details, onFollowButtonClickListener listener) {
        mDetails = details;
        mContext = context;
        mListener = listener;
    }
    @Override
    public RowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(mContext).inflate(R.layout.card_details, parent, false);
        return new RowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RowViewHolder holder, int position) {
        AuthorDetails authorDetails = mDetails[position];
        if(position == 0) {
            holder.mTitle.setText(authorDetails.getUsername());
            holder.mDescription.setText(authorDetails.getAbout());
            holder.mFollowers.setText(authorDetails.getFollowers().toString());
            holder.mFollowing.setText(authorDetails.getFollowing().toString());
            holder.mFollowerText.setText(mContext.getString(R.string.followers));
            holder.mFollowingText.setText(mContext.getString(R.string.following));
            if(authorDetails.getIsFollowing()) {
                holder.mFollowButton.setText(mContext.getString(R.string.un_follow));
                holder.mFollowButton.setBackground(mContext.getDrawable(R.drawable.followed_button_background));

            }
            else {
                holder.mFollowButton.setText(mContext.getString(R.string.follow));
                holder.mFollowButton.setBackground(mContext.getDrawable(R.drawable.follow_button_background));
            }
            Picasso.with(mContext)
                    .load(authorDetails.getImage())
                    .into(holder.mProfile);
        }
        else {
            holder.mTitle.setText(authorDetails.getTitle());
            holder.mDescription.setText(authorDetails.getDescription());
            holder.mFollowerText.setText(mContext.getString(R.string.likes));
            holder.mFollowingText.setText(mContext.getString(R.string.comments));
            holder.mFollowing.setText(authorDetails.getLikesCount().toString());
            holder.mFollowers.setText(authorDetails.getCommentCount().toString());
            if(authorDetails.getIsFollowing()) {
                holder.mFollowButton.setText(mContext.getString(R.string.un_follow));
                holder.mFollowButton.setBackground(mContext.getDrawable(R.drawable.followed_button_background));
            }
            else {
                holder.mFollowButton.setText(mContext.getString(R.string.follow));
                holder.mFollowButton.setBackground(mContext.getDrawable(R.drawable.follow_button_background));
            }
            if(commonItems.containsKey(authorDetails.getTitle())) {
                HashSet<Integer> set  = commonItems.get(authorDetails.getTitle());
                set.add(position);
            }
            else {
                HashSet<Integer> positions  = new HashSet<>();
                positions.add(position);
                commonItems.put(authorDetails.getTitle(), positions);
            }
            Picasso.with(mContext)
                    .load(authorDetails.getSi())
                    .into(holder.mProfile);
        }
    }

    @Override
    public int getItemCount() {
        return mDetails.length;
    }

    public static class RowViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitle, mDescription, mFollowerText, mFollowingText, mFollowers, mFollowing;
        private ImageView mProfile;
        private Button mFollowButton;

        public RowViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.user_name);
            mDescription = (TextView) itemView.findViewById(R.id.user_description);
            mFollowerText = (TextView) itemView.findViewById(R.id.user_followers_text);
            mFollowingText = (TextView) itemView.findViewById(R.id.user_following_text);
            mFollowers = (TextView) itemView.findViewById(R.id.user_followers);
            mFollowing = (TextView) itemView.findViewById(R.id.user_following);
            mDescription = (TextView) itemView.findViewById(R.id.user_description);
            mProfile = (ImageView) itemView.findViewById(R.id.profile_image);
            mFollowButton = (Button) itemView.findViewById(R.id.user_follow_button);
            mFollowButton.setOnClickListener(this);
            mDescription.setOnClickListener(this);

        }
        private boolean shouldEnableDescription(TextView mDescription) {
            Layout layout = mDescription.getLayout();
            if(layout != null && layout.getEllipsisCount(mDescription.getLineCount() - 1) > 0) {
                return true;
            }
            return false;
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.user_description) {
                if(shouldEnableDescription((TextView) v)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage(((TextView) v).getText());
                    if(getLayoutPosition() > 0) {
                        builder.setTitle(mDetails[getLayoutPosition()].getTitle());
                    }
                    else {
                        builder.setTitle(mDetails[0].getUsername());
                    }
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                return;
            }
            Button button  = (Button) v;

            if(button.getText().equals(mContext.getString(R.string.follow))) {
                if(getLayoutPosition() == 0) {
                    mDetails[0].setIsFollowing(true);
                    button.setText(mContext.getString(R.string.un_follow));
                    button.setBackground(mContext.getDrawable(R.drawable.followed_button_background));
                }
                else {
                    mListener.onFollowButtonClicked(true, commonItems.get(mDetails[getLayoutPosition()].getTitle()));
                }
            }
            else  {
                if (getLayoutPosition() == 0) {
                    mDetails[0].setIsFollowing(false);
                    button.setText(mContext.getString(R.string.follow));
                    button.setBackground(mContext.getDrawable(R.drawable.follow_button_background));

                }
                else{
                    mListener.onFollowButtonClicked(false, commonItems.get(mDetails[getLayoutPosition()].getTitle()));
                }
            }
        }

    }

    public interface onFollowButtonClickListener {
        void onFollowButtonClicked(boolean following, HashSet<Integer> set);
    }
}

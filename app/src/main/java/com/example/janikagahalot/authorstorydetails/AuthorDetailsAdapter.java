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
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(RowViewHolder holder, int position) {
        AuthorDetails authorDetails = mDetails[position];
        Log.e(this.toString(),"position being " + position);
        Log.e(this.toString(),"following being " + authorDetails.getIsFollowing());

        if(authorDetails.getIsFollowing() || mDetails[0].getIsFollowing()) {
            holder.mFollowButton.setText(mContext.getString(R.string.un_follow));
            holder.mFollowButton.setBackground(mContext.getDrawable(R.drawable.followed_button_background));
        }
        else {
            holder.mFollowButton.setText(mContext.getString(R.string.follow));
            holder.mFollowButton.setBackground(mContext.getDrawable(R.drawable.follow_button_background));
        }

        if(position == 0) {
            holder.mTitle.setText(authorDetails.getUsername());
            holder.mDescription.setText(authorDetails.getAbout());
            holder.mFollowers.setText(authorDetails.getFollowers().toString());
            holder.mFollowing.setText(authorDetails.getFollowing().toString());
            holder.mFollowerText.setText(mContext.getString(R.string.followers));
            holder.mFollowingText.setText(mContext.getString(R.string.following));
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
                mDetails[getLayoutPosition()].setIsFollowing(true);
                button.setText(mContext.getString(R.string.un_follow));
                button.setBackground(mContext.getDrawable(R.drawable.followed_button_background));

                if(getLayoutPosition() == 0) {
                    mListener.onFollowAll(true);
                }
                else {
                    mListener.onFollowButton(true, mDetails[getLayoutPosition()].getDb());
                }
            }
            else  {
                mDetails[getLayoutPosition()].setIsFollowing(false);
                button.setText(mContext.getString(R.string.follow));
                button.setBackground(mContext.getDrawable(R.drawable.follow_button_background));
                if (getLayoutPosition() == 0) {
                    mListener.onFollowAll(false);
                }
                else{
                    mListener.onFollowButton(false, mDetails[getLayoutPosition()].getDb());
                }
            }
        }

    }

    public interface onFollowButtonClickListener {
        void onFollowButton(boolean following, String key);
        void onFollowAll(boolean following);
    }
}

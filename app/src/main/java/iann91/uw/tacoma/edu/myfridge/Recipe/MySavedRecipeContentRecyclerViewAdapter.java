package iann91.uw.tacoma.edu.myfridge.Recipe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.InputStream;
import java.util.List;

import iann91.uw.tacoma.edu.myfridge.R;
import iann91.uw.tacoma.edu.myfridge.Recipe.recipeItem.RecipeContent;

/**
 * {@link RecyclerView.Adapter} that can display a {@link iann91.uw.tacoma.edu.myfridge.Recipe.recipeItem.RecipeContent} and makes a call to the
 * specified {@link iann91.uw.tacoma.edu.myfridge.Recipe.MyRecipesFragment.OnSavedRecipeListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MySavedRecipeContentRecyclerViewAdapter extends RecyclerView.Adapter<MySavedRecipeContentRecyclerViewAdapter.ViewHolder> {

    private final List<RecipeContent> mValues;
    private final MyRecipesFragment.OnSavedRecipeListFragmentInteractionListener mListener;

    public MySavedRecipeContentRecyclerViewAdapter(List<RecipeContent> items, MyRecipesFragment.OnSavedRecipeListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_myrecipes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).getmTitle());

        MyRecipeRecyclerViewAdapter.DownloadImageTask d = new MyRecipeRecyclerViewAdapter.DownloadImageTask(holder.mImageView);
        d.execute(mValues.get(position).getmImageUrl());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onSavedListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if(mValues!=null){
            size = mValues.size();
        }
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public final ImageView mImageView;
        public RecipeContent mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.myrecipe_content);
            mImageView = (ImageView) view.findViewById(R.id.myrecipe_img);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    /**
     * AsyncTask for downloading the images for the recipes, which will be displayed in the list.
     */
    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }



}

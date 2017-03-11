package iann91.uw.tacoma.edu.myfridge.Recipe;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import iann91.uw.tacoma.edu.myfridge.R;
import iann91.uw.tacoma.edu.myfridge.Recipe.recipeItem.RecipeContent;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnRecipeFragmentInteractionListener}
 * interface.
 */
public class RecipeFragment extends Fragment {

    /** Constants for building the API Url. */
    private static final String MAIN_API_URL = "https://api.edamam.com/search?",
            PARAM_QUERY = "q",
            APP_ID = "app_id",
            APP_ID_VALUE = "059bdc05",
            APP_KEY = "app_key",
            APP_KEY_VALUE
            = "478709483e30d7e1e79e7cc102eda2ac",
            FROM = "from",
            FROM_VALUE = "0",
            START = "to",
            TO_VALUE = "15";

    /** String for the current resulting API. */
    private static String mResultAPIUrl;

    /** String message for counting the column for this Fragment (List). */
    private static final String ARG_COLUMN_COUNT = "column-count";

    /** Field for counting the columns for this Fragment(List). */
    private int mColumnCount = 1;

    /** Field for the RecyclerViewer for this Fragment (List). */
    private RecyclerView mRecyclerView;

    /** Listener field for interacting with this Fragment (List). */
    private OnRecipeFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeFragment() {
    }


    /**
     * Method for constructing a new instance of this Fragment (List).
     *
     * @param contentSearch - is the message passed to this fragment, so it will build
     *                      the API URL based on the query (message).
     * @return - a new Fragment (List).
     */
    public static RecipeFragment newInstance(String contentSearch) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        //args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);

        mResultAPIUrl = buildUrl(contentSearch).toString();

        return fragment;
    }

    /**
     * onCrate() for this Fragment. Initializes the mColumnCount.
     * @param savedInstanceState - the saved arguments for this activity.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    /**
     * Inflates the view and the view elements for this Fragment.
     * If this Fragment was called in favorite mode, it bind the mRecyclerView to the static
     * favoriteList for the current user. If it was not in favorite mode, it uses the API Url
     * based on the query passed.
     *
     * @param inflater - the inflater for this Fragment (List).
     * @param container - the container for this Fragment (List).
     * @param savedInstanceState - the savedInstanceState for this Fragment (List).
     *
     * @return - a view object of this Fragment(List).
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

                DownloadRecipesTask task = new DownloadRecipesTask();
                task.execute(new String[] {mResultAPIUrl});


        }
        return view;
    }

    /**
     * onAttach() method for this Fragment (List). It sets the Listener based on the context.
     *
     * @param context - the current context.
     * @throws RuntimeException - if the calling activity didn't implement the listener.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeFragmentInteractionListener) {
            mListener = (OnRecipeFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    /**
     * onDetach() method for this Fragment (List).
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnRecipeFragmentInteractionListener {
        void onRecipeFragmentInteraction(RecipeContent item);
    }

    /**
     *
     * Helper method to construct the API search URL based on certain keyword.
     *
     * @param searchQuery The query keyword for the API search
     * @return returns the API search URL
     */
    private static URL buildUrl(String searchQuery) {
        Uri builtUri = Uri.parse(MAIN_API_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, searchQuery)
                .appendQueryParameter(APP_ID, APP_ID_VALUE)
                .appendQueryParameter(APP_KEY, APP_KEY_VALUE)
                .appendQueryParameter(FROM, FROM_VALUE)
                .appendQueryParameter(START, TO_VALUE)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * AsyncTask for downloading the recipes from the built API URL. Sets the RecyclerView
     * based on the generated list of recipes.
     */
    private class DownloadRecipesTask extends AsyncTask<String, String, String> {

        /**
         * doInBackground() method for this task. Downloads the recipes from the external API.
         * @param urls - GET Url for the external API.
         * @return - the resulting JSON data.
         */
        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();
                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        result.append(s);
                    }
                } catch (Exception e) {
                    result = new StringBuilder("Unable to download the list of recipes, Reason: " + e
                            .getMessage());
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            }

            publishProgress(result.toString());
            return result.toString();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            List<RecipeContent> recipeList = new ArrayList<RecipeContent>();
            String value = RecipeContent.parseRecipesJSON(values[0], recipeList);

            if(value != null) {
                Toast.makeText(getActivity().getApplicationContext(), value, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            if (!recipeList.isEmpty()) {
                mRecyclerView.setAdapter(new MyRecipeRecyclerViewAdapter(recipeList, mListener));
            }
        }

        /**
         * onPostExecute() method for this task. Fills a list of recipe objects based on the
         * retrieved JSON data.
         * @param result - the retrieved JSON data.
         */
        @Override
        protected void onPostExecute(String result) {
            if (result.startsWith("Unable to")) {
                Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }


        }

    }

}

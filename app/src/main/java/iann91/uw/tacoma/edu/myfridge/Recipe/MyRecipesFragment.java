package iann91.uw.tacoma.edu.myfridge.Recipe;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import iann91.uw.tacoma.edu.myfridge.R;
import iann91.uw.tacoma.edu.myfridge.Recipe.recipeItem.RecipeContent;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnSavedRecipeListFragmentInteractionListener}
 * interface.
 */
public class MyRecipesFragment extends Fragment  {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnSavedRecipeListFragmentInteractionListener mListener;

    /** Field for the RecyclerViewer for this Fragment (List). */
    private RecyclerView mRecyclerView;
    protected static ArrayList<RecipeContent> mSavedRecipeList = new ArrayList<>();




    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MyRecipesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MyRecipesFragment newInstance(RecipeContent theRecipe) {
        MyRecipesFragment fragment = new MyRecipesFragment();
        Bundle args = new Bundle();
       // args.putInt(ARG_COLUMN_COUNT, theList);
        fragment.setArguments(args);
        if(mSavedRecipeList!=null ) {

            mSavedRecipeList.add(theRecipe);
            for (int i = 0; i < mSavedRecipeList.size(); i++) {
                Log.i("info oF ARRAY", "" + mSavedRecipeList.get(i).getmTitle());
            }
        }

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myrecipes_list, container, false);




        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            if(!mSavedRecipeList.isEmpty())
            mRecyclerView.setAdapter(new MySavedRecipeContentRecyclerViewAdapter(mSavedRecipeList, mListener));


        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSavedRecipeListFragmentInteractionListener) {
            mListener = (OnSavedRecipeListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

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
    public interface OnSavedRecipeListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSavedListFragmentInteraction(RecipeContent item);
    }


}

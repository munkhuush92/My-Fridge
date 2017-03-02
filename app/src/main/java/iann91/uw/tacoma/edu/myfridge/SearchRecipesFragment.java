package iann91.uw.tacoma.edu.myfridge;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import iann91.uw.tacoma.edu.myfridge.Recipe.RecipeFragment;
import iann91.uw.tacoma.edu.myfridge.Recipe.recipeItem.RecipeContent;


/**
 * Fragment for searching recipes online.
 * @author iann91 Munkh92
 * @version 1.0
 */
public class SearchRecipesFragment extends Fragment {

    private RecipeFragment.OnListFragmentInteractionListener mListener;

    public SearchRecipesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FloatingActionButton floatingActionButton = (FloatingActionButton)
                getActivity().findViewById(R.id.fab);
        floatingActionButton.hide();



        return inflater.inflate(R.layout.fragment_search_recipes, container, false);
    }


}

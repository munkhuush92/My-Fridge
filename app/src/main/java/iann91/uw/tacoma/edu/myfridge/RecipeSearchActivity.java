//package iann91.uw.tacoma.edu.myfridge;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//
//import iann91.uw.tacoma.edu.myfridge.Recipe.RecipeDetailFragment;
//import iann91.uw.tacoma.edu.myfridge.Recipe.RecipeFragment;
//import iann91.uw.tacoma.edu.myfridge.Recipe.recipeItem.RecipeContent;
//
///**
// * Created by admin on 2/26/2017.
// */
//
//public class RecipeSearchActivity extends AppCompatActivity implements
//        RecipeFragment.OnRecipeFragmentInteractionListener {
//
//
///**
// * onCreate method for the current activity. Initializes and commits the RecipeFragment
// * together with the message that has been passed to it.
// *
// * @param savedInstanceState - the saved arguments for this activity.
// */
//@Override
//protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_recipe_list);
//        Intent intent = getIntent();
//
//
//        Log.i("RecipesListActivity", "onCreate()");
//
//        if ((savedInstanceState == null)
//        && (findViewById(R.id.activity_recipes_list_fragment_container) != null)) {
//
//
//        Log.i("RecipesListActivity", "recipeFragment about to be created");
//        RecipeFragment recipeFragment = RecipeFragment.newInstance("LOL", false);
//
//        getSupportFragmentManager().beginTransaction()
//        .replace(R.id.activity_recipes_list_fragment_container, recipeFragment)
//        .commit();
//
//        Log.i("RecipesListActivity", "fragment committed");
//        }
//
//        }
//
//
///**
// * Listener method for interacting with a item on the fragment list. When an item is clicked
// * it opens the RecipeDetailFragment.
// * @param item - The recipe that has been clicked.
// */
//@Override
//public void onRecipeFragmentInteraction(RecipeContent item) {
//        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
//        Bundle args = new Bundle();
//        args.putSerializable(RecipeDetailFragment.RECIPE_ITEM_SELECTED, item);
//        recipeDetailFragment.setArguments(args);
//
//        getSupportFragmentManager()
//        .beginTransaction()
//        .replace(R.id.activity_recipes_list_fragment_container, recipeDetailFragment)
//        .addToBackStack(null)
//        .commit();
//        }
//
//
//}

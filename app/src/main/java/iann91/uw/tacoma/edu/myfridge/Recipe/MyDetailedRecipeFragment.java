package iann91.uw.tacoma.edu.myfridge.Recipe;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import iann91.uw.tacoma.edu.myfridge.GroceryListFragment;
import iann91.uw.tacoma.edu.myfridge.R;
import iann91.uw.tacoma.edu.myfridge.Recipe.recipeItem.RecipeContent;

import static iann91.uw.tacoma.edu.myfridge.Recipe.RecipeDetailFragment.RECIPE_ITEM_SELECTED;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyDetailedRecipeFragment extends Fragment {


    private TextView mRecipeTitle;
    private ImageView mRecipeImage;
    private TextView mRecipeIngredients;
    private RecipeContent mRecipe;

    public MyDetailedRecipeFragment(RecipeContent theRecipe) {
        mRecipe = theRecipe;
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_mysaved_detailed_recipe, container, false);
        mRecipeTitle = (TextView) view.findViewById(R.id.mysaved_recipe_title_tv);
        mRecipeImage = (ImageView) view.findViewById(R.id.mysaved_recipe_img);
        mRecipeIngredients = (TextView) view.findViewById(R.id.mysaved_recipe_ingredients);


        //setting up email button on MyDetailedRecipeFragment
        Button emailButton = (Button) view.findViewById(R.id.emailSentButton);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Send email", "");

                String[] TO = {"someone@gmail.com"};
                String[] CC = {"xyz@gmail.com"};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");


                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    getActivity().finish();
                    Log.i("Finished sending ema..", "");
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(),
                            "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //setting up delete button on MyDetailedRecipeFragment
        Button deleteRecipeButton = (Button) view.findViewById(R.id.deleteRecipebutton);
        deleteRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyRecipesFragment.mSavedRecipeList.remove(mRecipe);
                FragmentManager fm=  getActivity().getSupportFragmentManager();
                fm.beginTransaction().remove(MyDetailedRecipeFragment.this).commit();
                fm.popBackStack();
            }
        });

        //setting up generate list button on MyDetailedRecipeFragment
        Button generateButton = (Button) view.findViewById(R.id.generateGrocListbutton);
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(String cur: mRecipe.getmIngredients()){
                    //GroceryListFragment.mGroceryList.add(cur);
                }

                FragmentManager fm=  getActivity().getSupportFragmentManager();
                fm.beginTransaction().remove(MyDetailedRecipeFragment.this).commit();
                fm.popBackStack();
            }
        });


        return view;
    }



    /**
     * onStart() method for this Fragment. Sets article based on argument passed in.
     */
    @Override
    public void onStart() {
        super.onStart();
        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
      //  Bundle args = getArguments();

        if (mRecipe != null) {
            updateView(mRecipe);
        }

    }

    /**
     * Updates the view for this Fragment based on which recipe has been selected.
     *
     * @param recipe - The recipe that has been selected.
     */
    public void updateView(final RecipeContent recipe) {
        if (recipe != null) {
           // mRecipe= recipe;
            mRecipeTitle.setText(recipe.getmTitle());

            // Downloads the image for the recipe.
            MyRecipeRecyclerViewAdapter.DownloadImageTask task = new MyRecipeRecyclerViewAdapter
                    .DownloadImageTask(mRecipeImage);
            task.execute(recipe.getmImageUrl());


            // Parsing the ingredients from array to a single string with new line character.
            String joinIngredients = "";
            for(String s : recipe.getmIngredients()) {
                joinIngredients += s + "\n";
            }

            mRecipeIngredients.setText(joinIngredients);


        }
    }


}
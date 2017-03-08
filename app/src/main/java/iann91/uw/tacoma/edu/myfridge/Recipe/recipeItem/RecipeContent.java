package iann91.uw.tacoma.edu.myfridge.Recipe.recipeItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class RecipeContent implements Serializable {

    /** The title of the recipe. */
    private String mTitle;

    /** The image url of the recipe. */
    private String mImageUrl;

    /** The ingredients array of the recipe. */
    private String[] mIngredients;

    /** The instructions url of the recipe. */
    private String mInstructionUrl;

    /** Strings representing JSON data attributes for the external API. */
    private static final String HITS = "hits";
    private static final String RECIPE = "recipe";
    private static final String LABEL = "label";
    private static final String IMAGE = "image";
    private static final String INGREDIENT_LINES = "ingredientLines";
    private static final String URL = "url";

    /** Strings representing JSON data attributes for the favorite list. */
    private static final String TITLE = "title";
    private static final String IMAGE_URL = "image_url";
    private static final String INGREDIENTS = "ingredients";
    private static final String INSTRUCTIONS_URL = "instructions_url";

    /**
     * Constructor for the class. Initializes the class' fields.
     * @param mTitle - the title of the recipe.
     * @param mImageUrl - the image url of the recipe.
     * @param mIngredients - the ingredients of the recipe.
     */
    public RecipeContent(String mTitle, String mImageUrl, String[] mIngredients, String theUrl) {
        this.mTitle = mTitle;
        this.mImageUrl = mImageUrl;
        this.mIngredients = mIngredients;
        this.mInstructionUrl = theUrl;
    }

    /**
     * Gets the recipe's title.
     * @return - the title.
     */
    public String getmTitle() {
        return mTitle;
    }

    /**
     * Gets the recipe's iamge url.
     * @return - the image url.
     */
    public String getmImageUrl() {
        return mImageUrl;
    }

    /**
     * Gets the recipe's ingredients.
     * @return - string array of ingredients.
     */
    public String[] getmIngredients() {
        return mIngredients;
    }

    /**
     * Gets the instructions url.
     * @return - the instructions url.
     */
    public String getmInstructionUrl() {
        return mInstructionUrl;
    }

    /**
     * Sets the recipe's title.
     * @param mTitle - the title of the recipe.
     */
    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    /**
     * Sets the recipe's image url.
     * @param mImageUrl - the image url of the recipe.
     */
    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    /**
     * Sets the recipe's instructions url.
     * @param mInstructionsUrl - the instructions url of the recipe.
     */
    public void setmInstructionsUrl(String mInstructionsUrl) {
        this.mInstructionUrl = mInstructionsUrl;
    }

    /**
     * Sets the recipe's ingredients.
     * @param mIngredients - the ingredients of the recipe.
     */
    public void setmIngredients(String[] mIngredients) {
        this.mIngredients = mIngredients;
    }

    /**
     * Method for parsing JSON data from the external API. Parses the data and fills a list
     * of recipes based on the JSON data.
     * @param recipesJSON - the JSON data string retrieved from the server.
     * @param recipeList - the recipe list that needs to be filled with data.
     * @return - a String. If null than successful, else something went wrong.
     */
    public static String parseRecipesJSON(String recipesJSON, List<RecipeContent> recipeList) {
        String reason = null;
        if (recipesJSON != null) {
            try {
                JSONObject obj = new JSONObject(recipesJSON);
                JSONArray arr = obj.getJSONArray(RecipeContent.HITS);

                // Goes through all JSON data objects retrieved from external API,
                // and processes them accordingly.
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject rObj = arr.getJSONObject(i);
                    JSONObject recipeJson = rObj.getJSONObject(RecipeContent.RECIPE);

                    String label = recipeJson.getString(RecipeContent.LABEL);

                    String image = recipeJson.getString(RecipeContent.IMAGE);

                    JSONArray JSONingredients = recipeJson.getJSONArray(RecipeContent.INGREDIENT_LINES);
                    //Fills array based on JSON array elements.
                    String[] ingred = new String[JSONingredients.length()];
                    for (int j = 0; j < ingred.length; j++) {
                        ingred[j] = JSONingredients.getString(j);
                    }

                    String url = recipeJson.getString(RecipeContent.URL);

                    // Construct Recipe object based on the retrieved data.
                    RecipeContent recipe = new RecipeContent(label, image, ingred, url);

                    // Adds the Recipe object to the list.
                    recipeList.add(recipe);
                }


            } catch (JSONException e) {
                reason = "Unable to parse data, Reason: " + e.getMessage();
            }
        }
        return reason;
    }


    /**
     * Compares two Recipe objects for equality.
     * @param theOther represents the other Recipe object.
     * @return the result of the comparison.
     */
    @Override
    public boolean equals(Object theOther) {
        boolean result = false;
        if(theOther instanceof RecipeContent) {
            RecipeContent other = (RecipeContent) theOther;
            if (this.mTitle.equals(other.mTitle)) {
                result = true;
            }
        }
        return result;
    }

}

package iann91.uw.tacoma.edu.myfridge.item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Holds the information for specific grocery item.
 * @author iann91 munkh92
 * @version 1.0
 */
public class Item implements Serializable{
    public static final String ITEM_NAME = "nameFoodItem"
                , ITEM_QUANTITY = "sizeFoodItem", PERSON_ID = "PersonID", ITEM_TYPE = "foodType";

    private String mItemName, mItemQuantity, mPersonID, mItemType;

    /**
     * Initializes item with given parameters.
     * @param mItemName name of item.
     * @param mItemQuantity item quantity.
     * @param mPersonID person id for item.
     * @param mItemType type of item.
     */
    public Item(String mItemName, String mItemQuantity, String mPersonID, String mItemType) {
        this.mItemName = mItemName;
        this.mItemQuantity = mItemQuantity;
        this.mPersonID = mPersonID;
        this.mItemType = mItemType;
    }

    public String getmItemType() {
        return mItemType;
    }

    public void setmItemType(String mItemType) {
        this.mItemType = mItemType;
    }

    public static String getmPersonId() {
        return PERSON_ID;
    }

    public String getmItemName() {
        return mItemName;
    }

    public void setmItemName(String mItemName) {
        this.mItemName = mItemName;
    }

    public String getmItemQuantity() {
        return mItemQuantity;
    }

    public void setmItemQuantity(String mItemQuantity) {
        this.mItemQuantity = mItemQuantity;
    }

    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns item list if success.
     * @param itemJSON
     * @return reason or null if successful.
     */
    public static String parseItemJSON(String itemJSON, List<Item> itemList) {
        String reason = null;
        if (itemJSON != null) {
            try {
                JSONArray arr = new JSONArray(itemJSON);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    Item item = new Item(obj.getString(Item.ITEM_NAME), obj.getString(Item.ITEM_QUANTITY),
                            obj.getString(Item.PERSON_ID), obj.getString(Item.ITEM_TYPE));
                    itemList.add(item);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }

}

package iann91.uw.tacoma.edu.myfridge.item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by imnic on 2/15/2017.
 */

public class Item implements Serializable{
    public static final String ID = "id", ITEM_NAME = "itemName"
            , ITEM_QUANTITY = "itemQuantity", ITEM_TYPE = "itemType";

    private String mItemId, mItemName, mItemQuantity, mItemType;

    public Item(String mItemId, String mItemName, String mItemQuantity, String mItemType) {
        this.mItemId = mItemId;
        this.mItemName = mItemName;
        this.mItemQuantity = mItemQuantity;
        this.mItemType = mItemType;
    }

    public String getmItemType() {
        return mItemType;
    }

    public void setmItemType(String mItemType) {
        this.mItemType = mItemType;
    }

    public String getmItemId() {
        return mItemId;
    }

    public void setmItemId(String mItemId) {
        this.mItemId = mItemId;
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
     * Returns course list if success.
     * @param courseJSON
     * @return reason or null if successful.
     */
    public static String parseItemJSON(String courseJSON, List<Item> itemList) {
        String reason = null;
        if (courseJSON != null) {
            try {
                JSONArray arr = new JSONArray(courseJSON);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    Item item = new Item(obj.getString(Item.ID), obj.getString(Item.ITEM_NAME)
                            , obj.getString(Item.ITEM_QUANTITY), obj.getString(Item.ITEM_TYPE));
                    itemList.add(item);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }

}

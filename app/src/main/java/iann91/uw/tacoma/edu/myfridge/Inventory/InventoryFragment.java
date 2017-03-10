package iann91.uw.tacoma.edu.myfridge.Inventory;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import iann91.uw.tacoma.edu.myfridge.R;
import iann91.uw.tacoma.edu.myfridge.data.ItemDB;
import iann91.uw.tacoma.edu.myfridge.item.Item;


/**
 * Fragment to display the users inventory.
 * @author iann91 Munkh92
 */
public class InventoryFragment extends Fragment{
    private InventoryFragment.ItemAddLocallyListener mLocalListener;
    private static final String ITEM_URL
            = "http://cssgate.insttech.washington.edu/~iann91/downloaditems.php?cmd=items";
    private String mCategory;
    private Map <String, ArrayList<Item>> myItems;
    private SwapInventoryFragListener mListener;
    private int mID;

    private ItemDB mItemDB;
    private ArrayList<Item> mItemList;


    public InventoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Initializes necessary fields and view to display fragment.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_inventory, container,
                false);
        mItemList = new ArrayList<>();
        myItems = new HashMap<>();

        mID = getArguments().getInt("id");


        final Button dairyButton = (Button) view.findViewById(R.id.dairy_button);
        final Button fruitButton = (Button) view.findViewById(R.id.fruit_button);
        final Button vegetablesButton = (Button) view.findViewById(R.id.vegetables_button);
        final Button meatButton = (Button) view.findViewById(R.id.meat_button);
        final Button grainsButton = (Button) view.findViewById(R.id.grains_button);

        if (savedInstanceState == null || getActivity().getSupportFragmentManager().findFragmentById(R.id.list) == null) {
            dairyButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try{
                        mItemList = (ArrayList<Item>)mItemDB.getItems("Dairy");
                    }catch(NullPointerException e) {

                    }
                    mListener.swapToItemFragment(new ItemFragment(), "Dairy", mItemList);
                }
            });

            fruitButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try{
                        mItemList = (ArrayList<Item>)mItemDB.getItems("Fruit");
                    }catch(NullPointerException e){

                    }
                    mListener.swapToItemFragment(new ItemFragment(), "Fruit", mItemList);
                }
            });

            vegetablesButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        mItemList = (ArrayList<Item>)mItemDB.getItems("Vegetables");
                    } catch(NullPointerException e) {

                    }

                    mListener.swapToItemFragment(new ItemFragment(), "Vegetables", mItemList);
                }
            });

            grainsButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        mItemList = (ArrayList<Item>)mItemDB.getItems("Grains");
                    } catch(NullPointerException e) {

                    }

                    mListener.swapToItemFragment(new ItemFragment(), "Grains", mItemList);
                }
            });

            meatButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        mItemList = (ArrayList<Item>)mItemDB.getItems("Meat");
                    } catch(NullPointerException e) {

                    }
                    mListener.swapToItemFragment(new ItemFragment(), "Meat", mItemList);
                }
            });
        }

        FloatingActionButton floatingActionButton = (FloatingActionButton)
                getActivity().findViewById(R.id.fab);
        floatingActionButton.hide();


        DownloadItemsTask task = new DownloadItemsTask();
        task.execute(new String[]{ITEM_URL});



        return view;
    }

    /**
     * Sets listener to ItemAddListener when attached.
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddItemFragment.ItemAddDatabaseListener) {
            mListener = (InventoryFragment.SwapInventoryFragListener) context;
            mLocalListener = (InventoryFragment.ItemAddLocallyListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ItemAddListener");
        }
    }

    private class DownloadItemsTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to download the list of courses, Reason: "
                            + e.getMessage();
                }
                finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            if (result.startsWith("Unable to")) {
                Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            List<Item> itemList = new ArrayList<Item>();
            result = Item.parseItemJSON(result, itemList);
            // Something wrong with the JSON returned.
            if (result != null) {
                Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            // Everything is good, show the list of courses.
            if (!itemList.isEmpty()) {
                myItems = mLocalListener.addDownloadedItems((ArrayList<Item>)itemList);
                Log.i("My itess", myItems.toString());

                if(mItemDB == null) {
                    mItemDB = new ItemDB(getActivity().getApplicationContext());
                }
                mItemDB.deleteCourses();

                // Also, add to the local database
                for (int i=0; i<itemList.size(); i++) {
                    Item item = itemList.get(i);
                    mItemDB.insertItem(item.getmItemName(),
                            item.getmItemQuantity(),
                            mID,
                            item.getmItemType());
                }


//                mRecyclerView.setAdapter(new MyItemRecyclerViewAdapter(mItems, mListener));


            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mLocalListener = null;
    }


    /**
     * Interface for adding an item to the database.
     */
    public interface SwapInventoryFragListener {
        void swapToItemFragment(Fragment fragment, String category, ArrayList<Item> theItems);
    }

    public interface ItemAddLocallyListener {
        Map<String,ArrayList<Item>> addDownloadedItems(ArrayList<Item> theItems);
    }

}

package iann91.uw.tacoma.edu.myfridge;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


/**
 * Displays the list of grocery items the user needs to buy.
 */
public class GroceryListFragment extends Fragment {

    private ArrayList<String> mGroceryList;

    private ListView mGrocListView;
    private ArrayList<String > mSelectedItems;
    ArrayAdapter<String> mainAdapter;
    public GroceryListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mGroceryList = new ArrayList<String>();
        mSelectedItems = new ArrayList<String>();
        final View view = inflater.inflate(R.layout.fragment_grocery_list, container, false);
        TextView grocTextView = (TextView) view.findViewById(R.id.my_grocery_list_textview);


        //setting list view
        mGrocListView = (ListView) view.findViewById(R.id.grocery_listView);
        mGrocListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mGrocListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = ((TextView)view).getText().toString();
                if(mSelectedItems.contains(selectedItem)){
                    mSelectedItems.remove(selectedItem);
                }else {
                    mSelectedItems.add(selectedItem);
                }
            }
        });

        if(getArguments().getBoolean("List filled")) {
            String[] tempList = getArguments().getString("Grocery List").split("\n");
            for(String cur:tempList) mGroceryList.add(cur);
            mainAdapter = new ArrayAdapter<String>(getActivity(), R.layout.rowgrocerylist, R.id.row_item_grocery, mGroceryList );
            mGrocListView.setAdapter(mainAdapter);


        }else{
            grocTextView.setText("My Grocery List is empty");
        }

        Button deleteGroceryListItemButton = (Button) view.findViewById(R.id.deleteCheckedItems);
        deleteGroceryListItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb = new StringBuilder();
                for(String cur: mSelectedItems){
                    sb.append(cur+"\n");
                    mGroceryList.remove(cur);

                }
                Toast.makeText(getActivity(), sb.toString(), Toast.LENGTH_SHORT).show();
                mSelectedItems.clear();
                mainAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

}

package iann91.uw.tacoma.edu.myfridge;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * Displays the list of grocery items the user needs to buy.
 */
public class GroceryListFragment extends Fragment {


    //protected static ArrayList<String> mGroceryList = new ArrayList<>();
    private ArrayList<String> mGroceryList;
    public GroceryListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mGroceryList = new ArrayList<String>();
        View view = inflater.inflate(R.layout.fragment_grocery_list, container, false);
        TextView grocTextView = (TextView) view.findViewById(R.id.my_grocery_list_textview);
        if(getArguments().getBoolean("List filled")) {
            String[] tempList = getArguments().getString("Grocery List").split("\n");
            for(String cur:tempList) mGroceryList.add(cur);

            //create an ArrayAdaptar from the String Array
            dataAdapter = new MyCustomAdapter(this,
                    R.layout.country_info, countryList);
            ListView listView = (ListView) findViewById(R.id.listView1);
            // Assign adapter to ListView
            listView.setAdapter(dataAdapter);
        }else{
            grocTextView.setText("My Grocery List is empty");
        }

        FloatingActionButton floatingActionButton = (FloatingActionButton)
                getActivity().findViewById(R.id.fab);
        floatingActionButton.hide();
        return view;
    }

    private class MyCustomAdapter extends ArrayAdapter<String> {

        private ArrayList<String> countryList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<String> countryList) {
            super(context, textViewResourceId, countryList);
            this.countryList = new ArrayList<String>();
            this.countryList.addAll(countryList);
        }

        private class ViewHolder {
            TextView code;
            //CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getActivity().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.country_info, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Country country = (Country) cb.getTag();
                        Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        country.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Country country = countryList.get(position);
            holder.code.setText(" (" +  country.getCode() + ")");
            holder.name.setText(country.getName());
            holder.name.setChecked(country.isSelected());
            holder.name.setTag(country);

            return convertView;

        }

    }




}

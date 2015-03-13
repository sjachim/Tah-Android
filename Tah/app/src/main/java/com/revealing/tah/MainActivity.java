package com.revealing.tah;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import utility.PullToRefreshListView;


public class MainActivity extends Activity {
    private PullToRefreshListView listView;
    private PullToRefreshListViewSampleAdapter adapter;
    int ii=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (PullToRefreshListView) findViewById(R.id.listView);

        adapter = new PullToRefreshListViewSampleAdapter() {};
        listView.setAdapter(adapter);

        // Request the adapter to load the data
        adapter.loadData("first");


        listView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {

            @Override
            public void onRefresh() {
                // Your code to refresh the list contents goes here

                // for example:
                // If this is a webservice call, it might be asynchronous so
                // you would have to call listView.onRefreshComplete(); when
                // the webservice returns the data
                ii++;
                String ss=""+ii;
                adapter.loadData(ss);

                // Make sure you call listView.onRefreshComplete()
                // when the loading is done. This can be done from here or any
                // other place, like on a broadcast receive from your loading
                // service or the onPostExecute of your AsyncTask.

                // For the sake of this sample, the code will pause here to
                // force a delay when invoking the refresh
                listView.postDelayed(new Runnable() {


                    @Override
                    public void run() {
                        listView.onRefreshComplete();
                    }
                }, 2000);
            }
        });
    }


    public  class PullToRefreshListViewSampleAdapter extends android.widget.BaseAdapter {

        private ArrayList<String> items = new ArrayList<String>();;

        public class ViewHolder {
            public String id;
            public TextView name;
        }

        /**
         * Loads the data.
         */
        public void loadData(String add) {

            // Here add your code to load the data for example from a webservice or DB

            items = new ArrayList<String>();
            items.add(add);
            items.add("Ajax Amsterdam");
            items.add("Barcelona");
            items.add("Manchester United");
            items.add("Chelsea");
            items.add("Real Madrid");
            items.add("Bayern Munchen");
            items.add("Internazionale");
            items.add("Valencia");
            items.add("Arsenal");
            items.add("AS Roma");
            items.add("Tottenham Hotspur");
            items.add("PSV");
            items.add("Olympique Lyon");
            items.add("AC Milan");
            items.add("Dortmund");
            items.add("Schalke 04");
            items.add("Twente");
            items.add("Porto");
            items.add("Juventus");


            // MANDATORY: Notify that the data has changed
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;

            String record = (String) getItem(position);

            LayoutInflater inflater = MainActivity.this.getLayoutInflater();

            ViewHolder viewHolder = new ViewHolder();

            if (convertView == null){
                rowView = inflater.inflate(R.layout.list_item,null);

                viewHolder.name = (TextView) rowView.findViewById(R.id.textView1);

                rowView.setTag(viewHolder);
            }

            final ViewHolder holder = (ViewHolder) rowView.getTag();

            holder.name.setText(record);

            return rowView;
        }
    }
}

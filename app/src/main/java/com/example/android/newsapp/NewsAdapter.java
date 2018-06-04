package com.example.android.newsapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(Activity context, ArrayList<News> news) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list, parent, false);
        }

        // Get the News object located at this position in the list
        News currentNews = getItem(position);
        // Find the TextView with view ID sectionName
        TextView sectionName = (TextView) listItemView.findViewById(R.id.sectionName);
        sectionName.setText(currentNews.getmSectionName());
        // convert date and time from "webPublicationDate" key .
        Date dataObject = null;
        String dtStart = currentNews.getmTime();
        SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            dataObject = formate.parse(dtStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TextView date = (TextView) listItemView.findViewById(R.id.date);
        date.setText(currentNews.getmTime());
        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(dataObject);
        // Display the date of the current News in that TextView
        date.setText(formattedDate);

        TextView time = (TextView) listItemView.findViewById(R.id.time);
        time.setText(currentNews.getmTime());
        // Format the time string (i.e. "4:30PM")
        String formattedTime = formatTime(dataObject);
        // Display the time of the current News in that TextView
        time.setText(formattedTime);


        // Find the TextView with view ID titleName.
        TextView titleName = (TextView) listItemView.findViewById(R.id.webTitle);
        titleName.setText(currentNews.getmTitile());

        //Find the TextView with the ID author
        TextView author = listItemView.findViewById(R.id.author);
        author.setText(currentNews.getmAuthor());

        // Find the TextView with view ID titleName.
        TextView PillarName = (TextView) listItemView.findViewById(R.id.pillarName);
        PillarName.setText(currentNews.getmPillarName());

        // Return the list item view that is now showing the appropriate data
        return listItemView;


    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

}

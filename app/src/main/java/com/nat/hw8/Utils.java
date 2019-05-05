package com.nat.hw8;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.nat.hw8.database.DateItem;
import com.nat.hw8.database.Item;
import com.nat.hw8.database.NewsItem;
import com.nat.hw8.retrofit.NewsItemRetrofit;
import com.nat.hw8.retrofit.NewsListRetrofit;
import com.nat.hw8.retrofit.ResponsePayload;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class Utils {

    private static SimpleDateFormat dateFormat;
    private static String today;
    private static String yesterday;
    private static String todayStr;
    private static String yesterdayStr;
    private static SimpleDateFormat sdf;

    public static void initUtils(Context context) {
        Locale russian = new Locale("ru");
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, russian);

        Resources resources = context.getResources();
        DateFormatSymbols dfs = DateFormatSymbols.getInstance(russian);
        String[] months = resources.getStringArray(R.array.months);
        dfs.setMonths(months);

        sdf = (SimpleDateFormat) df;
        sdf.setDateFormatSymbols(dfs);

        dateFormat = new SimpleDateFormat("d-M-yyyy");

        today = dateFormat.format(new Date());
        yesterday = dateFormat.format(new Date(System.currentTimeMillis() - 24*60*60*1000));
        todayStr = resources.getString(R.string.today);
        yesterdayStr = resources.getString(R.string.yesterday);
    }

    static public String dateToStr(String d) {
        if (d.equals(today)) {
            return todayStr;
        } else if (d.equals(yesterday)) {
            return yesterdayStr;
        } else {
            Date formatedDate = dateFormat.parse(d, new ParsePosition(0));
            return sdf.format(formatedDate);
        }
    }

    static public ArrayList<Item> prepareData(List<NewsItem> data) {
        HashMap<String, List<NewsItem>> newsHM = new HashMap<String, List<NewsItem>>();
        ArrayList<Date> dates = new ArrayList<Date>();

        ArrayList<Item> itemList = new ArrayList<>();
        List<NewsItem> newsItemList;
        String dateStr;

        for (NewsItem newsItem : data) {
            String date = mlsToDate(newsItem.getDate());
            if (!newsHM.containsKey(date)) {
                List<NewsItem> list = new ArrayList<>();
                list.add(newsItem);
                newsHM.put(date, list);
            } else {
                newsHM.get(date).add(newsItem);
            }
        }

        for (String date : newsHM.keySet()) {
            dates.add(dateFormat.parse(date, new ParsePosition(0)));
        }

        Collections.sort(dates, Collections.reverseOrder());

        for (Date date : dates) {
            dateStr = dateFormat.format(date);
            itemList.add(new DateItem(dateToStr(dateStr)));
            newsItemList = newsHM.get(dateStr);
            if (newsItemList != null) {
                itemList.addAll(newsItemList);
            }
        }
        return itemList;
    }

    public static String mlsToDate(long mls) {
        Date date = new Date(mls);
        return dateFormat.format(date);
    }

    static NewsItem retrofitToRoomNewsModel(NewsListRetrofit retrofitNewsItem) {
        NewsItem newsItem = new NewsItem(
                    retrofitNewsItem.getId(),
                    retrofitNewsItem.getText(),
                    "",
                    retrofitNewsItem.getPublicationMsDate().getMilliseconds()
        );
        return newsItem;
    }

    public static NewsItem updateNewsItemContent(NewsItem newsItem, ResponsePayload<NewsItemRetrofit> responsePayload) {
        NewsItemRetrofit newsItemRetrofit = responsePayload.getPayload();
        String content = newsItemRetrofit.getContent();
        newsItem.setContent(content);
        return newsItem;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

}

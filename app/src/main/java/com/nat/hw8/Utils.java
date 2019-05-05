package com.nat.hw8;

import android.content.Context;
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

    static public String dateToStr(String d) {
        Locale russian = new Locale("ru");
        String[] months = {
                "января", "февраля", "марта", "апреля", "мая", "июня",
                "июля", "августа", "сентября", "октября", "ноября", "декабря"
        };
        SimpleDateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");
        String today = dateFormat.format(new Date());
        String yesterday = dateFormat.format(new Date(System.currentTimeMillis() - 24*60*60*1000));


        DateFormatSymbols dfs = DateFormatSymbols.getInstance(russian);
        dfs.setMonths(months);
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, russian);
        SimpleDateFormat sdf = (SimpleDateFormat) df;
        sdf.setDateFormatSymbols(dfs);

        if (d.equals(today)) {
            return "Сегодня";
        } else if (d.equals(yesterday)) {
            return "Вчера";
        } else {
            Date formatedDate = dateFormat.parse(d, new ParsePosition(0));
            String date = sdf.format(formatedDate);
            return date;
        }
    }

    static public ArrayList<Item> prepareData(List<NewsItem> data) {
        HashMap<String, List<NewsItem>> newsHM = new HashMap<String, List<NewsItem>>();
        for (NewsItem newsItem : data) {
            String date = newsItem.getDate();
            if (!newsHM.containsKey(date)) {
                List<NewsItem> list = new ArrayList<>();
                list.add(newsItem);
                newsHM.put(date, list);
            } else {
                newsHM.get(date).add(newsItem);
            }
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");
        ArrayList<Date> dates = new ArrayList<Date>();

        for (String dateStr : newsHM.keySet()) {
            dates.add(dateFormat.parse(dateStr, new ParsePosition(0)));
        }

        ArrayList<Item> itemList = new ArrayList<>();
        List<NewsItem> newsItemList;
        Collections.sort(dates, Collections.reverseOrder());

        for (Date date : dates) {
            String dateStr = dateFormat.format(date);
            itemList.add(new DateItem(dateToStr(dateStr)));
            newsItemList = newsHM.get(dateStr);
            itemList.addAll(newsItemList);
        }
        return itemList;
    }

    static private String mlsToDate(long mls) {
        Date date = new Date(mls);
        SimpleDateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");
        return dateFormat.format(date);
    }

    static NewsItem retrofitToRoomNewsModel(NewsListRetrofit retrofitNewsItem) {
        NewsItem newsItem = new NewsItem(
                    retrofitNewsItem.getId(),
                    retrofitNewsItem.getText(),
                    "",
                    mlsToDate(retrofitNewsItem.getPublicationMsDate().getMilliseconds())
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

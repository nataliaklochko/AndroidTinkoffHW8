package com.nat.hw6.ui;

import com.nat.hw6.database.DateItem;
import com.nat.hw6.database.Item;
import com.nat.hw6.database.NewsItem;

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


}

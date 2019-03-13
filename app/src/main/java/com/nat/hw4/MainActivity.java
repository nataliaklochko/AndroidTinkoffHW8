package com.nat.hw4;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private NewsFragmentPagerAdapter fragmentPagerAdapter;

    private static ArrayList<NewsItem> newsLast = new ArrayList<NewsItem>() {
        {
            add(new NewsItem(
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
                    "Phasellus placerat viverra auctor. Duis laoreet mi eget elit efficitur, ac hendrerit justo ultrices. Pellentesque ornare, risus at pellentesque pharetra, est dui efficitur lorem, id ornare nulla orci nec leo",
                    "13-3-2019",
                    "Donec eu urna consequat, feugiat felis non, cursus elit. Mauris vulputate porttitor ante, ut porta enim porta nec. Donec sem felis, commodo vel fermentum eu, aliquet at nisi. Aenean ullamcorper consectetur lorem, ut interdum est imperdiet a. Donec et purus ultricies, auctor magna eget, mollis ex. Aenean pretium rhoncus elit at posuere. Vivamus tempor sollicitudin urna, vel ullamcorper massa sollicitudin eu.",
                    "last"));
            add(new NewsItem(
                    "Orci varius natoque penatibus et magnis dis parturient montes",
                    "Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus",
                    "13-3-2019",
                    "Nunc mattis tempor mauris et hendrerit. Nunc condimentum massa at tellus sollicitudin lobortis. Nunc luctus sapien ut dui ullamcorper, eu dictum diam viverra. Morbi finibus tristique urna vitae malesuada. Sed dignissim posuere magna id dictum. Sed scelerisque tempor velit, id euismod lectus accumsan in.",
                    "last"));
            add(new NewsItem(
                    "Vestibulum gravida",
                    "Vestibulum gravida, mauris sit amet posuere luctus, mi nisl ultrices lectus, porttitor faucibus dui libero eu mauris",
                    "12-3-2019",
                    "Vivamus malesuada commodo leo, eget faucibus lacus faucibus ut. Maecenas pretium, sapien ut hendrerit egestas, orci enim hendrerit quam, sed pellentesque purus massa aliquet mauris. Cras consequat odio egestas pellentesque tempus. Nam id venenatis ipsum, malesuada blandit felis. Etiam in dui quis nibh sodales dapibus. Curabitur nec ligula vitae nunc blandit finibus non sit amet tortor. Suspendisse congue gravida purus. Nulla quam diam, ultrices ac quam sed, interdum dignissim ex. Morbi consequat mi eros, vitae lobortis elit efficitur et. Integer magna mauris, aliquam et vestibulum nec, semper sit amet eros. Duis porttitor justo quis tortor tristique, vel varius turpis consequat. Suspendisse non leo vel felis dapibus finibus ut at urna. Nam non porttitor dolor. Integer aliquam vel orci vitae maximus. Duis eget consectetur mauris.",
                    "last"));
            add(new NewsItem(
                    "Praesent ipsum leo",
                    "Praesent ipsum leo, imperdiet sed tincidunt non, ultricies condimentum odio",
                    "11-3-2018",
                    "Curabitur iaculis felis non egestas fermentum. Proin non tortor sed ante congue rhoncus. In varius nec magna quis eleifend. Pellentesque semper convallis quam eget elementum. Fusce tincidunt vitae felis ut lobortis. Nullam nec ex congue, sodales est ut, iaculis massa. Nam eleifend finibus tortor. Fusce nec lacinia tortor. Donec fringilla sagittis felis, vitae efficitur turpis blandit vel. Etiam quis ullamcorper mi. Praesent sit amet feugiat diam, eu lacinia magna.",
                    "last"));
            add(new NewsItem(
                    "Morbi id dapibus metus",
                    "In neque ante, congue id sagittis ut, ullamcorper quis lorem",
                    "5-3-2018",
                    "Nullam dapibus sed orci congue pulvinar. Phasellus euismod, ligula id eleifend lacinia, lectus tortor pretium velit, ultricies consequat neque dui quis augue. Sed a ultrices nisl. Nam non nibh mauris. Vivamus vitae pulvinar dolor. Vivamus interdum ultricies vulputate. Aenean ut dictum odio. Vivamus ac turpis nibh.",
                    "last"));
            add(new NewsItem(
                    "Etiam molestie semper lorem",
                    "Cras imperdiet erat at felis eleifend faucibus",
                    "5-3-2018",
                    " Vivamus imperdiet rhoncus sem in sollicitudin. Vivamus quis magna accumsan, convallis tortor luctus, porttitor arcu. Donec sodales faucibus mauris, a fermentum urna ultricies id. In semper laoreet efficitur. Etiam ut rutrum mi, non tincidunt lacus. Vivamus sagittis nisl a neque scelerisque pellentesque. Curabitur porta erat eget leo tristique venenatis. Morbi quis venenatis risus.",
                    "last"));
            add(new NewsItem(
                    "Nulla aliquam diam ligula",
                    "Quisque blandit sagittis sem sed dignissim",
                    "5-3-2018",
                    "Aenean ac accumsan diam. Aliquam in urna purus. Phasellus a cursus nibh. Suspendisse egestas finibus leo at imperdiet. Mauris a mauris posuere, condimentum nulla eu, malesuada metus. Duis luctus sapien et felis placerat mollis. Phasellus semper a lorem vitae venenatis. Donec dictum, lectus a iaculis laoreet, purus odio varius dolor, vitae rutrum libero augue eget ante. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur eu neque convallis, tristique risus vel, molestie nisl.",
                    "last"));
            add(new NewsItem(
                    "Curabitur a libero euismod, vulputate eros at, vulputate metus",
                    "Curabitur fringilla, ex et dictum euismod, diam neque feugiat diam, quis consequat risus dui et justo",
                    "1-3-2018",
                    "Nullam tempus viverra nulla, vel ullamcorper orci. Sed pulvinar lobortis urna. Sed eu ex interdum, vehicula quam ac, viverra sapien. Mauris blandit porttitor pellentesque. Mauris a sagittis odio, sit amet fermentum nibh. Suspendisse laoreet, mauris ut congue molestie, erat nulla porttitor lorem, in congue tellus nunc in velit. Interdum et malesuada fames ac ante ipsum primis in faucibus. Vestibulum ultricies, arcu in rutrum ultricies, orci sapien porttitor lorem, venenatis facilisis nunc nisl hendrerit lectus. Morbi eleifend non velit vitae luctus. Nullam lobortis imperdiet ullamcorper. Aenean at diam tempus, pretium mauris eget, tincidunt justo. Fusce pretium varius efficitur.",
                    "last"));
        }
    };

    private static ArrayList<NewsItem> newsFavourites = new ArrayList<NewsItem>() {
        {
            add(new NewsItem(
                    "Orci varius natoque penatibus et magnis dis parturient montes",
                    "Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus",
                    "13-3-2019",
                    "Nunc mattis tempor mauris et hendrerit. Nunc condimentum massa at tellus sollicitudin lobortis. Nunc luctus sapien ut dui ullamcorper, eu dictum diam viverra. Morbi finibus tristique urna vitae malesuada. Sed dignissim posuere magna id dictum. Sed scelerisque tempor velit, id euismod lectus accumsan in.",
                    "last"));
            add(new NewsItem(
                    "Nulla aliquam diam ligula",
                    "Quisque blandit sagittis sem sed dignissim",
                    "5-3-2018",
                    "Aenean ac accumsan diam. Aliquam in urna purus. Phasellus a cursus nibh. Suspendisse egestas finibus leo at imperdiet. Mauris a mauris posuere, condimentum nulla eu, malesuada metus. Duis luctus sapien et felis placerat mollis. Phasellus semper a lorem vitae venenatis. Donec dictum, lectus a iaculis laoreet, purus odio varius dolor, vitae rutrum libero augue eget ante. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur eu neque convallis, tristique risus vel, molestie nisl.",
                    "last"));
        }
    };



    private static ArrayList<ListItem> newsLastList;
    private static ArrayList<ListItem> newsFavouritesList;

    private static NewsPageFragment newsLastFragment;
    private static NewsPageFragment newsFavouritesFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        fragmentPagerAdapter = new NewsFragmentPagerAdapter(getSupportFragmentManager());

        newsLastList = prepareData(newsLast);
        newsFavouritesList = prepareData(newsFavourites);

        newsLastFragment = NewsPageFragment.newInstance(newsLastList);
        newsFavouritesFragment = NewsPageFragment.newInstance(newsFavouritesList);
        fragmentPagerAdapter.addFragment("", newsLastFragment);
        fragmentPagerAdapter.addFragment("", newsFavouritesFragment);

        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_access_time);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_star);

    }


    static public boolean addToFavourites(NewsItem news) {
        news.setLast("");
        if (!newsFavourites.contains(news)) {
            newsFavourites.add(news);
            newsFavouritesList = prepareData(newsFavourites);
            newsFavouritesFragment.getAdapter().refreshData(newsFavouritesList);
            return true;
        } else {
            return false;
        }
    }

    static private String dateToStr(String d) {
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

    static private ArrayList<ListItem> prepareData(ArrayList<NewsItem> data) {
        HashMap<String, ArrayList<NewsItem>> newsHM = new HashMap<String, ArrayList<NewsItem>>();
        for (NewsItem news : data) {
            String date = news.getDate();
            if (!newsHM.containsKey(date)) {
                ArrayList<NewsItem> list = new ArrayList<NewsItem>();
                list.add(news);
                newsHM.put(date, list);
            } else {
                newsHM.get(date).add(news);
            }
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");
        ArrayList<Date> dates = new ArrayList<Date>();

        for (String dateStr : newsHM.keySet()) {
            dates.add(dateFormat.parse(dateStr, new ParsePosition(0)));
        }

        ArrayList<ListItem> itemList = new ArrayList<>();
        ArrayList<NewsItem> newsList;
        Collections.sort(dates, Collections.reverseOrder());

        for (Date date : dates) {
            String dateStr = dateFormat.format(date);
            itemList.add(new DateItem(dateToStr(dateStr)));
            newsList = newsHM.get(dateStr);
            itemList.addAll(newsList);
        }
        return itemList;
    }


}


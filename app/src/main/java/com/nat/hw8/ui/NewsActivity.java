package com.nat.hw8.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nat.hw8.NewsViewModel;
import com.nat.hw8.R;
import com.nat.hw8.Utils;
import com.nat.hw8.database.FavouritesNews;
import com.nat.hw8.database.NewsItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;



public class NewsActivity extends AppCompatActivity {

    public static final String NEWS_TAG = "news";
    private static final String NEW_USER = "new_user_news_activity";
    private Boolean fav;
    private Boolean old;
    private TextView description;
    private TextView content;
    private TextView date;
    private ImageButton starBtn;
    private SharedPreferences sharedPreferences;
    private NewsItem newsItem;
    private NewsViewModel newsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news);

        Bundle intent = getIntent().getExtras();
        newsItem = intent.getParcelable(NEWS_TAG);
        refreshActivity(newsItem);
        showIntroToast();

        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        newsViewModel.isFavorite(newsItem.getId()).observe(this, observerFavourite);
        newsViewModel.isOld(newsItem.getId()).observe(this, observerContent);

        starBtn = (ImageButton) findViewById(R.id.star_btn);
    }


    private void showIntroToast() {
        sharedPreferences = getSharedPreferences(MainActivity.PREF, Context.MODE_PRIVATE);
        if (!sharedPreferences.contains(NEW_USER)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(NEW_USER, false);
            editor.apply();
            Toast.makeText(getApplicationContext(), R.string.instruction_star, Toast.LENGTH_LONG).show();
        }
    }


    private void setContent(Boolean old) {
        if (old) {
            newsViewModel.getOldNewsItem(newsItem).observe(this, observerNewItem);
        } else {
            newsViewModel.getNewsItem(newsItem).observe(this, observerNewItem);
        }
    }

    private void setFavourite(Boolean fav) {
        if (fav) {
            starBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newsViewModel.insertFavourites(new FavouritesNews(newsItem.getId()));
                    Toast.makeText(NewsActivity.this, R.string.favourites_msg, Toast.LENGTH_SHORT).show();
                    starBtn.setVisibility(ImageButton.INVISIBLE);
                }
            });
        } else {
            starBtn.setVisibility(ImageButton.INVISIBLE);
        }
    }

    private void refreshActivity(NewsItem newsItem) {
        description = (TextView) findViewById(R.id.news_activity_description);
        content = (TextView) findViewById(R.id.news_activity_content);
        date = (TextView) findViewById(R.id.news_activity_date);

        description.setText(Html.fromHtml(newsItem.getDescription(), Html.FROM_HTML_MODE_COMPACT));
        content.setText(Html.fromHtml(newsItem.getContent(), Html.FROM_HTML_MODE_COMPACT));
        date.setText(Utils.mlsToDate(newsItem.getDate()));
    }

    private Observer<Boolean> observerFavourite = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean favValue) {
            fav = favValue;
            setFavourite(fav);
        }
    };

    private Observer<NewsItem> observerNewItem = new Observer<NewsItem>() {
        @Override
        public void onChanged(NewsItem updatedNewsItem) {
            newsItem = updatedNewsItem;
            newsViewModel.insertNewsItem(newsItem);
            refreshActivity(newsItem);
        }
    };

    private Observer<Boolean> observerContent = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean value) {
            old = !value;
            setContent(old);
        }
    };


}

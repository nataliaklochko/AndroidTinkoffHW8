package com.nat.hw6.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nat.hw6.NewsViewModel;
import com.nat.hw6.R;
import com.nat.hw6.database.FavouritesNews;
import com.nat.hw6.database.NewsItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class NewsActivity extends AppCompatActivity {

    public static final String NEWS_TAG = "news";
    private static final String NEW_USER = "new_user_news_activity";
    private Boolean fav;
    private TextView title;
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

        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);

        starBtn = (ImageButton) findViewById(R.id.star_btn);

        newsViewModel.isFavorite(newsItem.getId()).observe(this, observer);
    }

    private void refreshActivity(Boolean fav) {
        try {
            if (fav) {
                starBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newsViewModel.insertFavourites(new FavouritesNews(newsItem.getId()));
                        Toast.makeText(NewsActivity.this, R.string.favourites_msg, Toast.LENGTH_SHORT).show();
                        starBtn.setVisibility(ImageButton.INVISIBLE);
                    }
                });

                sharedPreferences = getSharedPreferences(MainActivity.PREF, Context.MODE_PRIVATE);
                if (!sharedPreferences.contains(NEW_USER)) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(NEW_USER, false);
                    editor.apply();

                    Toast.makeText(getApplicationContext(), R.string.instruction_star, Toast.LENGTH_LONG).show();
                }

            } else {
                starBtn.setVisibility(ImageButton.INVISIBLE);
            }

            title = (TextView) findViewById(R.id.news_activity_title);
            content = (TextView) findViewById(R.id.news_activity_content);
            date = (TextView) findViewById(R.id.news_activity_date);

            title.setText(newsItem.getTitle());
            content.setText(newsItem.getContent());
            date.setText(newsItem.getDate());

        } catch (NullPointerException e) {
            throw new NullPointerException("Новость не отображена");
        }
    }

    private Observer<Boolean> observer = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean favValue) {
            fav = favValue;
            refreshActivity(fav);
        }

    };

}

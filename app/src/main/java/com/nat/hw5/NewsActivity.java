package com.nat.hw5;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nat.hw5.database.FavouritesNews;
import com.nat.hw5.database.NewsItem;

import androidx.appcompat.app.AppCompatActivity;

import static com.nat.hw5.NewsPageFragment.newsViewModel;


public class NewsActivity extends AppCompatActivity {

    public static final String NEWS_TAG = "news";
    private static final String NEW_USER = "new_user_news_activity";
    private boolean fav;
    private TextView title;
    private TextView content;
    private TextView date;
    private ImageButton starBtn;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news);

        try {
            Bundle intent = getIntent().getExtras();
            final NewsItem newsItem = intent.getParcelable(NEWS_TAG);

            fav = (newsViewModel.getFavouriteNews(newsItem.getId()) == null);
            starBtn = (ImageButton) findViewById(R.id.star_btn);

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

}

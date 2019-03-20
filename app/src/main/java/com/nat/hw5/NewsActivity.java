package com.nat.hw5;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class NewsActivity extends AppCompatActivity {

    public static String NEWS_TAG = "news";
    public static String LAST_TAG = "last";
    private TextView title;
    private TextView content;
    private TextView date;
    private ImageButton starBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        try {
            Bundle intent = getIntent().getExtras();
            final NewsItem newsItem = intent.getParcelable(NEWS_TAG);
            boolean last = intent.getBoolean(LAST_TAG);
            starBtn = (ImageButton) findViewById(R.id.star_btn);

            if (last) {
                starBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NewsPageFragment.newsViewModel.insertFavourites(new FavouritesNews(newsItem.getId()));
                        Toast.makeText(NewsActivity.this, R.string.favourites_msg, Toast.LENGTH_SHORT).show();
                        starBtn.setVisibility(ImageButton.INVISIBLE);
                    }
                });
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

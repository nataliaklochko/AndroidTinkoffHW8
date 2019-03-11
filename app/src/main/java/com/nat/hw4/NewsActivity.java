package com.nat.hw4;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class NewsActivity extends AppCompatActivity {

    public static String NEWS_TAG = "news";
    private TextView title;
    private TextView content;
    private TextView date;
    private ImageButton starBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        try {
            final NewsItem news = getIntent().getExtras().getParcelable(NEWS_TAG);
            starBtn = (ImageButton) findViewById(R.id.star_btn);

            if (lastNews(news)) {
                starBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (MainActivity.addToFavourites(news)) {
                            Toast.makeText(NewsActivity.this, R.string.favourites_msg, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(NewsActivity.this, R.string.already_in_favourites_msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                starBtn.setVisibility(ImageButton.INVISIBLE);
            }
            title = (TextView) findViewById(R.id.news_activity_title);
            content = (TextView) findViewById(R.id.news_activity_content);
            date = (TextView) findViewById(R.id.news_activity_date);

            title.setText(news.getTitle());
            content.setText(news.getContent());
            date.setText(news.getDate());

        } catch (NullPointerException e) {
            throw new NullPointerException("Новость не отображена");
        }
    }

    private boolean lastNews(NewsItem news) {
        return news.getLast().equals("last");
    }

}

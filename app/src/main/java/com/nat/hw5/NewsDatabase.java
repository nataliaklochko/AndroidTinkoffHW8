package com.nat.hw5;

import android.content.Context;
import android.os.AsyncTask;

import java.text.SimpleDateFormat;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = { NewsItem.class, FavouritesNews.class }, version = 1, exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {

    private static NewsDatabase instance;
    private static final String DB_NAME = "news_database.db";
    public abstract NewsDao newsDao();

    public abstract FavouritesNewsDao favouritesNewsDao();

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private final NewsDao newsDao;
        private SimpleDateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");
        private NewsItem[] newsItemList = {
                new NewsItem(
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
                        "Phasellus placerat viverra auctor. Duis laoreet mi eget elit efficitur, ac hendrerit justo ultrices. Pellentesque ornare, risus at pellentesque pharetra, est dui efficitur lorem, id ornare nulla orci nec leo",
                        "Donec eu urna consequat, feugiat felis non, cursus elit. Mauris vulputate porttitor ante, ut porta enim porta nec. Donec sem felis, commodo vel fermentum eu, aliquet at nisi. Aenean ullamcorper consectetur lorem, ut interdum est imperdiet a. Donec et purus ultricies, auctor magna eget, mollis ex. Aenean pretium rhoncus elit at posuere. Vivamus tempor sollicitudin urna, vel ullamcorper massa sollicitudin eu.",
                        "13-3-2019"
                ),
                new NewsItem(
                        "Orci varius natoque penatibus et magnis dis parturient montes",
                        "Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus",
                        "Nunc mattis tempor mauris et hendrerit. Nunc condimentum massa at tellus sollicitudin lobortis. Nunc luctus sapien ut dui ullamcorper, eu dictum diam viverra. Morbi finibus tristique urna vitae malesuada. Sed dignissim posuere magna id dictum. Sed scelerisque tempor velit, id euismod lectus accumsan in.",
                        "13-3-2019"
                ),
                new NewsItem(
                        "Vestibulum gravida",
                        "Vestibulum gravida, mauris sit amet posuere luctus, mi nisl ultrices lectus, porttitor faucibus dui libero eu mauris",
                        "Vivamus malesuada commodo leo, eget faucibus lacus faucibus ut. Maecenas pretium, sapien ut hendrerit egestas, orci enim hendrerit quam, sed pellentesque purus massa aliquet mauris. Cras consequat odio egestas pellentesque tempus. Nam id venenatis ipsum, malesuada blandit felis. Etiam in dui quis nibh sodales dapibus. Curabitur nec ligula vitae nunc blandit finibus non sit amet tortor. Suspendisse congue gravida purus. Nulla quam diam, ultrices ac quam sed, interdum dignissim ex. Morbi consequat mi eros, vitae lobortis elit efficitur et. Integer magna mauris, aliquam et vestibulum nec, semper sit amet eros. Duis porttitor justo quis tortor tristique, vel varius turpis consequat. Suspendisse non leo vel felis dapibus finibus ut at urna. Nam non porttitor dolor. Integer aliquam vel orci vitae maximus. Duis eget consectetur mauris.",
                        "12-3-2019"
                ),
                new NewsItem(
                        "Praesent ipsum leo",
                        "Praesent ipsum leo, imperdiet sed tincidunt non, ultricies condimentum odio",
                        "Curabitur iaculis felis non egestas fermentum. Proin non tortor sed ante congue rhoncus. In varius nec magna quis eleifend. Pellentesque semper convallis quam eget elementum. Fusce tincidunt vitae felis ut lobortis. Nullam nec ex congue, sodales est ut, iaculis massa. Nam eleifend finibus tortor. Fusce nec lacinia tortor. Donec fringilla sagittis felis, vitae efficitur turpis blandit vel. Etiam quis ullamcorper mi. Praesent sit amet feugiat diam, eu lacinia magna.",
                        "11-3-2018"
                ),
                new NewsItem(
                        "Morbi id dapibus metus",
                        "In neque ante, congue id sagittis ut, ullamcorper quis lorem",
                        "Nullam dapibus sed orci congue pulvinar. Phasellus euismod, ligula id eleifend lacinia, lectus tortor pretium velit, ultricies consequat neque dui quis augue. Sed a ultrices nisl. Nam non nibh mauris. Vivamus vitae pulvinar dolor. Vivamus interdum ultricies vulputate. Aenean ut dictum odio. Vivamus ac turpis nibh.",
                        "5-3-2018"
                ),
                new NewsItem(
                        "Etiam molestie semper lorem",
                        "Cras imperdiet erat at felis eleifend faucibus",
                        " Vivamus imperdiet rhoncus sem in sollicitudin. Vivamus quis magna accumsan, convallis tortor luctus, porttitor arcu. Donec sodales faucibus mauris, a fermentum urna ultricies id. In semper laoreet efficitur. Etiam ut rutrum mi, non tincidunt lacus. Vivamus sagittis nisl a neque scelerisque pellentesque. Curabitur porta erat eget leo tristique venenatis. Morbi quis venenatis risus.",
                        "5-3-2018"
                ),
                new NewsItem(
                        "Nulla aliquam diam ligula",
                        "Quisque blandit sagittis sem sed dignissim",
                        "Aenean ac accumsan diam. Aliquam in urna purus. Phasellus a cursus nibh. Suspendisse egestas finibus leo at imperdiet. Mauris a mauris posuere, condimentum nulla eu, malesuada metus. Duis luctus sapien et felis placerat mollis. Phasellus semper a lorem vitae venenatis. Donec dictum, lectus a iaculis laoreet, purus odio varius dolor, vitae rutrum libero augue eget ante. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur eu neque convallis, tristique risus vel, molestie nisl.",
                        "5-3-2018"
                ),
                new NewsItem(
                        "Curabitur a libero euismod, vulputate eros at, vulputate metus",
                        "Curabitur fringilla, ex et dictum euismod, diam neque feugiat diam, quis consequat risus dui et justo",
                        "Nullam tempus viverra nulla, vel ullamcorper orci. Sed pulvinar lobortis urna. Sed eu ex interdum, vehicula quam ac, viverra sapien. Mauris blandit porttitor pellentesque. Mauris a sagittis odio, sit amet fermentum nibh. Suspendisse laoreet, mauris ut congue molestie, erat nulla porttitor lorem, in congue tellus nunc in velit. Interdum et malesuada fames ac ante ipsum primis in faucibus. Vestibulum ultricies, arcu in rutrum ultricies, orci sapien porttitor lorem, venenatis facilisis nunc nisl hendrerit lectus. Morbi eleifend non velit vitae luctus. Nullam lobortis imperdiet ullamcorper. Aenean at diam tempus, pretium mauris eget, tincidunt justo. Fusce pretium varius efficitur.",
                        "1-3-2018"
                )
        };

        private PopulateDbAsyncTask(NewsDatabase db) {
            newsDao = db.newsDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (NewsItem newsItem : newsItemList) {
                newsDao.insert(newsItem);
            }
            return null;
        }
    }

    public static NewsDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (NewsDatabase.class) {
                instance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        NewsDatabase.class,
                        DB_NAME
                )
                        .addCallback(roomCallback)
                        .build();
            }
        }
        return instance;
    }

}

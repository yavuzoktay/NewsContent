package com.yavuzoktay.teniscim;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.yavuzoktay.teniscim.adapters.PostsAdapter;
import com.yavuzoktay.teniscim.models.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();
    private String RSS_URL = "http://www.haberturk.com/rss/manset.xml";
    private PullAndParseXML pullAndParseXML;
    private List<Post> posts;
    private RecyclerView recyclerView;
    private PostsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pullAndParseXML = new PullAndParseXML(RSS_URL);
        pullAndParseXML.downloadXML();

        while (pullAndParseXML.parsingComplete) ;
        posts = pullAndParseXML.getPostList().subList(1, 5);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        adapter = new PostsAdapter(this, posts);

        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    private ArrayList getImageUrls(String text) {

        ArrayList links = new ArrayList();
        String patternString = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&amp;@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&amp;@#/%=~_()|]";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String urlStr = matcher.group();
            if (urlStr.startsWith("(") && urlStr.endsWith(")")) {
                urlStr = urlStr.substring(1, urlStr.length() - 1);
            }
            if (urlStr.endsWith(".jpg") || urlStr.endsWith(".png") || urlStr.endsWith(".gif"))
                links.add(urlStr);
        }
        return links;
    }

}
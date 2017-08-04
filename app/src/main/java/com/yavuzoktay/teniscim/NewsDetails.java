package com.yavuzoktay.teniscim;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

public class NewsDetails extends AppCompatActivity {

    WebView webView;
    Bundle bundle;
    TextView textView;
    ProgressBar pg;
    String[] stopWords = {"ve", "acaba", "ama", "ancak", "artık", "asla", "aslında", "az", "bana", "bazen", "bazı", "bazıları",
            "bazısı", "belki", "ben", "beni", "benim", "beş", "bile", "bir", "birçoğu", "birçok", "birçokları", "biri", "birisi",
            "birkaç", "birkaçı", "birşey", "birşeyi", "biz", "bize", "bizi", "bizim", "böyle", "böylece", "bu", "buna", "bunda",
            "bundan", "bunu", "bunun", "burada", "bütün", "çoğu", "çoğuna", "çoğunu", "çok", "çünkü","da", "daha", "de", "değil",
            "demek", "diğer", "diğeri", "diğerleri", "diye", "dolayı", "elbette", "en","fakat", "falan", "felan", "filan", "gene",
            "gibi", "hangi", "hangisi", "hani", "hatta", "hem", "henüz", "hep", "hepsi", "hepsine", "hepsini", "her", "her biri",
            "herkes", "herkese", "herkesi", "hiç", "hiç kimse", "hiçbiri", "hiçbirine", "hiçbirini",  "için", "içinde", "ile","ise", "işte",
            "kaç", "kadar", "kendi", "kendine", "kendini", "ki", "kim", "kime", "kimi", "kimin", "kimisi",
            "madem", "mı", "mi", "mu", "mü",  "nasıl", "ne", "ne kadar", "ne zaman", "neden", "nedir", "nerde", "nerede",
            "nereden", "nereye", "nesi", "neyse", "niçin", "niye", "ona", "ondan", "onlar", "onlara", "onlardan", "onların",
            "onu", "onun", "orada", "oysa", "oysaki",
            "öbürü", "ön", "önce", "ötürü", "öyle", "sana", "sen", "senden", "seni", "senin", "siz", "sizden", "size",
            "sizi", "sizin", "son", "sonra", "seobilog",
            "şayet", "şey", "şimdi", "şöyle", "şu", "şuna", "şunda", "şundan", "şunlar", "şunu", "şunun", "tabi", "tamam", "tüm",
            "tümü", "üzere",  "var", "veya", "veyahut", "ya", "ya da", "yani", "yerine", "yine", "yoksa", "zaten", "zira"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        webView= (WebView) findViewById(R.id.webView);
        textView = (TextView) findViewById(R.id.textView);
        bundle=getIntent().getExtras();
        webView.loadUrl(bundle.getString("Link"));
        pg = (ProgressBar) findViewById(R.id.progressBar);

        new VeriA().execute();
    }
    public boolean getVarmi(String word){

        //Bulundu
        boolean r = false;
        for (String stopword: stopWords) {
            if(stopword.contains(word)) {
                r = true;
                break;
            }
        }

        return  r;
    }

    public class VeriA extends AsyncTask<Void,Void,List<String>>{
        @Override
        protected List<String> doInBackground(Void... voids) {

            List<String> lst = null;

            try {
                org.jsoup.nodes.Document doc= Jsoup.connect(bundle.getString("Link")).get();
                Elements elements=doc.select("div[class=news-detail-content]");
                if(elements.size()>0){
                    String paragraf = elements.get(0).text();
                    Log.d("", paragraf);


                    HashMap<String, Integer> wordCountMap = new HashMap<String, Integer>();

                    String[] words = paragraf.toLowerCase().split(" ");

                    for (String word : words)
                            {
                                String w = word.replace(",","");
                                w = w.replace(".","");
                                w = w.replace(":","");
                                w = w.replace("?","");
                                w = w.replace("!","");
                                    if (wordCountMap.containsKey(w)) {
                                        wordCountMap.put(w, wordCountMap.get(w) + 1);
                                    } else {
                                        wordCountMap.put(w, 1);
                                    }
                            }

                        Set<Map.Entry<String, Integer>> entrySet = wordCountMap.entrySet();
                        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String,Integer>>(entrySet);
                        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>()
                        {
                            @Override
                            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2)
                            {
                                return (e2.getValue().compareTo(e1.getValue()));
                            }
                        });

                        int i = 1;
                        lst = new ArrayList<String>();
                        for (Map.Entry<String, Integer> entry : list)
                        {

                            if(i < 5){
                                if(!getVarmi(entry.getKey()) && entry.getKey().length() > 1 ){
                                    lst.add(entry.getKey());
                                    i++;
                                }
                            }
                            else
                                break;
                        }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  lst;
        }
        @Override
        protected void onPostExecute(List<String> lst) {
            super.onPostExecute(lst);

            Object[] str =lst.toArray();
            String words = TextUtils.join(", ", str);
            textView.setText(words);
            pg.setVisibility(View.GONE);

        }
    }
}

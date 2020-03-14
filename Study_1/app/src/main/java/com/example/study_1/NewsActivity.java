package com.example.study_1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String[] mDataset = {"1", "2"};
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        queue = Volley.newRequestQueue(this);
        getNews();

        //1. 화면이 로딩 -> 뉴스 정보를 받아온다 ----
        //2. 정보  -> 어댑터에 넘겨준다
        //3. 어댑터 -> 셋팅
    }

    public void getNews() { // 뉴스 정보를 받아오는 함수

        String url = "http://newsapi.org/v2/top-headlines?country=kr&apiKey=7fdd29124a424f19a4bd71757042f4ef";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //response -> NewsData Class에 분류

                            JSONObject jsonObj = new JSONObject(response); // response를 받아와서 json형태로 만든다

                            //json의 articles에 있는 배열을 JSONArray에 받는다.
                            JSONArray arrayArticles = jsonObj.getJSONArray("articles");

                            final List<NewsData> news = new ArrayList<>();//articles의 title,imageurl,description을 받을 list

                            //articles의 개수만큼 반복
                            for(int i = 0; i < arrayArticles.length(); i++){
                                JSONObject obj = arrayArticles.getJSONObject(i);// i번째에 있는 jsonObject를 받는다

                                Log.d("NEWS", obj.toString());
                                NewsData newsData = new NewsData();
                                newsData.setTitle(obj.getString("title"));
                                newsData.setUrlToImage(obj.getString("urlToImage"));
                                newsData.setDescription(obj.getString("description"));

                                news.add(newsData);
                            }

                            // specify an adapter (see also next example)
                            mAdapter = new MyAdapter(news, NewsActivity.this, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Object obj = view.getTag();
                                    if(obj != null){
                                        int position = (int)obj;

                                        NewsData newsData = ((MyAdapter)mAdapter).getNews(position);
                                        Intent intent =new Intent(NewsActivity.this , NewsDetailActivity.class);

                                        intent.putExtra("news", newsData);
                                        startActivity(intent);

                                    }
                                }
                            });
                            recyclerView.setAdapter(mAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}

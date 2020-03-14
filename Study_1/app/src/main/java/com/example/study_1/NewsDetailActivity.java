package com.example.study_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NewsDetailActivity extends AppCompatActivity {

    private NewsData news;
    private TextView textView_title, textView_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        setComp();
        getNewsDetail();;
        setNews();

    }

    //기본 컴포넌트 셋팅
    public void setComp(){
        textView_title = findViewById(R.id.textView_titleResult);
        textView_description = findViewById(R.id.textView_descriptionResult);
    }

    //이전 액티비티에서 받아오는 인텐트
    public void getNewsDetail(){
        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();

            Object obj = bundle.get("news");
            if(obj != null && obj instanceof NewsData){
                this.news = (NewsData) obj;
            }
        }
    }

    //이전 액티비티에서 받아오는 인텐트에서 정보를 확인하여 뉴스표시
    public void setNews(){
        if(this.news != null){
            String title = this.news.getTitle();
            if(title != null){
                textView_title.setText(title);
            }

            String description = this.news.getDescription();
            if(description != null){
                //전체 본문은 url 값의 실제 뉴스 사이트에 있으며,
                //해당 전체 본문을 불러오기 위해서는 크롤링을 해야함.
                textView_description.setText(description);
            }
        }
    }
}

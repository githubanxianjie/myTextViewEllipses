package main.recyclerview.com.mytextviewellipses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static main.recyclerview.com.mytextviewellipses.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
       final TextView tv_content=(TextView)findViewById(R.id.tv_content);
        tv_content.setText(R.string.text);
        findViewById(R.id.tv_show_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取省略的字符数，0表示没和省略
                int ellipsisCount = tv_content.getLayout().getEllipsisCount(tv_content.getLineCount() - 1);
                //ellipsisCount>0说明没有显示全部，存在省略部分。
                if (ellipsisCount > 0) {
                    //展示全部，按钮设置为点击收起。
                    tv_content.setMaxHeight(getResources().getDisplayMetrics().heightPixels);
                    ((TextView) findViewById(R.id.tv_show_all)).setText("收起");
                } else {
                    //显示3行，按钮设置为点击显示全部。
                    ((TextView) findViewById(R.id.tv_show_all)).setText("显示全部");
                    tv_content.setMaxLines(3);
                }

            }
        });
        findViewById(R.id.btn_list_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(MainActivity.this,TestListActivity.class));
            }
        });

    }
}

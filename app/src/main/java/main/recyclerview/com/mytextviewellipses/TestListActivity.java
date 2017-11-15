package main.recyclerview.com.mytextviewellipses;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by anxianjie-g on 2017/11/9 18:00
 * email：xanxianjie@sina.com
 */

public class TestListActivity extends AppCompatActivity {

    private static final int LINECOUNT = 3;
    /**
     * 数据
     */
    private List<DataModel> data = new ArrayList<>();
    private RecyclerView.Adapter adapter;
    private int ellCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        String temp = getResources().getString(R.string.text);
        String text;
        StringBuilder sb;
        Random rd = new Random();
        for (int i = 0; i < 40; i++) {
            sb = new StringBuilder();
            text = temp.substring(0, rd.nextInt(temp.length() - 1));
            sb.append(i + text);
            data.add(new DataModel(null, sb.toString()));
        }
        adapter = new RecyclerView.Adapter<MyViewHolder>() {
            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(TestListActivity.this).inflate(R.layout.activity_list_item, parent, false);
                return new MyViewHolder(view);
            }

            @Override
            public void onBindViewHolder(final MyViewHolder holder, int position) {
                final DataModel dm = data.get(position);
                holder.tv_content.setText(dm.text);
                holder.tv_showAll.setTag(dm);
                holder.tv_content.setTag(dm);
                setViewStatus(holder.tv_content, holder.tv_showAll, dm);
            }

            @Override
            public int getItemCount() {
                return data.size();
            }
        };
        mRecyclerView.setAdapter(adapter);
    }

    private DataModel tempModel;

    /**
     * 当前设置item数据
     * @param tv_content
     * @param tv_showAll
     * @param dm
     */
    private void setViewStatus(final TextView tv_content, final TextView tv_showAll, final DataModel dm) {
        if (dm.hasEllipsis == null) {
            tv_content.post(new Runnable() {
                @Override
                public void run() {
                    checkEllipsData(tv_content, tv_showAll);
                }
            });
        } else {
            if (tv_content.getLayout() != null) {
                checkEllipsData(tv_content, tv_showAll);
            } else {
                tv_content.post(new Runnable() {
                    @Override
                    public void run() {
                        checkEllipsData(tv_content, tv_showAll);
                    }
                });
            }
        }
    }

    /**
     * 检查在第LINECOUNT 行中是否有超出的字符
     * @param tv_content
     * @param tv_showAll
     */
    private void checkEllipsData(TextView tv_content, TextView tv_showAll) {
        ellCount = 0;
        tempModel = (DataModel) tv_content.getTag();
        if (tv_content.getLineCount() >= LINECOUNT) {
            ellCount = tv_content.getLayout().getEllipsisCount(LINECOUNT - 1);
        }
        tempModel.hasEllipsis = (ellCount >= 1 || tv_content.getLineCount() > LINECOUNT);
        showAllView(tv_content, tv_showAll, tempModel);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_content, tv_showAll;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_showAll = (TextView) itemView.findViewById(R.id.tv_show_all);
            tv_showAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataModel dm = (DataModel) v.getTag();
                    dm.isShowAll = !dm.isShowAll;
                    adapter.notifyItemChanged(data.indexOf(dm));
                }
            });
            tv_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataModel dm = (DataModel) v.getTag();
                    if (dm.hasEllipsis) {
                        dm.isShowAll = !dm.isShowAll;
                        adapter.notifyItemChanged(data.indexOf(dm));
                    }
                }
            });
        }
    }

    /**
     * 显示当前item文本全部或者收起
     * @param tv_contnet
     * @param tv_allShow
     * @param dataModel
     */
    private void showAllView(TextView tv_contnet, TextView tv_allShow,
                             DataModel dataModel) {
        if (dataModel.hasEllipsis) {
            tv_allShow.setVisibility(View.VISIBLE);
            if (dataModel.isShowAll) {
                // 展示全部，按钮设置为点击收起。
                tv_contnet.setMaxHeight(getResources()
                        .getDisplayMetrics().heightPixels);
                tv_allShow.setText("收起");
            } else {
                // 显示3行，按钮设置为点击显示全部。
                tv_contnet.setMaxLines(LINECOUNT);
                tv_allShow.setText("显示全部");

            }
        } else {
            tv_allShow.setVisibility(View.GONE);
        }
        tv_allShow.setTag(dataModel);
        tv_contnet.setTag(dataModel);
    }

    /**
     * 数据模型。
     */
    public static final class DataModel {
        /**
         * 是否是显示全部
         */
        private boolean isShowAll;
        /**
         * 文字是否超出范围
         */
        private Boolean hasEllipsis;
        private String text;


        public DataModel(Boolean hasEllipsis, String text) {
            this.hasEllipsis = hasEllipsis;
            this.text = text;
        }


    }
}

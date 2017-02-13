package org.gaochun.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;


import org.gaochun.adapter.DemoAdapter;
import org.gaochun.model.DemoBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckActivity extends Activity implements OnClickListener, OnItemClickListener {

     //返回按钮
    private ViewGroup btnCancle = null;

     //确定按钮
    private ViewGroup btnAdd = null;

     //选择所有
    private Button btnSelectAll = null;

     //清除所有
    private Button btnDelete = null;

    private ListView lvListView = null;
    private DemoAdapter adpAdapter = null;

    List<DemoBean> mTempList;   //选中的数据
    StringBuffer buffer = new StringBuffer();//考虑到线程安全，此处使用StringBuffer
    boolean first = true;       //判断，号的添加

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_check);

        handleIntent();

        // 初始化视图
        initView();

        // 初始化控件
        initData();

    }

    private void handleIntent() {
        mTempList = new ArrayList<>();

        final Intent intent = getIntent();
        final String learnString = intent.getStringExtra("v");
        if (!TextUtils.isEmpty(learnString)) {
            final String[] learns = learnString.split(",");
            for (String tag : learns) {
                DemoBean bean = new DemoBean();
                bean.setTitle(tag);
                mTempList.add(bean);
            }

        } // else ignored.
    }

    /**
     * 初始化控件
     */
    private void initView() {

        btnCancle = (ViewGroup) findViewById(R.id.btnCancle);
        btnCancle.setOnClickListener(this);

        btnAdd = (ViewGroup) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);

        btnSelectAll = (Button) findViewById(R.id.btnSelectAll);
        btnSelectAll.setOnClickListener(this);

        lvListView = (ListView) findViewById(R.id.lvListView);
        lvListView.setOnItemClickListener(this);

    }

    /**
     * 初始化视图
     */
    private void initData() {

        // 模拟假数据
        List<DemoBean> demoDatas = new ArrayList<DemoBean>();

        demoDatas.add(new DemoBean("张三", true));
        demoDatas.add(new DemoBean("李四", true));
        demoDatas.add(new DemoBean("李八", true));
        demoDatas.add(new DemoBean("李的", true));
        demoDatas.add(new DemoBean("李等", true));
        demoDatas.add(new DemoBean("王五", false));
        demoDatas.add(new DemoBean("赵六", true));
        demoDatas.add(new DemoBean("麻七", true));
        demoDatas.add(new DemoBean("王八", true));
        demoDatas.add(new DemoBean("王亿", true));
        demoDatas.add(new DemoBean("王二", true));
        demoDatas.add(new DemoBean("王三", true));
        demoDatas.add(new DemoBean("赵九", true));
        demoDatas.add(new DemoBean("赵九", true));

        adpAdapter = new DemoAdapter(this, demoDatas, mTempList);

        lvListView.setAdapter(adpAdapter);

    }


    /**
     * 按钮点击事件
     */
    @Override
    public void onClick(View v) {

        /*
         * 当点击返回的时候
         */
        if (v == btnCancle) {
            //finish();

            Map<Integer, Boolean> map = adpAdapter.getCheckMap();
            int count = adpAdapter.getCount();
            for (int i = 0; i < count; i++) {

                // 因为List的特性,删除了2个item,则3变成2,所以这里要进行这样的换算,才能拿到删除后真正的position
                int position = i - (count - adpAdapter.getCount());
                if (map.get(i) != null && map.get(i)) {

                    DemoBean bean = (DemoBean) adpAdapter.getItem(position);
                    Log.i("tag---------------",bean.getTitle());

                    if (first) {
                        first = false;
                    } else {
                        buffer.append(",");
                    }
                    buffer.append(bean.getTitle());
                }
            }

            final Intent intent = new Intent();
            intent.putExtra("more", buffer.toString());
            setResult(RESULT_OK, intent);
            finish();
        }

        /*
         * 当点击增加的时候
         */
        if (v == btnAdd) {
            adpAdapter.add(new DemoBean("赵六", true));
            adpAdapter.notifyDataSetChanged();
        }

        /*
         * 当点击删除的时候
         */
        if (v == btnDelete) {
            /*
             * 删除算法最复杂,拿到checkBox选择寄存map
             */
            Map<Integer, Boolean> map = adpAdapter.getCheckMap();
            // 获取当前的数据数量
            int count = adpAdapter.getCount();

            // 进行遍历
            for (int i = 0; i < count; i++) {

                // 因为List的特性,删除了2个item,则3变成2,所以这里要进行这样的换算,才能拿到删除后真正的position
                int position = i - (count - adpAdapter.getCount());
                if (map.get(i) != null && map.get(i)) {

                    DemoBean bean = (DemoBean) adpAdapter.getItem(position);

                    if (bean.isCanRemove()) {
                        adpAdapter.getCheckMap().remove(i);
                        adpAdapter.remove(position);
                    } else {
                        map.put(position, false);
                    }

                }
            }
            adpAdapter.notifyDataSetChanged();

        }

        /*
         * 当点击全选的时候
         */
        if (v == btnSelectAll) {

            if (btnSelectAll.getText().toString().trim().equals("全选")) {

                // 所有项目全部选中
                adpAdapter.configCheckMap(true);

                adpAdapter.notifyDataSetChanged();

                btnSelectAll.setText("全不选");
            } else {

                // 所有项目全部不选中
                adpAdapter.configCheckMap(false);

                adpAdapter.notifyDataSetChanged();

                btnSelectAll.setText("全选");
            }

        }
    }

    /**
     * 当ListView 子项点击的时候
     */
    @Override
    public void onItemClick(AdapterView<?> listView, View itemLayout,
                            int position, long id) {

        if (itemLayout.getTag() instanceof DemoAdapter.ViewHolder) {

            DemoAdapter.ViewHolder holder = (DemoAdapter.ViewHolder) itemLayout.getTag();

            // 会自动出发CheckBox的checked事件
            holder.cbCheck.toggle();
            adpAdapter.label = false;

        }
    }
}

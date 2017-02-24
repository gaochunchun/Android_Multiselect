package org.gaochun.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import org.gaochun.adapter.ContactsAdapter;
import org.gaochun.model.Contact;
import org.gaochun.model.SideBarView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * ===========================================
 * 作    者：gao_chun
 * 版    本：1.0
 * 创建日期：2016-11-21.
 * 描    述：测试通讯录界面
 * ===========================================
 */
public class CheckRecyclerViewActivity extends Activity implements View.OnClickListener{

    private RecyclerView rvContacts;
    private SideBarView sideBar;

    private ArrayList<Contact> contacts = new ArrayList<>();

    ContactsAdapter adpAdapter;


    List<Contact> mTempList;   //选中的数据
    StringBuffer buffer = new StringBuffer();//考虑到线程安全，此处使用StringBuffer
    boolean first = true;       //判断，号的添加

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("联系人");
        handleIntent();

        initData();
        initView();
    }

    private void handleIntent() {
        mTempList = new ArrayList<>();

        final Intent intent = getIntent();
        final String learnString = intent.getStringExtra("recyclerview");
        if (!TextUtils.isEmpty(learnString)) {
            final String[] learns = learnString.split(",");
            for (String tag : learns) {
                Contact bean = new Contact();
                bean.setName(tag);
                mTempList.add(bean);
                //mTagSet.add(tag);
            }

        } // else ignored.
    }

    private void initView() {
        setContentView(R.layout.test_activity_contacts);
        rvContacts = (RecyclerView) findViewById(R.id.rv_contacts);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));

        //adapter初始化
        adpAdapter = new ContactsAdapter(contacts, mTempList);
        rvContacts.setAdapter(adpAdapter);

        /*rvContacts.setOnClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/

        sideBar = (SideBarView) findViewById(R.id.side_bar);
        sideBar.setOnSelectIndexItemListener(new SideBarView.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String index) {
                for (int i = 0; i < contacts.size(); i++) {
                    if (contacts.get(i).getIndex().equals(index)) {
                        ((LinearLayoutManager) rvContacts.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }
        });
    }

    private void initData() {
        contacts.addAll(Contact.getChineseContacts());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                Map<Integer, Boolean> map = adpAdapter.getCheckMap();
                int count = adpAdapter.getItemCount();
                //int count = adpAdapter.getCount();
                for (int i = 0; i < count; i++) {

                    // 因为List的特性,删除了2个item,则3变成2,所以这里要进行这样的换算,才能拿到删除后真正的position
                    int position = i - (count - adpAdapter.getItemCount());
                    if (map.get(i) != null && map.get(i)) {

                        //DemoBean bean = (DemoBean) adpAdapter.getItem(position);
                        Contact bean = adpAdapter.getItemData(position);
                        Log.i("tag---------------",bean.getName());
                        if (first) {
                            first = false;
                        } else {
                            buffer.append(",");
                        }
                        buffer.append(bean.getName());
                    }
                }

                final Intent intent = new Intent();
                intent.putExtra("rv", buffer.toString());
                setResult(RESULT_OK, intent);
                finish();
                break;

            case R.id.btndel:

                break;
        }

    }
}

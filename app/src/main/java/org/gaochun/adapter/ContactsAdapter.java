package org.gaochun.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.gaochun.activity.R;
import org.gaochun.model.Contact;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ===========================================
 * 作    者：gao_chun
 * 版    本：1.0
 * 创建日期：2016-11-19.
 * 描    述：测试 联系人Adapter
 * ===========================================
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    public boolean label = true;    //该标记用于处理选中后再次进入时选中状态
    private List<Contact> contacts;
    private List<Contact> mTempList = null;
    private Map<Integer, Boolean> isCheckMap = new HashMap<Integer, Boolean>();


    public ContactsAdapter(List<Contact> contacts,  List<Contact> mTempList) {
        this.contacts = contacts;
        this.mTempList = mTempList;
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.test_item_contacts, null);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        if (position == 0 || !contacts.get(position-1).getIndex().equals(contact.getIndex())) {
            holder.tvIndex.setVisibility(View.VISIBLE);
            holder.tvIndex.setText(contact.getIndex());
        } else {
            holder.tvIndex.setVisibility(View.GONE);
        }
        holder.tvName.setText(contact.getName());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder {
        public TextView tvIndex;
        public ImageView ivAvatar;
        public TextView tvName;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            tvIndex = (TextView) itemView.findViewById(R.id.tv_index);
            ivAvatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
    /*@Override
    protected void convert(BaseViewHolder helper, Contact contact) {

        final int position = helper.getAdapterPosition();
        if (position == 0 || !contacts.get(position - 1).getIndex().equals(contact.getIndex())) {
            helper.setVisible(R.id.tv_index, true);
            helper.setText(R.id.tv_index, contact.getIndex());
        } else {
            helper.setVisible(R.id.tv_index, false);
        }

        //设置每一个item的文本
        TextView tvTitle = helper.getView(R.id.tv_name);
        tvTitle.setText(contact.getName());

        //获得该item 是否允许删除
        //boolean canRemove = bean.isCanRemove();

        CheckBox cbCheck = helper.getView(R.id.cbCheckBox);

        *//*
         * 设置单选按钮的选中
         *//*
        cbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //将选择项加载到map里面寄存
                isCheckMap.put(position, isChecked);
            }
        });


        *//*if (!canRemove) {
            // 隐藏单选按钮,因为是不可删除的
            cbCheck.setVisibility(View.GONE);
            cbCheck.setChecked(false);
        } else {
            cbCheck.setVisibility(View.VISIBLE);*//*

        if (isCheckMap.get(position) == null) {
            isCheckMap.put(position, false);
        }

        //保存Tag
        ViewHolder holder = new ViewHolder();
        holder.cbCheck = cbCheck;
        holder.tvTitle = tvTitle;
        helper.getConvertView().setTag(holder);

        if (label){
            if (mTempList.size() > 0) {
                for (int i = 0; i < mTempList.size(); i++) {
                    if (contact.getName().contains(mTempList.get(i).getName())) {
                        isCheckMap.put(position, true);
                    }
                }
            }
        }
        cbCheck.setChecked(isCheckMap.get(position));

    }


    *//**
     * 首先,默认情况下,所有项目都是没有选中的.这里进行初始化
     *//*
    public void configCheckMap(boolean bool) {
        for (int i = 0; i < contacts.size(); i++) {
            isCheckMap.put(i, bool);
        }
    }

    // 移除一个项目的时候
    public void remove(int position) {
        this.contacts.remove(position);
    }


    public Map<Integer, Boolean> getCheckMap() {
        return this.isCheckMap;
    }

    public static class ViewHolder {
        public TextView tvTitle = null;
        public CheckBox cbCheck = null;
    }*/
}
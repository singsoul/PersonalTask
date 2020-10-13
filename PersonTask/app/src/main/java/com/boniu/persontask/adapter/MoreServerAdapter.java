package com.boniu.persontask.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.boniu.persontask.bean.PersonalInfo;
import com.boniu.persontask.R;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MoreServerAdapter extends BaseQuickAdapter<PersonalInfo.ResultBean.PersonalServiceVosBean, BaseViewHolder> {

    public MoreServerAdapter( @Nullable List<PersonalInfo.ResultBean.PersonalServiceVosBean> data) {
        super(R.layout.item_more_server, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonalInfo.ResultBean.PersonalServiceVosBean item) {
        ImageView ivImage = helper.getView(R.id.iv_image);
        Glide.with(mContext).load(item.getLogo()).into(ivImage);
        helper.setText(R.id.tv_title,item.getTitle()+"");
    }
}

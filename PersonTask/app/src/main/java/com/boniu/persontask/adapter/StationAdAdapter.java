package com.boniu.persontask.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.boniu.persontask.PersonalInfo;
import com.boniu.persontask.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.security.MessageDigest;
import java.util.List;

public class StationAdAdapter extends BaseQuickAdapter<PersonalInfo.ResultBean.PersonalFreeVosBean.PersonalAreaVosBean, BaseViewHolder> {

    public StationAdAdapter(@Nullable List<PersonalInfo.ResultBean.PersonalFreeVosBean.PersonalAreaVosBean> data) {
        super(R.layout.item_station, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonalInfo.ResultBean.PersonalFreeVosBean.PersonalAreaVosBean item) {
        RequestOptions myOptions = new RequestOptions()
                .centerCrop()
                .transform(new GlideRoundTransform(mContext, 2));
        if (!TextUtils.isEmpty(item.getTitle())) {
            helper.setGone(R.id.l1,true);
            helper.setText(R.id.tv_name,item.getTitle()+"");
            ImageView ivImage = helper.getView(R.id.iv_image);
            Glide.with(mContext).load(item.getLogo()).into(ivImage);
        } else {
            helper.setGone(R.id.l1,false);
            ImageView ivAd = helper.getView(R.id.ivAd);
            Glide.with(mContext).load(item.getLogo()).apply(myOptions).into(ivAd);
        }

    }

    /**
     * 图片转换圆角图片
     */
    public class GlideRoundTransform extends BitmapTransformation {

        private float radius = 0f;

        public GlideRoundTransform(Context context) {
            this(context, 4);
        }

        /**
         * 自定义圆角大小
         *
         * @param context
         * @param dp
         */
        public GlideRoundTransform(Context context, int dp) {
            this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return roundCrop(pool, toTransform);
        }

        private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

        }
    }
}

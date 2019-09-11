package com.kiwi.phonelive.activity;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.kiwi.phonelive.AppConfig;
import com.kiwi.phonelive.R;
import com.kiwi.phonelive.glide.ImgLoader;
import com.kiwi.phonelive.http.HttpCallback;
import com.kiwi.phonelive.http.HttpUtil;
import com.kiwi.phonelive.interfaces.ImageResultCallback;
import com.kiwi.phonelive.utils.DialogUitl;
import com.kiwi.phonelive.utils.ProcessImageUtil;
import com.kiwi.phonelive.utils.ToastUtil;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyCoinUploadActivity extends AbsActivity implements View.OnClickListener {
    private ProcessImageUtil mImageUtil;
    private ImageView ivback;
    private ImageView ivadd;
    private EditText etmessage;
    private TextView tvupload;
    private File files;
    private double money;
    private int totalCoin;
    private TextView tvAccount;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mycoin_upload;
    }

    @Override
    protected void main() {
        ivback = findViewById(R.id.btn_back);
        ivadd = findViewById(R.id.iv_upload_add);
        etmessage = findViewById(R.id.et_upload_message);
        tvupload = findViewById(R.id.tv_upload);
        tvAccount = findViewById(R.id.tv_account);
        ivback.setOnClickListener(this);
        ivadd.setOnClickListener(this);
        tvupload.setOnClickListener(this);
        tvAccount.setText(AppConfig.getInstance().getUid()+"");
        etmessage.setFilters(new InputFilter[]{inputFilter, new InputFilter.LengthFilter(200)});
        etmessage.setFilters(new InputFilter[]{inputFilter, new InputFilter.LengthFilter(12)});
        etmessage.setFilters(new InputFilter[]{inputFilter, new InputFilter.LengthFilter(14)});
        mImageUtil = new ProcessImageUtil(this);
        initIntent();
        mImageUtil.setImageResultCallback(new ImageResultCallback() {
            @Override
            public void beforeCamera() {

            }

            @Override
            public void onSuccess(File file) {
                if (file != null) {
                    ImgLoader.display(file, ivadd);
                    files = file;
                }
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void initIntent() {
        int coin = getIntent().getIntExtra("coin", -1);
        int give = getIntent().getIntExtra("give", -1);
        money = getIntent().getDoubleExtra("money", -1);
        totalCoin = coin + give;
    }


    private InputFilter inputFilter = new InputFilter() {
        //        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_]"); //连符号和空格都屏蔽
        Pattern pattern = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\ud83e\udc00-\ud83e\udfff]" +
                "|[\u2100-\u32ff]|[\u0030-\u007f][\u20d0-\u20ff]|[\u0080-\u00ff]");

        @Override
        public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
            Matcher matcher = pattern.matcher(charSequence);
            if (!matcher.find()) {
                return null;
            } else {
                ToastUtil.show(R.string.no_enmoji);
                return "";
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back: //返回
                finish();
                break;
            case R.id.iv_upload_add: //添加凭证
                editAvatar();
                break;
            case R.id.tv_upload: //上传
                if (files != null && !files.getPath().equals("")) {
                    HttpUtil.uploadRecord(TextUtils.isEmpty(etmessage.getText()) ? "" : etmessage.getText().toString(), files, money + "", totalCoin + "", new HttpCallback() {
                        @Override
                        public void onSuccess(int code, String msg, String[] info) {
                            ToastUtil.show(R.string.upload_success);
                            finish();
                        }
                    });
                } else {
                    ToastUtil.show(R.string.select_photo);
                }
                break;
        }
    }

    private void editAvatar() {
        DialogUitl.showStringArrayDialog(mContext, new Integer[]{
                R.string.camera, R.string.alumb}, new DialogUitl.StringArrayDialogCallback() {
            @Override
            public void onItemClick(String text, int tag) {
                if (tag == R.string.camera) {
                    mImageUtil.getImageByCamera();
                } else {
                    mImageUtil.getImageByAlumb();
                }
            }
        });
    }
}

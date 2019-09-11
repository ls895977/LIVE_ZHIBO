package com.kiwi.phonelive.activity;

import android.view.View;
import android.widget.RadioButton;

import com.kiwi.phonelive.R;
import com.kiwi.phonelive.http.HttpCallback;
import com.kiwi.phonelive.http.HttpUtil;
import com.kiwi.phonelive.utils.LocalManageUtil;
import com.kiwi.phonelive.utils.ShpreUtil;
import com.kiwi.phonelive.utils.SpUtil;
import com.kiwi.phonelive.utils.WordUtil;


public class SelectLanguageActivity extends AbsActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_language;
    }

    @Override
    protected void main() {
        setTitle(WordUtil.getString(R.string.select_language));
        RadioButton rb_ch = findViewById(R.id.rb_chinese);
        RadioButton rb_en = findViewById(R.id.rb_english);
        final String selectLanguage = LocalManageUtil.getSelectLanguage(mContext);
        if (selectLanguage.equals("系统语言") || selectLanguage.equals("中文")) {
            rb_ch.setChecked(true);
        } else {
            rb_en.setChecked(true);
        }
        rb_ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLanguage("zh-Hans-US");
            }
        });

        rb_en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLanguage("en-US");
            }
        });
    }

    private void selectLanguage(int select) {
        LocalManageUtil.saveSelectLanguage(this, select);

        MainActivity.reStart(this);
    }

    private void setLanguage(final String l) {
        HttpUtil.setLanguage(l, new HttpCallback() {
            @Override
            public void onSuccess(int code, String msg, String[] info) {
                if (code == 0) {
                    if (l.contains("zh")) {
                        selectLanguage(1);
                    } else {
                        selectLanguage(3);
                    }
                }
            }
        });
    }
}

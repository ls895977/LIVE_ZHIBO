package com.kiwi.phonelive.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.kiwi.phonelive.R;


/**
 * ========================================
 * <p/>
 * 版 权：江苏精易达信息技术股份有限公司 版权所有 （C） 2018
 * <p/>
 * 作 者：liyunte
 * <p/>
 * <p/>
 * 版 本：1.0
 * <p/>
 * 创建日期： 2018/4/28 16:21
 * <p/>
 * 描 述：进度对话框
 * <p/>
 *   LoadDialog dialog = new LoadDialog(this);
 *   dialog.show();
 *   dialog.hide();
 *
 *   onDestroy(){
 *       if(dialog!=null){
 *           dialog.dismiss();
 *       }
 *   }
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */

public class LoadDialog extends ProgressDialog {

    public LoadDialog(Context context)
    {
        this(context,R.style.CustomDialog);
    }

    public LoadDialog(Context context, int theme)
    {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        init(getContext());
    }
    private void init(Context context)
    {
        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
//        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.layout_loading);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    @Override
    public void show()
    {
        super.show();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}

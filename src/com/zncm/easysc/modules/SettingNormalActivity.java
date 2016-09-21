package com.zncm.easysc.modules;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TableRow;

import com.umeng.analytics.MobclickAgent;
import com.zncm.easysc.R;
import com.zncm.easysc.utils.XUtil;
import com.zncm.utils.ViewUtils;
import com.zncm.utils.sp.StatedPerference;


public class SettingNormalActivity extends BaseHomeActivity implements OnClickListener {
    private TableRow tableRowFont;
    private TableRow tableRowComment;
    private CheckBox cbCommentOpen;
    private String fontType = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_normal);
        actionBar.setTitle("常规");
        initViews();
    }

    // umeng

    private void initViews() {
        tableRowFont = (TableRow) findViewById(R.id.tablerow_font);
        tableRowFont.setOnClickListener(this);
        initFontType();


        cbCommentOpen = (CheckBox) findViewById(R.id.cbCommentOpen);
        tableRowComment = (TableRow) findViewById(R.id.tablerow_commentopen);
        tableRowComment.setOnClickListener(this);
        cbCommentOpen.setChecked(StatedPerference.getCommentOpen());
        cbCommentOpen.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    StatedPerference.setCommentOpen(true);
                } else {
                    StatedPerference.setCommentOpen(false);
                }
            }
        });
    }


    private void initFontType() {

        if (StatedPerference.getFontSize() == 15f) {
            fontType = "小";
        } else if (StatedPerference.getFontSize() == 20f) {
            fontType = "中";
        } else if (StatedPerference.getFontSize() == 25f) {
            fontType = "大";
        }
        ViewUtils.setTextView(this, R.id.tvFontType, fontType
        );

    }


    @Override
    protected void onResume() {

        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {

        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tablerow_font:
                DialogFragment newFragment = new XUtil.FontSizeAlertDialogFragment(R.drawable.font_size, "字体大小") {

                    @Override
                    protected void doClick() {
                        initFontType();
                    }
                };
                newFragment.show(getSupportFragmentManager(), "dialog");
                break;

            default:
                break;
            case R.id.tablerow_commentopen:
                if (StatedPerference.getCommentOpen()) {
                    cbCommentOpen.setChecked(false);
                    StatedPerference.setCommentOpen(false);
                } else {
                    cbCommentOpen.setChecked(true);
                    StatedPerference.setCommentOpen(true);
                }
                break;
        }
    }


}

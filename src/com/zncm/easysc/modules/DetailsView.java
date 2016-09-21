package com.zncm.easysc.modules;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zncm.easysc.R;
import com.zncm.utils.sp.StatedPerference;

public class DetailsView extends RelativeLayout {
    private LayoutInflater mInflater;
    private LinearLayout llView;
    private LinearLayout llDetails;
    private RelativeLayout rlHide;
    private TextView tvPage;
    private TextView tvAuthor;
    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvComment;
    private ImageView ivArrows;
    private boolean bOpen = false;

    public DetailsView(Context context) {
        this(context, null);
    }

    public DetailsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        llView = (LinearLayout) mInflater.inflate(R.layout.view_details, null);
        llView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(llView);
        tvPage = (TextView) llView.findViewById(R.id.tvPage);
        tvAuthor = (TextView) llView.findViewById(R.id.tvAuthor);
        tvTitle = (TextView) llView.findViewById(R.id.tvTitle);
        tvContent = (TextView) llView.findViewById(R.id.tvContent);
        tvComment = (TextView) llView.findViewById(R.id.tvComment);
        ivArrows = (ImageView) llView.findViewById(R.id.ivArrows);
        llDetails = (LinearLayout) llView.findViewById(R.id.llDetails);
        rlHide = (RelativeLayout) llView.findViewById(R.id.rlHide);

        if (StatedPerference.getCommentOpen()) {
            bOpen = true;
            ivArrows.setImageResource(R.drawable.iv_arrow_up);
            llDetails.setVisibility(View.VISIBLE);
        } else {
            bOpen = false;
            ivArrows.setImageResource(R.drawable.iv_arrow_down);
            llDetails.setVisibility(View.GONE);
        }


        rlHide.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bOpen) {
                    bOpen = false;
                    ivArrows.setImageResource(R.drawable.iv_arrow_down);
                    llDetails.setVisibility(View.GONE);
                } else {
                    bOpen = true;
                    ivArrows.setImageResource(R.drawable.iv_arrow_up);
                    llDetails.setVisibility(View.VISIBLE);
                }
            }
        });

        tvTitle.setTextSize(StatedPerference.getFontSize() + 5);
        tvContent.setTextSize(StatedPerference.getFontSize());
        tvPage.setTextSize(StatedPerference.getFontSize() - 5);
        tvAuthor.setTextSize(StatedPerference.getFontSize() - 3);
        tvComment.setTextSize(StatedPerference.getFontSize() - 4);

    }

    public TextView getTvComment() {
        return tvComment;
    }

    public TextView getTvPage() {
        return tvPage;
    }

    public TextView getTvAuthor() {
        return tvAuthor;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public TextView getTvContent() {
        return tvContent;
    }
}

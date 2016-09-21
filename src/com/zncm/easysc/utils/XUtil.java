package com.zncm.easysc.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableRow;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.zncm.easysc.R;
import com.zncm.utils.sp.StatedPerference;

public class XUtil {

    public abstract static class TwoAlertDialogFragment extends SherlockDialogFragment {
        public int icon;
        public String title;
        public String positive;
        public String negative;

        public TwoAlertDialogFragment(String title) {
            this.icon = R.drawable.info;
            this.title = title;
            this.positive = "确定";
            this.negative = "取消";
        }

        public TwoAlertDialogFragment(int icon, String title) {
            this.icon = icon;
            this.title = title;
            this.positive = "确定";
            this.negative = "取消";
        }

        public TwoAlertDialogFragment(int icon, String title, String positive, String negative) {
            this.icon = icon;
            this.title = title;
            this.positive = positive;
            this.negative = negative;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            return new AlertDialog.Builder(getActivity()).setIcon(icon).setTitle(title)
                    .setPositiveButton(positive, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            doPositiveClick();
                        }
                    }).setNegativeButton(negative, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            doNegativeClick();
                        }
                    }).create();
        }

        public abstract void doPositiveClick();

        public abstract void doNegativeClick();

    }


    public abstract static class FontSizeAlertDialogFragment extends SherlockDialogFragment {
        public int icon;
        public String title;
        public String content;

        public FontSizeAlertDialogFragment(String title) {
            this.icon = R.drawable.info;
            this.title = title;
        }

        public FontSizeAlertDialogFragment(int icon, String title) {
            this.icon = icon;
            this.title = title;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.view_font_size, null);
            final TableRow big = (TableRow) view.findViewById(R.id.tablerow_font_big);
            final TableRow middle = (TableRow) view.findViewById(R.id.tablerow_font_middle);
            final TableRow small = (TableRow) view.findViewById(R.id.tablerow_font_small);

            big.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatedPerference.setFontSize(25f);
                    dismiss();
                    doClick();
                }
            });
            middle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatedPerference.setFontSize(20f);
                    dismiss();
                    doClick();
                }
            });
            small.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatedPerference.setFontSize(15f);
                    dismiss();
                    doClick();
                }
            });

            return new AlertDialog.Builder(getActivity()).setIcon(icon).setTitle(title).setView(view).create();

        }

        protected abstract void doClick();


    }


}

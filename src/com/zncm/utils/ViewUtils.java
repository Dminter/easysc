package com.zncm.utils;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

//布局文件工具类
public class ViewUtils {

	/**
	 * 
	 * @Title: bindClickListenerOnViews
	 * @param activity
	 * @param onClickListen
	 * @param ids
	 *            void
	 * @throws
	 */
	public static void bindClickListenerOnViews(Activity activity, OnClickListener onClickListen, int... ids) {
		int i = ids.length;
		for (int j = 0;; ++j) {
			if (j >= i)
				return;
			View view = activity.findViewById(ids[j]);
			if (view == null)
				break;
			view.setOnClickListener(onClickListen);
		}

	}

	public static void bindClickListenerOnViews(FragmentActivity activity, OnClickListener onClickListen, int... ids) {
		int i = ids.length;
		for (int j = 0;; ++j) {
			if (j >= i)
				return;
			View view = activity.findViewById(ids[j]);
			if (view == null)
				break;
			view.setOnClickListener(onClickListen);
		}

	}

	/**
	 * 
	 * @Description
	 * @param root
	 * @param onClickListen
	 * @param ids
	 * 
	 */
	public static void bindClickListenerOnViews(View root, OnClickListener onClickListen, int... ids) {
		int i = ids.length;
		for (int j = 0;; ++j) {
			if (j >= i)
				return;
			View view = root.findViewById(ids[j]);
			if (view == null)
				break;
			view.setOnClickListener(onClickListen);
		}

	}

	/**
	 * 
	 * @Title: bindClickListenerOnViews
	 * @param onClickListen
	 * @param views
	 *            void
	 * @throws
	 */
	public static void bindClickListenerOnViews(OnClickListener onClickListen, View... views) {
		int i = views.length;
		for (int j = 0;; ++j) {
			if (j >= i)
				return;
			View view = views[j];
			if (view == null)
				break;
			view.setOnClickListener(onClickListen);
		}

	}

	/**
	 * 
	 * @Title: clearTextViews
	 * @param tvs
	 *            void
	 * @throws
	 */
	public static void clearTextViews(TextView... tvs) {
		int i = tvs.length;
		for (int j = 0;; ++j) {
			if (j >= i)
				return;
			tvs[j].setText("");
		}
	}

	/**
	 * 
	 * @Title: hideView
	 * @param view
	 *            void
	 * @throws
	 */
	public static void hideView(View view) {
		if (view != null) {
			view.setVisibility(View.GONE);
			return;
		}
	}

	/**
	 * 
	 * @Title: hideViews
	 * @param activity
	 * @param ids
	 *            void
	 * @throws
	 */
	public static void hideViews(Activity activity, int... ids) {
		int i = ids.length;
		for (int j = 0;; ++j) {
			if (j >= i)
				return;
			View view = activity.findViewById(ids[j]);
			if (view == null)
				break;
			view.setVisibility(View.GONE);
		}
	}

	/**
	 * 
	 * @Title: hideViews
	 * @param views
	 *            void
	 * @throws
	 */
	public static void hideViews(View... views) {
		int i = views.length;
		for (int j = 0;; ++j) {
			if (j >= i)
				return;
			View localView = views[j];
			if (localView == null)
				break;
			localView.setVisibility(View.GONE);
		}
	}

	/**
	 * 
	 * @Title: setDrawable
	 * @param iv
	 * @param drawable
	 *            void
	 * @throws
	 */
	public static void setDrawable(ImageView iv, Drawable drawable) {
		if ((iv == null) || (drawable == null))
			return;
		iv.setImageDrawable(drawable);
	}

	public static void setViewBackground(Integer color, Activity activity, int... ids) {
		int i = ids.length;
		for (int j = 0;; ++j) {
			if (j >= i)
				return;
			View view = activity.findViewById(ids[j]);
			if (view == null)
				break;
			view.setBackgroundColor(color);
		}

	}

	public static void setViewFontSize(Integer fontSize, Activity activity, int... ids) {
		int i = ids.length;
		for (int j = 0;; ++j) {
			if (j >= i)
				return;

			View view = activity.findViewById(ids[j]);
			if (view == null)
				break;
			if (view instanceof TextView) {
				((TextView) view).setTextSize(fontSize);
			} else if (view instanceof EditText) {
				((EditText) view).setTextSize(fontSize);
			}

		}

	}

	/**
	 * 
	 * @Title: setViewInvisible
	 * @param view
	 *            void
	 * @throws
	 */
	public static void setViewInvisible(View view) {
		if (view != null) {
			view.setVisibility(View.INVISIBLE);
			return;
		}

	}

	/**
	 * 
	 * @Title: showView
	 * @param view
	 *            void
	 * @throws
	 */
	public static void showView(View view) {
		if (view != null) {
			view.setVisibility(View.VISIBLE);
			return;
		}

	}

	/**
	 * 
	 * @Title: showViews
	 * @param activity
	 * @param ids
	 *            void
	 * @throws
	 */
	public static void showViews(Activity activity, int... ids) {
		int i = ids.length;
		for (int j = 0;; ++j) {
			if (j >= i)
				return;
			View view = activity.findViewById(ids[j]);
			if (view == null)
				break;
			view.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * 
	 * @Title: showViews
	 * @param views
	 *            void
	 * @throws
	 */
	public static void showViews(View... views) {
		int i = views.length;
		for (int j = 0;; ++j) {
			if (j >= i)
				return;
			View localView = views[j];
			if (localView == null)
				break;
			localView.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * 
	 * @Description
	 * @param activity
	 * @param id
	 * @param text
	 * 
	 */
	public static void setTextView(Activity activity, int id, String text) {
		if (activity != null) {
			TextView tv = (TextView) activity.findViewById(id);
			setTextView(tv, text);
		}
	}

	/**
	 * 
	 * @Description
	 * @param rootView
	 * @param id
	 * @param text
	 * 
	 */
	public static void setTextView(View rootView, int id, String text) {
		if (rootView != null) {
			TextView tv = (TextView) rootView.findViewById(id);
			setTextView(tv, text);
		}
	}

	/**
	 * 
	 * @Description
	 * @param tv
	 * @param text
	 * 
	 */
	public static void setTextView(TextView tv, String text) {
		if (tv != null && text != null) {
			tv.setText(text);
		}
	}

	/**
	 * 
	 * @Description
	 * @param activity
	 * @param id
	 * @param text
	 * 
	 */
	public static String getTextView(Activity activity, int id) {
		if (activity != null) {
			TextView tv = (TextView) activity.findViewById(id);
			return getTextView(tv);
		} else {
			return "";
		}
	}

	/**
	 * 
	 * @Description
	 * @param activity
	 * @param id
	 * @param text
	 * 
	 */
	public static String getTextView(View rootView, int id) {
		if (rootView != null) {
			TextView tv = (TextView) rootView.findViewById(id);
			return getTextView(tv);
		} else {
			return "";
		}
	}

	/**
	 * 
	 * @Description
	 * @param tv
	 * @param text
	 * 
	 */
	public static String getTextView(TextView tv) {
		if (tv != null) {
			return tv.getText().toString();
		} else {
			return "";
		}
	}

}
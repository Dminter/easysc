package com.zncm.easysc.modules;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SearchViewCompat;
import android.support.v4.widget.SearchViewCompat.OnQueryTextListenerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.zncm.component.pullrefresh.PullToRefreshBase;
import com.zncm.easysc.R;
import com.zncm.easysc.data.base.ScData;
import com.zncm.easysc.global.GlobalConstants;
import com.zncm.easysc.modules.adapter.ScAdapter;
import com.zncm.easysc.utils.XUtil;
import com.zncm.utils.L;
import com.zncm.utils.StrUtil;
import com.zncm.utils.exception.CheckedExceptionHandler;

import java.util.ArrayList;
import java.util.List;

public class SearchF extends BaseFragment {
    private Activity mActivity;
    public List<ScData> datas = null;
    private int curPosition;
    private RuntimeExceptionDao<ScData, Integer> dao = null;
    private boolean bSearch = false;
    private ScAdapter mAdapter;
    private boolean bZiCount = false;
    private int ziCount;
    private boolean bSolitaire = false;
    private String solitaireEnd;
    private String scType = "唐诗";
    private String authors[];
    private String author;
    private boolean bAuthor = false;
    private boolean bType = false;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = menu.add("search");
        item.setIcon(android.R.drawable.ic_menu_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        SubMenu sub = menu.addSubMenu("分类");
        sub.setIcon(R.drawable.category);
        sub.add(0, 1, 0, "唐诗-五言乐府");
        sub.add(0, 2, 0, "唐诗-五言律诗");
        sub.add(0, 3, 0, "唐诗-五言绝句");
        sub.add(0, 4, 0, "唐诗-五言古诗");
        sub.add(0, 5, 0, "唐诗-七言乐府");
        sub.add(0, 6, 0, "唐诗-七言律诗");
        sub.add(0, 6, 0, "唐诗-七言绝句");
        sub.add(0, 7, 0, "唐诗-七言古诗");
        sub.add(0, 8, 0, "宋词");
        sub.add(0, 9, 0, "元曲");
        sub.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        if (authors == null) {
            authors = getResources().getStringArray(
                    R.array.author_items);
        }
        SubMenu subAuthor = menu.addSubMenu("诗人");
        subAuthor.setIcon(R.drawable.author);
        for (int i = 0; i < authors.length; i++) {
            subAuthor.add(0, 100 + i, 0, authors[i]);
        }
        subAuthor.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        SherlockFragmentActivity activity = (SherlockFragmentActivity) getActivity();
        View searchView = SearchViewCompat.newSearchView(activity.getSupportActionBar().getThemedContext());
        if (searchView != null) {
            SearchViewCompat.setOnQueryTextListener(searchView, new OnQueryTextListenerCompat() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    if (StrUtil.notEmptyOrNull(newText)) {
                        bSearch = true;
                        try {
                            QueryBuilder<ScData, Integer> builder = dao.queryBuilder();
                            builder.where().like("title", "%" + newText.toString().trim() + "%").or().like("author", "%" + newText.toString().trim() + "%").or().like("content", "%" + newText.toString().trim() + "%");
                            List<ScData> datas = dao.query(builder.prepare());
                            SearchF.this.datas = datas;
                            mAdapter.setItems(SearchF.this.datas);
                            loadComplete();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        bSearch = false;
                        getData(true);
                    }

                    return true;
                }
            });
            item.setActionView(searchView);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() > 0 && item.getItemId() <= 9) {
            choodeTypeDo(item.getTitle().toString());
            bType = true;
            bAuthor = false;
        } else if (item.getItemId() >= 100) {
            choodeAuthorDo(item.getTitle().toString());
            bType = false;
            bAuthor = true;
        }

        return false;
    }


    private void choodeTypeDo(String type) {
        scType = type;
        actionBar.setTitle(type.substring(0, 2));
        getData(true);
    }

    private void choodeAuthorDo(String author) {
        this.author = author;
        actionBar.setTitle(author);
        getData(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.mActivity = (Activity) getActivity();
        actionBar.setTitle(scType);
        datas = new ArrayList<ScData>();
        dao = getHelper().getScDataDao();
        mAdapter = new ScAdapter(mActivity) {
            @Override
            public void setData(int position, NoteViewHolder holder) {
                ScData data = (ScData) datas.get(position);
                if (data.getStatus() == 1) {
                    holder.tvTitle.setText(data.getTitle() + "☆");
                } else {
                    holder.tvTitle.setText(data.getTitle());
                }
                holder.tvAuthor.setText(data.getAuthor());
            }
        };
        mAdapter.setItems(datas);
        lvBase.setAdapter(mAdapter);
        plListView.setOnItemClickListener(new OnItemClickListener() {

            @SuppressWarnings({"rawtypes", "unchecked"})
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                curPosition = position - lvBase.getHeaderViewsCount();
                Intent intent = new Intent(mActivity, ScPagerActivity.class);
                intent.putExtra("current", curPosition);
                intent.putExtra("title", actionBar.getTitle());
                ArrayList list = new ArrayList();
                list.add(datas);
                intent.putParcelableArrayListExtra(GlobalConstants.KEY_LIST_DATA, list);
                startActivity(intent);
            }
        });

        lvBase.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                curPosition = position - lvBase.getHeaderViewsCount();
                final ScData data = datas.get(curPosition);

                DialogFragment newFragment = new XUtil.TwoAlertDialogFragment(R.drawable.star, "收藏") {

                    @Override
                    public void doPositiveClick() {
                        if (data != null) {
                            data.setStatus(1);
                            dao.update(data);
                            mAdapter.setItems(datas);
                            loadComplete();
                            L.toastShort("已收藏");
                        }
                    }


                    @Override
                    public void doNegativeClick() {

                    }
                };
                newFragment.show(getSherlockActivity().getSupportFragmentManager(), "dialog");
                return false;
            }
        });

        initListView();
        getData(true);
        authors = getResources().getStringArray(
                R.array.author_items);
        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @SuppressWarnings("deprecation")
    public void initListView() {
        plListView.setPullToRefreshEnabled(false);
        plListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {

            @Override
            public void onLastItemVisible() {
                if (!bSearch) {
                    getData(false);
                } else {
                    loadComplete();
                }
            }
        });
    }

    private void getData(boolean bFirst) {
        GetData getData = new GetData();
        getData.execute(bFirst);
    }

    class GetData extends AsyncTask<Boolean, Integer, Integer> {

        protected Integer doInBackground(Boolean... params) {
            try {
                if (dao == null) {
                    dao = getHelper().getScDataDao();
                }
                QueryBuilder<ScData, Integer> builder = dao.queryBuilder();
                if (bType) {
                    builder.where().like("type", "%" + scType + "%");
                }
                if (bAuthor) {
                    builder.where().like("author", "%" + author + "%");
                }
                builder.orderBy("id", true);
                if (params[0]) {
                    builder.limit(GlobalConstants.DB_PAGE_SIZE);
                } else {
                    builder.limit(GlobalConstants.DB_PAGE_SIZE).offset((long) datas.size());
                }
                List<ScData> list = dao.query(builder.prepare());
                if (params[0]) {
                    datas = new ArrayList<ScData>();
                }
                if (StrUtil.listNotNull(list)) {
                    datas.addAll(list);
                }
            } catch (Exception e) {
                CheckedExceptionHandler.handleException(e);
            }
            return 0;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(Integer ret) {
            super.onPostExecute(ret);
            loadComplete();
            mAdapter.setItems(datas);
        }

    }

}

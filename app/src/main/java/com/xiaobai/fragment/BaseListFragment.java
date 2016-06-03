package com.xiaobai.fragment;

import com.xiaobai.listview.IXViewListener;
import com.xiaobai.listview.XListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BaseListFragment extends BaseFragment implements IXViewListener {
    protected boolean isLoading = false;

    @Override
    public void onRefresh() {
        if(isLoading){
            return;
        }
        isLoading = true;
    }

    @Override
    public void onLoadMore() {
        if(isLoading){
            return;
        }
        isLoading = true;
    }

    public XListView getXList(){
        return null;
    }

    @Override
    public void onPostSuccess(int postId,String result) {
        isLoading = false;

        if(getXList()!=null){
            getXList().stopRefresh();
            getXList().stopLoadMore();
            getXList().setRefreshTime(getTime());
        }

    }

    @Override
    public void onPostFailure(int postId,String msg) {
        isLoading = false;

        if(getXList()!=null){
            getXList().stopRefresh();
            getXList().stopLoadMore();
            getXList().setRefreshTime(getTime());
        }

    }

    protected String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA)
                .format(new Date());
    }

}

package com.scurab.gwt.rlw.client.components;

import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.Timer;
import com.scurab.gwt.rlw.client.interfaces.DownloadFinishListener;

/**
 * Pager implementation for loading on data on demand
 * @author Joe Scurab
 *
 */
public class LazyPager extends SimplePager {

    public interface OnPageLoaderListener {
        public void onLoadPage(int page, DownloadFinishListener c);
    }

    private int mLoadedPages = 1;
    private boolean mHasNextPage = true;

    private OnPageLoaderListener mListener;

    public LazyPager() {
        super();
    }

    public LazyPager(TextLocation location, boolean showFastForwardButton, boolean showLastPageButton) {
        super(location, showFastForwardButton, showLastPageButton);
    }

    public LazyPager(TextLocation location, boolean showFastForwardButton, int fastForwardRows,
            boolean showLastPageButton) {
        super(location, showFastForwardButton, fastForwardRows, showLastPageButton);
    }

    public LazyPager(TextLocation location, Resources resources, boolean showFastForwardButton, int fastForwardRows,
            boolean showLastPageButton, ImageButtonsConstants imageButtonConstants) {
        super(location, resources, showFastForwardButton, fastForwardRows, showLastPageButton, imageButtonConstants);
    }

    public LazyPager(TextLocation location, Resources resources, boolean showFastForwardButton, int fastForwardRows,
            boolean showLastPageButton) {
        super(location, resources, showFastForwardButton, fastForwardRows, showLastPageButton);
    }

    public LazyPager(TextLocation location) {
        super(location);
    }

    @Override
    public int getPage() {
        return super.getPage();
    }

    @Override
    public int getPageCount() {
        return super.getPageCount() + (mHasNextPage ? 1 : 0);
    }

    @Override
    public boolean hasNextPage() {
        return super.getPage() < (mLoadedPages - 1) || mHasNextPage;
    }

    @Override
    public boolean hasNextPages(int pages) {
        return super.hasNextPages(pages);
    }

    @Override
    public boolean hasPage(int index) {
        return super.hasPage(index);
    }

    @Override
    public boolean hasPreviousPage() {
        return super.hasPreviousPage();
    }

    @Override
    public void nextPage() {
        if (getPage() == mLoadedPages - 1) {
            if (mListener != null) {
                mListener.onLoadPage(getPage() + 1, new DownloadFinishListener() {
                    @Override
                    public void onDownlodFinish(int records) {
                        if (records == -1) {// download error
                            return;
                        }
                        // there is probably next page if loaded records is same like page size
                        mHasNextPage = records == getPageSize();
                        if (records > 0) {
                            mLoadedPages++;// increase loaded page
                            // run nextpage in scheduler to refresh data
                            new Timer() {
                                @Override
                                public void run() {
                                    nextPage();
                                }
                            }.schedule(10);
                        } else {
                            // or invalidate view to update enability of next buttons
                            onRangeOrRowCountChanged();
                        }
                    }
                });
            }
        } else {
            super.nextPage();
        }
    }

    public void setLoadListener(OnPageLoaderListener listener) {
        mListener = listener;
    }

    public void resetLazy() {
        mLoadedPages = 1;
        mHasNextPage = true;
    }
}

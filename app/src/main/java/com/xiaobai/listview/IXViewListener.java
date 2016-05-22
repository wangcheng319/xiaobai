package com.xiaobai.listview;

import java.io.IOException;

/**
 * Implements this interface to get refresh/load more event.
 */
public interface IXViewListener {
	public void onRefresh() throws IOException;
	public void onLoadMore();
}

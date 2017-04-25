package com.sjl.lbox.util;

import android.content.Context;

import com.sjl.lbox.app.ui.CustomView.popupwindow.SelectPopupWindow;
import com.sjl.lbox.app.ui.CustomView.popupwindow.listener.OnItemClickListener;

import java.util.List;

/**
 * PopupWindowUtil
 *
 * @author SJL
 * @date 2017/2/23
 */

public class PopupWindowUtil {
    private static final String TAG = "PopupWindowUtil";
    private static SelectPopupWindow selectPopupWindow;

    public static SelectPopupWindow showSelectPopupWindow(Context context, List<String> list, OnItemClickListener onItemClickListener) {
        synchronized (TAG) {
            selectPopupWindow = new SelectPopupWindow(context, list, onItemClickListener);
            selectPopupWindow.show();
            return selectPopupWindow;
        }
    }
}

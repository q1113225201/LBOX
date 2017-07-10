package com.sjl.librouter.rule;

import android.content.Context;

/**
 * Rule 路由规则
 *
 * @author SJL
 * @date 2017/7/10
 */

public interface Rule<T, V> {
    void addRule(String uri, Class<T> clazz);

    V invokeRule(Context context, String uri);

    boolean existRule(String uri);
}

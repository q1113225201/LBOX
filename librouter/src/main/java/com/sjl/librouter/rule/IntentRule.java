package com.sjl.librouter.rule;

import android.content.Context;
import android.content.Intent;

import java.util.HashMap;

/**
 * IntentRule 意图跳转路由
 *
 * @author SJL
 * @date 2017/7/10
 */

public class IntentRule<T> implements Rule<T, Intent> {
    public static final String ACTIVITY_SCHEME = "activity://";
    public static final String SERVICE_SCHEME = "service://";
    public static final String RECEIVER_SCHEME = "receiver://";
    private HashMap<String, Class<T>> ruleMap = new HashMap<>();

    public IntentRule() {
        ruleMap = new HashMap<>();
    }

    @Override
    public void addRule(String uri, Class clazz) {
        ruleMap.put(uri, clazz);
    }

    @Override
    public Intent invokeRule(Context context, String uri) {
        if (!existRule(uri)) {
            throw new RuntimeException(String.format("%s not exist!", uri));
        }
        return new Intent(context, ruleMap.get(uri));
    }

    @Override
    public boolean existRule(String uri) {
        return ruleMap.get(uri) != null;
    }
}

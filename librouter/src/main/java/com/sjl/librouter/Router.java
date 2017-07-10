package com.sjl.librouter;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;

import com.sjl.librouter.rule.IntentRule;
import com.sjl.librouter.rule.Rule;

import java.util.HashMap;
import java.util.Set;

/**
 * 路由类
 *
 * @author SJL
 * @date 2017/7/10
 */

public class Router {
    private static Router instance;

    public static Router getInstance() {
        if (instance == null) {
            synchronized (Router.class) {
                if (instance == null) {
                    instance = new Router();
                }
            }
        }
        return instance;
    }

    private HashMap<String, Rule> ruleHashMap;

    private Router() {
        ruleHashMap = new HashMap<>();
        initDefault();
    }

    private void initDefault() {
        addRule(IntentRule.ACTIVITY_SCHEME, new IntentRule<Activity>());
        addRule(IntentRule.SERVICE_SCHEME, new IntentRule<Service>());
        addRule(IntentRule.RECEIVER_SCHEME, new IntentRule<BroadcastReceiver>());
    }

    /**
     * 添加路由规则
     *
     * @param scheme
     * @param rule
     * @return
     */
    public Router addRule(String scheme, Rule rule) {
        ruleHashMap.put(scheme, rule);
        return this;
    }

    /**
     * 添加路由
     *
     * @param uri
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Router addRouter(String uri, Class<T> clazz) {
        Rule<T, ?> rule = getRule(uri);
        if (rule == null) {
            throw new RuntimeException("uri unknow");
        }
        rule.addRule(uri, clazz);
        return this;
    }

    /**
     * 调用路由
     *
     * @param context
     * @param uri
     * @param <V>
     * @return
     */
    public <V> V invokeRouter(Context context, String uri) {
        Rule<?, V> rule = getRule(uri);
        if (rule == null) {
            throw new RuntimeException("uri unknow");
        }
        return rule.invokeRule(context, uri);
    }

    /**
     * 是否存在路由
     *
     * @param uri
     * @return
     */
    public boolean existRouter(String uri) {
        Rule<?, ?> rule = getRule(uri);
        return rule != null && rule.existRule(uri);
    }

    private <T, V> Rule<T, V> getRule(String uri) {
        HashMap<String, Rule> rules = ruleHashMap;
        Set<String> keySet = rules.keySet();
        for (String scheme : keySet) {
            if (uri.startsWith(scheme)) {
                return rules.get(scheme);
            }
        }
        return null;
    }
}

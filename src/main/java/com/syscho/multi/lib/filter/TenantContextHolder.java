package com.syscho.multi.lib.filter;

public class TenantContextHolder {
    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static void setEnv(String env) {
        CONTEXT.set(env);
    }

    public static String getEnv() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}

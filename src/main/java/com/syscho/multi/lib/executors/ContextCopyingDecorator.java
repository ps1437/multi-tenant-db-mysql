package com.syscho.multi.lib.executors;

import com.syscho.multi.lib.filter.TenantContextHolder;
import org.springframework.core.task.TaskDecorator;
import org.springframework.util.StringUtils;

public class ContextCopyingDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        var env = TenantContextHolder.getEnv();
        return () -> {
            try {
                if (StringUtils.hasText(env)) {
                    TenantContextHolder.setEnv(env);
                }
                runnable.run();
            } finally {
                TenantContextHolder.clear();
            }
        };
    }
}

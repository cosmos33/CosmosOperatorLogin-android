package com.cosmos.operatorlogin;

import android.content.Context;

public class Utils {
    /**
     * 将dp值转换为px值
     *
     * @param dipValue dp值
     * @return px
     */
    public static int dip2px(Context context, float dipValue) {
        try {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dipValue * scale + 0.5f);
        } catch (Exception e) {
            return (int) dipValue;
        }
    }
}

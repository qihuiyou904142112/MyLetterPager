package com.qhy.letter.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 系统键盘的调用和隐藏
 */
public class InputTools {


    /**
     * @throws
     * @MethodName:closeInputMethod
     * @Description:关闭系统软键盘
     */

    static public void closeInputMethod(Context context, View view) {

        try {
            //获取输入法的服务
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            //判断是否在激活状态
            if (imm.isActive()) {
                //隐藏输入法!!,
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

        } catch (Exception e) {
        } finally {
        }

    }

    /**
     * @throws
     * @MethodName:openInputMethod
     * @Description:打开系统软键盘
     */

    static public void openInputMethod(final EditText editText) {

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {

            public void run() {

                InputMethodManager inputManager = (InputMethodManager) editText

                        .getContext().getSystemService(

                                Context.INPUT_METHOD_SERVICE);

                inputManager.showSoftInput(editText, 0);

            }

        }, 200);

    }

}


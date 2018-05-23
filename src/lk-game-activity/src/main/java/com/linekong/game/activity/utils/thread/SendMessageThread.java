package com.linekong.game.activity.utils.thread;

import com.linekong.game.activity.utils.SendShortMessage;
import com.linekong.game.activity.utils.log.LoggerUtil;

import java.util.concurrent.Callable;

public class SendMessageThread implements Callable<Integer> {

    private String phoneNum;
    private String phoneCode;
    public SendMessageThread() {
    }
    public SendMessageThread(String phoneNum, String phoneCode) {
        this.phoneNum = phoneNum;
        this.phoneCode = phoneCode;
    }

    @Override
    public Integer call() throws Exception {
        try {
            return SendShortMessage.sendMessage(phoneNum, phoneCode);
        } catch (Exception e) {
            LoggerUtil.error(this.getClass(), e.toString());
            throw e;
        }
    }
}

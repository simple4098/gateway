package com.yql.biz.support.validation;

import com.yql.biz.exception.JatAppKeyInvalidException;
import com.yql.biz.model.Jat;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangxiaohong
 */
@Component
public class JatAppKeyValidator implements Validator<Jat> {
    private final static String ANDROID = "0IBvxPA4aYosCX1IrVWS0TWC5Rp5mteE";
    private final static String IOS = "Sx8jTgGVMaSSFRRlqnwTptWgM9pu956J";
    private final static String WEB = "CtjAiVZEYOKkzo7C5kZ0Cq9ycZooZ0q2";

    private final static List<String> APP_KEYS = new ArrayList<>(3);

    static {
        APP_KEYS.add(ANDROID);
        APP_KEYS.add(IOS);
        APP_KEYS.add(WEB);
    }

    @Override
    public boolean isValid(Jat source) {
        String appKey = source.getAppKey();
        if (!StringUtils.hasText(appKey)) {
            throw new JatAppKeyInvalidException();
        }
        if (!APP_KEYS.contains(appKey)) {
            throw new JatAppKeyInvalidException();
        }
        return true;
    }
}

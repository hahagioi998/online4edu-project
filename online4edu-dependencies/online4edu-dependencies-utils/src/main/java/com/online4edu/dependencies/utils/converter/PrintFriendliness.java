package com.online4edu.dependencies.utils.converter;

import com.online4edu.dependencies.utils.jackson.JacksonUtil;

import java.io.Serializable;

/**
 * Bean Json 输出
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2022/04/22 16:11
 */
public abstract class PrintFriendliness extends Convert implements Serializable {

    private static final long serialVersionUID = 6346134566802719199L;

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ":" + JacksonUtil.toJson(this);
    }
}

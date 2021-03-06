package com.cet.eem.model.constant;

import lombok.Getter;

public enum GroupOperator {

    /**
     * 统计
     */
    COUNT("COUNT"),
    /**
     * 最大值
     */
    MAX("MAX"),
    /**
     * 平均值
     */
    AVG("AVG"),
    /**
     * 最小值
     */
    MIN("MIN"),
    /**
     * 求和
     */
    SUM("SUM");

    @Getter
    private String value;

    GroupOperator(String value) {
        this.value = value;
    }
}

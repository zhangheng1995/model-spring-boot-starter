/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.example.conditions.query;

import com.example.model.dto.ConditionBlock;
import com.example.model.dto.ConditionBlockCompose;
import com.example.model.model.AbstractModelEntity;
import com.example.toolkit.Assert;
import com.example.toolkit.LambdaUtils;
import com.example.toolkit.support.SFunction;

import java.util.List;
import java.util.function.Consumer;
/**
 * Lambda 语法使用 Wrapper
 *
 * @author hubin miemie HCL
 * @since 2017-05-26
 */
@SuppressWarnings("serial")
public class LambdaQueryWrapper<T> extends AbstractLambdaWrapper<T, LambdaQueryWrapper<T>> {

    public static <T extends AbstractModelEntity> LambdaQueryWrapper<T> of(Class<T> tClass) {
        LambdaQueryWrapper<T> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.tClass = tClass;
        return queryWrapper;
    }

    private LambdaQueryWrapper() {
    }

    @Override
    public LambdaQueryWrapper<T> or() {
        ConditionBlockCompose filter = getFilter();
        List<ConditionBlock> expressions = filter.getExpressions();
        boolean composeMethod = filter.isComposeMethod();
        if (expressions.size() > 1 && composeMethod) {
            throw new IllegalStateException("not allow use different logical connector");
        }
        filter.setComposeMethod(false);
        return this;
    }

    @Override
    protected String keyToString(SFunction<T, ?> tsFunction) {
        return getColumn(LambdaUtils.resolve(tsFunction));
    }

    @Override
    public LambdaQueryWrapper<T> and(Consumer<LambdaQueryWrapper<T>> consumer) {
        boolean composeMethod = getFilter().isComposeMethod();
        Assert.isFalse(composeMethod, "not allow use different logical connector");
        LambdaQueryWrapper<T> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.globalTagId = this.globalTagId + 1;
        consumer.accept(queryWrapper);
        List<ConditionBlock> expressions = queryWrapper.getFilter().getExpressions();
        this.getFilter().getExpressions().addAll(expressions);
        return this;
    }

    @Override
    public LambdaQueryWrapper<T> or(Consumer<LambdaQueryWrapper<T>> consumer) {
        boolean composeMethod = getFilter().isComposeMethod();
        Assert.isTrue(composeMethod, "not allow use different logical connector");
        LambdaQueryWrapper<T> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.globalTagId = this.globalTagId + 1;
        consumer.accept(queryWrapper);
        List<ConditionBlock> expressions = queryWrapper.getFilter().getExpressions();
        this.getFilter().getExpressions().addAll(expressions);
        return this;
    }
}
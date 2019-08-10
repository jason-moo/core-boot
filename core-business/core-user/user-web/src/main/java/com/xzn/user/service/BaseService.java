package com.xzn.user.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * Created by jason_moo on 2018/12/5.
 */
public interface BaseService<T> {

    Integer insert(T t);

    Integer updateById(T t);

    T selectById(Integer id);

    /**
     * 返回一条
     *
     * @param wrapper
     *                eg：new QueryWrapper<CorpDO>().lambda().eq(CorpDO::getCode, "3172") oracle不要使用，字段命名不标准，使用有问题
     *                eg: new QueryWrapper<CorpDO>().eq("code", "3172")
     * @return
     */
    T selectOne(Wrapper<T> wrapper);

    Integer selectCount(Wrapper<T> wrapper);

    List<T> selectList(Wrapper<T> wrapper);

    IPage<T> selectPage(Page<T> page, Wrapper<T> wrapper);
}

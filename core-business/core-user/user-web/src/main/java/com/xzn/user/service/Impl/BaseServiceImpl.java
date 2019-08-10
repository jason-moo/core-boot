package com.xzn.user.service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzn.user.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by jason_moo on 2018/12/5.
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> implements BaseService<T> {

    @Autowired
    protected M mapper;

    @Override
    public Integer insert(T t) {
        return mapper.insert(t);
    }

    @Override
    public Integer updateById(T t) {
        return mapper.updateById(t);
    }

    @Override
    public T selectById(Integer id) {
        return mapper.selectById(id);
    }

    @Override
    public T selectOne(Wrapper<T> wrapper) {
        return mapper.selectOne(wrapper);
    }

    @Override
    public Integer selectCount(Wrapper<T> wrapper) {
        return mapper.selectCount(wrapper);
    }

    @Override
    public List<T> selectList(Wrapper<T> wrapper) {
        return mapper.selectList(wrapper);
    }

    @Override
    public IPage<T> selectPage(Page<T> page, Wrapper<T> wrapper) {
        return mapper.selectPage(page, wrapper);
    }
}

package com.xzn.user.service.Impl;

import com.xzn.user.entity.User;
import com.xzn.user.mapper.UserMapper;
import com.xzn.user.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuezn
 * @since 2019-08-10
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

}

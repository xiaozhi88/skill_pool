package com.xiaozhi.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaozhi.mp.entity.SysUser;

/**
 *
 *
 * @author zhangzy
 * @date 2020/7/19-16:29
 * @since v1.0
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    int deleteByPrimaryKey(Long userId);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
}
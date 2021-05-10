package com.xiaozhi.mp.mapper;

import com.xiaozhi.mp.entity.SysDict;
import com.xiaozhi.mp.entity.SysDictExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/***************************************************/
  /* 
  Usage                : ${DESCRIPTION}
  Project name         : skill_pool
  Author               : ${USER_NAME}
  Mail                 : ${USER_NAME}@chinaexpressair.com
  Date                 : 2021-05-10 09:54
  Version              : 1.0
  Modification history :
      Date          Author          Version          Description
  ---------------------------------------------------------------
   2021-05-10       ${USER_NAME}             1.0             新建
   @Copyright(C)2008-2020: 华夏航空股份有限公司 All right Reserved.
  */
public interface SysDictMapper {
    long countByExample(SysDictExample example);

    int deleteByExample(SysDictExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysDict record);

    int insertOrUpdate(SysDict record);

    int insertOrUpdateSelective(SysDict record);

    int insertSelective(SysDict record);

    List<SysDict> selectByExample(SysDictExample example);

    SysDict selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysDict record, @Param("example") SysDictExample example);

    int updateByExample(@Param("record") SysDict record, @Param("example") SysDictExample example);

    int updateByPrimaryKeySelective(SysDict record);

    int updateByPrimaryKey(SysDict record);

    int updateBatch(List<SysDict> list);

    int updateBatchSelective(List<SysDict> list);

    int batchInsert(@Param("list") List<SysDict> list);
}
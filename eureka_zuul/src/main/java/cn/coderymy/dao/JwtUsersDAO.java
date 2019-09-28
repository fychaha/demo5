package cn.coderymy.dao;

import cn.coderymy.po.JwtUsers;
import cn.coderymy.po.JwtUsersExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtUsersDAO {
    long countByExample(JwtUsersExample example);

    int deleteByExample(JwtUsersExample example);

    int deleteByPrimaryKey(Long id);

    int insert(JwtUsers record);

    int insertSelective(JwtUsers record);

    List<JwtUsers> selectByExample(JwtUsersExample example);

    JwtUsers selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") JwtUsers record, @Param("example") JwtUsersExample example);

    int updateByExample(@Param("record") JwtUsers record, @Param("example") JwtUsersExample example);

    int updateByPrimaryKeySelective(JwtUsers record);

    int updateByPrimaryKey(JwtUsers record);
}
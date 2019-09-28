package cn.coderymy.dao;

import cn.coderymy.po.JwtUsersRoles;
import cn.coderymy.po.JwtUsersRolesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtUsersRolesDAO {
    long countByExample(JwtUsersRolesExample example);

    int deleteByExample(JwtUsersRolesExample example);

    int deleteByPrimaryKey(Long id);

    int insert(JwtUsersRoles record);

    int insertSelective(JwtUsersRoles record);

    List<JwtUsersRoles> selectByExample(JwtUsersRolesExample example);

    JwtUsersRoles selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") JwtUsersRoles record, @Param("example") JwtUsersRolesExample example);

    int updateByExample(@Param("record") JwtUsersRoles record, @Param("example") JwtUsersRolesExample example);

    int updateByPrimaryKeySelective(JwtUsersRoles record);

    int updateByPrimaryKey(JwtUsersRoles record);
}
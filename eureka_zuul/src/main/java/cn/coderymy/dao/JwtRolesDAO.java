package cn.coderymy.dao;

import cn.coderymy.po.JwtRoles;
import cn.coderymy.po.JwtRolesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtRolesDAO {
    long countByExample(JwtRolesExample example);

    int deleteByExample(JwtRolesExample example);

    int deleteByPrimaryKey(Long id);

    int insert(JwtRoles record);

    int insertSelective(JwtRoles record);

    List<JwtRoles> selectByExample(JwtRolesExample example);

    JwtRoles selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") JwtRoles record, @Param("example") JwtRolesExample example);

    int updateByExample(@Param("record") JwtRoles record, @Param("example") JwtRolesExample example);

    int updateByPrimaryKeySelective(JwtRoles record);

    int updateByPrimaryKey(JwtRoles record);
}
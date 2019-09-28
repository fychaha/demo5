package cn.coderymy.dao;

import cn.coderymy.po.JwtRolesPermissions;
import cn.coderymy.po.JwtRolesPermissionsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtRolesPermissionsDAO {
    long countByExample(JwtRolesPermissionsExample example);

    int deleteByExample(JwtRolesPermissionsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(JwtRolesPermissions record);

    int insertSelective(JwtRolesPermissions record);

    List<JwtRolesPermissions> selectByExample(JwtRolesPermissionsExample example);

    JwtRolesPermissions selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") JwtRolesPermissions record, @Param("example") JwtRolesPermissionsExample example);

    int updateByExample(@Param("record") JwtRolesPermissions record, @Param("example") JwtRolesPermissionsExample example);

    int updateByPrimaryKeySelective(JwtRolesPermissions record);

    int updateByPrimaryKey(JwtRolesPermissions record);
}
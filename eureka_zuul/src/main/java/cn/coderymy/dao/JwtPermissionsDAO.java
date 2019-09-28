package cn.coderymy.dao;

import cn.coderymy.po.JwtPermissions;
import cn.coderymy.po.JwtPermissionsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtPermissionsDAO {
    long countByExample(JwtPermissionsExample example);

    int deleteByExample(JwtPermissionsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(JwtPermissions record);

    int insertSelective(JwtPermissions record);

    List<JwtPermissions> selectByExample(JwtPermissionsExample example);

    JwtPermissions selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") JwtPermissions record, @Param("example") JwtPermissionsExample example);

    int updateByExample(@Param("record") JwtPermissions record, @Param("example") JwtPermissionsExample example);

    int updateByPrimaryKeySelective(JwtPermissions record);

    int updateByPrimaryKey(JwtPermissions record);
}
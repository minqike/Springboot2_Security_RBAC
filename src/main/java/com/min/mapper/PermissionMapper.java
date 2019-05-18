package com.min.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.min.entity.Permission;

public interface PermissionMapper {

	@Select(" select * from sys_permission ")
	List<Permission> findAllPermission();

}

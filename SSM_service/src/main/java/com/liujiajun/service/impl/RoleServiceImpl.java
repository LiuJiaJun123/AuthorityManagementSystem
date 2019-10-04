package com.liujiajun.service.impl;

import com.github.pagehelper.PageHelper;
import com.liujiajun.dao.IRoleDao;
import com.liujiajun.dao.IRole_PermissionDao;
import com.liujiajun.dao.IUsers_RoleDao;
import com.liujiajun.domain.Permission;
import com.liujiajun.domain.Role;
import com.liujiajun.domain.Users_Role;
import com.liujiajun.exception.CustomException;
import com.liujiajun.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements IRoleService {


    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private IUsers_RoleDao users_roleDao;

    @Autowired
    private IRole_PermissionDao role_permissionDao;

    @Override
    public List<Role> findAll(int page,int pageSize) throws Exception {

        PageHelper.startPage(page,pageSize);
        return roleDao.findAll();
    }

    //添加角色
    @Override
    public void save(Role role) throws Exception {
        roleDao.save(role);
    }

    //删除角色
    @Override
    public void delete(String[] selectIds) {
        for (String id : selectIds) {
           //1、先查询是否有用户拥有该角色
            List<Users_Role> users_roles = users_roleDao.findByRoleId(id);
            //如果有用户拥有该角色
            if (users_roles!=null){
                //先从users_roles表中根据角色id删除
                for (Users_Role users_role : users_roles) {
                    users_roleDao.deleteByRoleId(users_role.getRoleId());
                }
            }
            //2、从role_permission删除记录
            role_permissionDao.deleteByRoleId(id);

            //3、再从role表删除记录
            roleDao.delete(id);
        }

    }

    @Override
    public Role findById(String id) throws Exception {
        return roleDao.findById(id);
    }

    /**
     * 查找该角色可以添加的权限，从权限表查找 该角色没有的权限
     * @param
     * @return
     */
    @Override
    public List<Permission> findPermissionCanAdd(String id)throws Exception {
        return roleDao.findPermissionCanAdd(id);
    }

    //角色添加权限
    @Override
    public void addPermission(String roleId, String[] permissionIds) throws Exception{
        for (String permissionId : permissionIds) {
            roleDao.addPermission(roleId,permissionId);
        }
    }

    /**
     * 查看角色详情
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Role findByRoleId(String id) throws Exception {
        return roleDao.findByRoleId(id);
    }

    //用户信息修改
    @Override
    public void update(Role role) {
        roleDao.update(role);
    }

    //角色的资源权限修改
    @Override
    public void updatePermission(String id, String[] permissionIds) throws Exception {
        //先从role_permission删除旧的
        roleDao.deletePermission(id);
        //再添加新的
        for (String permissionId : permissionIds) {
            roleDao.addPermission(id,permissionId);
        }
    }

    //根据角色名查找
    @Override
    public Role findByName(String roleName) throws Exception {
        return roleDao.findByName(roleName);
    }


}

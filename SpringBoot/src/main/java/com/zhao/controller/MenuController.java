package com.zhao.controller;


import com.zhao.result.Result;
import com.zhao.result.ResultInfo;
import com.zhao.api.MenuService;
import com.zhao.dto.MenuDTO;
import com.zhao.exception.div.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 获取菜单列表通过角色名字
     * @return
     */
    @GetMapping("/getMenuList")
    public Result getMenuListByRoleName(@RequestParam("roleName") String roleName){
//        HashMap<String, Object> map = new HashMap<>();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Object principal = authentication.getPrincipal();
//        if (principal == "anonymousUser") {
//            throw  new ServiceException(ResultInfo.CODE_600, "匿名用户");
//        }
//        MyUserDetails userDetails = null;
//        if (principal instanceof UserDetails) {
//            userDetails = (MyUserDetails) principal;
//        }
//        String roleName = null;
//        assert userDetails != null;
//        for (GrantedAuthority authority : userDetails.getAuthorities()) {
//            roleName = authority.getAuthority();
//        }
        Map<String, Object> map = new HashMap<>();
        List<MenuDTO> menuList = null;
        try{
            menuList = menuService.getMenuListByRoleName(roleName);
        }catch (NullPointerException e){
            throw  new ServiceException(ResultInfo.CODE_600, "未认证");
        }finally {
            map.put("menuList", menuList);
        }
        return Result.success(map,"获取菜单列表成功");
    }


    @GetMapping("/getAllMenuList")
    public Result getAllMenuList() {
        Map<String, Object> map = new HashMap<>();
        List<MenuDTO> menuList = null;
        try{
            menuList = menuService.getAllMenuList();
        }catch (NullPointerException e){
            throw  new ServiceException(ResultInfo.CODE_600, "查询失败");
        }finally {
            map.put("menuList", menuList);
        }
        return Result.success(map,"获取菜单列表成功");
    }
}

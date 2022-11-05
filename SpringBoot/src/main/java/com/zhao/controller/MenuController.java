package com.zhao.controller;


import com.zhao.annotations.OptLog;
import com.zhao.api.MenuService;
import com.zhao.dto.MenuDTO;
import com.zhao.result.ResultStandby;
import com.zhao.vo.ConditionVO;
import com.zhao.vo.MenuHiddenVO;
import com.zhao.vo.MenuSaveVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.zhao.constant.OptTypeConst.SAVE_OR_UPDATE;
import static com.zhao.constant.OptTypeConst.UPDATE;
import static com.zhao.enums.StatusCodeEnum.SUCCESS;
import static com.zhao.result.ResultStandby.success;


/**
 * 菜单控制器
 *
 * @author ran-feiran
 * @date 2022/10/12
 */
@RestController
@RequestMapping("/menu")
@Api(tags = "菜单模块")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "新增或更新菜单")
    @OptLog(optType = SAVE_OR_UPDATE)
    @PostMapping("/saveOrUpdateMenu")
    public ResultStandby<?> saveOrUpdateMenu(@RequestBody MenuSaveVO menuSaveVO) {
        menuService.saveOrUpdateMenu(menuSaveVO);
        return success(null, SUCCESS.getDesc());
    }

    @ApiOperation(value = "修改菜单隐藏状态")
    @OptLog(optType = UPDATE)
    @PutMapping("/updateHiddenById")
    public ResultStandby<?> updateHiddenById(@RequestBody MenuHiddenVO hiddenVO) {
        menuService.updateHiddenById(hiddenVO);
        return success(null, SUCCESS.getDesc());
    }

    @ApiOperation(value = "逻辑删除菜单")
    @OptLog(optType = UPDATE)
    @DeleteMapping("/del/{id}")
    public ResultStandby<?> deleteMenuById(@PathVariable("id") Integer id) {
        menuService.deleteMenuById(id);
        return success(null, SUCCESS.getDesc());
    }

    @ApiOperation(value = "根据角色名获取菜单列表")
    @GetMapping("/getMenuList")
    public ResultStandby<List<MenuDTO>> getMenuListByRoleName(@RequestParam("roleName") String roleName){
        return success(menuService.getMenuListByRoleName(roleName),SUCCESS.getDesc());
    }

    @ApiOperation(value = "获取所有菜单列表")
    @GetMapping("/getAllMenuList")
    public ResultStandby<List<MenuDTO>> getAllMenuList(ConditionVO conditionVO) {
        return success(menuService.getAllMenuList(conditionVO), SUCCESS.getDesc());
    }
}

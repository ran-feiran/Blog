package com.zhao.controller;


import com.zhao.annotations.OptLog;
import com.zhao.api.ApiService;
import com.zhao.dto.ApiBackDTO;
import com.zhao.result.ResultStandby;
import com.zhao.vo.AnonymousVO;
import com.zhao.vo.ConditionVO;
import com.zhao.vo.ResourceSaveVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.zhao.constant.OptTypeConst.*;
import static com.zhao.enums.StatusCodeEnum.SUCCESS;
import static com.zhao.result.ResultStandby.success;


/**
 * 资源控制器
 *
 * @author ran-feiran
 * @date 2022/10/13
 */
@RestController
@io.swagger.annotations.Api(tags = "资源模块")
public class ResourceController {

    @Autowired
    private ApiService apiService;

    @ApiOperation(value = "获取资源信息(后台)")
    @GetMapping("/resource/getResourcesInfoBack")
    public ResultStandby<List<ApiBackDTO>> getResourcesInfoBack(ConditionVO conditionVO) {
        return success(apiService.getResourcesInfoBack(conditionVO), SUCCESS.getDesc());
    }

    @ApiOperation(value = "修改资源匿名访问状态")
    @OptLog(optType = UPDATE)
    @PutMapping("/resource/updateAnonymousById")
    public ResultStandby<?> updateAnonymousById(@Valid @RequestBody AnonymousVO anonymousVO) {
        apiService.updateAnonymousById(anonymousVO);
        return success(null, SUCCESS.getDesc());
    }

    @ApiOperation(value = "资源删除")
    @OptLog(optType = REMOVE)
    @DeleteMapping("/resource/del/{id}")
    public ResultStandby<?> deleteResourceInfoById(@PathVariable("id") Integer id) {
        apiService.deleteResourceInfoById(id);
        return success(null, SUCCESS.getDesc());
    }

    @ApiOperation(value = "新增或更新资源")
    @OptLog(optType = SAVE_OR_UPDATE)
    @PostMapping("/resource/addOrEditResource")
    public ResultStandby<?> addOrEditResource(@Valid @RequestBody ResourceSaveVO resourceSaveVO) {
        apiService.addOrEditResource(resourceSaveVO);
        return success(null, SUCCESS.getDesc());
    }
}

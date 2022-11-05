package com.zhao.controller;


import com.zhao.annotations.OptLog;
import com.zhao.api.AboutService;
import com.zhao.result.ResultStandby;
import com.zhao.vo.BlogInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.zhao.constant.OptTypeConst.UPDATE;
import static com.zhao.enums.StatusCodeEnum.SUCCESS;
import static com.zhao.result.ResultStandby.success;


/**
 * 关于控制器
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
@RestController
@Api(tags = "关于我模块")
public class AboutController {

    @Autowired
    private AboutService aboutService;

    @ApiOperation(value = "得到关于我")
    @GetMapping("/about/getAbout")
    public ResultStandby<String> getAbout() {
        return success(aboutService.getAbout(), SUCCESS.getDesc());
    }

    @ApiOperation(value = "更新关于我")
    @OptLog(optType = UPDATE)
    @PutMapping("/about/updateAbout")
    public ResultStandby<?> updateAbout(@RequestBody BlogInfoVO blogInfoVO) {
        aboutService.updateAbout(blogInfoVO);
        return success(null, SUCCESS.getDesc());
    }
}

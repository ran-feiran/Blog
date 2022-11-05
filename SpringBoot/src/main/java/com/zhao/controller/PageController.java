package com.zhao.controller;

import com.zhao.annotations.OptLog;
import com.zhao.api.PageService;
import com.zhao.dto.PageBackDTO;
import com.zhao.result.ResultStandby;
import com.zhao.vo.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.zhao.constant.OptTypeConst.REMOVE;
import static com.zhao.constant.OptTypeConst.SAVE_OR_UPDATE;
import static com.zhao.enums.StatusCodeEnum.SUCCESS;
import static com.zhao.result.ResultStandby.success;

@RestController
@Api(tags = "页面模块")
public class PageController {

    @Autowired
    private PageService pageService;

    @ApiOperation(value = "获取页面列表")
    @GetMapping("/page/listPages")
    public ResultStandby<List<PageBackDTO>> listPages() {
        return success(pageService.listPages(), SUCCESS.getDesc());
    }

    @ApiOperation(value = "新增或更新页面")
    @OptLog(optType = SAVE_OR_UPDATE)
    @PostMapping("/page/addOrEditPage")
    public ResultStandby<?> addOrEditPage(@Valid @RequestBody PageVO pageVO) {
        pageService.addOrEditPage(pageVO);
        return success();
    }

    @ApiOperation(value = "删除页面")
    @OptLog(optType = REMOVE)
    @DeleteMapping("/page/del/batch/{id}")
    public ResultStandby<?> deletePageBatch(@PathVariable("id") Integer id) {
        pageService.removeById(id);
        return success();
    }

}

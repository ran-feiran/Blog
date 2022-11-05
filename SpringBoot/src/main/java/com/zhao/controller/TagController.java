package com.zhao.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhao.annotations.OptLog;
import com.zhao.api.TagService;
import com.zhao.dto.ArticleBlogDTO;
import com.zhao.dto.PageDTO;
import com.zhao.dto.PageListDTO;
import com.zhao.dto.TagBackDTO;
import com.zhao.pojo.Tag;
import com.zhao.result.ResultStandby;
import com.zhao.vo.ConditionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.zhao.constant.OptTypeConst.REMOVE;
import static com.zhao.constant.OptTypeConst.SAVE_OR_UPDATE;
import static com.zhao.enums.StatusCodeEnum.SUCCESS;
import static com.zhao.result.ResultStandby.success;

/**
 * 标签控制器
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
@RestController
@RequestMapping("/tag")
@Api(tags = "标签模块")
public class TagController {

    @Autowired
    private TagService tagService;

    @ApiOperation(value = "标签搜索")
    @GetMapping("/search")
    public ResultStandby<List<Tag>> searchTags(@RequestParam("keywords") String keywords) {
        return success(tagService.searchTags(keywords), SUCCESS.getDesc());
    }

    @ApiOperation(value = "获取标签列表")
    @GetMapping("/tags")
    public ResultStandby<PageDTO<Tag>> listTags() {
        return success(new PageDTO<>(tagService.list(), tagService.count()),SUCCESS.getDesc());
    }

    @ApiOperation(value = "获取分类数据(后台)")
    @GetMapping("/getTagList")
    public ResultStandby<List<Tag>> getTagList(){
        return success(tagService.list(),SUCCESS.getDesc());
    }

    @ApiOperation(value = "物理删除标签")
    @OptLog(optType = REMOVE)
    @DeleteMapping("/del/batch")
    public ResultStandby<?> delBatch(@RequestBody List<Integer> ids) {
        tagService.removeBatchByIds(ids);
        return success(null, SUCCESS.getDesc());
    }

    @ApiOperation(value = "新增或更新标签")
    @OptLog(optType = SAVE_OR_UPDATE)
    @PostMapping("/addOrEditTag")
    public ResultStandby<?> addOrEditCategory(@RequestBody Tag tag) {
        tagService.saveOrUpdate(tag);
        return success(null, SUCCESS.getDesc());
    }

    @ApiOperation(value = "获取标签列表(后台)")
    @GetMapping("/listTags")
    public ResultStandby<PageDTO<TagBackDTO>> listCategory(ConditionVO conditionVO) {
        return success(tagService.queryPageTags(conditionVO),SUCCESS.getDesc());
    }

    @ApiOperation(value = "通过标签id获取对应文章")
    @GetMapping("/query/{tagId}")
    public ResultStandby<PageListDTO<ArticleBlogDTO>> getTagById(@PathVariable("tagId") Integer tagId, @RequestParam("current") Integer current) {
        return success(new PageListDTO<>(tagService.listArticles(tagId, current - 1), tagService
                .getOne(new LambdaQueryWrapper<Tag>().eq(Tag::getTagId, tagId)).getTagName()),SUCCESS.getDesc());
    }
}

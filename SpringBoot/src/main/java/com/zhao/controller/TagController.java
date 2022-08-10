package com.zhao.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.jdbc.StringUtils;
import com.zhao.api.ArticleService;
import com.zhao.dto.ArticleBlogDTO;
import com.zhao.result.Result;
import com.zhao.result.ResultInfo;
import com.zhao.api.TagService;
import com.zhao.exception.div.ServiceException;
import com.zhao.pojo.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleService articleService;


    @GetMapping("/tags")
    public Result listTags() {
        List<Tag> tagList = tagService.list();
        long count = tagService.count();
        Map<String, Object> map = new HashMap<>();
        map.put("tagList",tagList);
        map.put("count",count);
        return Result.success(map,"");
    }

    @GetMapping("/getTagList")
    public Result getTagList(){
        List<Tag> tagList = tagService.list();
        if (tagList != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("tagList",tagList);
            return Result.success(map,"获取标签成功");
        }
        throw new ServiceException(ResultInfo.CODE_600,"获取标签失败");
    }


    @DeleteMapping("/deleteTag")
    public Result deleteCategory(@RequestParam("tagId") Integer tagId) {
        try {
            tagService.removeById(tagId);
        } catch (Exception e) {
            throw new ServiceException(ResultInfo.CODE_600,"删除标签失败");
        }
        return Result.success();
    }


    @PostMapping("/del/batch")
    public Result delBatch(@RequestBody List<Integer> ids) {
        if (ids.size() <= 0) {
            throw new ServiceException(ResultInfo.CODE_600,"批量删除标签失败");
        }
        try {
            tagService.removeBatchByIds(ids);
        } catch (Exception e) {
            throw new ServiceException(ResultInfo.CODE_600,"批量删除标签失败");
        }
        return Result.success();
    }


    @PostMapping("/addOrEditTag")
    public Result addOrEditCategory(@RequestBody Tag tag) {
        try {
            tagService.saveOrUpdate(tag);
        } catch (Exception e) {
            throw new ServiceException(ResultInfo.CODE_600,"操作标签失败");
        }
        return Result.success();
    }


    @GetMapping("/listTags")
    public Result listCategory(
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
            @RequestParam(value = "tagName",defaultValue = "") String tagName) {
        Page<Tag> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Tag> wrapper = null;
        if (!StringUtils.isNullOrEmpty(tagName)) {
            wrapper = new QueryWrapper<>();
            wrapper.like("tag_name",tagName);
        }
        Page<Tag> tagList = null;
        try {
            tagList = tagService.page(page, wrapper);
        } catch (Exception e) {
            throw new ServiceException(ResultInfo.CODE_600,"获取标签失败");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("tagList",tagList);
        return Result.success(map,"success");
    }

    @GetMapping("/{tagId}")
    public Result getTagById(@PathVariable("tagId") Integer tagId,
                             @RequestParam("current") Integer current) {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.select("tag_name").eq("tag_id",tagId);
        Tag one = tagService.getOne(wrapper);
        current = current - 1;
        List<ArticleBlogDTO> articleBlogDTOS = tagService.listArticles(tagId,current);
        Map<String, Object> map = new HashMap<>();
        map.put("articleList", articleBlogDTOS);
        map.put("name", one.getTagName());
        return Result.success(map,"");
    }
}

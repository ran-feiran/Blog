package com.zhao.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhao.api.BlogInfoService;
import com.zhao.api.RedisService;
import com.zhao.constant.RedisPrefixConst;
import com.zhao.dto.BlogHomeInfoDTO;
import com.zhao.mapper.ArticleMapper;
import com.zhao.mapper.CategoryMapper;
import com.zhao.mapper.TagMapper;
import com.zhao.mapper.UserMapper;
import com.zhao.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogInfoServiceImpl implements BlogInfoService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public BlogHomeInfoDTO getBlogInfo() {
        //昵称头像简介 公告 文章数量  分类数量 标签数量  公告 访问量
        //公告先写死 访问量后面在弄
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("nickname","avatar","intro").eq("username", "zhaoran");
        User user = userMapper.selectOne(queryWrapper);
        //获取文章数量
        long articleCount= articleMapper.selectCount(null);
        //分类数量
        long categoryCount = categoryMapper.selectCount(null);
        //标签数量
        long tagCount = tagMapper.selectCount(null);
        //公告
        String notice="点个赞在走吧";
        // 访问量
        redisService.incr(RedisPrefixConst.BLOG_VIEWS_COUNT,1L);
        Integer viewsCount =(Integer) redisService.get(RedisPrefixConst.BLOG_VIEWS_COUNT);
        BlogHomeInfoDTO blogHomeInfoDTO = new BlogHomeInfoDTO(user.getNickname(), user.getAvatar(), user.getIntro(),
                articleCount,
                categoryCount,
                tagCount,
                notice,
                viewsCount);
        return blogHomeInfoDTO;
    }
}

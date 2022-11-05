package com.zhao.service;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.mysql.cj.util.StringUtils;
import com.zhao.api.*;
import com.zhao.dto.*;
import com.zhao.mapper.CategoryMapper;
import com.zhao.mapper.TagMapper;
import com.zhao.pojo.Page;
import com.zhao.utils.IpUtil;
import com.zhao.vo.ConditionVO;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.zhao.constant.CommonConst.*;
import static com.zhao.constant.RedisPrefixConst.*;
import static com.zhao.enums.UserAreaTypeEnum.getUserAreaType;


@Service
@Slf4j
public class BlogInfoBackServiceImpl implements BlogInfoBackService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private UniqueViewService uniqueViewService;

    @Resource
    private HttpServletRequest request;

    @Autowired
    private PageService pageService;

    @Override
    public BlogInfoBackDTO getBlogBackInfo() {
        // 查询用户量
        long userCount = userService.count();
        // 查询留言量
        long messageCount = messageService.count();
        // 查询文章量
        long articleCount = articleService.count();
        // 查询访问量
        Integer viewsCount = (Integer) redisService.get(BLOG_VIEWS_COUNT);
        // 查询分类数据
        List<CateGoryDTO> cateGoryDTOS = categoryMapper.listCategories();
        cateGoryDTOS.forEach(item -> item.
                setArticleCount(
                        categoryMapper.queryArticleCountByCategoryId(item.getCategoryId())
                ));
        // 查询标签数据
        List<TagDTO> tagDTOS = tagMapper.selectTagList();
        // 查询文章统计
        List<ArticleStatisticsDTO> articleStatisticsList = articleService.listArticleStatistics();
        // 查询一周用户量
        List<UniqueViewDTO> uniqueViewList = uniqueViewService.listUniqueViews();
        // 查询文章访问排行量  文章id  文章访问量
        Map<Object, Double> articleMap = redisService.zReverseRangeWithScore(ARTICLE_VIEWS_COUNT, 0, 4);
        BlogInfoBackDTO blogBackInfoDTO =  BlogInfoBackDTO
                            .builder()
                            .userCount((int) userCount)
                            .messageCount((int) messageCount)
                            .articleCount((int) articleCount)
                            .viewsCount(viewsCount)
                            .categoryDTOList(cateGoryDTOS)
                            .tagDTOList(tagDTOS)
                            .articleStatisticsList(articleStatisticsList)
                            .uniqueViewDTOList(uniqueViewList)
                            .build();
        if (CollectionUtils.isNotEmpty(articleMap)) {
            // 查询文章排行
            List<ArticleRankDTO> articleRankDTOList = listArticleRank(articleMap);
            blogBackInfoDTO.setArticleRankDTOList(articleRankDTOList);
        }
        return blogBackInfoDTO;
    }

    @Override
    public List<UserAreaDTO> listUserAreas(ConditionVO conditionVO) {
        List<UserAreaDTO> userAreaDTOList = new ArrayList<>();
        switch (Objects.requireNonNull(getUserAreaType(conditionVO.getType()))) {
            case USER:
                // 查询注册用户区域分布
                Object userArea = redisService.get(USER_AREA);
                if (Objects.nonNull(userArea)) {
                    userAreaDTOList = JSON.parseObject(userArea.toString(), List.class);
                }
                return userAreaDTOList;
            case VISITOR:
                // 查询访客区域分布
                Map<String, Object> visitorArea = redisService.hGetAll(VISITOR_AREA);
                if (Objects.nonNull(visitorArea)) {
                    userAreaDTOList = visitorArea.entrySet().stream()
                            .map(item -> UserAreaDTO.builder()
                                    .name(item.getKey())
                                    .value(Long.valueOf(item.getValue().toString()))
                                    .build())
                            .collect(Collectors.toList());
                }
                return userAreaDTOList;
            default:
                break;
        }
        return userAreaDTOList;
    }

    @Override
    public void report() {
        // 获取ip地址
        String ipAddress = IpUtil.getIp(request);
        // 获取访问设备
        UserAgent userAgent = IpUtil.getUserAgent(request);
        // 拿到访客的浏览器
        Browser browser = userAgent.getBrowser();
        // 拿到访客的电脑的操作系统
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        // 生成唯一用户标识
        String uuid = ipAddress + browser.getName() + operatingSystem.getName();
        String md5 = DigestUtils.md5DigestAsHex(uuid.getBytes());
        log.info("访客唯一标识符：{}", md5);
        // 判断是否访问
        if (!redisService.sIsMember(UNIQUE_VISITOR, md5)) {
            // 统计游客地域分布
            String ipSource = IpUtil.getIpSource(ipAddress);
            if (!StringUtils.isNullOrEmpty(ipSource)) {
                ipSource = ipSource.substring(0, 3)
                        .replaceAll(PROVINCE, "")
                        .replaceAll(CITY, "");
                redisService.hIncr(VISITOR_AREA, ipSource, 1L);
            } else {
                redisService.hIncr(VISITOR_AREA, UNKNOWN, 1L);
            }
            // 访问量+1
            redisService.incr(BLOG_VIEWS_COUNT, 1);
            // 保存唯一标识
            redisService.sAdd(UNIQUE_VISITOR, md5);
        }
    }

    @Override
    public String loadLoginPage() {
        return pageService.getOne(new LambdaQueryWrapper<Page>().eq(Page::getPageLabel, "login")).getPageCover();
    }


    /**
     * 查询文章排行
     *
     * @param articleMap 文章信息
     * @return {@link List<ArticleRankDTO>} 文章排行
     */
    private List<ArticleRankDTO> listArticleRank(Map<Object, Double> articleMap) {
        return articleMap
                .entrySet()
                .stream()
                .map(item -> ArticleRankDTO
                        .builder()
                        .articleTitle(articleService.getArticle(Integer.parseInt(item.getKey().toString())).getArticleTitle())
                        .viewsCount(item.getValue().intValue())
                        .build())
                .sorted(Comparator.comparingInt(ArticleRankDTO::getViewsCount).reversed())
                .collect(Collectors.toList());
    }
}

package com.zhao.controller;


import com.zhao.annotations.OptLog;
import com.zhao.api.FileService;
import com.zhao.enums.FilePathEnum;
import com.zhao.result.ResultStandby;
import com.zhao.strategy.context.UploadStrategyContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

import static com.zhao.constant.OptTypeConst.UPLOAD;
import static com.zhao.enums.StatusCodeEnum.SUCCESS;
import static com.zhao.result.ResultStandby.success;


/**
 * 文件控制器
 *
 * @author ran-feiran
 * @date 2022/10/15
 */
@RestController
@Api(tags = "文件模块")
public class FileController {

    @Autowired
    private UploadStrategyContext uploadStrategyContext;
    @Autowired
    private FileService fileService;

    @ApiOperation(value = "上传配置型图片")
    @PostMapping("/file/config/images")
    public ResultStandby<String> uploadConfigImages(MultipartFile file) {
        return success(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.CONFIG.getPath()),
                        SUCCESS.getDesc());
    }

    @ApiOperation(value = "上传文章型图片")
    @PostMapping("/file/article/images")
    public ResultStandby<String> uploadArticleImages(MultipartFile file) {
        return success(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.ARTICLE.getPath()),
                        SUCCESS.getDesc());
    }

    @ApiOperation(value = "下载图片(本地接口：可忽略)")
    @GetMapping("/blog/{md5}")
    public ResultStandby<String> download(@PathVariable("md5") String md5,
                                          HttpServletResponse response) {
        fileService.downLoad(md5,response);
        return success();
    }
}

package com.zhao.strategy.Impl;

import com.zhao.exception.ServiceException;
import com.zhao.strategy.UploadStrategy;
import com.zhao.utils.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static com.zhao.enums.StatusCodeEnum.UPLOAD_ERROR;

/**
 * 抽象上传模板
 *
 * @author ran-feiran
 * @date 2022/10/16
 */
@Service
public abstract class AbstractUploadStrategyImpl implements UploadStrategy {

    @Override
    public String uploadFile(MultipartFile file, String path) {
        try {
            // 获取文件md5值
            String md5 = FileUtil.getMd5(file.getInputStream());
            // 获取文件扩展名
            String extName = FileUtil.getExtName(file.getOriginalFilename());
            // 重新生成文件名
            String fileName = md5 + extName;
            // 判断文件是否已存在
            if (!exists(path + fileName)) {
                // 不存在则继续上传
                upload(path, fileName, file.getInputStream());
            }
            // 返回文件访问路径
            return getFileAccessUrl(path + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException(UPLOAD_ERROR.getCode(), UPLOAD_ERROR.getDesc());
        }
    }

    @Override
    public String uploadFile(String fileName, InputStream inputStream, String path) {
        try {
            // 上传文件
            upload(path, fileName, inputStream);
            // 返回文件访问路径
            return getFileAccessUrl(path + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(UPLOAD_ERROR.getCode(), UPLOAD_ERROR.getDesc());
        }
    }


    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return {@link Boolean}
     */
    public abstract Boolean exists(String filePath);

    /**
     * 上传
     *
     * @param path        路径
     * @param fileName    文件名
     * @param inputStream 输入流
     * @throws IOException io异常
     */
    public abstract void upload(String path, String fileName, InputStream inputStream) throws IOException;

    /**
     * 获取文件访问url
     *
     * @param filePath 文件路径
     * @return {@link String}
     */
    public abstract String getFileAccessUrl(String filePath);
}

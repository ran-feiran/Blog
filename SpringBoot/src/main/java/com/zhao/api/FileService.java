package com.zhao.api;


import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 文件服务
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
@Deprecated
public interface FileService {

    /**
     * 上传文件
     * @param file
     * @return
     * @throws IOException
     */
    String upload(MultipartFile file) throws IOException;

    /**
     * 下载文件
     * @param url
     */
    void downLoad(String url, HttpServletResponse response);
}

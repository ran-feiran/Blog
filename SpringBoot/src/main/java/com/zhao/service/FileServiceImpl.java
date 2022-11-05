package com.zhao.service;

import cn.hutool.core.io.FileUtil;
import com.zhao.api.FileService;
import com.zhao.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static com.zhao.enums.StatusCodeEnum.FAIL;


@Service
@Deprecated
public class FileServiceImpl implements FileService {

    @Value("${upload.local.path}")
    private String upLoadFilePath;

    @Value("${upload.local.url}")
    private String upLoadFileUrl;

    @Override
    public String upload(MultipartFile file) throws IOException {
        //构建日期的文件夹路径 2022/1/1/文件名
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String dataPath = simpleDateFormat.format(date);
        //获取上传文件的全名称
        String original = file.getOriginalFilename();
        //获取 UUID
        String filename = UUID.randomUUID().toString().replaceAll("-", "");
        //获取上传文件的拓展名 1.jpg
        assert original != null;
        String fileType = original.substring(original.lastIndexOf("."));
        //拼接文件名称
        String newName = filename + fileType;
        //生成文件夹  2020/11/24/UUID.png
        filename = upLoadFilePath + dataPath + "/" + newName;
        File uploadFile = new File(filename);
        // 判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        File parentFile = uploadFile.getParentFile();
        // 创建父目录
        if(!parentFile.exists()) {
            parentFile.mkdirs();
        }
        // 上传文件到磁盘
        file.transferTo(uploadFile);
        return upLoadFileUrl + dataPath + "/" + newName;
    }

    @Override
    public void downLoad(String url, HttpServletResponse response)  {
        // 根据文件的唯一标识码获取文件
        File uploadFile = new File(upLoadFilePath + "blog/" + url);
        // 设置输出流的格式
        ServletOutputStream os = null;
        try {
            os = response.getOutputStream();
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(url, "UTF-8"));
            // 读取文件的字节流
            os.write(FileUtil.readBytes(uploadFile));
            os.flush();
            os.close();
        } catch (IOException e) {
            throw new ServiceException(FAIL.getCode(), FAIL.getDesc());
        }
    }
}

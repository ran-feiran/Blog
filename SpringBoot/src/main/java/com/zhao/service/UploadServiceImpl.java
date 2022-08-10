package com.zhao.service;

import cn.hutool.core.io.FileUtil;
import com.zhao.api.UploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


@Service
public class UploadServiceImpl implements UploadService {

    @Value("${upload.local.path}")
    private String upLoadFilePath;

    @Override
    public String upload(MultipartFile file) throws IOException {
        //每次上传的名字肯定不同的呀，来弄一个不同的名字
        //uuid  redis 分布式id 雪花算法 定义一个文件格式 yyyy/MM/dd+uuid
        //获取上传文件的文件流
        InputStream fileInputStream = file.getInputStream();
        //构建日期的文件夹路径 2020/11/24/文件名
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
        filename = upLoadFilePath+ dataPath+"/"+newName;

        File uploadFile = new File(filename);
        // 判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        File parentFile = uploadFile.getParentFile();
        if(!parentFile.exists()) {
            parentFile.mkdirs();
        }
        File uploadFile_user = new File(upLoadFilePath + newName);
        // 上传文件到磁盘
        // 解决 springboot项目在上传较大文件时报错：
        // org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException:
        // The field file exceeds its maximum permitted size of 1048576 bytes = 1MB
        file.transferTo(uploadFile);
        file.transferTo(uploadFile_user);
        return "https://static.ran-feiran.cn/" + newName;
    }

    @Override
    public void downLoad(String url, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File uploadFile = new File(upLoadFilePath + url);
        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(url, "UTF-8"));
//        response.setContentType("application/octet-stream");
        // 读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }
}

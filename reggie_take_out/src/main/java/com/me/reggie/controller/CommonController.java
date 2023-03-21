package com.me.reggie.controller;

import com.me.reggie.common.R;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;

    /**
     * 上传图片
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) throws Exception{

        //原始文件名
        String originalFilename = file.getOriginalFilename();

        //生成文件名
        String fileName = UUID.randomUUID().toString();

        fileName+=originalFilename.substring(originalFilename.lastIndexOf("."));

        //创建目录
        File dir = new File(basePath);
        if(!dir.exists()){
            dir.mkdirs();
        }

        //将临时文件转存到指定位置
        file.transferTo(new File(basePath+fileName));

        return R.success(fileName);
    }

    /**
     * 文件下载
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) throws Exception{

        //输入流，读取文件内容
        FileInputStream fileInputStream = new FileInputStream(new File(basePath+name));

        //输出流，将文件写回浏览器
        ServletOutputStream servletOutputStream = response.getOutputStream();

        response.setContentType("image/jpeg");

        int len = 0;
        byte[] bytes = new byte[1024];
        while ((len = fileInputStream.read(bytes))!=-1){
            servletOutputStream.write(bytes,0,len);
            servletOutputStream.flush();
        }

        //关闭资源
        servletOutputStream.close();;
        fileInputStream.close();
    }
}

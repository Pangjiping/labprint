package org.epha.com.labprint.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.epha.com.labprint.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@Slf4j
public class FileController {

    @Value("${file.location}")
    private String fileLocation;

    @GetMapping("/file/form_layouts")
    public String formLayouts(){
        return "form_layouts";
    }

    /**
     * 接收上传的文件并保存在指定文件夹中
     * @param files 用户上传的文件
     * @return
     * @throws IOException
     */
    @PostMapping("/file/upload")
    public String upload(@RequestParam("files") MultipartFile[] files) throws IOException{
        if (files.length>0){
            for (var file:files){
                file.transferTo(new File(fileLocation+file.getOriginalFilename()));
            }
        }

        return "index";
    }

    @PostMapping("/file/url")
    public String handleUrl(@RequestParam("url") String url) throws Exception {
        if (HttpClientUtil.isHttpUrl(url)){
            String content = HttpClientUtil.doGet(url);
            FileUtils.writeStringToFile(new File(fileLocation+"net-"+url),content);
        }

        return "index";
    }

}

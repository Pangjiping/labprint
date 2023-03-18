package org.epha.com.labprint.controller;

import lombok.extern.slf4j.Slf4j;
import org.epha.com.labprint.entity.BizException;
import org.epha.com.labprint.entity.ResponseData;
import org.epha.com.labprint.enums.ResponseEnum;
import org.epha.com.labprint.service.PrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@RestController
@Slf4j
public class PrintController {

    @Value("${file.location}")
    private String fileLocation;

    @Autowired
    private PrintService printService;

    @PostMapping("/print/pdf")
    public ResponseData<String> printPdf(@RequestParam("copies") Integer copies,
                                 @RequestParam("studentId") String studentId,
                                 // @RequestParam("doubleSides") Boolean doubleSides,
                                 @RequestParam("files") MultipartFile[] files) {

        List<String> fileList = new ArrayList<>();
        if (files.length > 0) {
            for (var file : files) {
                String studentFilePath = fileLocation + studentId + "\\";
                log.info("服务器保存文件成功！");
//                createFilePath(new File(studentFilePath));
                try {
                    file.transferTo(new File(studentFilePath + file.getOriginalFilename()));
                } catch (IOException e) {
                    throw new BizException(ResponseEnum.ERROR);
                }
                fileList.add(file.getOriginalFilename());
            }
        }

        // 打印
        for (String fileName : fileList) {
//            printService.printPdf(null, fileLocation + fileName, copies);
            log.info("开始打印");
            log.error("开始打印error");
        }

//        List<String> logs = getLogs("D:/logs/error.log");

        return ResponseData.success("success");
    }

    /**
     * 判断文件是否存在，不存在就创建
     * @param file
     */
    public void createFilePath(File file) {
        if (file.exists()) {
            log.info("File exists");
        } else {
            log.info("File not exists, create it ...");
            file.mkdir();
        }
    }

    public List<String> getLogs(String fileName) throws IOException {
        List<String> logs = new ArrayList<String>();
        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null){
            // 一行一行地处理...
            logs.add(line);
        }
        return logs;
    }

}

package org.epha.com.labprint.controller;

import lombok.extern.slf4j.Slf4j;
import org.epha.com.labprint.entity.BizException;
import org.epha.com.labprint.entity.ResponseData;
import org.epha.com.labprint.enums.ResponseEnum;
import org.epha.com.labprint.pojo.PrintPdfOptions;
import org.epha.com.labprint.pojo.PrintPdfOrderlyOptions;
import org.epha.com.labprint.pojo.PrintPdfPagesOptions;
import org.epha.com.labprint.service.PrintService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Pangjiping
 * @date: 2023/3/19
 */
@RestController
@Slf4j
public class PrintController {

    @Value("${file.location}")
    private String fileLocation;

    @Resource
    private PrintService printService;

    @PostMapping("/print/pdf")
    public ResponseData<String> printPdf(@RequestParam(value = "copies", required = false) Integer copies,
                           @RequestParam(value = "owner") String owner,
                           @RequestParam(value = "startPage", required = false) Integer startPage,
                           @RequestParam(value = "endPage", required = false) Integer endPage,
                           @RequestParam(value = "pageIndex", required = false) List<Integer> pages,
                           @RequestParam(value = "files") MultipartFile[] files) throws Exception {

        List<PrintPdfOptions> printOptionsList = new ArrayList<>();
        // 不用对files判空
        for (var file : files) {
            String ownerFilePath = fileLocation + owner + "\\";
            createFilePath(new File(ownerFilePath));

            try {
                file.transferTo(new File(ownerFilePath + file.getOriginalFilename()));
            } catch (IOException e) {
                throw new BizException(ResponseEnum.ERROR);
            }

            if (startPage != null && endPage != null) {
                // 连续打印优先级最高
                printOptionsList.add(new PrintPdfOrderlyOptions(
                        fileLocation,
                        owner,
                        file.getOriginalFilename(),
                        copies == null ? 1 : copies,
                        startPage,
                        endPage
                ));
            } else if (pages != null && pages.size() > 0) {
                // 页索引打印
                printOptionsList.add(new PrintPdfPagesOptions(
                        fileLocation,
                        owner,
                        file.getOriginalFilename(),
                        copies == null ? 1 : copies,
                        pages
                ));
            } else {
                // 兜底：上传文件就必须打印
                printOptionsList.add(new PrintPdfOrderlyOptions(
                        fileLocation,
                        owner,
                        file.getOriginalFilename(),
                        copies == null ? 1 : copies,
                        startPage,
                        endPage
                ));
            }
            for (PrintPdfOptions options : printOptionsList) {
                printService.printPdf(null, options);
            }
        }
        return ResponseData.success("success");
    }

    /**
     * 判断文件是否存在，不存在就创建
     * @param file
     */
    private void createFilePath(File file) {
        if (file.exists()) {
            log.info("File exists");
        } else {
            log.info("File not exists, create it ...");
            file.mkdir();
        }
    }

    @Deprecated
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

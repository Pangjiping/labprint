package org.epha.com.labprint.controller;

import org.epha.com.labprint.service.PrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@RestController
public class PrintController {

    @Value("${file.location}")
    private String fileLocation;

    @Autowired
    private PrintService printService;

    @PostMapping("/print/pdf")
    public String printPdf(@RequestParam("copies") Integer copies,
                           // @RequestParam("doubleSides") Boolean doubleSides,
                           @RequestParam("files") MultipartFile[] files)throws Exception{

        List<String> fileList = new ArrayList<>();
        if (files.length>0){
            for (var file:files){
                file.transferTo(new File(fileLocation+file.getOriginalFilename()));
                fileList.add(file.getOriginalFilename());
            }
        }

        // 打印
        for (String fileName:fileList){
            printService.printPdf(null,fileLocation+fileName,copies);
        }

        return "index";
    }

}

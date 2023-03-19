package org.epha.com.labprint.controller;

import org.epha.com.labprint.pojo.PrintPdfOptions;
import org.epha.com.labprint.pojo.PrintPdfOrderlyOptions;
import org.epha.com.labprint.pojo.PrintPdfPagesOptions;
import org.epha.com.labprint.service.PrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PrintController {

    @Value("${file.location}")
    private String fileLocation;

    @Autowired
    private PrintService printService;

    @PostMapping("/print/pdf")
    public String printPdf(@RequestParam(value = "copies", required = false) Integer copies,
                           @RequestParam(value = "owner") String owner,
                           @RequestParam(value = "startPage", required = false) Integer startPage,
                           @RequestParam(value = "endPage", required = false) Integer endPage,
                           @RequestParam(value = "pageIndex", required = false) List<Integer> pages,
                           @RequestParam(value = "files") MultipartFile[] files) throws Exception {

        List<PrintPdfOptions> printOptionsList = new ArrayList<>();
        if (files.length > 0) {
            for (var file : files) {
                file.transferTo(new File(fileLocation + file.getOriginalFilename()));


                if (startPage != null) {
                    printOptionsList.add(new PrintPdfOrderlyOptions(
                            owner,
                            file.getOriginalFilename(),
                            copies == null ? 1 : copies,
                            startPage,
                            endPage
                    ));
                } else if (pages != null && pages.size() > 0) {
                    printOptionsList.add(new PrintPdfPagesOptions(
                            owner,
                            file.getOriginalFilename(),
                            copies == null ? 1 : copies,
                            pages
                    ));
                }
            }
        }

        // 打印
        for (PrintPdfOptions options : printOptionsList) {
            printService.printPdf(null, options);
        }

        return "index";
    }

}

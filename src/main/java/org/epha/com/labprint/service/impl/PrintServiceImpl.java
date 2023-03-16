package org.epha.com.labprint.service.impl;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.epha.com.labprint.runner.PrinterRunner;
import org.epha.com.labprint.service.PrintService;
import org.epha.com.labprint.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class PrintServiceImpl implements PrintService {

    @Value("${file.location}")
    private String fileLocation;

    @Override
    public void printPdf(String printName, String fileLocation, Integer copies){
        // 获取打印机
        javax.print.PrintService printer = PrinterRunner.getPrinter(printName);
        FileInputStream fis = null;
        // 打印pdf
        try{
            fis = FileUtils.openInputStream(new File(fileLocation));
            PDDocument document = PDDocument.load(fis);
            PrinterJob job = PrinterJob.getPrinterJob();

            job.setPageable(new PDFPageable(document));
            job.setPrintService(printer);
            job.setCopies(copies);
            job.print();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            try{
                if (null!=fis){
                    fis.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}

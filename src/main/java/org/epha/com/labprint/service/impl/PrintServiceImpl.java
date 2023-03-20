package org.epha.com.labprint.service.impl;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.epha.com.labprint.pojo.PrintPdfOptions;
import org.epha.com.labprint.pojo.PrintPdfOrderlyOptions;
import org.epha.com.labprint.pojo.PrintPdfPagesOptions;
import org.epha.com.labprint.runner.PrinterRunner;
import org.epha.com.labprint.service.PrintService;
import org.epha.com.labprint.utils.PdfMergerUtil;
import org.springframework.stereotype.Service;

import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;

/**
 * @author: Pangjiping
 * @date: 2023/3/19
 */
@Service
public class PrintServiceImpl implements PrintService {

    @Override
    public void printPdf(String printerName, PrintPdfOptions options) {
        // 获取打印机
        javax.print.PrintService printer = PrinterRunner.getPrinter(printerName);
        FileInputStream fis = null;
        PDDocument document = null;
        int copies = 1;
        // 打印pdf
        try {
            fis = FileUtils.openInputStream(new File(options.getFileFullName()));
            document = PDDocument.load(fis);

            // 裁剪pdf
            if (options instanceof PrintPdfOrderlyOptions) {
                PrintPdfOrderlyOptions printPdfOrderlyOptions = (PrintPdfOrderlyOptions) options;
                copies = printPdfOrderlyOptions.getCopies() > 0 ? printPdfOrderlyOptions.getCopies() : 1;
                if (printPdfOrderlyOptions.getStartPage() != null
                        && printPdfOrderlyOptions.getEndPage() != null
                        && printPdfOrderlyOptions.getStartPage() > 0
                        && printPdfOrderlyOptions.getEndPage() > 0) {
                    PdfMergerUtil.mergePdf(document, printPdfOrderlyOptions.getStartPage(), printPdfOrderlyOptions.getEndPage());
                }
            } else if (options instanceof PrintPdfPagesOptions) {
                PrintPdfPagesOptions printPdfPagesOptions = (PrintPdfPagesOptions) options;
                copies = printPdfPagesOptions.getCopies() > 0 ? printPdfPagesOptions.getCopies() : 1;
                if (printPdfPagesOptions.getPages().size() > 0) {
                    PdfMergerUtil.mergePdfByIndex(document, printPdfPagesOptions.getPages());
                }
            }

            PrinterJob job = PrinterJob.getPrinterJob();

            job.setPageable(new PDFPageable(document));
            job.setPrintService(printer);
            job.setCopies(copies);
            job.print();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (null != document) {
                    document.close();
                }
                if (null != fis) {
                    fis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

package org.epha.com.labprint.service;

import org.epha.com.labprint.pojo.PrintPdfOptions;

public interface PrintService {
    void printPdf(String printerName, PrintPdfOptions options);
}

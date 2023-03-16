package org.epha.com.labprint.service;

import java.io.FileInputStream;

public interface PrintService {
    void printPdf(String printName, String fileLocation,Integer copies);
}

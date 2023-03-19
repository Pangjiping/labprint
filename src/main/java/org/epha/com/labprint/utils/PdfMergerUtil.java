package org.epha.com.labprint.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.util.List;

@Slf4j
public class PdfMergerUtil {
    public static void mergePdf(PDDocument document, int start, int end) {
        if (start < end) {
            return;
        }

        int documentNumberOfPages = document.getNumberOfPages();
        if (end >= documentNumberOfPages) {
            return;
        }
        log.debug("document.getNumberOfPages() returns {}", documentNumberOfPages);

        for (int i = end + 1; i <= documentNumberOfPages; i++) {
            document.removePage(i);
        }
    }

    public static void mergePdfByIndex(PDDocument document, List<Integer> pages) {
        if (pages.size() <= 0) {
            return;
        }

        int documentNumberOfPages = document.getNumberOfPages();
        for (int i = 1; i <= documentNumberOfPages; i++) {
            if (!pages.contains(i)) {
                document.removePage(i);
            }
        }
    }
}

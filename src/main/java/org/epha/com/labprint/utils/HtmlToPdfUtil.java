package org.epha.com.labprint.utils;

import org.springframework.beans.factory.annotation.Value;

@Deprecated
public class HtmlToPdfUtil {
    @Value("${file.pdf-tool}")
    private static String toPdfTool;

    public static void convert(String inputFile, String outputFile) {
        StringBuilder builder = new StringBuilder();

        builder.append(toPdfTool);
        builder.append(" -L 0"); // 左边距为0
        builder.append(" -T 0"); // 上边距为0
        builder.append(" ");
        builder.append(inputFile);
        builder.append(" ");
        builder.append(outputFile);

        try {
            Process process = Runtime.getRuntime().exec(builder.toString());
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

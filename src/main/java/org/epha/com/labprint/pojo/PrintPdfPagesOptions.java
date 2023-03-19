package org.epha.com.labprint.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PrintPdfPagesOptions implements PrintPdfOptions {
    @Value("${file.location}")
    private static String fileLocation;

    private String owner;
    private String fileName;
    private int copies;
    private List<Integer> pages;

    @Override
    public String getFileFullName() {
        StringBuilder builder = new StringBuilder(fileLocation);
        builder.append(getOwner()).append("\\").append(fileName);
        return builder.toString();
    }
}

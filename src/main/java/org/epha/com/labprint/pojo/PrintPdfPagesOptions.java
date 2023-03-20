package org.epha.com.labprint.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author: Pangjiping
 * @date: 2023/3/19
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class PrintPdfPagesOptions implements PrintPdfOptions {
    private String fileLocation;
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

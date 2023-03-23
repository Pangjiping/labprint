package org.epha.com.labprint.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author: Pangjiping
 * @date: 2023/3/19
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class PrintPdfOrderlyOptions implements PrintPdfOptions {
    private String fileLocation;
    private String owner;
    private String fileName;
    private Integer copies;
    private Integer startPage;
    private Integer endPage;

    @Override
    public String getFileFullName() {
        StringBuilder builder = new StringBuilder(fileLocation);
        builder.append(getOwner()).append("\\").append(fileName);
        return builder.toString();
    }
}

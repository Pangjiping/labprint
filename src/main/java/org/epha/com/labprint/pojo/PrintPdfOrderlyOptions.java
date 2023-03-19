package org.epha.com.labprint.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author: Pangjiping
 * @date: 2023/3/19
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class PrintPdfOrderlyOptions implements PrintPdfOptions {
    @Value("${file.location}")
    private static String fileLocation;


    private String owner;
    private String fileName;
    private int copies;
    private int startPage;
    private int endPage;

    @Override
    public String getFileFullName() {
        StringBuilder builder = new StringBuilder(fileLocation);
        builder.append(getOwner()).append("\\").append(fileName);
        return builder.toString();
    }
}

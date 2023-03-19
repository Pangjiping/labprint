package org.epha.com.labprint.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

/**
 * @author: Pangjiping
 * @date: 2023/3/19
 */
@Component
@Slf4j
public class PrinterRunner implements ApplicationRunner {

    private static PrintService[] services;

    @Deprecated
    private static PrintRequestAttributeSet aset;

    @Deprecated
    public static void initial() {
        aset = new HashPrintRequestAttributeSet();
        services = PrintServiceLookup.lookupPrintServices(null, null);
    }

    /**
     * 初始化打印机服务对象，在SpringBoot启动时执行一次，确保可以正确扫描到打印机
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        aset = new HashPrintRequestAttributeSet();
        services = PrintServiceLookup.lookupPrintServices(null, null);
        log.info("printer service found: {}", services);
    }

    /**
     * 根据打印机名称查找打印机，找不到将使用默认打印机
     *
     * @param name 打印机名称
     * @return PrintService对象
     */
    public static PrintService getPrinter(String name) {
        PrintService printer = null;
        if (null != services && null != name) {
            for (int i = 0; i < services.length; i++) {
                String printerName = services[i].toString();
                if (printerName.equals(name)) {
                    printer = services[i];
                    log.info("target printer service {}:{} found.", printerName, printer);
                    break;
                }
            }
        } else {
            log.warn("target printer service {} not found, default printer service will be used.", name);
            printer = PrintServiceLookup.lookupDefaultPrintService();
        }

        if (printer == null) {
            log.error("not found any printer.");
        }
        return printer;
    }
}

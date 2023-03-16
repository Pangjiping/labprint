package org.epha.com.labprint.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

@Component
@Slf4j
public class PrinterRunner implements ApplicationRunner {

    private static PrintService[] services;

    private static PrintRequestAttributeSet aset;

    /**
     * 初始化获取打印服务对象
     */
    public static void initial() {
        // 设置打印机设置
        aset = new HashPrintRequestAttributeSet();
        // 查找打印机服务
        services = PrintServiceLookup.lookupPrintServices(null, null);
    }

    /**
     * 初始化打印机服务对象
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        aset = new HashPrintRequestAttributeSet();
        services = PrintServiceLookup.lookupPrintServices(null, null);
        log.info("printer service found: {}",services);
    }

    /**
     * 根据打印机名称查找打印机，找不到将使用默认打印机
     * @param name 打印机名称
     * @return PrintService对象
     */
    public static PrintService getPrinter(String name){
        PrintService printer = null;
        if (null!=services&&null!=name){
            for (int i = 0; i < services.length; i++) {
                log.debug("printer service {} found.",services[i]);
                String printerName = services[i].toString();
                if (printerName.equals(name)){
                    printer=services[i];
                    log.info("target printer service {}:{} found.",printerName,printer);
                    break;
                }
            }
        }else{
            log.warn("target printer service {} not found, default printer service will be used.",name);
            printer = PrintServiceLookup.lookupDefaultPrintService();
        }

        if (printer==null){
            log.error("not found any printer.");
        }
        return printer;
    }
}

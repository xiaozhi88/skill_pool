package com.meitq.cifox.api.module.productmanagement.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * word转pdf
 * 借助LibreOffice转pdf
 *
 * @author zhangzy
 * @date 2020/8/19-10:46
 * @since v1.0
 */
@Log4j2
public class WordToPdfByLibreOffice {

    /**
     * word转pdf
     *
     * @param docxPath docx文件
     * @author zhangzy
     * @date 2020/8/19
     * @since v1.0
     */
    public static boolean wordConverterToPdf(String docxPath) throws IOException {
        File file = new File(docxPath);
        String path = file.getParent();
        try {
            String osName = System.getProperty("os.name");
            String officeHome = "";
            String command = "";
            if (osName.contains("Windows")) {
                docxPath = " D:" + docxPath;
                //window默认安装路径
                officeHome = "C:/Program Files/LibreOffice/program/";
                command = officeHome + "soffice --headless --invisible --convert-to pdf" + " " +  docxPath + " --outdir " + path;
            } else {
                command = "libreoffice6.4 --headless --convert-to pdf " +  docxPath + " --outdir " + path;
                // command = "doc2pdf --output=" + path + File.separator + file.getName().replaceAll(".(?i)docx", ".pdf") + " " + docxPath;
            }
            log.info("执行的命令: " + command);
            String result = executeCommand(command);
            System.out.println("生成pdf的result==" + result);
            if (result.equals("") || result.contains("writer_pdf_Export")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return false;
    }

    /**
     * 执行命令
     *
     * @param command 待执行命令
     * @return 返回 java.lang.String 描述此返回参数
     * @author zhangzy
     * @date 2020/8/19
     * @since v1.0
     */
    public static String executeCommand(String command) {
        StringBuffer output = new StringBuffer();
        Process p;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            inputStreamReader = new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8);
            reader = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
            //这个一般不需要
            //p.destroy();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(inputStreamReader);
        }
        System.out.println(output.toString());
        return output.toString();

    }

    //测试
    public static void main(String[] args) {
        try {
            // 建议都用/左斜杠这种，左斜杠是windows和linux通用的
            wordConverterToPdf("D:\\word_pdf_test\\demo\\product\\m.docx");
        } catch (IOException e) {
            System.out.println("word转换成pdf时出错");
            e.printStackTrace();
        }
    }
}

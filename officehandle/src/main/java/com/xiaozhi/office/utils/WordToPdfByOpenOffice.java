package com.meitq.cifox.api.module.productmanagement.utils;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * word转pdf
 * 借助openOffice转pdf,转换格式问题兼容较好,服务器部署复杂一些
 *
 * @author zhangzy
 * @date 2020/8/17-19:20
 * @since v1.0
 */
public class WordToPdfByOpenOffice {
    /**
     * word转pdf(openOffice启动命令写入代码中)
     *
     * @param sourceFile 源文件
     * @param destFile 生成的pdf文件
     * @return 返回 int 描述此返回参数
     * @author zhangzy
     * @date 2020/8/17
     * @since v1.0
     */
    public static int officeToPDF(String sourceFile, String destFile) {
        String osName = System.getProperty("os.name");
        String command = "";
        // OpenOffice的安装目录,
        String openOfficeHome = "";
        if (osName.contains("Windows")) {
            openOfficeHome = "C:/Program Files (x86)/OpenOffice 4/";
            command = openOfficeHome
                    + "program/soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";
        }else {
            openOfficeHome = "/opt/openoffice4/";
            command = openOfficeHome
                    + "program/soffice -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\" -nofirststartwizard &";
        }
        Process pro = null;
        try {
            // 启动OpenOffice的服务
            pro = Runtime.getRuntime().exec(command);
            // 创建连接,端口默认8100
            OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
            connection.connect();
            // 转pdf
            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
            converter.convert(new File(sourceFile), new File(destFile));
            // 关闭openOffice连接
            connection.disconnect();
            // 封闭OpenOffice服务的进程
            pro.destroy();
            return 0;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert pro != null;
            pro.destroy();
        }
        return 1;
    }

    /**
     * word转pdf(未加入openOffice启动命令)
     *
     * @param input word文件
     * @param output 输出的pdf文件
     * @author zhangzy
     * @date 2020/8/17
     * @since v1.0
     */
    public static void convert(String input, String output){
        File inputFile = new File(input);
        File outputFile = new File(output);
        OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
        try {
            connection.connect();
            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
            converter.convert(inputFile, outputFile);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try{
                connection.disconnect();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        officeToPDF("D:\\word_pdf_test\\模板\\docx模板\\高温日数\\2016年4月重庆市气候影响评价.docx",
                "D:\\word_pdf_test\\模板\\docx模板\\高温日数\\2016年4月重庆市气候影响评价.pdf" );
    }
}

package com.xiaozhi.office.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 压缩解压工具类
 *
 * @author zhangzy
 * @date 2020/8/19-09:16
 * @since v1.0
 */
public class ZipUtils {


    /**
     * 压缩文件
     *
     * @param zipFileName 保存的压缩包文件路径
     * @param inputFile 需要压缩的文件夹或者文件路径
     * @author zhangzy
     * @date 2020/8/19
     * @since v1.0
     */
    public static void zip(String inputFile, String zipFileName) throws Exception {
        zip(new File(inputFile), zipFileName);
    }

    private static void zip( File inputFile, String zipFileName) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
                zipFileName));
        //递归压缩方法
        zip(out, inputFile, "");
        System.out.println("zip done");
        out.close();
    }

    /**
     * 递归压缩方法
     *
     * @param out 压缩包输出流
     * @param f 需要压缩的文件
     * @param base 压缩的路径
     * @author zhangzy
     * @date 2020/8/19
     * @since v1.0
     */
    private static void zip(ZipOutputStream out, File f, String base) throws Exception {
        if (f.isDirectory()) {
            // 如果是文件夹，则获取下面的所有文件
            File[] fl = f.listFiles();
            //此处要将文件写到文件夹中只用在文件名前加"/"再加文件夹名
            out.putNextEntry(new ZipEntry(base + "/"));
            base = base.length() == 0 ? "" : base + "/";
            for (int i = 0; i < fl.length; i++) {
                zip(out, fl[i], base + fl[i].getName());
            }
        } else {
            // 如果是文件，则压缩
            // 生成下一个压缩节点
            out.putNextEntry(new ZipEntry(base));
            // 读取文件内容
            FileInputStream in = new FileInputStream(f);
            int b;
            while ((b = in.read()) != -1) {
                // 写入到压缩包
                out.write(b);
            }
            in.close();
        }
    }

    /**
     *
     * 解压文件
     *
     * @param zipPath 要解压的目标文件
     * @param descDir 指定解压目录
     * @return 解压结果：成功，失败
     * @author zhangzy
     * @date 2020/8/18
     * @since v1.0
     */
    public static boolean unZip(String zipPath, String descDir) {
        File zipFile = new File(zipPath);
        boolean flag = false;
        File pathFile = new File(descDir);
        if(!pathFile.exists()){
            pathFile.mkdirs();
        }
        ZipFile zip = null;
        try {
            //防止中文目录，乱码
            zip = new ZipFile(zipFile, Charset.forName("gbk"));
            for(Enumeration entries = zip.entries(); entries.hasMoreElements();){
                ZipEntry entry = (ZipEntry)entries.nextElement();
                String zipEntryName = entry.getName();
                InputStream in = zip.getInputStream(entry);
                //指定解压后的文件夹+当前zip文件的名称
                String outPath = (descDir+zipEntryName).replace("/", File.separator);
                //判断路径是否存在,不存在则创建文件路径
                File file = new File(outPath.substring(0, outPath.lastIndexOf(File.separator)));
                if(!file.exists()){
                    file.mkdirs();
                }
                //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
                if(new File(outPath).isDirectory()){
                    continue;
                }
                //保存文件路径信息（可利用md5.zip名称的唯一性，来判断是否已经解压）
                OutputStream out = new FileOutputStream(outPath);
                byte[] buf1 = new byte[2048];
                int len;
                while((len=in.read(buf1))>0){
                    out.write(buf1,0,len);
                }
                in.close();
                out.close();
            }
            flag = true;
            zip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    // 测试
    public static void main(String [] args){
        try {
            zip("D:\\word_pdf_test\\to_docx\\abc",
                    "D:\\word_pdf_test\\to_docx\\abc.zip");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package com.meitq.cifox.api.module.productmanagement.utils;

import org.apache.commons.collections.MapUtils;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * word转pdf(.docx文件)
 * poi形式转pdf,转换存在问题:不能很好兼容docx文件中的格式
 *
 * @author zhangzy
 * @date 2020/8/17-19:25
 * @since v1.0
 */
public class WordToPdfByPoi {
    /**
     * 将word文档， 转换成pdf, 中间替换掉变量
     *
     * @param source 源为word文档， 必须为docx文档
     * @param target 目标输出
     * @param params 需要替换的变量
     * @author zhangzy
     * @date 2020/8/17
     * @since v1.0
     */
    public static void wordConverterToPdf(InputStream source,
                                          OutputStream target, Map<String, String> params) throws Exception {
        wordConverterToPdf(source, target, null, params);
    }

    /**
     * 将word文档， 转换成pdf, 中间替换掉变量
     *
     * @param source 源为word文档， 必须为docx文档
     * @param target 目标输出
     * @param params 需要替换的变量
     * @param options PdfOptions.create().fontEncoding( "windows-1250" ) 或者其他
     * @author zhangzy
     * @date 2020/8/17
     * @since v1.0
     */
    public static void wordConverterToPdf(InputStream source, OutputStream target,
                                          PdfOptions options,
                                          Map<String, String> params) throws Exception {
        XWPFDocument doc = new XWPFDocument(source);
        paragraphReplace(doc.getParagraphs(), params);
        for (XWPFTable table : doc.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    paragraphReplace(cell.getParagraphs(), params);
                }
            }
        }
        PdfConverter.getInstance().convert(doc, target, options);
    }

    /**
     * 替换段落中内容
     *
     * @param paragraphs
     * @param params
     * @author zhangzy
     * @date 2020/8/17
     * @since v1.0
     */
    private static void paragraphReplace(List<XWPFParagraph> paragraphs, Map<String, String> params) {
        if (MapUtils.isNotEmpty(params)) {
            for (XWPFParagraph p : paragraphs){
                for (XWPFRun r : p.getRuns()){
                    String content = r.getText(r.getTextPosition());
                    if(StringUtils.isNotEmpty(content) && params.containsKey(content)) {
                        r.setText(params.get(content), 0);
                    }
                }
            }
        }
    }

    /**
     * word转pdf
     *
     * @param wordFile  word文件
     * @param pdfFile pdf文件
     * @author zhangzy
     * @date 2020/8/17
     * @since v1.0
     */
    public static void wordToPdf(String wordFile, String pdfFile){
        InputStream source;
        OutputStream target;
        try {
            source = new FileInputStream(wordFile);
            target = new FileOutputStream(pdfFile);
            Map<String, String> params = new HashMap<String, String>();
            PdfOptions options = PdfOptions.create();
            wordConverterToPdf(source, target, options, params);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        String filepath = "D:\\word_pdf_test\\模板\\docx模板\\月气候评价\\2016年4月重庆市气候影响评价.docx";
        String outpath = "D:\\word_pdf_test\\模板\\docx模板\\月气候评价\\2016年4月重庆市气候影响评价.pdf";
        wordToPdf(filepath, outpath);
    }
}  
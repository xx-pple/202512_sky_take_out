package com.sky.POI;


import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
@Slf4j
@Component
public class POITest {
    /**
     * 基于POI向Excel⽂件写⼊数据
     * @throws Exception
     */
    public static void write() throws Exception{
//在内存中创建⼀个Excel⽂件对象
        XSSFWorkbook excel = new XSSFWorkbook();
//创建Sheet⻚
        XSSFSheet sheet = excel.createSheet("itcast");
//在Sheet⻚中创建⾏，0表⽰第1⾏
        XSSFRow row1 = sheet.createRow(0);
//创建单元格并在单元格中设置值，单元格编号也是从0开始，1表⽰第2个单元格
        row1.createCell(1).setCellValue("姓名");
        row1.createCell(2).setCellValue("城市");
        XSSFRow row2 = sheet.createRow(1);
        row2.createCell(1).setCellValue("张三");
        row2.createCell(2).setCellValue("北京");
        XSSFRow row3 = sheet.createRow(2);
        row3.createCell(1).setCellValue("李四");
        row3.createCell(2).setCellValue("上海");
        log.info("文件已经写入： D:\\PROJECTS\\CourseWork\\web_develop\\work1\\new20251015\\itcast.xlsx");
        FileOutputStream out = new FileOutputStream(new File("D:\\PROJECTS\\CourseWork\\web_develop\\work1\\new20251015\\itcast.xlsx"));
        //通过输出流将内存中的Excel⽂件写⼊到磁盘上
        excel.write(out);
//关闭资源
        out.flush();
        out.close();
        excel.close();
    }
    /**
     * 基于POI读取Excel⽂件
     * @throws Exception
     */
    public static void read() throws Exception{
        FileInputStream in = new FileInputStream(new File("D:\\PROJECTS\\CourseWork\\web_develop\\work1\\new20251015\\itcast.xlsx"));
//通过输⼊流读取指定的Excel⽂件
        XSSFWorkbook excel = new XSSFWorkbook(in);
//获取Excel⽂件的第1个Sheet⻚
        XSSFSheet sheet = excel.getSheetAt(0);
//获取Sheet⻚中的最后⼀⾏的⾏号
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 0; i <= lastRowNum; i++) {
//获取Sheet⻚中的⾏
            XSSFRow titleRow = sheet.getRow(i);

//获取⾏的第2个单元格
            XSSFCell cell1 = titleRow.getCell(1);
//获取单元格中的⽂本内容
            String cellValue1 = cell1.getStringCellValue();
//获取⾏的第3个单元格
            XSSFCell cell2 = titleRow.getCell(2);
//获取单元格中的⽂本内容
            String cellValue2 = cell2.getStringCellValue();
            System.out.println(cellValue1 + " " +cellValue2);
        }
//关闭资源
        in.close();
        excel.close();
    }
    public static void main(String[] args) throws Exception {
        read();
    }
}

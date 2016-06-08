package oe.roma.photodoc.reports;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.*;
import oe.roma.photodoc.domain.Customer;
import oe.roma.photodoc.domain.CustomerObject;
import oe.roma.photodoc.domain.File;
import org.springframework.web.servlet.view.document.AbstractJExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by us8610 on 03.06.14.
 */
@SuppressWarnings("unchecked")
public class MainReportBuilder extends AbstractJExcelView {

    private static final String URL = "http://10.93.104.55:88/photodoc/";

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      WritableWorkbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        // get data model which is passed by the Spring container
        List<Customer> list = (List<Customer>) model.get("list");
        //List<File> listFile = (List<File>) model.get("list");

        WritableFont text1 = new WritableFont(WritableFont.TIMES, 12);
        WritableFont text3 = new WritableFont(WritableFont.TIMES, 12);
        text3.setColour(jxl.format.Colour.BLUE);

        DateFormat customDateFormat = new DateFormat ("dd.MM.yyyy");

        WritableCellFormat dateFormat = new WritableCellFormat (text1,customDateFormat);
        dateFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        dateFormat.setAlignment(Alignment.CENTRE);

        //для рєєстр
        WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12,WritableFont.BOLD);
        WritableCellFormat main = new WritableCellFormat(cellFont);

        //для контроролю
        WritableCellFormat header = new WritableCellFormat(cellFont);
        header.setBorder(Border.ALL, BorderLineStyle.THIN);


        WritableCellFormat normal = new WritableCellFormat(text1);
        normal.setBorder(Border.ALL, BorderLineStyle.THIN);
        normal.setWrap(true);

        WritableCellFormat normalh = new WritableCellFormat(text3);
        normalh.setBorder(Border.ALL, BorderLineStyle.THIN);
        normalh.setWrap(true);

        WritableFont text2 = new WritableFont(WritableFont.TIMES, 12);

        WritableCellFormat normal1 = new WritableCellFormat(text2);
        normal1.setBorder(Border.ALL, BorderLineStyle.THIN);

        WritableCellFormat normal2 = new WritableCellFormat(text2);
        normal2.setBorder(Border.ALL, BorderLineStyle.THIN);
        normal2.setAlignment(Alignment.CENTRE);
        normal2.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

        main.setAlignment(Alignment.CENTRE);
        main.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

        header.setAlignment(Alignment.CENTRE);
        header.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

        normal.setAlignment(Alignment.CENTRE);
        normal.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

        normalh.setAlignment(Alignment.CENTRE);
        normalh.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

        // create a new Excel sheet
        String reportValue0 = "РЕМ";
        String reportValue1 = "Номер договору";
        String reportValue2 = "Номер лічильника";
        String reportValue3 = "Споживач";
        String reportValue4 = "Об'єкт";
        String reportValue5 = "Адреса об'єкту";
        String reportValue6 = "Вид фотодокументу";
        String reportValue7 = "Дата фотодокументу";
        String reportValue8 = "Інспектор";
        String reportValue9 = "Фото";
        String reportValue10 = "Користувач";


        String utf8String0 = new String(reportValue0.getBytes("windows-1251"), "UTF-8");
        String utf8String1 = new String(reportValue1.getBytes("windows-1251"), "UTF-8");
        String utf8String2 = new String(reportValue2.getBytes("windows-1251"), "UTF-8");
        String utf8String3 = new String(reportValue3.getBytes("windows-1251"), "UTF-8");
        String utf8String4 = new String(reportValue4.getBytes("windows-1251"), "UTF-8");
        String utf8String5 = new String(reportValue5.getBytes("windows-1251"), "UTF-8");
        String utf8String6 = new String(reportValue6.getBytes("windows-1251"), "UTF-8");
        String utf8String7 = new String(reportValue7.getBytes("windows-1251"), "UTF-8");
        String utf8String8 = new String(reportValue8.getBytes("windows-1251"), "UTF-8");
        String utf8String9 = new String(reportValue9.getBytes("windows-1251"), "UTF-8");
        String utf8String10 = new String(reportValue10.getBytes("windows-1251"), "UTF-8");

        WritableSheet sheet = workbook.createSheet("Звіт", 0);

        int start=0;
        sheet.addCell(new Label(0, start, utf8String0, normal2));
        sheet.addCell(new Label(1, start, utf8String1, normal2));
        sheet.addCell(new Label(2, start, utf8String2, normal2));
        sheet.addCell(new Label(3, start, utf8String3, normal2));
        sheet.addCell(new Label(4, start, utf8String4, normal2));
        sheet.addCell(new Label(5, start, utf8String5, normal2));
        sheet.addCell(new Label(6, start, utf8String6, normal2));
        sheet.addCell(new Label(7, start, utf8String7, normal2));
        sheet.addCell(new Label(8, start, utf8String8, normal2));
        sheet.addCell(new Label(9, start, utf8String9, normal2));
        sheet.addCell(new Label(10, start, utf8String10, normal2));
        for(int i=0;i<10;i++)
            sheet.setColumnView(i,40);
        start++;
        for(Customer customer : list){
           // User user =
            CustomerObject object = customer.getObjects().get(0);
            File currentFile = object.getFiles().get(0);
            URL  url = new URL(URL + currentFile.getName());
            sheet.addCell(new Label(0, start, currentFile.getDepartment().getName(),normal));
            sheet.addCell(new Label(1, start, customer.getContract_number(),normal));
            sheet.addCell(new Label(2, start, object.getCounter_number(),normal));
            sheet.addCell(new Label(3, start, customer.getName(),normal));
            sheet.addCell(new Label(4, start, object.getName(),normal));
            sheet.addCell(new Label(5, start, object.getAddress(),normal));
            sheet.addCell(new Label(6, start, currentFile.getTypeDocument().getName(),normal));
            sheet.addCell(new DateTime(7, start, currentFile.getDate(),dateFormat));
            sheet.addCell(new Label(8, start, object.getInspector(),normal));
            WritableHyperlink hl = new WritableHyperlink(9, start,url);
            sheet.addHyperlink(hl);
            sheet.addCell(new Label(9, start, currentFile.getName(), normal));
            sheet.addCell(new Label(10,start, currentFile.getUser_name(),normal));
            start++;
        }
    }
}
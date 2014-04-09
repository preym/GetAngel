package com.ehc.angel.api;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.ss.util.CellUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 3/4/14
 * Time: 3:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class AngelStartUp {
  private static HSSFWorkbook workbook;
  private HSSFFont boldFont;
  private HSSFDataFormat format;
  private HSSFRow row;
  private static HSSFSheet sheet;
  private int noOfRequests = 0;

  ReportColumn[] reportColumns = new ReportColumn[]{
      new ReportColumn("id", "Id", FormatType.formatType.INTEGER),
      new ReportColumn("name", "Name", FormatType.formatType.TEXT),
      new ReportColumn("follower_count", "Followers", FormatType.formatType.INTEGER),
      new ReportColumn("angellist_url", "AngelList Url", FormatType.formatType.TEXT),
      new ReportColumn("blog_url", "Blog Url", FormatType.formatType.TEXT),
      new ReportColumn("twitter_url", "Twitter Url", FormatType.formatType.TEXT),
      new ReportColumn("linkedin_url", "LinkedIn Url", FormatType.formatType.TEXT),
      new ReportColumn("community_profile", "Community Profile", FormatType.formatType.TEXT),
      new ReportColumn("company_url", "Company Url", FormatType.formatType.TEXT),
      new ReportColumn("created_at", "Created At", FormatType.formatType.TEXT),
      new ReportColumn("crunchbase_url", "Crunchbase Url", FormatType.formatType.TEXT),
      new ReportColumn("hidden", "Hidden", FormatType.formatType.TEXT),
      new ReportColumn("high_concept", "High Concept", FormatType.formatType.TEXT),
      new ReportColumn("logo_url", "Logo Url", FormatType.formatType.TEXT),
      new ReportColumn("product_desc", "Product Desc", FormatType.formatType.TEXT),
      new ReportColumn("quality", "Quality", FormatType.formatType.TEXT),
      new ReportColumn("screenshots", "ScreenShots", FormatType.formatType.TEXT),
      new ReportColumn("status", "Status", FormatType.formatType.TEXT),
      new ReportColumn("thumb_url", "Thumb Url", FormatType.formatType.TEXT),
      new ReportColumn("updated_at", "Updated At", FormatType.formatType.TEXT),
      new ReportColumn("video_url", "Video Url", FormatType.formatType.TEXT),
      new ReportColumn("location", "Location", FormatType.formatType.TEXT),
      new ReportColumn("locations", "Locations", FormatType.formatType.TEXT),
      new ReportColumn("market", "Market Tag", FormatType.formatType.TEXT),
      new ReportColumn("companyType", "Company Type", FormatType.formatType.TEXT)
  };


  List<Startups> startups = new ArrayList<Startups>();
  private static File file = new File("./angel-startup.xls");

  public static void main(String[] args) {
    try {
      AngelStartUp angelStartUpObj = new AngelStartUp();
      int noOfPages = angelStartUpObj.getStartups(1);
      for (int index = 2; index <= noOfPages; index++) {
        if (angelStartUpObj.noOfRequests < 1000) {
          angelStartUpObj.getStartups(index);
        } else {
          index--;
          Thread.currentThread().wait(70 * 60000);
          angelStartUpObj.noOfRequests = 0;
        }
      }
      appendDataToFile(angelStartUpObj);
      angelStartUpObj.startups.clear();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  private static void appendDataToFile(AngelStartUp oReport) throws Exception {
    FileInputStream fis = new FileInputStream(file);
    workbook = new HSSFWorkbook(fis, true);
    sheet = workbook.getSheetAt(workbook.getNumberOfSheets() - 1);
    oReport.appendDataToWorkbook(oReport.startups);
    fis.close();
    FileOutputStream outputStream = new FileOutputStream(file);
    oReport.write(outputStream);
    outputStream.flush();
    outputStream.close();

  }

  public void appendDataToWorkbook(List<?> data) {

    data = filterdata(data);


    try {
      for (int i = 0; i < data.size(); i++) {
        row = sheet.createRow(sheet.getLastRowNum() + 1);
        Object bean = data.get(i);
        for (int y = 0; y < reportColumns.length; y++) {
          Object value = PropertyUtils.getProperty(bean,
              reportColumns[y].getMethod());
          writeCell(row, y, value, reportColumns[y].getType(),
              reportColumns[y].getColor(), reportColumns[y].getFont());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Autosize columns
    for (int i = 0; i < reportColumns.length; i++) {
      sheet.autoSizeColumn((short) i);
    }
  }


  public List<?> filterdata(List<?> data) {
    List<Startups> list = new ArrayList();
    Iterator iterator = data.iterator();
    while (iterator.hasNext()) {
      Startups startup = ((Startups) iterator.next());
      String twitterUrl = startup.getTwitter_url();
      if (twitterUrl != null && !twitterUrl.equals("")) {
        list.add(startup);
      }
    }

    return list;
  }


  private int getStartups(int pageNumber) {
    int noOfPages = 1;
    try {
      HttpResponse<JsonNode> request = Unirest.get("https://api.angel.co/1/tags/1654/startups?page=" + pageNumber)
          .asJson();
      JsonNode node = request.getBody();
      System.out.println(node.toString());
      Gson gson = new Gson();
      this.noOfRequests++;
      StartUp startupList = gson.fromJson(node.toString(), StartUp.class);
      if (startupList != null) {
        startups.addAll(startupList.getStartups());
        noOfPages = startupList.getLast_page().intValue();
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      return noOfPages;
    }
  }

  public AngelStartUp() {
    try {
      if (!file.exists()) {
        FileOutputStream fos = new FileOutputStream(file);
        workbook = new HSSFWorkbook();
        boldFont = workbook.createFont();
        boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        format = workbook.createDataFormat();
        sheet = workbook.createSheet("Sheet1");
        this.addSheetToWorkbook();
        this.write(fos);
        fos.flush();
        fos.close();
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void addSheetToWorkbook() {
    int numCols = reportColumns.length;
    try {
      row = sheet.createRow(0);
      for (int i = 0; i < numCols; i++) {
        writeCell(row, i, reportColumns[i].getHeader(), FormatType.formatType.TEXT,
            null, this.boldFont);
      }
    } catch (Exception e) {
      System.err.println("Caught Generate Error exception: "
          + e.getMessage());
    }
  }

  public HSSFFont boldFont() {
    return boldFont;
  }

  public void write(OutputStream outputStream) throws Exception {
    workbook.write(outputStream);
  }

  private void writeCell(HSSFRow row, int col, Object value,
                         FormatType.formatType formatType, Short bgColor, HSSFFont font) throws Exception {

    HSSFCell cell = HSSFCellUtil.createCell(row, col, null);
    if (value == null) {
      return;
    }
    if (font != null) {
      HSSFCellStyle style = workbook.createCellStyle();
      style.setFont(font);
      cell.setCellStyle(style);
    }
    switch (formatType) {
      case TEXT:
        cell.setCellValue(value.toString());
        break;
      case INTEGER:
        cell.setCellValue(((Number) value).intValue());
        HSSFCellUtil.setCellStyleProperty(cell, workbook,
            CellUtil.DATA_FORMAT,
            HSSFDataFormat.getBuiltinFormat(("#,##0")));
        break;
      case FLOAT:
        cell.setCellValue(((Number) value).doubleValue());
        HSSFCellUtil.setCellStyleProperty(cell, workbook,
            CellUtil.DATA_FORMAT,
            HSSFDataFormat.getBuiltinFormat(("#,##0.00")));
        break;
      case DATE:
        cell.setCellValue((Date) value);
        HSSFCellUtil.setCellStyleProperty(cell, workbook,
            CellUtil.DATA_FORMAT,
            HSSFDataFormat.getBuiltinFormat(("m/d/yy")));
        break;
      case MONEY:
        cell.setCellValue(((Number) value).intValue());
        HSSFCellUtil.setCellStyleProperty(cell, workbook,
            CellUtil.DATA_FORMAT,
            format.getFormat("$#,##0.00;$#,##0.00"));
        break;
      case PERCENTAGE:
        cell.setCellValue(((Number) value).doubleValue());
        HSSFCellUtil.setCellStyleProperty(cell, workbook,
            CellUtil.DATA_FORMAT,
            HSSFDataFormat.getBuiltinFormat("0.00%"));
    }

    if (bgColor != null) {
      HSSFCellUtil.setCellStyleProperty(cell, workbook,
          CellUtil.FILL_FOREGROUND_COLOR, bgColor);
      HSSFCellUtil.setCellStyleProperty(cell, workbook,
          CellUtil.FILL_PATTERN, HSSFCellStyle.SOLID_FOREGROUND);
    }
  }

}

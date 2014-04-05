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
import java.util.List;

public class AngelUser {
  private static HSSFWorkbook workbook;
  private static HSSFFont boldFont;
  private static HSSFDataFormat format;
  private HSSFRow row;
  private static HSSFSheet sheet;
  public static File file = new File("./angel-list.xls");
  static int startIndex = 1;
  static int endIndex = 50;

  ReportColumn[] reportColumns = new ReportColumn[]{
      new ReportColumn("id", "Id", FormatType.formatType.INTEGER),
      new ReportColumn("name", "Name", FormatType.formatType.TEXT),
      new ReportColumn("bio", "BIO", FormatType.formatType.TEXT),
      new ReportColumn("follower_count", "Followers", FormatType.formatType.INTEGER),
      new ReportColumn("angellist_url", "AngelList Url", FormatType.formatType.TEXT),
      new ReportColumn("blog_url", "Blog Url", FormatType.formatType.TEXT),
      new ReportColumn("online_bio_url", "Online Bio Url", FormatType.formatType.TEXT),
      new ReportColumn("twitter_url", "Twitter Url", FormatType.formatType.TEXT),
      new ReportColumn("facebook_url", "Facebook Url", FormatType.formatType.TEXT),
      new ReportColumn("linkedin_url", "LinkedIn Url", FormatType.formatType.TEXT),
      new ReportColumn("aboutme_url", "About Me Url", FormatType.formatType.TEXT),
      new ReportColumn("github_url", "Github Url", FormatType.formatType.TEXT),
      new ReportColumn("dribbble_url", "Dribbble Url", FormatType.formatType.TEXT),
      new ReportColumn("behance_url", "Behance Url", FormatType.formatType.TEXT),
      new ReportColumn("what_ive_built", "What Ive Built", FormatType.formatType.TEXT),
      new ReportColumn("location", "Location", FormatType.formatType.TEXT),
      new ReportColumn("role", "Role", FormatType.formatType.TEXT),
      new ReportColumn("investor", "Is Investor?", FormatType.formatType.TEXT)
  };

  ArrayList<User> users = new ArrayList<User>();

  public static void main(String[] args) {
    try {
      AngelUser oReport = new AngelUser();
      startIndex = (int) oReport.getLastUser() + 1;
      endIndex = startIndex + 49;
      for (; endIndex <= 1000; ) {
        String queryString = "";
        for (int id = startIndex; id <= endIndex; id++) {
          queryString = queryString + id + ",";
        }
        oReport.getUsersFromServer(queryString);
        appendDataToFile(oReport);
        startIndex = endIndex + 1;
        endIndex = endIndex + 50;
        if (oReport.users.get(oReport.users.size() - 1).getId() % 100 == 0) {
          FileInputStream fis = new FileInputStream(file);
          workbook = new HSSFWorkbook(fis);
          fis.close();
          createNewSheet(oReport);
        }
        oReport.users.clear();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void createNewSheet(AngelUser oReport) throws Exception {
    FileOutputStream fos = new FileOutputStream(file);
    boldFont = workbook.createFont();
    boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    format = workbook.createDataFormat();
    sheet = workbook.createSheet("Sheet" + (workbook.getNumberOfSheets() + 1));
    oReport.addSheetToWorkbook();
    oReport.write(fos);
    fos.flush();
    fos.close();
  }

  private double getLastUser() {
    double lastUserIndex = 0;
    try {
      FileInputStream fis = new FileInputStream(file);
      workbook = new HSSFWorkbook(fis);
      fis.close();
      sheet = workbook.getSheetAt(workbook.getNumberOfSheets() - 1);
      int rowIndex = sheet.getLastRowNum();
      if (rowIndex == 0) {
        sheet = workbook.getSheetAt(workbook.getNumberOfSheets() - 2);
      }
      row = sheet.getRow(sheet.getLastRowNum());
      lastUserIndex = row.getCell(0).getNumericCellValue();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } finally {
      return lastUserIndex;
    }
  }

  private static void appendDataToFile(AngelUser oReport) throws Exception {
    FileInputStream fis = new FileInputStream(file);
    workbook = new HSSFWorkbook(fis, true);
    sheet = workbook.getSheetAt(workbook.getNumberOfSheets() - 1);
    oReport.appendDataToWorkbook(oReport.users);
    fis.close();
    FileOutputStream outputStream = new FileOutputStream(file);
    oReport.write(outputStream);
    outputStream.flush();
    outputStream.close();
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

  public void appendDataToWorkbook(List<?> data) {
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

  private void getUsersFromServer(String queryString) {
    try {
      HttpResponse<JsonNode> request = Unirest.get("https://api.angel.co/1/users/batch?ids=" + queryString)
          .asJson();
      JsonNode node = request.getBody();
      System.out.println(node.toString());
      Gson gson = new Gson();
      User[] user = gson.fromJson(node.toString(), User[].class);
      if (user != null && user.length > 0) {
        for (User eachUser : user) {
          users.add(eachUser);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public AngelUser() {
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
      e.printStackTrace();
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

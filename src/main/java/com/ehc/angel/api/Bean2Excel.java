package com.ehc.angel.api;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.ss.util.CellUtil;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bean2Excel {
  private static HSSFWorkbook workbook;
  private static HSSFFont boldFont;
  private static HSSFDataFormat format;
  private static int id;

  HSSFRow row;
  static HSSFSheet sheet;

  File file = new File("./angel-list.xls");
  ReportColumn[] reportColumns = new ReportColumn[]{
      new ReportColumn("id", "Id", FormatType.INTEGER),
      new ReportColumn("name", "Name", FormatType.TEXT),
      new ReportColumn("bio", "BIO", FormatType.TEXT),
      new ReportColumn("follower_count", "Followers", FormatType.INTEGER),
      new ReportColumn("angellist_url", "AngelList Url", FormatType.TEXT),
      new ReportColumn("blog_url", "Blog Url", FormatType.TEXT),
      new ReportColumn("online_bio_url", "Online Bio Url", FormatType.TEXT),
      new ReportColumn("twitter_url", "Twitter Url", FormatType.TEXT),
      new ReportColumn("facebook_url", "Facebook Url", FormatType.TEXT),
      new ReportColumn("linkedin_url", "LinkedIn Url", FormatType.TEXT),
      new ReportColumn("aboutme_url", "About Me Url", FormatType.TEXT),
      new ReportColumn("github_url", "Github Url", FormatType.TEXT),
      new ReportColumn("dribbble_url", "Dribbble Url", FormatType.TEXT),
      new ReportColumn("behance_url", "Behance Url", FormatType.TEXT),
      new ReportColumn("what_ive_built", "What Ive Built", FormatType.TEXT),
      new ReportColumn("location", "Location", FormatType.TEXT),
      new ReportColumn("role", "Role", FormatType.TEXT),
      new ReportColumn("investor", "Is Investor?", FormatType.TEXT)
  };

  ArrayList<User> users = new ArrayList<User>();


  static {
    workbook = new HSSFWorkbook();
    boldFont = workbook.createFont();
    boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    format = workbook.createDataFormat();
    sheet = workbook.createSheet("Sheet1");
  }


  public static void main(String[] args) {
    try {
      Bean2Excel oReport = new Bean2Excel();
      for (id = 62; id <= 71; id++) {
        oReport.getUsers();
      }
      oReport.writeData(oReport.users);
      FileOutputStream output = new FileOutputStream(oReport.file, true);
      oReport.write(output);
      output.close();
    } catch (ParseException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }


  public void addSheet() {
    System.out.println("In addSheet Method");

    int numCols = reportColumns.length;
    try {
      // Create the report header at row 0
      row = sheet.createRow(0);
      // Loop over all the column beans and populate the report headers
      for (int i = 0; i < numCols; i++) {
        System.out.println("Writing Column headings");

        // Get the header text from the bean and write it to the cell
        writeCell(row, i, reportColumns[i].getHeader(), FormatType.TEXT,
            null, this.boldFont);
      }
      System.out.println("row count is:" + sheet.getLastRowNum());

    } catch (Exception e) {
      System.err.println("Caught Generate Error exception: "
          + e.getMessage());
    }
  }


  public void writeData(List<?> data) {


    System.out.println("in WriteData");
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(file);
      workbook = new HSSFWorkbook(fis);
      sheet = workbook.getSheetAt(0);

      sheet = workbook.getSheet("Sheet1");
      for (int i = 0; i < data.size(); i++) {
        System.out.println("iterate:" + i);
        // create a row in the spreadsheet
        row = sheet.createRow(sheet.getLastRowNum() + 1);
        System.out.println("row count is:" + sheet.getLastRowNum());


        // get the bean for the current row
        Object bean = data.get(i);
        // For each column object, create a column on the current row

        for (int y = 0; y < reportColumns.length; y++) {


          System.out.println("writing each cell");

          Object value = PropertyUtils.getProperty(bean,
              reportColumns[y].getMethod());
          writeCell(row, y, value, reportColumns[y].getType(),
              reportColumns[y].getColor(), reportColumns[y].getFont());

        }
      }
      fis.close();
    } catch (Exception e) {
      e.printStackTrace();
    }


    // Autosize columns
    for (int i = 0; i < reportColumns.length; i++) {
      System.out.println("Autosize each cell");
      sheet.autoSizeColumn((short) i);
    }

  }

  private void getUsers() {
    try {
      HttpResponse<JsonNode> request = Unirest.get("https://api.angel.co/1/users/batch?ids=" + id)
          .asJson();
      JsonNode node = request.getBody();
      System.out.println(node.isArray());
      System.out.println(node.toString());
      System.out.println(request);
      Gson gson = new Gson();
      User[] user = gson.fromJson(node.toString(), User[].class);
      if (user != null && user.length > 0) {
        users.add(user[0]);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Bean2Excel() {
    if (!this.file.exists()) {
      try {
        this.file.createNewFile();
        this.addSheet();
        FileOutputStream output = new FileOutputStream(this.file);
        workbook.write(output);
        output.flush();
        output.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }


  // increment the spreadsheet row before we step into
  // Write report rows
//      for (int i = 0; i < data.size(); i++) {
//        // create a row in the spreadsheet
//        row = sheet.createRow(currentRow++);
//        // get the bean for the current row
//        Object bean = data.get(i);
//        // For each column object, create a column on the current row
//        for (int y = 0; y < numCols; y++) {
//          Object value = PropertyUtils.getProperty(bean,
//              columns[y].getMethod());
//          writeCell(row, y, value, columns[y].getType(),
//              columns[y].getColor(), columns[y].getFont());
//        }
//      }

//      // Autosize columns
//      for (int i = 0; i < numCols; i++) {
//        sheet.autoSizeColumn((short) i);
//      }


  public HSSFFont boldFont() {
    return boldFont;
  }

  public void write(OutputStream outputStream) throws Exception {
    workbook.write(outputStream);
  }

  private void writeCell(HSSFRow row, int col, Object value,
                         FormatType formatType, Short bgColor, HSSFFont font) throws Exception {

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

  public enum FormatType {
    TEXT, INTEGER, FLOAT, DATE, MONEY, PERCENTAGE
  }
}

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
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AngelUser {
  private HSSFWorkbook workbook;
  private HSSFFont boldFont;
  private HSSFDataFormat format;
  private static int startIndex = 1;
  private static int endIndex = 50;
  private int sheetNumber = 1;

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
      File file = new File("./angel-list.xls");
      if (!file.exists()) {
        file.createNewFile();
      }
      FileOutputStream output = new FileOutputStream(file, true);
      for (; endIndex <= 500; ) {
        String queryString = "";
        for (int index = startIndex; index <= endIndex; index++) {
          queryString = queryString + index + ",";
        }
        oReport.getUsers(queryString);
        startIndex = endIndex + 1;
        endIndex = endIndex + 50;
        if (oReport.users.get(oReport.users.size() - 1).getId() % 100 == 0) {
          oReport.addSheet(oReport.users, oReport.reportColumns, "sheet" + oReport.sheetNumber++);
          oReport.users.clear();
        }
      }
      oReport.write(output);
      output.flush();
      output.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void getUsers(String queryString) {
    try {
      HttpResponse<JsonNode> request = Unirest.get("https://api.angel.co/1/startups/batch?ids=" + queryString)
          .asJson();
      JsonNode node = request.getBody();
      System.out.println(node.isArray());
      System.out.println(node.toString());
      System.out.println(request);
      Gson gson = new Gson();
      User[] userList = gson.fromJson(node.toString(), User[].class);
      if (userList != null) {
        for (User user : userList) {
          users.add(user);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public AngelUser() {
    workbook = new HSSFWorkbook();
    boldFont = workbook.createFont();
    boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    format = workbook.createDataFormat();
  }

  public void addSheet(List<?> data, ReportColumn[] columns, String sheetName) {
    HSSFSheet sheet = workbook.createSheet(sheetName);
    int numCols = columns.length;
    int currentRow = 0;
    HSSFRow row;
    try {
      row = sheet.createRow(currentRow);
      for (int i = 0; i < numCols; i++) {
        writeCell(row, i, columns[i].getHeader(), FormatType.formatType.TEXT,
            null, this.boldFont);
      }
      currentRow++; // increment the spreadsheet row before we step into
      for (int i = 0; i < data.size(); i++) {
        row = sheet.createRow(currentRow++);
        Object bean = data.get(i);
        for (int y = 0; y < numCols; y++) {
          Object value = PropertyUtils.getProperty(bean,
              columns[y].getMethod());
          writeCell(row, y, value, columns[y].getType(),
              columns[y].getColor(), columns[y].getFont());
        }
      }

      // Autosize columns
      for (int i = 0; i < numCols; i++) {
        sheet.autoSizeColumn((short) i);
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
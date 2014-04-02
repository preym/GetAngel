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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Bean2Excel {
  private HSSFWorkbook workbook;
  private HSSFFont boldFont;
  private HSSFDataFormat format;
  private static int id;
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
//          new ReportColumn("skill", "Skills", FormatType.TEXT),
      new ReportColumn("investor", "Is Investor?", FormatType.TEXT)
  };

  User[] users;

  public static void main(String[] args) {

    try {
      // Create the report object
      Bean2Excel oReport = new Bean2Excel();

        oReport.getUsers();
        oReport.addSheet(Arrays.asList(oReport.users), oReport.reportColumns, "sheet1");
        File file = new File("./angel-list.xls");
        if (!file.isFile()) {
          file.createNewFile();
        }
        OutputStream output = new FileOutputStream(file, true);
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


  private void getUsers() {
    try {
      HttpResponse<JsonNode> request = Unirest.get("https://api.angel.co/1/users/batch?ids=" + id)
          .asJson();
      JsonNode node = request.getBody();
      System.out.println(node.isArray());
      System.out.println(node.toString());
      System.out.println(request);
      Gson gson = new Gson();
      users = gson.fromJson(node.toString(), User[].class);
      System.out.println("size:" + users.length);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Bean2Excel() {
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
      // Create the report header at row 0
      row = sheet.createRow(currentRow);
      // Loop over all the column beans and populate the report headers
      for (int i = 0; i < numCols; i++) {
        // Get the header text from the bean and write it to the cell
        writeCell(row, i, columns[i].getHeader(), FormatType.TEXT,
            null, this.boldFont);
      }
      currentRow++; // increment the spreadsheet row before we step into
      // Write report rows
      for (int i = 0; i < data.size(); i++) {
        // create a row in the spreadsheet
        row = sheet.createRow(currentRow++);
        // get the bean for the current row
        Object bean = data.get(i);
        // For each column object, create a column on the current row
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

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

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 3/4/14
 * Time: 3:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class AngelStartUp {
  private HSSFWorkbook workbook;
  private HSSFFont boldFont;
  private HSSFDataFormat format;

  ReportColumn[] reportColumns = new ReportColumn[]{
      new ReportColumn("locations", "Locations", FormatType.formatType.TEXT),
      new ReportColumn("market", "Market Tag", FormatType.formatType.TEXT),
      new ReportColumn("companyType", "Company Type", FormatType.formatType.TEXT),
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
      new ReportColumn("location", "Location", FormatType.formatType.TEXT)
  };


  List<Startups> startups = new ArrayList<Startups>();

  public static void main(String[] args) {
    try {
      AngelStartUp oReport = new AngelStartUp();
      File file = new File("./angel-startup.xls");
      if (!file.exists()) {
        file.createNewFile();
      }
      FileOutputStream output = new FileOutputStream(file, true);
      int noOfPages = oReport.getStartups(1);

      for (int index = 1; index <= noOfPages; index++) {
        oReport.getStartups(index);
      }
      oReport.addSheet(oReport.startups, oReport.reportColumns, "sheet1");
      oReport.write(output);
      output.flush();
      output.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private int getStartups(int pageNumber) {
    int noOfPages = 1;
    try {
      HttpResponse<JsonNode> request = Unirest.get("https://api.angel.co/1/tags/1654/startups?page=" + pageNumber)
          .asJson();
      JsonNode node = request.getBody();
      System.out.println(node.isArray());
      System.out.println(node.toString());
      System.out.println(request);
      Gson gson = new Gson();
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

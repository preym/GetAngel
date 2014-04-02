package com.ehc.angel.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellUtil;

public class Bean2Excel {
    private HSSFWorkbook workbook;
    private HSSFFont boldFont;
    private HSSFDataFormat format;

    String json = "[{\"name\":\"Brandon Leonardo\",\"id\":1,\"bio\":\"Founder @instacart. Early @angellist engineer. Hack of all trades, master of awesome.\",\"follower_count\":647,\"angellist_url\":\"https://angel.co/brandon\",\"image\":\"https://s3.amazonaws.com/photos.angel.co/users/1-medium_jpg?1294803443\",\"blog_url\":\"\",\"online_bio_url\":\"http://brandonleonardo.com\",\"twitter_url\":\"http://twitter.com/shiftb\",\"facebook_url\":\"http://facebook.com/brandon.leonardo\",\"linkedin_url\":\"http://linkedin.com/in/leonardo\",\"aboutme_url\":\"\",\"github_url\":\"http://github.com/shiftb\",\"dribbble_url\":\"\",\"behance_url\":\"\",\"what_ive_built\":\"I built the Instacart web store in less than a week.\",\"locations\":[{\"id\":1701,\"tag_type\":\"LocationTag\",\"name\":\"mountain view\",\"display_name\":\"Mountain View\",\"angellist_url\":\"https://angel.co/mountain-view-1\"},{\"id\":1681,\"tag_type\":\"LocationTag\",\"name\":\"silicon valley\",\"display_name\":\"Silicon Valley\",\"angellist_url\":\"https://angel.co/silicon-valley\"}],\"roles\":[{\"id\":14725,\"tag_type\":\"RoleTag\",\"name\":\"entrepreneur\",\"display_name\":\"Entrepreneur\",\"angellist_url\":\"https://angel.co/entrepreneur-1\"},{\"id\":14726,\"tag_type\":\"RoleTag\",\"name\":\"developer\",\"display_name\":\"Developer\",\"angellist_url\":\"https://angel.co/developer\"}],\"skills\":[{\"id\":82532,\"tag_type\":\"SkillTag\",\"name\":\"ruby on rails\",\"display_name\":\"Ruby on Rails\",\"angellist_url\":\"https://angel.co/ruby-on-rails-1\",\"level\":null},{\"id\":17184,\"tag_type\":\"SkillTag\",\"name\":\"ruby\",\"display_name\":\"Ruby\",\"angellist_url\":\"https://angel.co/ruby\",\"level\":null},{\"id\":14780,\"tag_type\":\"SkillTag\",\"name\":\"java\",\"display_name\":\"Java\",\"angellist_url\":\"https://angel.co/java\",\"level\":null},{\"id\":21133,\"tag_type\":\"SkillTag\",\"name\":\"coffeescript\",\"display_name\":\"Coffeescript\",\"angellist_url\":\"https://angel.co/coffeescript\",\"level\":null},{\"id\":15593,\"tag_type\":\"SkillTag\",\"name\":\"css\",\"display_name\":\"CSS\",\"angellist_url\":\"https://angel.co/css\",\"level\":null},{\"id\":21135,\"tag_type\":\"SkillTag\",\"name\":\"git\",\"display_name\":\"Git\",\"angellist_url\":\"https://angel.co/git\",\"level\":null},{\"id\":16309,\"tag_type\":\"SkillTag\",\"name\":\"mysql\",\"display_name\":\"MySQL\",\"angellist_url\":\"https://angel.co/mysql-1\",\"level\":null},{\"id\":29440,\"tag_type\":\"SkillTag\",\"name\":\"mac os x\",\"display_name\":\"Mac OS X\",\"angellist_url\":\"https://angel.co/mac-os-x\",\"level\":null},{\"id\":14781,\"tag_type\":\"SkillTag\",\"name\":\"javascript\",\"display_name\":\"Javascript\",\"angellist_url\":\"https://angel.co/javascript\",\"level\":null},{\"id\":33096,\"tag_type\":\"SkillTag\",\"name\":\"backbone.js\",\"display_name\":\"Backbone.js\",\"angellist_url\":\"https://angel.co/backbone-js\",\"level\":null},{\"id\":16999,\"tag_type\":\"SkillTag\",\"name\":\"mongodb\",\"display_name\":\"MongoDB\",\"angellist_url\":\"https://angel.co/mongodb\",\"level\":null},{\"id\":79768,\"tag_type\":\"SkillTag\",\"name\":\"spine.js\",\"display_name\":\"Spine.js\",\"angellist_url\":\"https://angel.co/spine-js\",\"level\":null},{\"id\":15594,\"tag_type\":\"SkillTag\",\"name\":\"jquery\",\"display_name\":\"jQuery\",\"angellist_url\":\"https://angel.co/jquery\",\"level\":null}],\"investor\":false}," +
            "{\"name\":\"Babak Nivi\",\"id\":2,\"bio\":\"Founder @angellist @venture-hacks \u00b7 Worked at @bessemer-venture-partners @songbird @grockit \u00b7 Studied at @massachusetts-institute-of-technology  \u00b7 Published in Science \u00b7 2 Patents\",\"follower_count\":16588,\"angellist_url\":\"https://angel.co/nivi\",\"image\":\"https://s3.amazonaws.com/photos.angel.co/users/2-medium_jpg?1307554953\",\"blog_url\":\"http://venturehacks.com\",\"online_bio_url\":\"http://angel.co\",\"twitter_url\":\"https://twitter.com/venturehacks\",\"facebook_url\":\"https://www.facebook.com/babak.nivi\",\"linkedin_url\":\"http://www.linkedin.com/in/bnivi\",\"aboutme_url\":\"\",\"github_url\":\"\",\"dribbble_url\":\"\",\"behance_url\":\"\",\"what_ive_built\":\"\",\"locations\":[{\"id\":1692,\"tag_type\":\"LocationTag\",\"name\":\"san francisco\",\"display_name\":\"San Francisco\",\"angellist_url\":\"https://angel.co/san-francisco\"}],\"roles\":[{\"id\":14725,\"tag_type\":\"RoleTag\",\"name\":\"entrepreneur\",\"display_name\":\"Entrepreneur\",\"angellist_url\":\"https://angel.co/entrepreneur-1\"}],\"skills\":[{\"id\":15745,\"tag_type\":\"SkillTag\",\"name\":\"entrepreneurship\",\"display_name\":\"Entrepreneurship\",\"angellist_url\":\"https://angel.co/entrepreneurship\",\"level\":null},{\"id\":14770,\"tag_type\":\"SkillTag\",\"name\":\"fundraising\",\"display_name\":\"Fundraising\",\"angellist_url\":\"https://angel.co/fundraising\",\"level\":null}],\"investor\":true},{\"name\":\"Satya Patel\",\"id\":50,\"bio\":\"Partner @homebrew. Formerly VP Product at @twitter, ex-Google PM, ex-Partner at @battery-ventures. Penn educated. Vegas raised.\",\"follower_count\":1861,\"angellist_url\":\"https://angel.co/satyap\",\"image\":\"https://s3.amazonaws.com/photos.angel.co/users/50-medium_jpg?1294803476\",\"blog_url\":\"http://www.venturegeneratedcontent.com/\",\"online_bio_url\":\"http://venturegeneratedcontent.com/about/\",\"twitter_url\":\"http://twitter.com/satyap\",\"facebook_url\":\"https://www.facebook.com/satya1\",\"linkedin_url\":\"http://www.linkedin.com/in/satyapatel\",\"aboutme_url\":\"\",\"github_url\":\"\",\"dribbble_url\":\"\",\"behance_url\":\"\",\"what_ive_built\":\"\",\"locations\":[{\"id\":1850,\"tag_type\":\"LocationTag\",\"name\":\"menlo park\",\"display_name\":\"Menlo Park\",\"angellist_url\":\"https://angel.co/menlo-park\"},{\"id\":1681,\"tag_type\":\"LocationTag\",\"name\":\"silicon valley\",\"display_name\":\"Silicon Valley\",\"angellist_url\":\"https://angel.co/silicon-valley\"},{\"id\":1692,\"tag_type\":\"LocationTag\",\"name\":\"san francisco\",\"display_name\":\"San Francisco\",\"angellist_url\":\"https://angel.co/san-francisco\"},{\"id\":1694,\"tag_type\":\"LocationTag\",\"name\":\"palo alto\",\"display_name\":\"Palo Alto\",\"angellist_url\":\"https://angel.co/palo-alto\"},{\"id\":1704,\"tag_type\":\"LocationTag\",\"name\":\"las vegas\",\"display_name\":\"Las Vegas\",\"angellist_url\":\"https://angel.co/las-vegas\"},{\"id\":1813,\"tag_type\":\"LocationTag\",\"name\":\"salt lake city\",\"display_name\":\"Salt Lake City\",\"angellist_url\":\"https://angel.co/salt-lake-city\"},{\"id\":1664,\"tag_type\":\"LocationTag\",\"name\":\"new york, ny\",\"display_name\":\"New York City\",\"angellist_url\":\"https://angel.co/new-york-ny-1\"}],\"roles\":[{\"id\":9300,\"tag_type\":\"RoleTag\",\"name\":\"angels\",\"display_name\":\"Angel\",\"angellist_url\":\"https://angel.co/angels\"},{\"id\":9305,\"tag_type\":\"RoleTag\",\"name\":\"seed funds\",\"display_name\":\"Seed Fund\",\"angellist_url\":\"https://angel.co/seed-funds\"}],\"skills\":[],\"investor\":true}]";

    User[] users = new Gson().fromJson(json, User[].class);

    public static void main(String[] args) {

        // Used to create the hire dates
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy, MM, dd");

        try {
            // Create the report object
            Bean2Excel oReport = new Bean2Excel();
//            oReport.getUsers();

            // Create a list of employee data
            List employees = new ArrayList();
            employees.add(new Employee(100, "Abe Adams", sdf
                    .parse("2009, 12, 1"), 10000.00));
            employees.add(new Employee(101, "Betty Barnes", sdf
                    .parse("2010, 11, 1"), 11000.00));
            employees.add(new Employee(102, "Caleb Crown", sdf
                    .parse("2011, 10, 1"), 12000.00));
            employees.add(new Employee(103, "Dirk Daniels", sdf
                    .parse("2012, 09, 1"), 13000.00));

            // Create an array of report column objects
//            ReportColumn[] reportColumns = new ReportColumn[]{
//                    new ReportColumn("id", "Id", FormatType.INTEGER),
//                    new ReportColumn("name", "Last Name", FormatType.TEXT),
//                    new ReportColumn("hireDate", "Hire Date", FormatType.DATE),
//                    new ReportColumn("salary", "Salary", FormatType.MONEY)};


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
                    new ReportColumn("skill", "Skills", FormatType.TEXT),
                    new ReportColumn("investor", "Is Investor?", FormatType.TEXT)

            };


            // Create a worksheet with our employee data and report columns
            oReport.addSheet(Arrays.asList(oReport.users), reportColumns, "sheet1");

            // Set the Hire Date Column text to Bold and background to Green
//            reportColumns[2].setColor(HSSFColor.GREEN.index);

            // Add a 2nd sheet with the same data.
//            oReport.addSheet(employees, reportColumns, "sheet2");

            // Create an output stream to write the report to.

            File file = new File("./angel-list.xls");
            if (!file.isFile()) {
                file.createNewFile();
            }
            OutputStream output = new FileOutputStream(file, true);

            // Write the report to the output stream
            oReport.write(output);

            // Finally, save the report
            output.close();

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private void getUsers() {
        try {
            HttpResponse<JsonNode> request = Unirest.get("https://api.angel.co/1/users/batch?ids=51,100")
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
            // the data

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
                           FormatType formatType, Short bgColor, HSSFFont font)
            throws Exception {

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
package com.orangehrm.qa.QAAutomationFramework.utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReaderUtility {

    // Define the path to your Excel file
    private static final String EXCEL_PATH = System.getProperty("user.dir") + 
                                             "/src/test/resources/testdata/signup_data.xlsx";

    
    public static Object[][] getTestData(String sheetName) {
        XSSFWorkbook workbook = null;
        try (FileInputStream fis = new FileInputStream(EXCEL_PATH)) {
            
            // 1. Open the workbook
            workbook = new XSSFWorkbook(fis);
            
            // 2. Get the specified sheet
            XSSFSheet sheet = workbook.getSheet(sheetName);
            
            // Check if sheet exists
            if (sheet == null) {
                System.err.println("Error: Sheet named '" + sheetName + "' not found in Excel file.");
                return new Object[0][0];
            }

            // Get total rows and columns (excluding the header row/first column)
            int totalRows = sheet.getLastRowNum(); // last row index (0-based)
            int totalCols = sheet.getRow(0).getLastCellNum(); // last column count (1-based)

            // Initialize the 2D array for TestNG
            // totalRows is the count of data rows (excluding the header)
            Object[][] data = new Object[totalRows][totalCols]; 
            DataFormatter formatter = new DataFormatter();

            // 3. Iterate through rows and columns (starting from row index 1 to skip header)
            for (int i = 1; i <= totalRows; i++) {
                for (int j = 0; j < totalCols; j++) {
                    // Use DataFormatter to read all cell data as String, regardless of type
                    data[i - 1][j] = formatter.formatCellValue(sheet.getRow(i).getCell(j));
                }
            }
            
            return data;

        } catch (IOException e) {
            System.err.println("FATAL ERROR: Could not read Excel file at path: " + EXCEL_PATH);
            e.printStackTrace();
            return new Object[0][0];
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
package com.mamoon.simulator;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.List;

public class ExcelWriter {
    private final Simulator simulator = Simulator.getInstance();
    private final Workbook workbook = new XSSFWorkbook();
    private final String[] columnHeadings = {"Task ID","Creation Time","Priority","Completion Time","Total simulation Time"};
    public ExcelWriter(){}
    public void writeToExcel(String path){
        try {
            Sheet sh = workbook.createSheet("Output");
            Font headerFont = headerFont(workbook);
            CellStyle headerStyle = headerStyle(workbook, headerFont);
            Row headerRow = sh.createRow(0);
            settingTheHeader(columnHeadings, headerStyle, headerRow);
            settingResults(sh);
            autoSizeColumns(sh, columnHeadings);
            FileOutputStream fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private void autoSizeColumns(Sheet sh, String[] columnHeadings) {
        for(int i = 0; i< columnHeadings.length; i++) {
            sh.autoSizeColumn(i);
        }
    }

    private void settingResults(Sheet sh) {
        int rowNumber =1;
        List<Task> results=simulator.Output();
        Task task;
        for(int i=0;i< results.size();i++) {
            task=results.get(i);
            Row row = sh.createRow(rowNumber++);
            row.createCell(0).setCellValue(task.getID());
            row.createCell(1).setCellValue(task.getCreationCycle().getID());
            row.createCell(2).setCellValue((task instanceof LowPriorityTask)?"Low":"High");
            row.createCell(3).setCellValue(task.getCompletionCycle().getID());
            if(i== results.size()-1){
                row = sh.createRow(rowNumber++);
                row.createCell(4).setCellValue(task.getCompletionCycle().getID());
            }
        }
    }

    private void settingTheHeader(String[] columnHeadings, CellStyle headerStyle, Row headerRow) {
        for(int i = 0; i< columnHeadings.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnHeadings[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    private CellStyle headerStyle(Workbook workbook, Font headerFont) {
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(headerFont);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        return headerStyle;
    }

    private Font headerFont(Workbook workbook) {
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short)12);
        headerFont.setColor(IndexedColors.BLACK.index);
        return headerFont;
    }
}

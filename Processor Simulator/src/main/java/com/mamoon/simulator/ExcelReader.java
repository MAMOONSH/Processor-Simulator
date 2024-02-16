package com.mamoon.simulator;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Objects;

public class ExcelReader {
    private final Simulator simulator= Simulator.getInstance();
    private int creationTime=1;
    private boolean isHighPriority=true;
    private int requestedTime=4;
    private int numberOfTasks=0;
    private int rowIndex=-1;
    private int column=0;
    public ExcelReader() {}
    public void readMyExcel(String path){
        try {
            FileInputStream file = new FileInputStream("Input.xlsx");//Input.xlsx//path
            Workbook workbook = new XSSFWorkbook(file);
            DataFormatter dataFormatter = new DataFormatter();
            Iterator<Sheet> sheets = workbook.sheetIterator();
            extractingSheetData(dataFormatter, sheets);
            workbook.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    private void extractingSheetData(DataFormatter dataFormatter, Iterator<Sheet> sheets) {
        while(sheets.hasNext()) {
            Sheet sh = sheets.next();
            Iterator<Row> iterator = sh.iterator();
            extractingRowsData(dataFormatter, iterator);
        }
    }

    private void extractingRowsData(DataFormatter dataFormatter, Iterator<Row> iterator) {
        while(iterator.hasNext()) {
            rowIndex++;
            Row row = iterator.next();
            Iterator<Cell> cellIterator = row.iterator();
            extractingCellData(dataFormatter, cellIterator);
            column=0;
            addingTaskToSimulator();
        }
    }

    private void addingTaskToSimulator() {
        if(rowIndex<numberOfTasks+2&&rowIndex>=2)
            simulator.addTask(creationTime, isHighPriority, requestedTime);
    }

    private void extractingCellData(DataFormatter dataFormatter, Iterator<Cell> cellIterator) {
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            String cellValue = dataFormatter.formatCellValue(cell);
            if(rowIndex==0) {
                int numberOfProcessors = Integer.parseInt(cellValue);
                simulator.createProcessors(numberOfProcessors);
            }
            if(rowIndex==1)
                numberOfTasks=Integer.parseInt(cellValue);
            if(rowIndex>=2) {
                gettingFutureTaskData(cellValue);
            }
            column++;
        }
    }

    private void gettingFutureTaskData(String cellValue) {
        if (column == 0)
            creationTime = Integer.parseInt(cellValue);
        if (column == 1) {
            isHighPriority = Objects.equals(cellValue, "TRUE");
        }
        if (column == 2)
            requestedTime = Integer.parseInt(cellValue);
    }

}

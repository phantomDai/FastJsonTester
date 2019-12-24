package Log;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;

/**
 * @author JHH
 */

public class DeleteUselessRecorder {

    public void deleteUselessRecorder(String excelPath){

        //best ID version
        int bestID = 0;
        //best Fmeasure value
        double bestFmeasure = 0;
        //best F2measure value
        double bestF2measure = 0;
        //best measure value
        double bestmeasure = 1000;
        //current best ID version
        double tempID= 0;
        //current best Fmeasure value
        double tempFmeasure = 0;
        //current best F2measure value
        double tempF2measure = 0;



        //Open Excel file
        FileInputStream file = null;
            try {
             file = new FileInputStream(new File(excelPath));
        } catch (
        FileNotFoundException e) {
            e.printStackTrace();
        }
        Workbook workbook = null;
            try {
            workbook = new XSSFWorkbook(file);
        } catch (
        IOException e) {
            e.printStackTrace();
        }
        //open first Sheet
        Sheet sheet = workbook.getSheetAt(0);

        int currentRows = sheet.getLastRowNum();
        Cell cellData = null;
        int i;

        for(i=0;i<currentRows;i=i+34){

            XSSFRow row = (XSSFRow) sheet.getRow(i+2);
            XSSFCell cellFmeasure = row.getCell(1);
            XSSFCell cellF2measure = row.getCell(2);


            tempFmeasure = Double.parseDouble(String.valueOf(cellFmeasure)) ;
            tempF2measure = Double.parseDouble(String.valueOf(cellF2measure)) ;

            //Select the best ID version
            if(bestmeasure>(tempFmeasure+tempF2measure)){

                bestID = i/34;
                bestFmeasure = tempFmeasure;
                bestF2measure = tempF2measure;
                bestmeasure = tempFmeasure+tempF2measure;

            }

        }

        //Delete other version information, leaving the best version information
        try {
            FileInputStream is = new FileInputStream(excelPath);
            XSSFWorkbook workbookDelete = new XSSFWorkbook(is);
            XSSFSheet sheetDelete = workbookDelete.getSheetAt(0);


           for(i=0;i<bestID*34;i++){

                XSSFRow row = sheetDelete.getRow(i);
                sheetDelete.removeRow(row);
                FileOutputStream os = new FileOutputStream(excelPath);
                workbookDelete.write(os);
                is.close();
                os.close();
            }
            for(i = 34*(bestID+1);i <= currentRows;i++){
                XSSFRow row = sheetDelete.getRow(i);
                sheetDelete.removeRow(row);
                FileOutputStream os = new FileOutputStream(excelPath);
                workbookDelete.write(os);
                is.close();
                os.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Move the Cell
        try {
            FileInputStream is = new FileInputStream(excelPath);
            XSSFWorkbook workbookMove = new XSSFWorkbook(is);
            XSSFSheet sheetMove = (XSSFSheet) workbookMove.getSheetAt(0);
            if(bestID != 0){
                sheetMove.shiftRows(34*bestID, 34*(bestID+1)-1, -34*bestID);
            }
            FileOutputStream os = new FileOutputStream(excelPath);
            workbookMove.write(os);
            is.close();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}

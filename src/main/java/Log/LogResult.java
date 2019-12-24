package Log;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import static Constant.Constant.logPath;
import static java.io.File.separator;

/**
 * @author JHH
 */

public class LogResult {


    public void logResult(String excelPath, String Objectname) {

        /**best ID version*/
        String bestID = null;

        //best Fmeasure value
        double bestFmeasure = 0;

        //best F2measure value
        double bestF2measure = 0;

        //best measure value
        double bestmeasure = 1000;

        //current best ID version
        String tempID = null;

        //current best Fmeasure value
        double tempFmeasure = 0;

        //current best F2measure value
        double tempF2measure = 0;

        // int screenRow=0;

        //打开Excel文件
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
        // 打开Excel中的第一个Sheet
        Sheet sheet = workbook.getSheetAt(0);

        int currentRows = sheet.getLastRowNum();
        System.out.println(currentRows);
        Cell cellData = null;
        int i;

        for (i = 0; i < currentRows; i = i + 34) {
            //System.out.println(i+1);
            XSSFRow rowID = (XSSFRow) sheet.getRow(i );
            XSSFRow rowMeasure = (XSSFRow) sheet.getRow(i + 2);

            XSSFCell cellID = rowID.getCell(0);
            XSSFCell cellFmeasure = rowMeasure.getCell(1);
            XSSFCell cellF2measure = rowMeasure.getCell(2);


            tempID = String.valueOf(cellID);
            tempFmeasure = Double.parseDouble(String.valueOf(cellFmeasure));
            tempF2measure = Double.parseDouble(String.valueOf(cellF2measure));

            if (bestmeasure > (tempFmeasure + tempF2measure)) {

                String[] bestid = tempID.split("= ");
                bestID = bestid[1];
                bestFmeasure = tempFmeasure;
                bestF2measure = tempF2measure;
                bestmeasure = tempFmeasure + tempF2measure;

            }

        }

        File recordfile = new File(logPath + separator + Objectname);
        if (!recordfile.exists()){
            try {
                recordfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(recordfile,false));
            printWriter.write("ID version:" + bestID + "\n");
            printWriter.write("Fmeasure:" + bestFmeasure + "\n");
            printWriter.write("F2measure:" + bestF2measure + "\n");
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    }


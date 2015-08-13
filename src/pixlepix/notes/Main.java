package pixlepix.notes;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Random;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import pixlepix.notes.element.ElementClassifier;
import pixlepix.notes.element.ElementNote;
import pixlepix.notes.lib.DottedCell;

public class Main {
    
    public static void main(String[] args) {
        String file = args[0];
        
        String resultFile = file.replace(".txt", ".pdf");
        
        try{
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(resultFile));
            document.open();
            document.newPage();
            
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            String chunk = "";
            
            while((line = reader.readLine()) != null) {
                if(line.trim().isEmpty()){
                    readChunk(chunk, document);
                    chunk = "";
                }else{
                    chunk = chunk + line;
                    //Add whitespace between lines
                    if(!chunk.endsWith(" ")){
                        chunk += " ";
                    }
                }
                
            }
            //If the file has no trailing whitespace
            if(chunk != ""){
                readChunk(chunk, document);
            }

            tableSuper = new PdfPTable(2);
            tableSuper.setKeepTogether(false);
            tableSuper.setSplitLate(false);
            tableSuper.setSplitRows(true);

            PdfPCell defaultSuperCell = tableSuper.getDefaultCell();
            defaultSuperCell.setBorder(PdfPCell.NO_BORDER);

            tableSuper.addCell(tables[0]);
            tableSuper.addCell(tables[1]);


            document.add(tableSuper);

            document.close();
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    //0 = Title
    //10 = Normal
    static int stage = 0;

    static PdfPTable tableSuper;
    static PdfPTable[] tables = new PdfPTable[2];
    
    public static void readChunk(String chunk, Document doc) throws DocumentException {
        Font title = new Font(Font.FontFamily.HELVETICA, 25F, Font.BOLD);

        Font text = new Font(Font.FontFamily.TIMES_ROMAN, 15);
        if(stage == 0){
            Paragraph p = new Paragraph(chunk);
            p.setAlignment(Paragraph.ALIGN_CENTER);
            p.setFont(title);

            doc.add(p);
            stage = 10;
        }else if(stage == 10){
            if(tables[0] == null) {

                for(int i = 0; i < 2; i ++) {
                    
                    PdfPTable subTable = new PdfPTable(1);
                    tables[i] = subTable;
                    subTable.setSplitRows(true);
                    subTable.setKeepTogether(false);
                    subTable.setSplitLate(false);
                }
            }
            

            PdfPTable shortestTable = tables[0];
            for(PdfPTable table : tables){
                if(table.getRows().size() < shortestTable.getRows().size()){
                    shortestTable = table;
                }
            }
            
            ElementNote element = ElementClassifier.getElement(chunk);
            
            PdfPCell cell = getDefaultCell();
            element.modifyCell(cell);
            cell.setCellEvent(element.getEvent());
            
            
            
            String formattedChunk = element.reformatRawText(chunk);
            Paragraph paragraph = new Paragraph(formattedChunk);
            element.addText(cell, formattedChunk);

            shortestTable.addCell(cell);

        }
        
    }
    
    public static PdfPCell getDefaultCell(){
        PdfPCell defaultCell = new PdfPCell();
        defaultCell.setBorder(PdfPCell.NO_BORDER);
        //This is a property of cells, maintained here for posterity
        //But the cellEvent is applied per element
        //defaultCell.setCellEvent(new DottedCell());
        defaultCell.setPadding(25);
        defaultCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return defaultCell;
    }
}

package pixlepix.notes;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import pixlepix.notes.element.ElementClassifier;
import pixlepix.notes.element.ElementNote;
import pixlepix.notes.lib.DottedCell;
import pixlepix.notes.lib.IconSearcher;

import javax.imageio.ImageIO;

public class Main {
    
    public static BufferedImage arrow = null;

    public static PdfWriter writer = null;
    
    public static void main(String[] args) {
        IconSearcher.init();

        String file = args[0];
        
        String resultFile = file.replace(".txt", ".pdf");

        //Fetch arrow
        try {
            arrow = ImageIO.read(new File("resources/icon/arrow.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            Document document = new Document();
            writer = PdfWriter.getInstance(document, new FileOutputStream(resultFile));
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
                    subTable.setSplitRows(false);
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
            element.addText(cell, formattedChunk);

            shortestTable.addCell(cell);

            //Buffer cells improve spacing
            //And prevent the cells from extending to the bottom of the page especially
            PdfPCell bufferCell = new PdfPCell();
            bufferCell.setPadding(0);
            bufferCell.setBorder(0);
            bufferCell.setFixedHeight(0);
            shortestTable.addCell(bufferCell);
        }
        
    }
    
    public static PdfPCell getDefaultCell(){
        PdfPCell defaultCell = new PdfPCell();
        defaultCell.setBorder(PdfPCell.NO_BORDER);
        //This is a property of cells, maintained here for posterity
        //But the cellEvent is applied per element
        //defaultCell.setCellEvent(new DottedCell());
        defaultCell.setPadding(25);
        defaultCell.setVerticalAlignment(Element.ALIGN_BASELINE);
        return defaultCell;
    }
}

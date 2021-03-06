package pixlepix.dynamicnotes;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import pixlepix.dynamicnotes.element.ElementClassifier;
import pixlepix.dynamicnotes.element.ElementNote;
import pixlepix.dynamicnotes.lib.IconSearcher;

import javax.imageio.ImageIO;

public class Main {
    
    public static BufferedImage arrow = null;

    public static PdfWriter writer = null;

    public static void main(String[] args){
        GuiConvert gui = new GuiConvert();
        gui.initUI();
        gui.setVisible(true);
    }

    public static void convert(String text) {
        IconSearcher.init();


        //Fetch arrow
        try {
            arrow = ImageIO.read(new File("resources/icon/arrow.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{

            
            String[] lines = text.replaceAll("\r", "").split("\n");


            String chunk = "";

            //Init output file
            String filename = lines[0].replaceAll(" ", "").replaceAll("\r", "") + ".pdf";
            File resultFile = new File(filename);
            resultFile.delete();

            //Init document
            Document document = new Document();
            writer = PdfWriter.getInstance(document, new FileOutputStream(resultFile));
            writer.flush();
            document.open();
            document.newPage();

            //Read through lines of file
            for(String line : lines) {
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

            //Create outer table
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
            writer.close();

            //Open file in browser
            Desktop d = Desktop.getDesktop();
            d.open(resultFile);

            stage = 0;
            tables = new PdfPTable[2];



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

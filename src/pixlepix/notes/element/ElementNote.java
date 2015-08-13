package pixlepix.notes.element;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import pixlepix.notes.lib.DottedCell;

/**
 * Created by pixlepix on 8/12/15.
 */
public abstract class ElementNote {
    public PdfPCellEvent getEvent(){
        return new DottedCell();
    }
    
    public String reformatRawText(String s){
        return s;
        
    }
    
    public void modifyCell(PdfPCell cell){

    }

    public void addText(PdfPCell cell, String text){
        cell.addElement(new Phrase(text));
    }
}

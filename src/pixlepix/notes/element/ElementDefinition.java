package pixlepix.notes.element;

import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import pixlepix.notes.element.cell.DefinitionCell;

/**
 * Created by pixlepix on 8/12/15.
 */
public class ElementDefinition extends ElementNote {

    @Override
    public PdfPCellEvent getEvent() {
        return new DefinitionCell();
    }

    @Override
    public void addText(PdfPCell cell, String text) {
        String[] parts = text.split("=");

        Paragraph title = new Paragraph(parts[0]);
        title.setAlignment(Element.ALIGN_CENTER);

        Paragraph body = ElementCommonUtil.paragraphSmartAlignment("\n\n" + parts[1]);


        cell.addElement(title);
        cell.addElement(body);
    }
}
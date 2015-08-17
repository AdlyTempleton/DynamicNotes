package pixlepix.notes.element;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import pixlepix.notes.element.cell.DefinitionCell;
import pixlepix.notes.lib.VocabDictionary;
import pixlepix.notes.lib.VocabWord;

import static pixlepix.notes.lib.VocabDictionary.*;

/**
 * Created by pixlepix on 8/12/15.
 */
public class ElementDefinition extends ElementNote {

    public ElementDefinition(String chunk) {
        super(chunk);
    }

    @Override
    public PdfPCellEvent getEvent() {
        return new DefinitionCell();
    }

    @Override
    public void addText(PdfPCell cell, String text) {
        String[] parts = text.split("==");

        Paragraph p = new Paragraph();
        Anchor anchor = new Anchor(parts[0]);
        anchor.setName(parts[0]);
        p.add(anchor);
        ElementCommonUtil.paragraphSmartAlignment(p, parts[0]);
        cell.addElement(p);

        VocabDictionary.words.add(new VocabWord(0, parts[0], parts[1]));

        super.addText(cell, "\n\n" + parts[1]);
    }
}
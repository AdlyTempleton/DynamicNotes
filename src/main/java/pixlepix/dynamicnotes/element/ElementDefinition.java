package pixlepix.dynamicnotes.element;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import pixlepix.dynamicnotes.element.cell.DefinitionCell;
import pixlepix.dynamicnotes.lib.VocabDictionary;
import pixlepix.dynamicnotes.lib.VocabWord;

import java.util.Random;

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
        String[] parts = text.split("=");

        Paragraph p = new Paragraph();
        Anchor anchor = new Anchor(parts[0]);
        anchor.setName(parts[0]);
        p.add(anchor);
        ElementCommonUtil.paragraphSmartAlignment(p, parts[0]);

        Random iconGen = new Random(parts[0].hashCode());


        //Note that the icons are not zero-indexed
        int icons = 1 + iconGen.nextInt(121);
        VocabWord vocab = new VocabWord(icons, parts[0], parts[1]);
        VocabDictionary.words.add(vocab);

        cell.addElement(new Chunk(vocab.getIcon(), 2, -2));
        cell.addElement(p);

        super.addText(cell, "\n\n" + parts[1]);
    }
}
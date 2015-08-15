package pixlepix.notes.element;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.codec.PngImage;
import pixlepix.notes.Main;

import java.io.IOException;

/**
 * Created by pixlepix on 8/13/15.
 */
public class ElementCauseEffect extends ElementNote {
    public ElementCauseEffect(String chunk) {
        super(chunk);
    }

    @Override
    public void addText(PdfPCell cell, String text) {
        String[] parts = text.split("->");

        Paragraph cause = ElementCommonUtil.paragraphSmartAlignment(parts[0] + "\n\n");
        Paragraph effect = ElementCommonUtil.paragraphSmartAlignment("\n" + parts[1]);
        
        cell.addElement(cause);
        try {
            Image image = Image.getInstance("resources/icon/arrow.png");
            image.scalePercent(30F);
            image.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(image);
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cell.addElement(effect);
    }
}

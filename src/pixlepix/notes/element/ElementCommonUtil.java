package pixlepix.notes.element;

import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;

/**
 * Created by pixlepix on 8/13/15.
 */
public class ElementCommonUtil {
    public static Paragraph paragraphSmartAlignment(String s){
        Paragraph paragraph = new Paragraph(s);
        if (s.length() < 25){
            paragraph.setAlignment(Element.ALIGN_CENTER);
        }
        return paragraph;
    }
}

package pixlepix.dynamicnotes.element;

import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;

/**
 * Created by pixlepix on 8/13/15.
 */
public class ElementCommonUtil {
    public static void paragraphSmartAlignment(Paragraph p, String s){
        if (s.length() < 25){
            p.setAlignment(Element.ALIGN_CENTER);
        }
    }
}

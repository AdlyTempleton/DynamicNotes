package pixlepix.notes.lib;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;

import java.io.IOException;

/**
 * Created by pixlepix on 8/16/15.
 */
public class VocabWord {
    public int icon;
    public String title;
    public String definition;

    public VocabWord(int icon, String title, String definition) {
        this.icon = icon;
        this.title = title;
        this.definition = definition;
    }

    public Image getIcon(){
        try {

            Image img = Image.getInstance(String.format("icons/abstract-%03d.png", icon));


            img.scaleAbsolute(16, 16);
            return img;

        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

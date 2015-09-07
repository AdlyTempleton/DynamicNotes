package pixlepix.dynamicnotes.element;

import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import org.scilab.forge.jlatexmath.TeXFormula;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by pixlepix on 8/13/15.
 */
public class ElementLatex extends ElementNote{
    public ElementLatex(String chunk) {
        super(chunk);
    }

    @Override
    public void addText(PdfPCell cell, String text) {
        
        
        //Create a latex directory
        File latexDir = new File(".latex");
        if (!latexDir.exists()) {
            try{
                latexDir.mkdir();
            }
            catch(SecurityException se){
                se.printStackTrace();
            }
        }

        TeXFormula formula = TeXFormula.getPartialTeXFormula(text.replaceFirst("=", ""));
        String filename = ".latex/" + text.hashCode() + ".png";
        if(!new File(filename).exists()) {
            TeXFormula.setDPITarget(720F);
            formula.createImage("png", 0, 12F, filename, Color.WHITE, Color.BLACK, true);
        }
        try {
            Image image = Image.getInstance(filename);
            //We print the image large and scale it smaller for better resolution
            //image.scalePercent(10F);
            cell.addElement(image);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadElementException e) {
            e.printStackTrace();
        }
    }
}

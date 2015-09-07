package pixlepix.dynamicnotes.lib;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;

import java.awt.*;
import java.util.Random;

/**
 * Created by pixlepix on 8/10/15.
 */
public class DottedCell implements PdfPCellEvent {
    
    
    @Override
    public void cellLayout(PdfPCell cell, Rectangle position,
                           PdfContentByte[] canvases) {

        //Spacing
        float x1 = position.getLeft() + 10;
        float x2 = position.getRight() - 10;
        float y1 = position.getTop() - 10;
        float y2 = position.getBottom() + 10;
        
        PdfContentByte canvas = canvases[PdfPTable.LINECANVAS];
        canvas.setLineDash(5f, 5f);
        canvas.setLineWidth(2.5f);
        Color color = generateRandomColor(Color.WHITE);
        canvas.setRGBColorStroke(color.getRed(), color.getGreen(), color.getBlue());

        canvas.roundRectangle(x1, y1, x2 - x1, y2 - y1, 6);
        canvas.stroke();
    }



    public Color generateRandomColor(Color mix) {
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        // mix the color
        if (mix != null) {
            red = (red + mix.getRed()) / 2;
            green = (green + mix.getGreen()) / 2;
            blue = (blue + mix.getBlue()) / 2;
        }

        Color color = new Color(red, green, blue);
        return color;
    }
}
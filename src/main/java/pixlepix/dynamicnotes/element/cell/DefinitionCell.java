package pixlepix.dynamicnotes.element.cell;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import pixlepix.dynamicnotes.lib.DottedCell;

import java.awt.*;

/**
 * Created by pixlepix on 8/12/15.
 */
public class DefinitionCell extends DottedCell {
    @Override
    public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
        super.cellLayout(cell, position, canvases);
        
        PdfContentByte canvas = canvases[PdfPTable.LINECANVAS];

        float x1 = position.getLeft() + 10;
        float x2 = position.getRight() - 10;
        
        //Undo dash from superclass
        canvas.setLineDash(1, 0 , 0);
        canvas.setLineCap(PdfContentByte.LINE_CAP_BUTT);
        canvas.setLineWidth(.3f);
        
        Color color = Color.BLACK;
        canvas.setRGBColorStroke(color.getRed(), color.getGreen(), color.getBlue());

        canvas.moveTo(position.getLeft() + 35, position.getTop() - 65);
        canvas.lineTo(position.getRight() - 35, position.getTop() - 65);
        canvas.stroke();
    }
}

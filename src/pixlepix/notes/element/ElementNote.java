package pixlepix.notes.element;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import pixlepix.notes.lib.DottedCell;
import pixlepix.notes.lib.IconSearcher;
import pixlepix.notes.lib.VocabDictionary;
import pixlepix.notes.lib.VocabWord;

import java.util.Arrays;

/**
 * Created by pixlepix on 8/12/15.
 */
public abstract class ElementNote {
    public PdfPCellEvent getEvent(){
        return new DottedCell();
    }
    
    public String reformatRawText(String s){
        return s;
        
    }
    
    //This should not be used unless absolutely needed
    //It may not be formatted etc
    public String text;
    
    public ElementNote(String text){
        this.text = text;
        
    }
    
    public void modifyCell(PdfPCell cell){

    }

    public void addText(PdfPCell cell, String text, boolean clipart) {
        Paragraph p = new Paragraph();
        ElementCommonUtil.paragraphSmartAlignment(p, text);
        String[] words = text.split(" ");
        for(int i = 0; i < words.length; i++){
            String word = words[i];

            //Check for clipart
            if(clipart){
                Image image = IconSearcher.findFile(word);

                if(image != null){

                    p.add(new Chunk(image, 2, -2));
                    p.add(" " + word);
                    words[i] = "";
                }

                if(i + 1 < words.length){
                    String twoWords = word + " " + words[i + 1];
                    image = IconSearcher.findFile(twoWords);

                    if(image != null){
                        p.add(new Chunk(image, 2, -2));

                        p.add(" " + twoWords);

                        words[i + 1] = "";
                        words[i] = "";
                    }
                }
            }

            Element chunk = new Chunk(" " + words[i]);

            String suffix = String.join(" ", Arrays.copyOfRange(words, i, words.length));
            VocabWord vocab = VocabDictionary.getWord(suffix);

            if(vocab != null){
                Anchor anchor = new Anchor(" " + words[i]);
                anchor.setReference("#" + vocab.title);

                Annotation definition = new Annotation(vocab.title, vocab.definition);

                p.add(anchor);
                p.add(definition);
            }else{
                p.add(chunk);
            }
        }
        ElementCommonUtil.paragraphSmartAlignment(p, text);
        cell.addElement(p);
    }

    public void addText(PdfPCell cell, String text){
        addText(cell, text, true);
    }
}

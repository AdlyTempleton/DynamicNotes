package pixlepix.notes.lib;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.sun.xml.internal.ws.util.StringUtils;
import uk.ac.shef.wit.simmetrics.similaritymetrics.BlockDistance;
import uk.ac.shef.wit.simmetrics.similaritymetrics.CosineSimilarity;
import uk.ac.shef.wit.simmetrics.similaritymetrics.Levenshtein;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by pixlepix on 8/15/15.
 */
public class IconSearcher {
    public static ArrayList<String> icons = new ArrayList<String>();

    public static void init(){
        File[] files = new File("icons").listFiles();
        for(File file : files){
            icons.add(file.getName().replace(".png", "").replace("-", " "));
        }
    }

    public static String find(String target){
        Levenshtein metric = new Levenshtein();

        //First, we look for an exact match
        for(String potentialMatch : icons){
            if(potentialMatch.toLowerCase().equals(target.toLowerCase())){
                return potentialMatch;
            }
        }


        for(String potentialMatch : icons){
            if(metric.getSimilarity(potentialMatch.toLowerCase(), target.toLowerCase()) > .8F){
                return potentialMatch;
            }
            if(metric.getSimilarity(potentialMatch.toLowerCase().replace("-", " "), target.toLowerCase()) > .8F){
                return potentialMatch;
            }
            if(metric.getSimilarity(potentialMatch.toLowerCase().replace("", " "), target.toLowerCase()) > .8F){
                return potentialMatch;
            }
        }
        return null;
    }

    public static Image findFile(String target){
        String match = find(target);
        if(match == null){
            return null;
        }
        try {
            Image image =  Image.getInstance("icons/" + match + ".png");
            image.scaleAbsolute(16, 16);
            return image;
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

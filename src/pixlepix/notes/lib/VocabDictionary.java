package pixlepix.notes.lib;

import java.util.ArrayList;

/**
 * Created by pixlepix on 8/16/15.
 */
public class VocabDictionary {

    /*
    Will find the VocabWord that starts at a particular location
    Note that it will only check at the beginning of s
    This should be called using slices for every word in text
     */
    public static VocabWord getWord(String s){
        for(VocabWord word : words){
            if(s.toLowerCase().startsWith(word.title.toLowerCase())){
                return word;
            }
        }
        return null;
    }

    public static ArrayList<VocabWord> words = new ArrayList<>();

}

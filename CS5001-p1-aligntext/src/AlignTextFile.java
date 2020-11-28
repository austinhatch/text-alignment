import java.io.*;
import java.util.*;

public class AlignTextFile {
    private String file_name;
    private int line_length;
    private String align_direction;
    private ArrayList<String[]> translated_text;
    private ArrayList<String> bubble_lines;
    private final int bubble_line_spaces = 4;

    /** Creates an object for aligning a given text file
     * 
     * @param fname the file name given in command line (requires ".txt" extension)
     * @param llength the desired amount of characters per line (including spaces)
     * @param aligndirection desired direction of alignment
     */
    public AlignTextFile(String fname, int llength, String aligndirection) {
        this.file_name = fname;
        this.line_length = llength;
        this.align_direction = aligndirection;
    }

    /** Reads a text file into String[][] stored as an object attribute translated_text. 
     * Each String[] represents a paragraph in the original text file.
     * @return true if file was read and translated correctly, false if exception occurs
     **/
    public boolean readTextFile(){
        String[] paragraphs = FileUtil.readFile(this.file_name);
        
        /** indicate whether there was an exception in FileUtil.readFile**/
        if(paragraphs.length == 1 && paragraphs[0] == "") {
            return false;
        }
        
        ArrayList<String[]> all_words = new ArrayList<String[]>();
        
        for(String s : paragraphs){
            String[] paragraph_text = s.split(" ");
            all_words.add(paragraph_text);
                        
         }    
        
         this.translated_text = all_words;    
         return true;
    }
    
    
    /** Chooses appropriate alignment function based on the align_direction attribute
     * 
     * @return true if alignment was run without error, false otherwise
     */
    public boolean writeAlignedText() {
        
        if(this.align_direction.equals(AlignText.LEFT_ALIGN)){
            this.leftAlign();
            return true;
        }
        
        else if(this.align_direction.equals(AlignText.RIGHT_ALIGN)){
            this.rightAlign();
            return true;
        }
        
        else if(this.align_direction.equals(AlignText.CENTER_ALIGN)) {
            this.centerAlign();
            return true;
        }
        
        else if(this.align_direction.equals(AlignText.BUBBLE)) {
            this.bubbleAlign();
            return true;
        }
        
        return false;
 
    }
        
        

    private void leftAlign(){
        
        for(String[] paragraph : this.translated_text) {
            String[] remaining_words = this.printLeftAlign(paragraph, this.line_length);
                
            while(remaining_words.length > 0) {
               remaining_words = this.printLeftAlign(remaining_words, this.line_length);
                    
           }
                
        }

    }
        
        
    private String[] printLeftAlign(String[] words, int line_char_limit){
        int line_char_count = 0;
        int current_word_index = 0;
        String output_line = "";
        
        //While there is still space left in the line for additional characters
        while(true) {
            
            line_char_count = output_line.length();
            
            //Check if we have reached the end of our words array. Break loop if you have
            if(current_word_index > words.length-1) {
                break;
            }
            
            //Grab the next remaining word in the paragraph
            String current_word = words[current_word_index];
 
            //if the first word is longer than the character limit, add that word and nothing else
            if(current_word_index == 0 && current_word.length() >= (line_char_limit-1)) {
                String updated_line = output_line.concat(current_word);
                output_line = updated_line;
                current_word_index ++;
                break;
            }
            
            //add a word if it can be added with a space without going over the char limit
            if((line_char_count + 1 + current_word.length()) <= line_char_limit) {
                if(output_line.length() == 0) {
                    String updated_line = output_line.concat(current_word);
                    output_line = updated_line;
                }
                else {
                    String updated_line = output_line.concat(' '+current_word);
                    output_line = updated_line;
                }
                current_word_index ++;
            }  
            
            //if a word cannot fit in the line, break the while loop
            else {
                break;
            }
        }
        
        System.out.println(output_line);
        
        String[] remaining_words = Arrays.copyOfRange(words, current_word_index, words.length);
        return remaining_words; 
    }
    
    
    /** Recursive printing of right aligned text by parsing remaining words from each paragraph
     * 
     */
    private void rightAlign() {
        for(String[] paragraph : this.translated_text) {
            String[] remaining_words = this.printRightAlign(paragraph, this.line_length);
                
            while(remaining_words.length > 0) {
               remaining_words = this.printRightAlign(remaining_words, this.line_length);
                    
           }
                
        }
        
    }

    
    /** Helper function for printing of right aligned text by parsing remaining words from each paragraph and adding leading spaces
     * 
     */
    private String[] printRightAlign(String[] words, int line_char_limit){
        int line_char_count = 0;
        int current_word_index = 0;
        String output_line = "";
        
        //While there is still space left in the line for additional characters
        while(true) {
            
            line_char_count = output_line.length();
            
            //Check if we have reached the end of our words array. Break loop if you have
            if(current_word_index > words.length-1) {
                break;
            }
            
            //Grab the next remaining word in the paragraph
            String current_word = words[current_word_index];
 
            //if the first word is longer than the character limit, add that word and nothing else
            if(current_word_index == 0 && current_word.length() >= (line_char_limit-1)) {
                String updated_line = output_line.concat(current_word);
                output_line = updated_line;
                line_char_count = output_line.length();
                current_word_index ++;
                break;
            }
            
            //add a word if it can be added with a space without going over the char limit
            if((line_char_count + 1 + current_word.length()) <= line_char_limit) {
                if(output_line.length() == 0) {
                    String updated_line = output_line.concat(current_word);
                    output_line = updated_line;
                }
                else {
                    String updated_line = output_line.concat(' '+current_word);
                    output_line = updated_line;
                }
//                line_char_count = output_line.length();
                current_word_index ++;
            }  
            
            //if a word cannot fit in the line, break the while loop
            else {
                break;
            }
        }
 
          
        int num_leading_spaces = line_char_limit - line_char_count;
        String leading_spaces = "";
        for(int i = 0 ; i < num_leading_spaces; i++) {
            leading_spaces += (' ');
        }
        output_line = leading_spaces.concat(output_line);
        System.out.println(output_line);
        
        String[] remaining_words = Arrays.copyOfRange(words, current_word_index, words.length);
        return remaining_words;  
    }
        

    
    
    private void centerAlign() {
        for(String[] paragraph : this.translated_text) {        
            String[] remaining_words = this.printCenterAlign(paragraph, this.line_length);
            
            while(remaining_words.length > 0) {
                remaining_words = this.printCenterAlign(remaining_words, this.line_length); 
           }
                
        }
        
    }

    private String[] printCenterAlign(String[] words, int line_char_limit){
        int line_char_count = 0;
        int current_word_index = 0;
        String output_line = "";
        
        //While there is still space left in the line for additional characters
        while(true) {
            
            line_char_count = output_line.length();
            
            //Check if we have reached the end of our words array. Break loop if you have
            if(current_word_index > words.length-1) {
                break;
            }
            
            //Grab the next remaining word in the paragraph
            String current_word = words[current_word_index];
 
            //if the first word is longer than the character limit, add that word and nothing else
            if(current_word_index == 0 && current_word.length() >= (line_char_limit-1)) {
                String updated_line = output_line.concat(current_word);
                output_line = updated_line;
                line_char_count = output_line.length();
                current_word_index ++;
                break;
            }
            
            //add a word if it can be added with a space without going over the char limit
            if((line_char_count + 1 + current_word.length()) <= line_char_limit) {
                if(output_line.length() == 0) {
                    String updated_line = output_line.concat(current_word);
                    output_line = updated_line;
                }
                else {
                    String updated_line = output_line.concat(' '+current_word);
                    output_line = updated_line;
                }
                current_word_index ++;
            }  
            
            //if a word cannot fit in the line, break the while loop
            else {
                break;
            }
        }
        
        //Get the difference between the line text and the limit
        int num_spaces = line_char_limit - line_char_count;
        
        //Add spaces before if needed
        if(num_spaces > 0) {
                     
            String leading_spaces = "";
            for(int i = 0 ; i < num_spaces/2; i++) {
                leading_spaces += (' ');
            }
            
            if(num_spaces %2 != 0) {
                leading_spaces += (' ');
            }
                    
            output_line = leading_spaces.concat(output_line);
        }
        
        System.out.println(output_line);
        
        //Return the rest of the words in the array
        String[] remaining_words = Arrays.copyOfRange(words, current_word_index, words.length);
        return remaining_words;    
    }
    
    
    private void bubbleAlign() {
        this.bubble_lines = new ArrayList<String>();
        
        for(String[] paragraph : this.translated_text) {
            String[] remaining_words = this.addBubbleAlign(paragraph, this.line_length);
           
                
            while(remaining_words.length > 0) {
               
               remaining_words = this.addBubbleAlign(remaining_words, this.line_length);
                    
           }
                
        }
        
        String top_line = " ";
        String bottom_line = " ";
        
        //calculate longest row of text as it may be longer than specified limit
        int longest_line_length = Collections.max(this.bubble_lines, Comparator.comparing(String::length)).length();
        
        //sets the desired line_length to be larger if there is a larger line to make sure bubble prints correctly
        if(longest_line_length > this.line_length - bubble_line_spaces) {
            this.line_length = longest_line_length + bubble_line_spaces;
        }
        
        for(int i = 0; i < this.line_length - 2; i ++) {
            top_line = top_line + "_";
            bottom_line = bottom_line + "-";
        }
        
        top_line.concat(" ");
        
        bottom_line.concat(" ");
        
        System.out.println(top_line);
        
        for(String bubble_line : this.bubble_lines) {
            int num_trailing_spaces = this.line_length - bubble_line_spaces - bubble_line.length();
            String trailing_spaces = "";
            for(int i = 0 ; i < num_trailing_spaces; i++) {
                trailing_spaces += ' ';
            }
           bubble_line = "| "+bubble_line + trailing_spaces + " |";

           System.out.println(bubble_line);
            
            
        }
        
        System.out.println(bottom_line);
        
        int tail_position = 9;
        
        String tail_top = "";
        String tail_bottom = " ";
        for(int j = 0 ; j < tail_position - 1; j++) {
            tail_top += " ";
            tail_bottom += " ";    
        }
        
        tail_top += "\\"; 
        tail_bottom += "\\";
        
        System.out.println(tail_top);
        System.out.println(tail_bottom);
  
        
    }
    
    private String[] addBubbleAlign(String[] words, int line_char_limit) {
        int bubble_char_limit = line_char_limit - bubble_line_spaces;
        int line_char_count = 0;
        int current_word_index = 0;
        String output_line = "";
        
        //While there is still space left in the line for additional characters
        while(true) {
            
            line_char_count = output_line.length();
            
            //Check if we have reached the end of our words array. Break loop if you have
            if(current_word_index > words.length-1) {
                break;
            }
            
            //Grab the next remaining word in the paragraph
            String current_word = words[current_word_index];
 
            //if the first word is longer than the character limit, add that word and nothing else
            if(current_word_index == 0 && current_word.length() >= (bubble_char_limit-1)) {
                String updated_line = output_line.concat(current_word);
                output_line = updated_line;
                current_word_index ++;
                break;
            }
            
            //add a word if it can be added with a space without going over the char limit
            if((line_char_count + 1 + current_word.length()) <= bubble_char_limit) {
                if(output_line.length() == 0) {
                    String updated_line = output_line.concat(current_word);
                    output_line = updated_line;
                }
                else {
                    String updated_line = output_line.concat(' '+current_word);
                    output_line = updated_line;
                }
                current_word_index ++;
            }  
            
            //if a word cannot fit in the line, break the while loop
            else {
                break;
            }
        }
        
        //Add fitted lines to an array to be formatted in the bubbleAlign function
        this.bubble_lines.add(output_line);
        String[] remaining_words = Arrays.copyOfRange(words, current_word_index, words.length);
        return remaining_words;   
            
        
    }
    
}

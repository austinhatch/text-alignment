
import java.util.*;

/** represents the main command line access to the AlignText program
 * 
 * 
 *
 */
public class AlignText{
    
    
    public final static String LEFT_ALIGN = "L";
    public final static String CENTER_ALIGN = "C";
    public final static String RIGHT_ALIGN = "R";
    public final static String BUBBLE = "B";
    



    /**
     * 
     * @param args expects String file_name String line_length String [align_mode]
     */
    public static void main(String[] args) {
	    
	if(args.length < 2 || args.equals(null)) {
            System.out.println("usage: java AlignText file_name line_length [align_mode]");
	    return;
	}
	    
	String file_path = args[0];
	   
	String line_char_limit = args[1];
	    
	int char_limit;
    
	try {
	    char_limit = Integer.parseInt(line_char_limit);
	}
	catch(NumberFormatException nfe) {
	    System.out.println("usage: java AlignText file_name line_length [align_mode]");
	    return;
	}
	
	if(char_limit < 1) {
	    System.out.println("usage: java AlignText file_name line_length [align_mode]");
	    return;
	}
	    
	    
	String align_direct = "L";
	    
	if(args.length > 2) {
	    String align_input = args[2];
	    if(align_input.toUpperCase().equals(LEFT_ALIGN) || align_input.toUpperCase().equals(RIGHT_ALIGN) || align_input.toUpperCase().equals(CENTER_ALIGN) || align_input.toUpperCase().equals(BUBBLE)) {
	        align_direct = align_input;
	    }
	    else {
	        System.out.println("usage: java AlignText file_name line_length [align_mode]");
	        return;
	    }
	        
	}
	
	AlignTextFile text = new AlignTextFile(file_path, char_limit, align_direct);
	
	Boolean file_read = text.readTextFile();
	if(! file_read) {
	    //System.out.println("usage: java AlignText file_name line_length [align_mode]");
	    return;
	}

	
	Boolean success = text.writeAlignedText();

	if(success) {
	    return;
	}
	    
	else {
	    System.out.println("usage: java AlignText file_name line_length [align_mode]");
	    return;
	}
    }

}

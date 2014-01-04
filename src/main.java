import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Stack;


public class main {
	
	public static void main(String[] args){
		
		Properties cfg = new Properties();
		
		// check if we have get 6 inputs
		if(args.length != 6){
			System.out.println("error: less that 6 argument have given");
			return;
		}
		
		// read the config file
		
	try {
		File cfgF = new File(args[0]);
		cfg.load(new FileReader(cfgF));
	}
		
		
		
		
		
	}
		
		
	
		
}

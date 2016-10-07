import java.io.*;
public class ID3 {
	
  	public static void main(String[] args) {
        try{
        	inputHandle();
        }catch(Exception e){
        	System.out.println(e);
        }
  	}
  	public static void inputHandle()throws IOException {
  		 BufferedReader br = new BufferedReader(new FileReader("test.txt"));
         String line=null;
         while( (line=br.readLine()) != null) {
                System.out.println(line);  
         }
  	}
}
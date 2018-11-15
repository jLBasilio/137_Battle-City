import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MapUtils{
	
	//load and reads map file, and return a string of tile id's
	public static String loadMapFile(String path){
		StringBuilder builder = new StringBuilder();

		try{
			BufferedReader br =  new BufferedReader(new FileReader(path));
			String line;
			while((line=br.readLine()) != null){
				builder.append(line +"\n");
			}

			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}

		return builder.toString();
	}

	//parse string into int
	public static int parseInt(String num){
		try{
			return Integer.parseInt(num);
		}catch(NumberFormatException e){
			e.printStackTrace();
			return 0;
		}
	}
}
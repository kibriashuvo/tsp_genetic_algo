import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class InputToDistance {
	int[][] distMap;
	City[] input;
	
	public InputToDistance() {
		// TODO Auto-generated constructor stub
	
	}

	public void generateDistanceMap(LinkedList<City> cities) {
		int no_of_cities = cities.size();
		distMap = new int[no_of_cities][no_of_cities];
	}

	public int getDistance(City a, City b) {
		double distance = Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
		return (int) Math.ceil(distance);
	}

	public void combineAll(LinkedList<City> cities) {
		int no_of_cities = cities.size();
		Object[] cityArray = cities.toArray();
		for (int i = 0; i < no_of_cities; i++) {
			for (int j = 0; j < no_of_cities; j++) {
				distMap[i][j] = getDistance((City)cityArray[i], (City)cityArray[j]);
			}
		}
	}

	public static LinkedList<City> fileRead() {
		
		LinkedList<City> list =  new LinkedList<>(); 
		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader("input.txt"));

			while ((sCurrentLine = br.readLine()) != null) {
				String[] s = sCurrentLine.split(" ");
				City c = new City();
				c.name = Integer.parseInt(s[0]);
				c.x = Integer.parseInt(s[1]);
				c.y = Integer.parseInt(s[2]);
				list.add(c);
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		
		return list;
		
	}
	
	
	public int[] stringToInt(String s){
		String[] splitArray = s.split("-");
		int len = splitArray.length;
		int[] ret = new int[len];
		for(int i=0;i<len;i++){
			ret[i] = Integer.valueOf(splitArray[i]);
		}
		return ret;
	}
	
	public int getEvaluationScore(String ind){
		int score = 0;
		int[] indArr = stringToInt(ind);
		int len = indArr.length;
		for(int i=0;i<len;i++){
			int start;
			int end;
			if(i==len-1){
				
				start = indArr[i]-1;
				end = indArr[0]-1;
				
			}else{
				start = indArr[i]-1;
				end = indArr[i+1]-1;
			}
			
		//	System.out.println((start+1)+" to "+(end+1)+" "+distMap[start][end]);
			score += distMap[start][end];
			//System.out.println(score);
		}
		
		return score;
	}
	
	public void init(){
		LinkedList<City> ss = fileRead();
		this.generateDistanceMap(ss);
		this.combineAll(ss);
		
	}

	public static void main(String[] args) {
		
		
		LinkedList<City> ss = fileRead();
		
		InputToDistance iq = new InputToDistance();
		iq.generateDistanceMap(ss);
		iq.combineAll(ss);
		int len = ss.size();
		for (int i=0;i<len;i++){
			for(int j=0;j<len;j++){
				System.out.print(iq.distMap[i][j]+" ");
			}
			System.out.println();
		}
		
		//System.out.println(iq.distMap[3][7]);
		
		System.out.println(iq.getEvaluationScore("7-10-8-9-30-4-2-3-1-13-5-24-25-26-28-21-22-11-6-15-16-23-12-20-17-14-19-27-29-18"));
		
		
		
				
	}

}

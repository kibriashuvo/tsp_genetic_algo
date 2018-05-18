import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Random;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;





public class EER {
	HashMap<Integer,LinkedList<Integer>> edgeMap;
	
	public int[] stringToInt(String s){
		String[] splitArray = s.split("-");
		int len = splitArray.length;
		int[] ret = new int[len];
		for(int i=0;i<len;i++){
			ret[i] = Integer.valueOf(splitArray[i]);
		}
		return ret;
	}
	
	public void generateEdgeMap(String p1,String p2){
		edgeMap = new HashMap<Integer,LinkedList<Integer>>();
		int[] p1_array = stringToInt(p1);
		int[] p2_array = stringToInt(p2);		
		
		
		int len = p1_array.length;	
		
		
		
		//=============================
		

		for(int i=0;i<len;i++){			
			LinkedList<Integer> parser = new LinkedList<Integer>();
			int curr = p1_array[i];
			if(i==0){
				parser.add(p1_array[i+1]);
				parser.add(p1_array[len-1]);
			}else if(i==len-1){				
				parser.add(p1_array[0]);
				parser.add(p1_array[i-1]);
			}else{
				parser.add(p1_array[i-1]);
				parser.add(p1_array[i+1]);
				
			}
			
			for(int j=0;j<len;j++){
				if(p2_array[j]==curr){
					if(j==0){
						parser.add(p2_array[j+1]);
						parser.add(p2_array[len-1]);
					}else if(j==len-1){						
						parser.add(p2_array[0]);
						parser.add(p2_array[j-1]);
					}else{
						parser.add(p2_array[j-1]);						
						parser.add(p2_array[j+1]);
						
					}
				}
			}
			
		//	EER(parser);
			
		/*	System.out.print(curr+" ");
			for(Integer d:parser){
				System.out.print(d+" ");
			}
			System.out.println();
			System.out.print(curr+" ");
			for(Integer d:remDup(parser)){
				System.out.print(d+" ");
			}
			System.out.println();*/
			edgeMap.put(curr, remDup(parser));
					
		}
		
	}
	public LinkedList<Integer> remDup(LinkedList<Integer> list){		
		
		for(int i=0;i<list.size();i++){
			for(int j=i+1;j<list.size();j++){				
				if(list.get(i)==list.get(j)){
					int removed = list.remove(j);
					//System.out.println("Removed"+removed);
				
					//============Enhanced================
					//list.set(i, -removed);
				}				
					
			}
			
		}
		return list;
		
		
	}
	
	public int fittestCity(){
		int minSize = Integer.MAX_VALUE;
		int minCity = -1;
		for(Entry<Integer, LinkedList<Integer>> en: this.edgeMap.entrySet()){
			int size = en.getValue().size();
			if(size<minSize && size!=0){
				minSize = size;
				minCity = en.getKey();
			}
				
		}
		if(minCity!=0)
			return minCity;
		else
			return -1;
	}
	public void removeFromRHS(int remValue){
		for(Entry<Integer, LinkedList<Integer>> en: this.edgeMap.entrySet()){
			if(en.getValue().contains(remValue)){
				//System.out.println(en.getKey()+" "+remValue);
				en.getValue().remove((Object)remValue);
			}else if(en.getValue().contains(-remValue)){
				en.getValue().remove((Object)(-remValue));
			}
				
		}
	}
	
	
	
	
	public int nextCity(int curr){
		LinkedList<Integer> list = this.edgeMap.get((Object)curr);
		//System.out.println(list);
		if(list==null)
			return -2;
		if(list.size()==0){
			return this.fittestCity();
		}
		
		
		
		Random r= new Random();
	
		int minSize = Integer.MAX_VALUE;
		int minCity = -1;
		/*
		if(list.size()==3){
			for(Integer i: list){
				if(i<0)
					return (-i);
			}
		}
		*/
		
		for(Integer i:list){
			
			if(i<0)
				i = -i;
			
			if(this.edgeMap.get((Object)i)==null)
				continue;
		
			int thisSize = this.edgeMap.get((Object)i).size();
			/*if(thisSize==0)
				continue;*/
			
			if(thisSize<minSize){
				minSize = thisSize;
				minCity = i;
			}else if(thisSize==minSize){				//TO Randomize
				int p = r.nextInt(2);
				if(p==0)
					minCity = i;
			}
		}
		return minCity;
	}
	
	
	public void printEdgeMap(){
		for(Entry<Integer, LinkedList<Integer>> m:this.edgeMap.entrySet()){
			System.out.print(m.getKey()+" :");
			for(Integer x:m.getValue()){
				System.out.print(x+" ");
			}
			System.out.println();
		}
	
	}
	
	
	
	public LinkedList<Integer> crossoverEER(String p1,String p2){
	
		int[] p1_arr = this.stringToInt(p1);
		int[] p2_arr = this.stringToInt(p2);
		
		
		Random r = new Random();
		
		LinkedList<Integer> offspring = new LinkedList<>();
		
		int current_city;
		
		int p = r.nextInt(2);
		
		if(p==0)
			current_city = p1_arr[0];
		else
			current_city = p2_arr[0];
		
	//	System.out.println("Starting: "+current_city);
		
		offspring.add(current_city);
		
		removeFromRHS(current_city);
		
		while(true){
			if(isDone())
				break;
			current_city = nextCity(current_city);	
			if(current_city==-1)
				continue;
			if(current_city==-2)
				continue;
		//	System.out.println("Selected"+current_city);
			//this.printEdgeMap();
			if(offspring.contains(current_city))
				continue;
			offspring.add(current_city);
			removeFromRHS(current_city);
			
			
			
		}
		//System.out.println(offspring.size());
		//return this.getChromosomeString(offspring);

//		System.out.println("From EER returned offspring");
		return offspring;
		
		//=========================================
		/*	Random r = new Random();
		
		LinkedList<Integer> offspring = new LinkedList<>();
		
		int len = this.edgeMap.size();
		
		//best among all
		//int current_city = fittestCity();
		
		//Random from two parents
		
		int current_city;
		
		int p = r.nextInt(2);
		
		if(p==0)
			current_city = Integer.parseInt(p1.substring(0, 1));
		else
			current_city = Integer.parseInt(p2.substring(0, 1));
		
		offspring.add(current_city);
		
		removeFromRHS(current_city);
		
		for(int i=1;i<len;i++){			
			current_city = nextCity(current_city);			
			offspring.add(current_city);
			removeFromRHS(current_city);
			
		}		
		return this.getChromosomeString(offspring);
	*/	
	}
	
	public boolean isDone(){
		for(Entry<Integer, LinkedList<Integer>> en:this.edgeMap.entrySet()){
			if(en.getValue().size()>0)
				return false;
		}
		return true;
	}

	public LinkedList<Integer> perfomX(String p1,String p2){
		this.edgeMap = new HashMap<>();
		this.generateEdgeMap(p1, p2);
		//System.out.println("From EER generated EdgeMap");
		return this.crossoverEER(p1, p2);
		
	}
	
	public String getChromosomeString(LinkedList<Integer> list){
		String ret = "";
		for(Integer i:list){
			ret+=i+"-";
		}
		System.out.println(ret.length());
		return ret.substring(0, 80);
	}

	
	public static void main(String[] args){
		EER e = new EER();
		
		//String o = e.perfomX("18-23-30-4-17-11-24-29-16-25-5-7-2-27-12-9-20-10-3-13-28-22-21-1-8-15-14-26-6-19", "7-30-3-20-8-15-21-12-24-17-6-10-2-22-19-13-28-29-16-14-5-25-9-11-27-1-26-23-18-4");
		
		e.generateEdgeMap("18-23-30-4-17-11-24-29-16-25-5-7-2-27-12-9-20-10-3-13-28-22-21-1-8-15-14-26-6-19", "7-30-3-20-8-15-21-12-24-17-6-10-2-22-19-13-28-29-16-14-5-25-9-11-27-1-26-23-18-4");
		
		//System.out.println(o);
		
		//e.removeFromRHS(1);
		
	
		//System.out.println(e.nextCity(2));
		
	for(Entry<Integer, LinkedList<Integer>> m:e.edgeMap.entrySet()){
			System.out.print(m.getKey()+" :");
			for(Integer x:m.getValue()){
				System.out.print(x+" ");
			}
			System.out.println();
		}
	
	
		LinkedList<Integer> o = e.crossoverEER("18-23-30-4-17-11-24-29-16-25-5-7-2-27-12-9-20-10-3-13-28-22-21-1-8-15-14-26-6-19", "7-30-3-20-8-15-21-12-24-17-6-10-2-22-19-13-28-29-16-14-5-25-9-11-27-1-26-23-18-4");
	
			System.out.println(o);
		/*
		LinkedList<Integer> yy = new LinkedList<>();
		yy.add(2);
		yy.add(45);
		
		LinkedList<Integer> gg = new LinkedList<>();
		
		gg= (LinkedList<Integer>)yy.clone() ;
		
		gg.pollLast();
		gg.add(1,8);
		Random r = new Random();
		
		
		int current_city;
		
		int p = r.nextInt(2);
		String p1 ="123";
		String p2 ="567";
		
		if(p==0)
			current_city = Integer.parseInt(p1.substring(0, 1));
		else
			current_city = Integer.parseInt(p2.substring(0,1));
		
		
*/		
		
		
	}
	
	

}

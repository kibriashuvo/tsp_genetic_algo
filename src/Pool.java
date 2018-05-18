import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;



class compareIndividuals implements Comparator<Individual> {

	public int compare(Individual l1, Individual l2) {		
		if (l1.score > l2.score)
			return 1;
		else if(l1.score==l2.score)
			return 0;
		else
			return -1;

	}

}



public class Pool {	
	PriorityQueue<Individual> pool;
	LinkedList<Individual> poolList;
	
	Individual[] poolArray;
	
	InputToDistance id;
	
	
	
	public Pool() {
		pool= new PriorityQueue<Individual>(new compareIndividuals());
		poolList = new LinkedList<>();
		poolArray = new Individual[1200];
		id = new InputToDistance();
		id.init();
	}

	public String getChromosomeString(LinkedList<Integer> list){
		String ret = "";
		for(Integer i:list){
			ret+=i+"-";
		}
		return ret.substring(0, 80);
	}
	
	
	public void generatePool(int numberofindv){
	
		for(int j=0;j<1200;j++){
			LinkedList<Integer> list = new LinkedList<>();
			for(int i=1;i<=numberofindv;i++){
				list.add(i);
			}
			java.util.Collections.shuffle(list);
			String chromosome = getChromosomeString(list);
		
			int score = id.getEvaluationScore(chromosome);
			
			Individual temp = new Individual(chromosome,score);
			
			this.pool.add(temp);
			//==========List============
			this.poolList.add(temp);
			
		}
		
		
		
	}
	public Individual[] getParents(double bias,int max){
		poolArray = listToArray();
		Arrays.sort(poolArray,new compareIndividuals()); 
		Individual[] ret = new Individual[2];
		int one = this.selectIndividual(bias, max);
		int two = this.selectIndividual(bias, max);
		
		if(poolArray[one].chomosome.equals(poolArray[two].chomosome))
			two = this.selectIndividual(bias, max);
		ret[0] = poolArray[one];
		ret[1] = poolArray[two];
		
		return ret;
		
		
		
	}
	
	public void addToPopulation(Individual off){
		int max = Integer.MIN_VALUE;
		Individual worst = null;
		
		for(Individual i:poolList){
			if(i.score>max){
				max = i.score;
				worst = i;
			}
		}
		if(off.score>worst.score)
			return;
		else{
			poolList.remove(worst);
			poolList.add(off);
			poolArray = listToArray();
		}
	}
	
	
	
	public int selectIndividual(double bias,int max){
		int  index =(int)( max * (bias - Math.sqrt(bias * bias - 4.0 * (bias -1) * Math.random()))/2.0/(bias -1));
		return index;
	}
	
	public void loadPool(){
		this.fileRead();
	}
	
/*	public void storePool(){
		LinkedList<Individual> dump = new LinkedList<>();
		int len = this.pool.size();
		for(int i=0;i<len;i++){
			Individual dumped = this.pool.poll();
			dump.add(dumped);
			writeFile(dumped.chomosome+" "+dumped.score);
		}
		
		
	}*/
	
	public void dumpPool(Individual[] array,String fileName){
		for(Individual i:array){
			writeFile(i.chomosome+" "+i.score,fileName);
		}
	}
	
	public void printPool(){
		
		poolArray = listToArray();
		Arrays.sort(poolArray,new compareIndividuals()); 
		
		for(Individual i:poolArray){
			System.out.println(i.chomosome+" "+i.score);
		}
		this.dumpPool(poolArray,"dumpPool.txt" );
		
	}

	
	public Individual[] listToArray(){
		Individual[] temp = new Individual[1200];
		for(int i=0;i<1200;i++){
			temp[i] = this.poolList.get(i);
		}
		return temp;
	}
	//----------------------File Read Write--------------------
	
	
		
	public void fileRead(){
		BufferedReader br = null;
		
		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader("pool.txt"));

			while ((sCurrentLine = br.readLine()) != null) {
				String temp[] = sCurrentLine.split(" ");
				this.pool.add(new Individual(temp[0],  Integer.parseInt(temp[1])));
				this.poolList.add(new Individual(temp[0],  Integer.parseInt(temp[1])));
				
				
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		

	}
	
	
	public static void writeFile(String content,String fileName){
		
		
		 try{
	          File file =new File(fileName);
	    	  if(!file.exists()){
	    	 	file.createNewFile();
	    	  }
	    	  FileWriter fw = new FileWriter(file,true);
	    	  BufferedWriter bw = new BufferedWriter(fw);
	    	  PrintWriter pw = new PrintWriter(bw);	         
	    	  pw.println(content);	    	
	    	  pw.close();

		 
	       }catch(IOException ioe){
	    	   System.out.println("Exception occurred:");
	    	   ioe.printStackTrace();
	      }
	}
	

	
	
	
	public static void main(String[] args) {
		Pool p = new Pool();
		EER er = new EER();
		InputToDistance id = new InputToDistance();
		id.init();
		//p.generatePool(30);
	//	int o = 0;
		p.loadPool();
		System.out.println(p.pool.peek().score);
		/*LinkedList<Individual> list = new LinkedList<>();
		for(int i=0;i<1200;i++){
			list.add(p.pool.poll());
		}
		
		for(Individual i:list){
			System.out.println(i.chomosome+" Score: "+i.score);
		}
		*/
		//p.storePool();
		
		//String out = er.perfomX(list.get(0).chomosome,list.get(1).chomosome);
		//System.out.println(id.getEvaluationScore(out));
	}

}

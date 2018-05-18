import java.util.LinkedList;


public class GENITOR {

	Pool p;
	InputToDistance id;
	EER eer;
	public GENITOR(){
		p =new Pool();
		id = new InputToDistance();
		eer= new EER();
		id.init();
		p.loadPool();
	}
	
	public String getChromosomeString(LinkedList<Integer> list){
		String ret = "";
		for(Integer i:list){
			ret+="-"+i;
		}
		//System.out.println(ret.length());
		return ret.substring(1);
	}
	public static void main(String[] args) {
		GENITOR genitor = new GENITOR();
		double bias = 1.90;
		
		/*
		Individual[] parents = genitor.p.getParents(bias, 1200);
		
		System.out.println(parents[0].chomosome+" "+parents[0].score);

		System.out.println(parents[1].chomosome+" "+parents[1].score);
		

		String offspring = genitor.eer.perfomX(parents[0].chomosome, parents[1].chomosome);
		
		int value = genitor.id.getEvaluationScore(offspring);
		

		System.out.println(offspring+" "+value);*/
		
		
		for(int i=0;i<5000;i++){
			Individual[] parents = genitor.p.getParents(bias, 1200);
			//System.out.println("got Parents");
			LinkedList<Integer> offspring = genitor.eer.perfomX(parents[0].chomosome, parents[1].chomosome);
			//System.out.println("Performed X");
			String offstring = genitor.getChromosomeString(offspring);
			int value = genitor.id.getEvaluationScore(offstring);
			//System.out.println("got Evaluation");
			
			genitor.p.addToPopulation(new Individual(offstring, value));
			//System.out.println("Added to population");
			//System.out.println("Iteration: "+i+" "+offstring+" "+value);
			/*try {
				TimeUnit.MILLISECONDS.sleep((long) 500.0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			
		}
		
		genitor.p.printPool();
		/*int j=1;
		for(Individual i:genitor.p.poolList){
			System.out.println(i.chomosome+" "+i.score);
		}*/
	}

}

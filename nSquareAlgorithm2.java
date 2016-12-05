import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class nSquareAlgorithm2 {
	static Database Database = new Database();
	
	public static void main(String[] args){
		List<String> inputList = new ArrayList<>();
		inputList.add("Resorts World Sentosa");
		inputList.add("Buddha Tooth Relic Temple");
		inputList.add("Zoo");
		inputList.add("Vivo City");
		inputList.add("Singapore Flyer");
		String transportMethod = "publicT";
		String orTimeCost = "time";
		List<String> s = myRoute(inputList,transportMethod,orTimeCost);
		s.indexOf(transportMethod);
		System.out.print(s);
	}	

	public static List<String> myRoute(List<String> inputList, String transportMethod, String orTimeCost){
		Map<String, Integer> Time = new HashMap<>();
		Map<String, Long> Cost = new HashMap<>();
		ArrayList<Long> eachRouteCost = new ArrayList<>();
		ArrayList<Integer> eachRouteTime = new ArrayList<>();
		Database Database = new Database();
		String publicT, foot, taxi, time, cost, returnVal = "blank";
		publicT = "publicT";
		foot = "foot";
		taxi = "taxi";
		time = "time";
		cost = "cost";
		int amount =factorial(inputList.size());
		
		//all combinations of pathways stored in hashmap allPathCombi
		List<List<String>> allPaths = listPermutations(inputList);
		List<String> output = new ArrayList<>();
	    Map<Integer, String> allPathCombi = new HashMap<>();
		for (List<String> eachPath : allPaths) {					//each path contains the order of the places, not combined pathways yet
		    eachPath.add(0,"Hotel");
		    eachPath.add("Hotel");
		    output.clear();
	    	for (int i=0; i<eachPath.size()-1;i++){
	    		output.add(eachPath.get(i)+"-"+eachPath.get(i+1));		//each output contains the pathways to map to database keys
	    	}
	    	
	    	StringBuilder sb = new StringBuilder();
	    	for (String s : output){
	    	    sb.append(s);
	    	    if (output.indexOf(s) == (output.size()-1)){
	    	    	sb.append("&");
	    	    }
	    	    else
	    	    	sb.append("|");
	    	}
	    	String st = sb.toString();
	    	allPathCombi.put(allPaths.indexOf(eachPath), st);
		}
		
		Map<String, Integer> allTimes = new HashMap<>();
		Map<String, Long> allCosts = new HashMap<>();
		for (int i=0; i<amount ; i++){ 	//max 120 permutations --> outputs allTimes, allCosts
			String allPathCombiTailored = allPathCombi.get(i).replace("&", "");
			String[] eachPathway = allPathCombiTailored.split("\\|");
			eachRouteTime.clear();
			eachRouteCost.clear();
	
			for(String item : eachPathway){						//assume each item is key for database
//				System.out.print(item + "\n");
				if(transportMethod == publicT){
				String buscost = Database.publicT.get(item).get(1);
				String bustime = Database.publicT.get(item).get(0);
				eachRouteTime.add(Integer.parseInt(bustime));
				eachRouteCost.add((long) Double.parseDouble(buscost));
				}
				
				else if(transportMethod == taxi){
				String taxicost = Database.taxi.get(item).get(1);
				String taxitime = Database.taxi.get(item).get(0);
				eachRouteTime.add(Integer.parseInt(taxitime));
				eachRouteCost.add((long) Double.parseDouble(taxicost));
				}
				
				else if(transportMethod == foot){
				String footcost = Database.foot.get(item).get(1);
				String foottime = Database.foot.get(item).get(0);
				eachRouteTime.add(Integer.parseInt(foottime));
				eachRouteCost.add((long) Double.parseDouble(footcost));
				}
				
			}
			
			int eachRouteTimeSum = sumInt(eachRouteTime);
			double eachRouteCostSum = sumdouble(eachRouteCost);
			
			StringBuilder stringbuilderValue1 = new StringBuilder();
			StringBuilder stringbuilderValue2 = new StringBuilder();
	    	stringbuilderValue1.append(String.valueOf(eachRouteCostSum));
	    	stringbuilderValue2.append(String.valueOf(eachRouteTimeSum));
	    	String stringCostValue = stringbuilderValue1.toString();
	    	String stringTimeValue = stringbuilderValue2.toString();
			
			allTimes.put(allPathCombi.get(0), Integer.parseInt(stringTimeValue));
			allCosts.put(allPathCombi.get(i), (long) Double.parseDouble(stringCostValue));
		}
		
		
		
		
		if (orTimeCost == time){
			Object minTime = orderHashMapI(allTimes).get(0);
			returnVal = String.valueOf(minTime);
			
		}else if (orTimeCost == cost){
			Object minCost = orderHashMapL(allCosts).get(0);
			returnVal = String.valueOf(minCost);
		}
		
		
		List<String> x = prepNodes(returnVal);
		return x;

	}

	
		
		public static int factorial(int num){
			int fact = 1;
			for(int j=1;j<=num;j++){    
			      fact=fact*j;    
			  }
			return fact;    
		}
		public static int sumInt(List<Integer> list) {
		     int sum= 0; 
		     for (int i:list)
		         sum = sum + i;
		     return sum;
		}
		public static double sumdouble(List<Long> list) {
		     double sum= 0.00; 
		     for (double i:list)
		         sum = sum + i;
		     return sum;
		}
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static List orderHashMapI(Map<String,Integer> map){
			List list=new ArrayList(map.entrySet());
			Collections.sort(list,new Comparator(){
				public int compare(Object obj1, Object obj2){
					return ((Comparable)((Map.Entry)(obj1)).getValue()).compareTo(((Map.Entry)(obj2)).getValue());
					}
				});
			return list;
		}
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static List orderHashMapL(Map<String,Long> map){
			List list=new ArrayList(map.entrySet());
			Collections.sort(list,new Comparator(){
				public int compare(Object obj1, Object obj2){
					return ((Comparable)((Map.Entry)(obj1)).getValue()).compareTo(((Map.Entry)(obj2)).getValue());
					}
				});
			return list;
		}
		
		public static List<String> prepNodes(String rawAnswer){
			String[] rawConvertList = rawAnswer.split("\\|");
			List<String> rawList = new ArrayList<String>(Arrays.asList(rawConvertList));
			List<String> finalList = new ArrayList<String>();
			rawList.remove(0);
			for (String s:rawList){
				String[] eachElement = s.split("-");
				List<String> eachElementList = new ArrayList<String>(Arrays.asList(eachElement));
				String x = eachElementList.get(0);
				finalList.add(x);
			}
			return finalList;
		}
		
		public static String prepValue(String rawAnswer){
			rawAnswer = rawAnswer.replaceAll("\\D+","");
			return rawAnswer;
		}		
		
		public static List<List<String>> listPermutations(List<String> pathCombinations) {
		    if (pathCombinations.size() == 0) {
		        List<List<String>> result = new ArrayList<List<String>>();
		        result.add(new ArrayList<String>());
		        return result;
		    }
		    List<List<String>> returnMe = new ArrayList<List<String>>();
		    String firstElement = pathCombinations.remove(0);
		    List<List<String>> recursiveReturn = listPermutations(pathCombinations);
		    for (List<String> li : recursiveReturn) {
		        for (int index = 0; index <= li.size(); index++) {
		            List<String> temp = new ArrayList<String>(li);
		            temp.add(index, firstElement);
		            returnMe.add(temp);
		        }
		    }
		    return returnMe;
		}
}
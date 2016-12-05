import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FastSolver{
    public static Database database = new Database();
    public static ArrayList<String> finalOrder=new ArrayList<String>();
    public static ArrayList<String> HMPvalue = new ArrayList<String>();
    
    public static ArrayList<String> iterateList = new ArrayList<String>();
    static String nextNode, nearestNode, nodesToExplore, publicT, foot, taxi, time, cost;
    static int det; //det == 0 time, det == 1 cost
 
	

    public static void main (String args[]) {
    	
    	ArrayList<String> choicelist = new ArrayList<>();
//        choicelist.add("Resorts World Sentosa");
        choicelist.add("Buddha Tooth Relic Temple");
        choicelist.add("Singapore Flyer");
        choicelist.add("Zoo");
        choicelist.add("Vivo City");
    	
    	String transportMethod = "foot";
    	String orTimeCost = "time";
    	
    	ArrayList<String> x = myRoute(choicelist, transportMethod, orTimeCost);
    	System.out.print(x); 
    }
    
    public static ArrayList<String> myRoute(ArrayList<String> choicelist, String transportMethod, String orTimeCost){
    	if (orTimeCost == "cost"){
    		det = 1;
    	}else
    		det = 0;
    	
    	 //for hotel to all nodes
        String x = minPathFirstRoute(choicelist,det, transportMethod); //gives next nearest node in key-value pair
        finalOrder.add(contItr(x));
        choicelist.remove(contItr(x)); //removes that particular node from list
        
        //centre nodes
        while (choicelist.size()>1){
        String y = myAlgoRoute(choicelist,contItr(x),det, transportMethod);
        choicelist.remove(contItr(y)); //removes that particular node from list
        finalOrder.add(contItr(y));
        }
        
        //last node
        if (choicelist.size()>0)
        	finalOrder.add(choicelist.get(0));
		return finalOrder;
    	
    }
    
    
    
    public static String contItr(String minPath){
    	String[] eachElement = minPath.split("-");
		String letslookat = eachElement[1];
    	String[] subElement = letslookat.split("=");
    	String nextBase = subElement[0];
    	return nextBase;
    }
    
    @SuppressWarnings("static-access")
	public static String myAlgoRoute(ArrayList<String> inputList, String base, int det, String transportMethod){
    	//first loop with hotel as source node
    	Map<String, Long> allTimeCost = new HashMap<>();
    	for (String x:inputList){
    		if(transportMethod == "publicT"){
    		allTimeCost.put(createKey(base,x),(long) Double.parseDouble(database.publicT.get(createKey(base,x)).get(det)));
    		}else if (transportMethod == "taxi"){
    			allTimeCost.put(createKey(base,x),(long) Double.parseDouble(database.taxi.get(createKey(base,x)).get(det)));
    		}else if (transportMethod == "foot"){
    			allTimeCost.put(createKey(base,x),(long) Double.parseDouble(database.foot.get(createKey(base,x)).get(det)));
    		}
    	}
    	String minPath = String.valueOf(orderHashMapI(allTimeCost)); //outputs dictionary key-value pair with lowest value
    	return minPath;
    }
    
    public static String minPathFirstRoute(ArrayList<String> inputList, int det, String transportMethod){
    	//first loop with hotel as source node
    	Map<String, Long> allTimeCost = new HashMap<>();
    	for (String x:inputList){
    		if(transportMethod == "publicT"){
        		allTimeCost.put(createKey("Hotel",x),(long) Double.parseDouble(database.publicT.get(createKey("Hotel",x)).get(det)));
        		}else if (transportMethod == "taxi"){
        			allTimeCost.put(createKey("Hotel",x),(long) Double.parseDouble(database.taxi.get(createKey("Hotel",x)).get(det)));
        		}else if (transportMethod == "foot"){
        			allTimeCost.put(createKey("Hotel",x),(long) Double.parseDouble(database.foot.get(createKey("Hotel",x)).get(det)));
        		}
    	}
    	String minPath = String.valueOf(orderHashMapI(allTimeCost)); //outputs dictionary key-value pair with lowest value
    	return minPath;
    }
    
    public static String createKey(String a, String b){
    	return a + "-" + b;
    }
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object orderHashMapI(Map<String,Long> map){
		List list=new ArrayList(map.entrySet());
		Collections.sort(list,new Comparator(){
			public int compare(Object obj1, Object obj2){
				return ((Comparable)((Map.Entry)(obj1)).getValue()).compareTo(((Map.Entry)(obj2)).getValue());
				}
			});
		System.out.print(list.get(0));
		return list.get(0);
	}
 
}



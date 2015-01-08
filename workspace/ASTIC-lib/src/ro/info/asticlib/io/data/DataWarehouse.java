package ro.info.asticlib.io.data;

public class DataWarehouse {
	
	public static BWOperator BW;
	
	public static void activateOperators(){
		if(BW == null){
			BW = new BWOperator();
		}
	}
	
	
}

package dataStructure;

import java.util.Comparator;

public class Comperator implements Comparator<node_data> {

	@Override
	public int compare(node_data n1, node_data n2) {
		
		if(n1.getWeight()==n2.getWeight()) {
			return 0;
		}
		if(n1.getWeight()>n2.getWeight()) {
			return 1;
		}
		return -1;
	}
	

}

package dataStructure;

import java.io.Serializable;

public class Edge implements edge_data, Serializable {

	private node_data src,dest;
	private double weight;
	private int tag;
	private String info;
	
	
	public Edge() {}

	public Edge(node_data src, node_data dest, double weight) {
		this.src=src;
		this.dest=dest;
		this.weight=weight;
	}
	
	@Override
	public int getSrc() {	
		return this.src.getKey();
	}

	@Override
	public int getDest() {		
		return this.dest.getKey();
	}

	@Override
	public double getWeight() {	
		return this.weight;
	}

	@Override
	public String getInfo() {
		return this.info; 
	}

	@Override
	public void setInfo(String s) {
		this.info=s;
	}

	@Override
	public int getTag() {
		return this.tag;
	}

	@Override
	public void setTag(int t) {
		this.tag=t;
	}
	
	public void setWeight(double w) {
		this.weight=w;
	}
	
	public String toString() {
		return "Edge source: "+this.src.getKey()+" Edge destination: "+this.dest.getKey()+" Edge weight: "+this.weight;
	}

}

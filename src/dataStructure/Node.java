package dataStructure;

import java.io.Serializable;

import utils.Point3D;
/*
 * constructor of copy node
 * 
 */

public class Node implements node_data,Serializable{

	private double weight;
	private int tag,key;
	private Point3D location;
	private String info;
	public static final Comperator comp = new Comperator();


//	public Node(){}
	
	public Node(int key){
		this.key=key;
		double x=((Math.random()*1000)/1.5);
		double y=(((Math.random()*1000)/1.5)+50);
		Point3D point3d=new Point3D(x, y);
		this.location=point3d;
		this.weight=Double.POSITIVE_INFINITY;
		this.info=null; 
		this.tag=-1;
	}
	
	public Node(node_data n){
		this.key=n.getKey();
		this.location=n.getLocation();
		this.info=n.getInfo();
		this.weight=n.getWeight(); 
		this.tag=n.getTag();
	}


	@Override
	public int getKey() {
		return this.key;
	}

	@Override
	public Point3D getLocation() {
		return this.location;
	}

	@Override
	public void setLocation(Point3D p) {
		this.location= new Point3D(p);
	}

	@Override
	public double getWeight() {
		return this.weight;
	}

	@Override
	public void setWeight(double w) {
		this.weight=w;
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
	@Override
	public boolean equals(Object o) {
		if(o instanceof node_data) {
			node_data tmp= (node_data)o;
			if(this.key==tmp.getKey()) {
				return true;
			}
			return false;
		}
		return false;
	}

	public String toString() {
		return "Node #"+this.key; //Info: "+this.info;
	}

}

package dataStructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.management.RuntimeErrorException;

import utils.Point3D;


public class DGraph implements graph, Serializable{

	private HashMap<Integer,node_data> mapNode;
	private HashMap<Integer,LinkedHashMap<Integer,edge_data>> mapEdge;
	private int numofNodes, numofEdges;
	private int MC;


	public DGraph() {
		mapNode= new LinkedHashMap<Integer,node_data>();
		mapEdge= new LinkedHashMap<Integer,LinkedHashMap<Integer,edge_data>>();
		numofNodes=0;
		numofEdges=0;
		MC=0;
	}

	public DGraph(int n) {
		this();
		for (int i = 0; i < n; i++) {
			node_data node_tmp=new Node(i);
			addNode(node_tmp);
		}
	}

	@Override
	public node_data getNode(int key) {
		if (mapNode.containsKey(key)) {
			return mapNode.get(key);
		}
		return null;
	}

	@Override
	public edge_data getEdge(int src, int dest) {
		if (mapEdge.containsKey(src)) {
			if(mapEdge.get(src).containsKey(dest)) {      
				return mapEdge.get(src).get(dest);
			}
		}
		return null;
	}
	

	@Override
	public void addNode(node_data n) {
		if(mapNode.containsKey(n.getKey())) {
			throw new RuntimeException("Node key already Exists in the Graph");
		}
		mapNode.put(n.getKey(), n);
		numofNodes++;
		MC++;
	}

	@Override
	public void connect(int src, int dest, double w) {
		if(!(mapNode.containsKey(src)) )
		{throw new RuntimeException("Src does not exist");}
		if(!(mapNode.containsKey(dest)))
		{throw new RuntimeException("Dest does not exist");}

		if(this.getEdge(src, dest)!= null) {
			mapEdge.get(src).remove(dest);	
			numofEdges--;
			MC--;
		}
		
		if(mapEdge.get(src)==null) {
			LinkedHashMap<Integer, edge_data> map = new LinkedHashMap<Integer,edge_data>();
			edge_data edge = new Edge(mapNode.get(src),mapNode.get(dest),w);
			map.put(dest, edge);
			mapEdge.put(src,map);
			numofEdges++;
			MC++;
		}
		else {
			edge_data edge = new Edge(mapNode.get(src),mapNode.get(dest),w);
			mapEdge.get(src).put(dest, edge);
			numofEdges++;
			MC++;
		}
	}

	@Override
	public Collection<node_data> getV() {

		Collection<node_data> v;
		v=mapNode.values();
		return v;  		
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		if(mapEdge.containsKey(node_id)) {
			Collection<edge_data> e; 
			e=mapEdge.get(node_id).values();
			return e;
		}
		return null;
	}

	@Override
	public node_data removeNode(int key) {

		if(!(mapNode.containsKey(key))) {
			throw new RuntimeException("None Existing Vertex");
		}
		node_data node= mapNode.get(key);

		// Get a set of all the entries (key - value pairs) contained in the LinkesHashMap		 
		Set<Entry<Integer, node_data>> entrySet = mapNode.entrySet();

		// Obtain an Iterator for the entries Set		 
		Iterator<Entry<Integer, node_data>> itN = entrySet.iterator();

		while(itN.hasNext()) { //removes the edges the key is connected to
			node_data n= new Node(itN.next().getValue());
			if(n.getKey()==key) {
				if(mapEdge.get(n.getKey())!= null){
					int s=mapEdge.get(key).size();
					mapEdge.remove(key);
					numofNodes=numofNodes-s;
					MC=MC+s;
				}
			}
			else if(mapEdge.get(n.getKey())!= null) {
				if (mapEdge.get(n.getKey()).containsKey(key)) {			
				mapEdge.get(n.getKey()).remove(key);
				numofEdges--;
				MC++;
				}
			}
		}
		if(mapNode.get(key) != null) {
			numofNodes--;
			MC++;
		}
		mapNode.remove(key); 

		return node;
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		edge_data edge= mapEdge.get(src).get(dest);
		if(edge==null) { throw new RuntimeException("None Existing Edge");}
		System.out.println(mapNode.size());
		mapEdge.get(src).remove(dest);
		System.out.println(mapNode.size());
		numofEdges--;
		MC++;

		return edge;
	}

	@Override
	public int nodeSize() {
		return numofNodes;
	}

	@Override
	public int edgeSize() {
		return numofEdges;
	}

	@Override
	public int getMC() {
		return MC;
	}

}

package algorithms;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;

import dataStructure.Comperator;
import dataStructure.DGraph;
import dataStructure.Edge;
import dataStructure.Node;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
//import sun.jvm.hotspot.opto.MachSafePointNode;
/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author 
 *
 */

public class Graph_Algo implements graph_algorithms {
//yishaygarme
	private graph gr;

	public Graph_Algo() {}
	
	public Graph_Algo(graph g) {
		this.init(g);
	}
	@Override
	public void init(graph g) {
		this.gr= g;
	}

	@Override
	public void init(String file_name){
//		gr  = null; 
//		graph grG = new DGraph();

		try
		{    
			FileInputStream file = new FileInputStream(file_name); 
			ObjectInputStream in = new ObjectInputStream(file); 

			init((graph) in.readObject()); 
			
			
			in.close(); 
			file.close(); 

			System.out.println("Object has been deserialized"); 
		} 

		catch(IOException ex) 
		{ 
			System.out.println("FileNotFoundException is caught"); 
		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 		
	}

	@Override
	public void save(String file_name) {

		try
		{    
			FileOutputStream file = new FileOutputStream(file_name); 
			ObjectOutputStream out = new ObjectOutputStream(file); 

			out.writeObject(gr); 

			out.close(); 
			file.close(); 

			System.out.println("Object has been serialized"); 
		}   
		catch(IOException ex) 
		{ 
			System.out.println("IOException is caught");
			ex.printStackTrace();
		} 

	}

	@Override
	public boolean isConnected() {

		for (node_data node : gr.getV())
		{
			if(gr.getE(node.getKey())!= null) {
				for (edge_data edge : gr.getE(node.getKey())) {

					if(shortestPathDist(node.getKey(),edge.getDest())==Double.POSITIVE_INFINITY){return false;}
					if(shortestPathDist(edge.getDest(),node.getKey() )==Double.POSITIVE_INFINITY){return false;}
				}
			}
			else {return false;}
		}
		return true;
	}


	@Override
	public double shortestPathDist(int src, int dest) {
		if(gr.getNode(src)==null) {return Double.POSITIVE_INFINITY;}
		if(gr.getNode(dest)==null) {return Double.POSITIVE_INFINITY;}
		
		PriorityQueue<node_data> Q =new PriorityQueue<node_data>(Node.comp);
		Collection<node_data> nv =gr.getV(); //all node values on graph
		Iterator<node_data> iter = nv.iterator(); 
		PriorityQueue<node_data> Qafter =new PriorityQueue<node_data>(Node.comp);//queue to hold nodes that are 'poll' from Q

		while(iter.hasNext()) { //add all nodes to Q
			node_data n=iter.next();
			if (n.getKey()==src) {
				n.setWeight(0);
				n.setTag(-1);
				Q.add(n);
			}
			else {		
				n.setTag(-1);
				n.setWeight(Double.POSITIVE_INFINITY);
				Q.add(n);
			}
		}
		node_data de = gr.getNode(dest);

		while(de.getTag() != 1 ) {
			node_data node = Q.poll(); //removes node with lightest weight
			if(node.getTag()!= 1) {
				Collection<edge_data> edges = gr.getE(node.getKey());//all edges that node connects to
				if(edges != null) {
					for (edge_data edge : edges) { //iterates all edges of specified node
						node_data d = gr.getNode(edge.getDest());
						if(d.getWeight()>(edge.getWeight()+node.getWeight())) {
							d.setWeight(edge.getWeight()+node.getWeight());
							d.setInfo(node.getKey()+ ""); //info is key of source
							edge.setTag(1);	
							Q.remove(d);
							Q.add(d);
						}
					}
					node.setTag(1);
					Qafter.add(node); //adds each visted node					
				}
				node.setTag(1);
			}
		}

		return gr.getNode(dest).getWeight();
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {

		if (shortestPathDist(src, dest)==Double.POSITIVE_INFINITY)
		{
			return null;
		}

		node_data sof = new Node(gr.getNode(dest));
		List<node_data> path = new ArrayList<node_data>();
		
		while(sof.getKey() != src) 
		{
			path.add(sof);
			int info= Integer.parseInt(sof.getInfo()); 
			node_data be = gr.getNode(info);
			sof= new Node(be);		
		}
		path.add(gr.getNode(sof.getKey()));
		path.sort(Node.comp);

		return path;

	}

	@Override
	public List<node_data> TSP(List<Integer> targets) {
		Iterator<Integer> iter = targets.iterator(); 
		List<node_data>path=new LinkedList<node_data>();
		List<node_data>temp=new LinkedList<node_data>();
		iter.next(); //catches element in place 0
		for (Integer nd : targets) {	
			int iterator=iter.next();
			if((temp=shortestPath(nd,iterator))==null) {
				if((temp=shortestPath(iterator,nd))==null) {
					return null;
				}
				path.addAll(temp);
			}
			path.addAll(temp);
			boolean flag = check(path,targets);
			if(flag) {return path;}
		}
		return null;
	}
	public boolean check(List<node_data> path,List<Integer> targets) {
		for (Integer nd : targets) {
			if(!(path.contains(gr.getNode(nd)))) {
				return false;
			}

		}
		return true;
	}

	@Override
	public graph copy() { 

		Collection<node_data> nv =gr.getV(); //all node values on graph
		LinkedHashMap<Integer,Collection<edge_data>> ed = new LinkedHashMap<Integer,Collection<edge_data>>();

		for (node_data n : nv) {
			ed.put(n.getKey(), gr.getE(n.getKey()));
		}
		graph copyGraph= new DGraph();

		for (node_data n : nv) {
			node_data nc = new Node(n);
			copyGraph.addNode(nc);
		}
		for(node_data n: nv)
		{
			if(ed.get(n.getKey())!=null) {
				for (edge_data e : ed.get(n.getKey())) {
					copyGraph.connect(n.getKey(),e.getDest() , e.getWeight());
				}
			}
		}
		return copyGraph;
	}
}




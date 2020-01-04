package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.Node;
import dataStructure.graph;
import dataStructure.node_data;

class Graph_AlgoTest {
	DGraph g2;
	Graph_Algo g;

	@BeforeEach
	void InitGraph() {

		g2 = new DGraph(10);

		g2.connect(1, 2, 20);
		g2.connect(2, 4, 30);
		g2.connect(4, 2, 40);
		g2.connect(2, 3, 50);
		g2.connect(3, 5, 20);
		g2.connect(3, 6, 30);
		g2.connect(4, 1, 40);
		g2.connect(4, 6, 60);
		g2.connect(6, 5, 70);
		g2.connect(5, 0, 80);	
		
		g=new Graph_Algo();
		g.init(g2);
	}

	@Test
	void testSaveandInit() {
		Graph_Algo gr = new Graph_Algo();
		g.save("graph.txt");
		gr.init("graph.txt");
		assertTrue(g.isConnected()==gr.isConnected());
		assertTrue(g.shortestPathDist(1, 4)==gr.shortestPathDist(1, 4));
	}

	@Test
	void testIsConnected() { //throws exception need to fix
		boolean flag =g.isConnected();
		assertTrue(flag==false);
	}

	@Test
	void testShortestPathDist() { 
		assertTrue(90==g.shortestPathDist(1,5));
		assertEquals(Double.POSITIVE_INFINITY,g.shortestPathDist(1,9));
	}

	@Test
	void testShortestPath() {
		assertTrue(g.shortestPath(4, 6)!=null);
		assertEquals(null,g.shortestPath(3,1));
	}

	@Test
	void testTSP() {
		List<Integer> targets= new ArrayList<Integer>();
		targets.add(3); targets.add(0); targets.add(2); targets.add(1);
		List<node_data> newT= g.TSP(targets);
		assertTrue(newT!=null);		
	}

	@Test
	void testCopy() {
		graph m = g.copy();
		assertTrue(m.getV().size()== g2.getV().size());
		assertTrue(m.edgeSize()== g2.edgeSize());
	}

}

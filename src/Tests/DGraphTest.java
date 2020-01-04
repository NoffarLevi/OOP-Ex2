package Tests;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataStructure.DGraph;
import dataStructure.Node;
import dataStructure.edge_data;
import dataStructure.node_data;

class DGraphTest {

	DGraph D;

	@Test
	void testDGraph() {
		assertTrue(6==D.edgeSize());
	}

	@BeforeEach
	void testDGraphInt() {
		D = new DGraph(10);
		D.connect(0, 1, 12);
		D.connect(2, 6, 5);
		D.connect(0, 5, 8);
		D.connect(3, 1, 15);
		D.connect(8, 5, 7);
		D.connect(0, 3, 1);
		D.connect(0, 2, 11);
		D.connect(4, 5, 13);
		D.connect(9, 3, 4);
		D.connect(2, 9, 10);
	}

	@Test
	void testGetNode() {
		int key=(int)(Math.random()*10);
		node_data n= D.getNode(key);
		assertTrue(key==n.getKey());

	}

	@Test
	void testGetEdge() {
		int src=0;
		int dest=3;
		edge_data e =D.getEdge(src, dest);
		assertTrue(src==e.getSrc());
		assertTrue(dest==e.getDest());	
		assertTrue(1==e.getWeight());
	}

	@Test
	void testAddNode() {
		node_data n= new Node(10);
		D.addNode(n);
		assertTrue(10==n.getKey());
	}

	@Test
	void testConnect() {
		int src=(int)(Math.random()*10);
		int dest=(int)(Math.random()*10);
		D.connect(src, dest,13);
		assertTrue(13==D.getEdge(src, dest).getWeight());
		try {
			D.connect(30,40,70);
		}
		catch(NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testGetV() {
		int size=D.getV().size();
		assertEquals(10,size);
	}

	@Test
	void testGetE() {

		for (node_data n : D.getV()) {
			if(D.getE(n.getKey())==null) {
				System.out.println(n.toString()+" does not contain edges");
			}
			else {
				assertFalse(D.getE(n.getKey()).isEmpty());
			}			
		}
	}

	@Test
	void testRemoveNode() {
		node_data n=D.removeNode(0);
		assertEquals(0,n.getKey());			
		assertEquals(9,D.getV().size());
	}


	@Test
	void testRemoveEdge() {
		edge_data e=D.removeEdge(0, 3);
		assertEquals(0,e.getSrc());
		assertEquals(3,e.getDest());
		assertEquals(1,e.getWeight());
	}

	@Test
	void testNodeSize() {		
		assertTrue(10==D.nodeSize());
	}

	@Test
	void testEdgeSize() {
		assertTrue(10==D.edgeSize());
	}

}

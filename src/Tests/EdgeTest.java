package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dataStructure.Edge;
import dataStructure.Node;
import dataStructure.edge_data;
import dataStructure.node_data;

class EdgeTest {


	@Test
	void testEdgeNode_dataNode_dataDouble() {
		node_data n0=new Node(1);
		node_data n1=new Node(2);
		edge_data edge = new Edge(n0,n1,10);
		assertEquals(n0.getKey(),edge.getSrc());
		assertEquals(n1.getKey(),edge.getDest());
		assertEquals(10,edge.getWeight());
	}

	@Test
	void testGetSrc() {
		node_data n0=new Node(1);
		node_data n1=new Node(2);
		edge_data edge = new Edge(n0,n1,10);
		assertEquals(n0.getKey(),edge.getSrc());
	}

	@Test
	void testGetDest() {
		node_data n0=new Node(1);
		node_data n1=new Node(2);
		edge_data edge = new Edge(n0,n1,10);
		assertEquals(n1.getKey(),edge.getDest());
	}

	@Test
	void testGetWeight() {
		node_data n0=new Node(1);
		node_data n1=new Node(2);
		edge_data edge = new Edge(n0,n1,10);
		assertEquals(10,edge.getWeight());
	}

	@Test
	void testGetInfo() {
		node_data n0=new Node(1);
		node_data n1=new Node(2);
		edge_data edge = new Edge(n0,n1,10);
		assertEquals(null,edge.getInfo());
	}

	@Test
	void testSetInfo() {
		node_data n0=new Node(1);
		node_data n1=new Node(2);
		edge_data edge = new Edge(n0,n1,10);
		edge.setInfo("edge1");
		assertEquals("edge1",edge.getInfo());
	}

	@Test
	void testGetTag() {
		node_data n0=new Node(1);
		node_data n1=new Node(2);
		edge_data edge = new Edge(n0,n1,10);
		edge.setTag(1);
		assertEquals(1,edge.getTag());
	}

	@Test
	void testSetTag() {
		node_data n0=new Node(1);
		node_data n1=new Node(2);
		edge_data edge = new Edge(n0,n1,10);
		edge.setTag(n0.getKey());
		assertEquals(n0.getKey(),edge.getTag());
	}

}

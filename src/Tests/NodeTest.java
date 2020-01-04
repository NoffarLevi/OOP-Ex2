package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dataStructure.Node;
import dataStructure.node_data;
import utils.Point3D;

class NodeTest {


	@Test
	void testNode() {
		node_data n0=new Node(0);
		node_data n1=new Node(1);
		node_data n2=new Node(2);
		assertEquals("Node #0",n0.toString());
		assertEquals("Node #1",n1.toString());
		assertEquals("Node #2",n2.toString());
		
	}

	@Test
	void testNodeNode_data() {
		node_data n0=new Node(0);
		node_data n1=new Node(1);
		node_data n2=new Node(n0);
		node_data n3=new Node(n1);
		assertEquals("Node #0",n0.toString());
		assertEquals("Node #1",n1.toString());
		assertEquals("Node #0",n2.toString());
		assertEquals("Node #1",n3.toString());
		
	}

	@Test
	void testGetKey() {
		node_data n0=new Node(0);
		node_data n1=new Node(1);
		int nk0= n0.getKey();
		int nk1= n1.getKey();
		assertEquals(0,nk0);
		assertEquals(1,nk1);
	}

	@Test
	void testGetLocation() {
		node_data n0=new Node(0);
		node_data n1=new Node(1);
		Point3D p0= n0.getLocation();
		Point3D p1= n1.getLocation();
		assertEquals(p0.toString(),n0.getLocation().toString());
		assertEquals(p1.toString(),n1.getLocation().toString());
		
	}

	@Test
	void testSetLocation() {
		node_data n0=new Node(0);
		node_data n1=new Node(1);
		Point3D p0= n0.getLocation();
		n1.setLocation(p0);
		assertEquals(p0.toString(),n1.getLocation().toString());		
	}

	@Test
	void testGetWeight() {
		node_data n0=new Node(0);
		double p0= n0.getWeight();
		assertEquals(Double.POSITIVE_INFINITY,p0);
	}

	@Test
	void testSetWeight() {
		node_data n0=new Node(0);
		node_data n1=new Node(1);
		n0.setWeight(10);
		double p0= n0.getWeight();
		n1.setWeight(p0);
		assertEquals(10,p0);
		assertEquals(n1.getWeight(),p0);
	}

	@Test
	void testGetInfo() {
		node_data n0=new Node(0);
		node_data n1=new Node(1);
		assertTrue(n0.getInfo()==n1.getInfo());
	}

	@Test
	void testSetInfo() {
		node_data n0=new Node(0);
		node_data n1=new Node(1);
		n0.setInfo(n1.getKey()+"");
		assertEquals(n1.getKey()+"",n0.getInfo());
	}

	@Test
	void testGetTag() {
		node_data n0=new Node(0);
		node_data n1=new Node(1);
		assertTrue(n0.getTag()==n1.getTag());
	}

	@Test
	void testSetTag() {
		node_data n0=new Node(0);
		n0.setTag(0);
		assertTrue(0==n0.getTag());
	}

	@Test
	void testEqualsObject() {
		node_data n0=new Node(0);
		node_data n1=new Node(n0);
		assertTrue(n0.equals(n1));
	}

}

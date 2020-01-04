package dataStructure;

public class main {

	public static void main(String[] args) {
		DGraph g = new DGraph();
		g.connect(0, 1, 10);
		g.connect(0, 2, 20);
		g.connect(1, 3, 25);
		g.connect(1, 4, 7);
		g.connect(2, 4, 30);
		g.connect(3, 5, 4);
		g.connect(4, 5, 2);
		g.connect(5, 0, 1);
	//	Graph_Algo algo = new Graph_Algo();
	//	algo.init(g);
	//	System.out.println(algo.shortestPath(0, 2));
	//	System.out.println(algo.shortestPathDist(0, 2));
		System.out.println(g.getV());
	
	}
}
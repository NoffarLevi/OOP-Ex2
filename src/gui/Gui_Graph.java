package gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.sun.nio.sctp.MessageInfo;

import algorithms.Graph_Algo;
import dataStructure.*;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;

import utils.Point3D;
import utils.StdDraw;



public class Gui_Graph extends JFrame{


	private graph g;
	private Graph_Algo Galgo;
	private int MC;

	public Gui_Graph() {	
		g = new DGraph();
		Galgo = new Graph_Algo();
		Galgo.init(g);
		MC=0;
		initGUI();
	}

	public Gui_Graph(graph g)
	{
		this.g=g;
		Galgo = new Graph_Algo();
		Galgo.init(g);
		MC=g.getMC();
		initGUI();
	}

	public Gui_Graph(String file) {
		this.g=null;
		Galgo = new Graph_Algo();
		Galgo.init(file);
		this.g=Galgo.copy();
		MC=g.getMC();
		initGUI();
	}

	private void initGUI() 
	{

		this.setSize(1000, 1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		MenuBar menuBar = new MenuBar();

		Menu file = new Menu("File");
		Menu file1 = new Menu("Algorithms");
		Menu file2 = new Menu("Build Graph Options");

		menuBar.add(file);
		menuBar.add(file1);
		menuBar.add(file2);

		this.setMenuBar(menuBar);	

		MenuItem save = new MenuItem("Save");
		MenuItem load = new MenuItem("Load");
		MenuItem isConnected = new MenuItem("isConnected");
		MenuItem TSP = new MenuItem("TSP");
		MenuItem shortestPath = new MenuItem("shortestPath");
		MenuItem shortestPathDist = new MenuItem("shortestPathDist");
		MenuItem addNode = new MenuItem("addNode");
		MenuItem addEdge = new MenuItem("addEdge");
		MenuItem removeEdge = new MenuItem("removeEdge");
		MenuItem removeNode = new MenuItem("removeNode");

		file.add(save);
		file.add(load);
		file1.add(isConnected);
		file1.add(TSP);
		file1.add(shortestPath);
		file1.add(shortestPathDist);
		file2.add(addNode);
		file2.add(addEdge);
		file2.add(removeEdge);
		file2.add(removeNode);

		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				writeFileDialog();
			}
		});

		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				readFileDialog();
			}
		});

		isConnected.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isConGui();
			}
		});

		TSP.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TSPGui();			
			}
		});

		shortestPath.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				shortestPathGui();
			}
		});

		shortestPathDist.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				shortestPathDistGui();
			}
		});

		this.setVisible(true);

		addNode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addNodeGui();
			}
		});

		addEdge.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addEdgeGui();
			}
		});

		removeNode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeNodeGui();
			}
		});

		removeEdge.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeEdgeGui();
			}
		});

		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					synchronized(g) {
						if(MC != g.getMC()) {
							MC=g.getMC();
							repaint();
						}
					}
				}
			}

		});
		th.start();
	}

	private void isConGui() {
		if(g.getV().isEmpty()) {
			JOptionPane.showMessageDialog(this, "No Elements", "isConnected?", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			String m;
			if(Galgo.isConnected()) {
				m="The Graph is fully Connected";
			}
			else {
				m="The Graph isn't fully Connected";
			}
			JOptionPane.showMessageDialog(this, m, "isConnected?", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void TSPGui() {

		JFrame fr= new JFrame("TSP?");
		JLabel label =new JLabel("Seperated only by a ',' , Enter integers");
		JTextArea ints = new JTextArea(20,20);
		JButton but =new JButton("Show TSP path");

		fr.setLayout(new FlowLayout());
		fr.setBounds(0, 0, 500, 500);
		fr.add(label);
		fr.add(ints);
		fr.add(but);
		if(g.getV().isEmpty()) {
			fr.setVisible(false);
			JOptionPane.showMessageDialog(fr, "No Elements", "isConnected?", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			fr.setVisible(true);
		}
		but.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String [] m =ints.getText().split(",");
					List<Integer> targets = new LinkedList<Integer>();
					for (String integer : m) {
						targets.add(Integer.parseInt(integer));
					}
					List<node_data>tspPath=new LinkedList<node_data>();
					tspPath=Galgo.TSP(targets);

					if(tspPath==null) {
						JOptionPane.showMessageDialog(fr, "No TSP Path", "TSP?", JOptionPane.ERROR_MESSAGE);
					}else {
						String mes="";       
						for (int i=0; i<tspPath.size()-1; i++) {
							mes+=tspPath.get(i)+"->";
						}
						mes+=tspPath.get(tspPath.size()-1);

						JOptionPane.showMessageDialog(fr, mes, "TSP?", JOptionPane.INFORMATION_MESSAGE);

					}					
				}				
				catch(RuntimeException error) {
					String msg =error.getMessage();
					JOptionPane.showMessageDialog(fr, msg, "TSP?", JOptionPane.ERROR_MESSAGE);
				}				
			}
		});
	}
	private void shortestPathGui() {

		JFrame fr= new JFrame("shortestPath?");
		JLabel label1 =new JLabel("Key Source");
		JTextArea ints1 = new JTextArea(1,11);
		JLabel label2 =new JLabel("Key Destination");
		JTextArea ints2 = new JTextArea(1,11);
		JButton but =new JButton("Show shortest path");

		fr.setLayout(new FlowLayout());
		fr.setBounds(0, 0, 500, 500);
		fr.add(label1);
		fr.add(ints1);
		fr.add(label2);
		fr.add(ints2);
		fr.add(but);
		if(g.getV().isEmpty()) {
			fr.setVisible(false);
			JOptionPane.showMessageDialog(fr, "No Elements", "shortestPath?", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			fr.setVisible(true);
		}
		but.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {


				try {
					List<node_data> path = new ArrayList<node_data>();
					path=Galgo.shortestPath(Integer.parseInt(ints1.getText()),Integer.parseInt(ints2.getText()));
					System.out.println("p"+path);
					if(path==null) {
						JOptionPane.showMessageDialog(fr, "No Path", "shortestPath?", JOptionPane.ERROR_MESSAGE);
					}else {
						String mes=" ";
						for (int i=0; i<path.size()-1; i++) {
							mes=mes+path.get(i)+"->";
						}
						mes=mes+path.get(path.size()-1);

						JOptionPane.showMessageDialog(fr, mes, "shortestPath?", JOptionPane.INFORMATION_MESSAGE);
					}					
				}
				catch(RuntimeException error) {
					String msg =error.getMessage();
					JOptionPane.showMessageDialog(fr, msg, "shortestPath?", JOptionPane.ERROR_MESSAGE);
				}
			}			
		});
	}
	private void shortestPathDistGui() {

		JFrame fr= new JFrame("shortestPathDist?");
		JLabel label1 =new JLabel("Key Source");
		JTextArea ints1 = new JTextArea(1,11);
		JLabel label2 =new JLabel("Key Destination");
		JTextArea ints2 = new JTextArea(1,11);
		JButton but =new JButton("Show shortest path Distance");

		fr.setLayout(new FlowLayout());
		fr.setBounds(0, 0, 500, 500);
		fr.add(label1);
		fr.add(ints1);
		fr.add(label2);
		fr.add(ints2);
		fr.add(but);
		if(g.getV().isEmpty()) {
			fr.setVisible(false);
			JOptionPane.showMessageDialog(fr, "No Elements", "isConnected?", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			fr.setVisible(true);
		}
		but.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					double dist;
					dist=Galgo.shortestPathDist(Integer.parseInt(ints1.getText()),Integer.parseInt(ints2.getText()));
					if(dist==Double.POSITIVE_INFINITY) {
						JOptionPane.showMessageDialog(fr, "No Path", "shortestPathDist?", JOptionPane.ERROR_MESSAGE);
					}else {
						String mes;
						mes=dist+"";
						JOptionPane.showMessageDialog(fr, mes, "shortestPath?", JOptionPane.INFORMATION_MESSAGE);
					}										
				}
				catch(RuntimeException error) {
					String msg =error.getMessage();
					JOptionPane.showMessageDialog(fr, msg, "TSP?", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	private void addNodeGui() {

		try {
			node_data node = new Node(g.getV().size());
			g.addNode(node);
			repaint();
		}
		catch(RuntimeException error) {
			JOptionPane.showMessageDialog(this, error.getMessage(), "addNode", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void addEdgeGui() {
		JFrame fr= new JFrame("addEdge");
		JLabel label1 =new JLabel("Source Key");
		JTextArea src = new JTextArea(1,11);
		JLabel label2 =new JLabel("Destination Key");
		JTextArea dest = new JTextArea(1,12);
		JLabel label3 =new JLabel("Weight");
		JTextArea w = new JTextArea(1,15);
		JButton but =new JButton("Add Edge");

		fr.setLayout(new FlowLayout());
		fr.setBounds(0, 0, 500, 200);
		fr.add(label1);
		fr.add(src);
		fr.add(label2);		
		fr.add(dest);
		fr.add(label3);
		fr.add(w);
		fr.add(but);

		if(g.getV().size()<2) {
			fr.setVisible(false);
			JOptionPane.showMessageDialog(fr, "Too Little Elements", "addEdge", JOptionPane.ERROR_MESSAGE);
		}
		else {
			fr.setVisible(true);
		}
		but.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(Integer.parseInt(w.getText())<=0) {
						JOptionPane.showMessageDialog(fr, "WEIGHT MUST BE A POSITIVE NUMBER", "addEdge", JOptionPane.ERROR_MESSAGE);
					}
					else {
						g.connect(Integer.parseInt(src.getText()), Integer.parseInt(dest.getText()), Integer.parseInt(w.getText()));
						repaint();
						JOptionPane.showMessageDialog(fr, "Successful Connection", "addEdge", JOptionPane.INFORMATION_MESSAGE);	
						repaint();
					}
					
				}
				catch(RuntimeException error) {
					JOptionPane.showMessageDialog(fr, error.getMessage(), "addEdge", JOptionPane.INFORMATION_MESSAGE);	
				}
			}

		}); 
	}


	private void removeNodeGui() {

		JFrame fr= new JFrame("removeNode");
		JLabel label1 =new JLabel("Enter the Vertex Key you want to remove");
		JTextArea Nremove = new JTextArea(1,11);
		JButton but =new JButton("Remove Vertex");

		fr.setLayout(new FlowLayout());
		fr.setBounds(0, 0, 500, 200);
		fr.add(label1);
		fr.add(Nremove);
		fr.add(but);

		if(g.getV().size()<1) {
			fr.setVisible(false);
			JOptionPane.showMessageDialog(fr, "No Elements", "removeNode", JOptionPane.ERROR_MESSAGE);
		}
		else {
			fr.setVisible(true);
		}
		but.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					g.removeNode(Integer.parseInt(Nremove.getText()));
					repaint();
				}
				catch(RuntimeException err) {
					JOptionPane.showMessageDialog(fr, err.getMessage(), "removeNode", JOptionPane.ERROR_MESSAGE);
				}
				repaint();
			}
		});
	}

	private void removeEdgeGui() {
		
		JFrame fr= new JFrame("removeEdge");
		JLabel label1 =new JLabel("Enter the Vertex Key source you want to remove");
		JTextArea Nsrc = new JTextArea(1,11);
		JLabel label2 =new JLabel("Enter the Vertex Key destination you want to remove");
		JTextArea Ndest = new JTextArea(1,11);
		JButton but =new JButton("Remove Edge");

		fr.setLayout(new FlowLayout());
		fr.setBounds(0, 0, 500, 200);
		fr.add(label1);
		fr.add(Nsrc);
		fr.add(label2);
		fr.add(Ndest);
		fr.add(but);

		if(g.getV().size()<1) {
			fr.setVisible(false);
			JOptionPane.showMessageDialog(fr, "No Elements", "removeEdge", JOptionPane.ERROR_MESSAGE);
		}
		else {
			fr.setVisible(true);
		}
		but.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					g.removeEdge(Integer.parseInt(Nsrc.getText()),Integer.parseInt(Ndest.getText()));
					repaint();
				}
				catch(RuntimeException err) {
					JOptionPane.showMessageDialog(fr, err.getMessage(), "removeEdge", JOptionPane.ERROR_MESSAGE);
				}
			}
		});		
	}

	public void writeFileDialog() {
		//try write to the file
		FileDialog fd = new FileDialog(this, "Save the Graph as txt file", FileDialog.SAVE);
		fd.setFile("*.txt");
		fd.setFilenameFilter(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".txt");
			}
		});
		fd.setVisible(true);
		String folder = fd.getDirectory();
		String fileName = fd.getFile();
		Galgo.save(folder+fileName);
	}

	public void readFileDialog() {
		//try read from the file
		FileDialog fd = new FileDialog(this, "Load FileGraph", FileDialog.LOAD);
		//    fd.setFile("*.txt");
		fd.setFilenameFilter(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".txt");
			}
		});

		fd.setVisible(true);

		String folder = fd.getDirectory();
		String fileName = fd.getFile();  

		Galgo.init(folder+fileName);	  
		this.g= Galgo.copy();
		repaint();
	}

	public static void main(String[] args) 
	{


		DGraph g2 = new DGraph(7);

		g2.connect(0, 1, 10);
		g2.connect(1, 2, 20);
		g2.connect(2, 4, 30);
		g2.connect(4, 2, 40);
		g2.connect(2, 3, 50);
		g2.connect(3, 0, 20);	
		g2.connect(3, 5, 20);
		g2.connect(3, 6, 30);
		g2.connect(4, 1, 40);
		g2.connect(4, 3, 50);
		g2.connect(4, 6, 60);
		g2.connect(6, 5, 70);
		g2.connect(5, 0, 80);



		//		Graph_Algo g=new Graph_Algo();
		//		g.init(g2);
		//
		//		
		//		Gui_Graph gu= new Gui_Graph(g2);
		Gui_Graph gu= new Gui_Graph();

		//		gu.setSize(1000, 1000);
		//		gu.setVisible(true);
		//     	gu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//		g.init("yishay.txt");



		//		graph m = g.copy();
		//		System.out.println(m.getV());
		//	Gui_Graph gf= new Gui_Graph(m);

		//		System.out.println("#path"+ g.shortestPathDist(0,4));
		//		System.out.println("!"+g2.getNode(2));
		//		System.out.println("#paths is"+(g.shortestPath(1,4).toString()));


	}

	public void paint(Graphics g)
	{
		super.paint(g);

		for (node_data n : this.g.getV()) 
		{
			g.setColor(Color.BLUE);
			g.fillOval(n.getLocation().ix()-5,n.getLocation().iy()-10, 20, 20);
			g.setFont(new Font("Arial", Font.BOLD, 14));
			g.drawString(n.getKey()+"", n.getLocation().ix()+20, n.getLocation().iy());

			if(this.g.getE(n.getKey())!= null) {
				for (edge_data e : this.g.getE(n.getKey())) {

					node_data des = this.g.getNode(e.getDest());
					g.setColor(Color.RED);
					g.drawLine(n.getLocation().ix(), n.getLocation().iy(), des.getLocation().ix(), des.getLocation().iy());

					int midX=(n.getLocation().ix()+des.getLocation().ix())/2;
					int midY=(n.getLocation().iy()+des.getLocation().iy())/2;
					g.setFont(new Font("Arial", Font.BOLD, 15));
					g.drawString(e.getWeight()+"", midX, midY);

					g.setColor(Color.YELLOW);

					double m= (des.getLocation().y()-n.getLocation().y() )/  (des.getLocation().x()-n.getLocation().x() );
					double xDirect;
					if(n.getLocation().ix()>des.getLocation().ix()) {
						xDirect=des.getLocation().x()+((des.getLocation().x()/2)/2/2/2/2);
					}
					else xDirect=des.getLocation().x()-((des.getLocation().x()/2)/2/2/2/2);
					double yDirect=(des.getLocation().y()+(m*xDirect)-(m*des.getLocation().x()));
					g.fillOval((int)xDirect,(int)yDirect+5, 10, 10);	

				}
			}
		}
	}



}

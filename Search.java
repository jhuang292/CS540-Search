///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Search.java
// File:             Search.java
// Semester:         CS540 Fall 2016
//
// Author:           Junxiong Huang / jhuang292@wisc.edu
// CS Login:         junxiong
// Lecturer's Name:  Jerry Zhu
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                 
//
// Description:      Use DFS and BFS Algorithm to do water jugs problem
//                   
//
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.*;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Search {

	// Global variables
	// Capacity of 2 jugs
	static int c1, c2;
	// Goal state of the jugs
	static int goalS;
	// Iteration times
	static int Icount = 0;

	// Private State class to track the state of the jugs
	private static class State {
		public int s1, s2;

		public State(int s1, int s2) {
			this.s1 = s1;
			this.s2 = s2;
		}
	}

	// Private Node class to save the data and parent information
	private static class Node {
		// Track the parent node
		public Node parent;
		private State state;

		// Node with state and parent information
		public Node(State state, Node parent) {
			this.state = state;
			this.parent = parent;
		}
	}

	// BFS Algorithm
	public static void bfs(State initialState) {
		// Define two linkedlists to save the information of the nodes
		Queue<Node> open = new LinkedList<Node>();
		Queue<Node> close = new LinkedList<Node>();

		// Declare and initial the initial state
		Node initialNode = new Node(initialState, null);
		open.add(initialNode);
		while (!open.isEmpty()) {

			Queue<Node> memoryList = new LinkedList<Node>();
			Node cur = open.remove();

			// If the node is visited, skip
			boolean visited = false;
			// Iterate the nodes list
			for (Node node : close) {
				if (node.state.s1 == cur.state.s1 && node.state.s2 == cur.state.s2) {
					visited = true;
				}
			}
			if (visited)
				continue;

			// If the current state is the goal state, print result
			if (cur.state.s1 == goalS && cur.state.s2 == 0) {
				System.out.println("Result:");

				// List to save the goal state satisfied nodes
				List<Node> goalList = new ArrayList<Node>();
				while (cur != null) {
					goalList.add(cur);
					cur = cur.parent;
				}
				for (int i = goalList.size() - 1; i >= 0; i--) {
					System.out.print("(" + goalList.get(i).state.s1 + ", " + goalList.get(i).state.s2 + ")");
				}
				// Reset interator count index
				Icount = 0;
				return;
			}

			// 6 operations for fill, empty or pour
			// If the first jug is not full, fill it
			if (cur.state.s1 != c1) {
				State state1 = new State(c1, cur.state.s2);
				open.add(new Node(state1, cur));
				memoryList.add(new Node(state1, cur));
			}

			// If the second jug is not full, fill it
			if (cur.state.s2 != c2) {
				State state2 = new State(cur.state.s1, c2);
				open.add(new Node(state2, cur));
				memoryList.add(new Node(state2, cur));
			}

			// Empty the first jug
			if (cur.state.s1 != 0) {
				State state3 = new State(0, cur.state.s2);
				open.add(new Node(state3, cur));
				memoryList.add(new Node(state3, cur));
			}

			// Empty the second jug
			if (cur.state.s2 != 0) {
				State state4 = new State(cur.state.s1, 0);
				open.add(new Node(state4, cur));
				memoryList.add(new Node(state4, cur));
			}

			// Pour the water from the first jug to the second one
			if (cur.state.s1 != 0 && cur.state.s2 != c2) {
				if ((cur.state.s1 + cur.state.s2) > c2) {
					State state5 = new State(cur.state.s1 + cur.state.s2 - c2, c2);
					open.add(new Node(state5, cur));
					memoryList.add(new Node(state5, cur));
				} else {
					State state6 = new State(0, cur.state.s1 + cur.state.s2);
					open.add(new Node(state6, cur));
					memoryList.add(new Node(state6, cur));
				}
			}

			// Pour the water from the second jug to the first one
			if (cur.state.s1 != c1 && cur.state.s2 != 0) {
				if ((cur.state.s1 + cur.state.s2 > c1)) {
					State state7 = new State(c1, cur.state.s1 + cur.state.s2 - c1);
					open.add(new Node(state7, cur));
					memoryList.add(new Node(state7, cur));
				} else {
					State state8 = new State(cur.state.s1 + cur.state.s2, 0);
					open.add(new Node(state8, cur));
					memoryList.add(new Node(state8, cur));
				}
			}

			// After one certain operation, add the current node to the close
			// list
			close.add(cur);
			System.out.println(Icount);

			// Output the close list
			for (Node node : close) {
				System.out.print("(" + node.state.s1 + ", " + node.state.s2 + ") ");
			}
			System.out.println();

			// Output the open list
			for (Node node : memoryList) {
				System.out.print("(" + node.state.s1 + ", " + node.state.s2 + ") ");
			}
			System.out.println();
			Icount++;
		}
		System.out.println("Unsolvable");
		System.exit(-1);
	}

	public static void dfs(State initialState) {
		// Define two linkedlists to save the information of the nodes
		Stack<Node> open = new Stack<Node>();
		Stack<Node> close = new Stack<Node>();

		// Declare and initial the initial state
		Node initialNode = new Node(initialState, null);
		open.add(initialNode);

		while (!open.isEmpty()) {

			Stack<Node> memoryList = new Stack<Node>();
			Node cur = open.pop();

			// If the node is visited, skip
			boolean visited = false;
			// Iterate the nodes list
			for (Node node : close) {
				if (node.state.s1 == cur.state.s1 && node.state.s2 == cur.state.s2) {
					visited = true;
				}
			}
			if (visited)
				continue;

			// If the current state is the goal state, print result
			if (cur.state.s1 == goalS && cur.state.s2 == 0) {
				System.out.println("Result:");

				// List to save the goal state satisfied nodes
				List<Node> goalList = new ArrayList<Node>();
				while (cur != null) {
					goalList.add(cur);
					cur = cur.parent;
				}
				for (int i = goalList.size() - 1; i >= 0; i--) {
					System.out.print("(" + goalList.get(i).state.s1 + ", " + goalList.get(i).state.s2 + ")");
				}
				// Reset interator count index
				Icount = 0;
				return;
			}

			// 6 operations for fill, empty or pour
			// If the first jug is not full, fill it
			if (cur.state.s1 != c1) {
				State state1 = new State(c1, cur.state.s2);
				open.push(new Node(state1, cur));
				memoryList.push(new Node(state1, cur));
			}

			// If the second jug is not full, fill it
			if (cur.state.s2 != c2) {
				State state2 = new State(cur.state.s1, c2);
				open.push(new Node(state2, cur));
				memoryList.push(new Node(state2, cur));
			}

			// Empty the first jug
			if (cur.state.s1 != 0) {
				State state3 = new State(0, cur.state.s2);
				open.push(new Node(state3, cur));
				memoryList.push(new Node(state3, cur));
			}

			// Empty the second jug
			if (cur.state.s2 != 0) {
				State state4 = new State(cur.state.s1, 0);
				open.push(new Node(state4, cur));
				memoryList.push(new Node(state4, cur));
			}

			// Pour the water from the first jug to the second one
			if (cur.state.s1 != 0 && cur.state.s2 != c2) {
				if ((cur.state.s1 + cur.state.s2) > c2) {
					State state5 = new State(cur.state.s1 + cur.state.s2 - c2, c2);
					open.push(new Node(state5, cur));
					memoryList.push(new Node(state5, cur));
				} else {
					State state6 = new State(0, cur.state.s1 + cur.state.s2);
					open.push(new Node(state6, cur));
					memoryList.push(new Node(state6, cur));
				}
			}

			// Pour the water from the second jug to the first one
			if (cur.state.s1 != c1 && cur.state.s2 != 0) {
				if ((cur.state.s1 + cur.state.s2 > c1)) {
					State state7 = new State(c1, cur.state.s1 + cur.state.s2 - c1);
					open.push(new Node(state7, cur));
					memoryList.push(new Node(state7, cur));
				} else {
					State state8 = new State(cur.state.s1 + cur.state.s2, 0);
					open.push(new Node(state8, cur));
					memoryList.push(new Node(state8, cur));
				}
			}
			// After one certain operation, add the current node to the close
			// list
			close.add(cur);
			System.out.println(Icount);

			// Output the close list
			for (Node node : close) {
				System.out.print("(" + node.state.s1 + ", " + node.state.s2 + ") ");
			}
			System.out.println();

			// Output the open list
			for (Node node : memoryList) {
				System.out.print("(" + node.state.s1 + ", " + node.state.s2 + ") ");
			}
			System.out.println();
			Icount++;

		}
		System.out.println("Unsolvable");
		System.exit(-1);
	}

	public static void main(String[] args) {
		// Error check for input
		if (args.length != 3) {
			System.out.println("Unsolvable");
			System.exit(-1);
		}
		c1 = Integer.parseInt(args[0]);
		c2 = Integer.parseInt(args[1]);
		goalS = Integer.parseInt(args[2]);

		if (c1 <= 0 || c2 <= 0 || goalS <= 0 || c1 <= c2 || c1 < goalS) {
			System.out.println("Unsolvable");
			System.exit(-1);
		}
		System.out.println("BFS");
		System.out.println("Iteration:");
		bfs(new State(0, 0));

		System.out.println();
		System.out.println("DFS");
		System.out.println("Iteration:");
		dfs(new State(0, 0));
	}
}

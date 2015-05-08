import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SocialGraph extends UndirectedGraph<String> {

	/**
	 * Creates an empty social graph.
	 * 
	 * DO NOT MODIFY THIS CONSTRUCTOR.
	 */
	public SocialGraph() {
		super();
	}

	/**
	 * Creates a graph from a preconstructed hashmap. This method will be used
	 * to test your submissions. You will not find this in a regular ADT.
	 * 
	 * DO NOT MODIFY THIS CONSTRUCTOR. DO NOT CALL THIS CONSTRUCTOR ANYWHERE IN
	 * YOUR SUBMISSION.
	 * 
	 * @param hashmap
	 *            adjacency lists representation of a graph that has no loops
	 *            and is not a multigraph.
	 */
	public SocialGraph(HashMap<String, ArrayList<String>> hashmap) {
		super(hashmap);
	}

	// Returns a set of 2-degree friends of person, if person exists in this
	// graph. Otherwise, throws IllegalArgumentException.

	public Set<String> friendsOfFriends(String person) {
		// TODO
		if (person == null)
			throw new IllegalArgumentException();
		// check if the person exists in the graph
		if (!hashmap.containsKey(person)) {
			throw new IllegalArgumentException();
		}
		// return a set of 2-degree friends of person.
		Set<String> returnValue = new HashSet<String>();
		// go into the list of first degree friends.
		for (String bufferForFirstDegreeFriend : this.getNeighbors(person)) {
			// go into the list of first degree friends to find secondary
			// friends.
			// exclude the current person that is asking for the list of result
			for (String bufferForSecondaryDegreeFriend : this
					.getNeighbors(bufferForFirstDegreeFriend)) {
				if (!this.getNeighbors(person).contains(
						bufferForSecondaryDegreeFriend)
						&& !bufferForSecondaryDegreeFriend.equals(person)) {
					returnValue.add(bufferForSecondaryDegreeFriend);
				}
			}
		}

		return returnValue;

	}

	public List<String> getPathBetween(String pFrom, String pTo) {
		// TODO
		// paremeter validation
		// Sorry my partner
		if (!(pFrom == null) && !(pTo == null))
			// check if both person are in the map.
			if (hashmap.containsKey(pFrom) && hashmap.containsKey(pTo))
				// check if they are the same
				if (!pFrom.equals(pTo))
					;

				else
					throw new IllegalArgumentException();
			else
				throw new IllegalArgumentException();
		else
			throw new IllegalArgumentException();

		// the queue to store paths
		ArrayList<ArrayList<String>> queue = new ArrayList<ArrayList<String>>();
		// the ArrayList to store individual path.
		ArrayList<String> path = new ArrayList<String>();
		// the ArrayList to store the visted node
		ArrayList<String> visted = new ArrayList<String>();
		
		// dequeue the first time
		path.add(pFrom);
		//queue.add(path);
		visted.add(pFrom);

		// while the last vertex from the dequeued path is not the pTo keep
		// enqueuing the last vertex's neighbors
		while (!path.get(path.size() - 1).equals(pTo)) {
			
			// enqueue
			
			for (String buffer : hashmap.get(path.get(path.size() - 1))) {
				ArrayList<String> copy=new ArrayList<String>();
				// check if the vertex has already being visited
				if (!visted.contains(buffer)) {
					
					path.add(buffer);
					visted.add(buffer);
					for(String a:path)
					{copy.add(a);}
					queue.add(copy);
					// remove the last node added to leave space for the other
					path.remove(path.size() - 1);
				}

			}

			// before dequeue check if the queue is empty
			// if two nodes are not connected than the size of queue would be 0
			if (queue.size() == 0)
				return null;
			// dequeue
			path = queue.get(0);
			queue.remove(0);
		}
		return path;

	}

	/**
	 * Returns a pretty-print of this graph in adjacency matrix form. People are
	 * sorted in alphabetical order, "X" denotes friendship.
	 * 
	 * This method has been written for your convenience (e.g., for debugging).
	 * You are free to modify it or remove the method entirely. THIS METHOD WILL
	 * NOT BE PART OF GRADING.
	 *
	 * NOTE: this method assumes that the internal hashmap is valid (e.g., no
	 * loop, graph is not a multigraph). USE IT AT YOUR OWN RISK.
	 *
	 * @return pretty-print of this graph
	 */
	public String pprint() {
		// Get alphabetical list of people, for prettiness
		List<String> people = new ArrayList<String>(this.hashmap.keySet());
		Collections.sort(people);

		// String writer is easier than appending tons of strings
		StringWriter writer = new StringWriter();

		// Print labels for matrix columns
		writer.append("   ");
		for (String person : people)
			writer.append(" " + person);
		writer.append("\n");

		// Print one line of social connections for each person
		for (String source : people) {
			writer.append(source);
			for (String target : people) {
				if (this.getNeighbors(source).contains(target))
					writer.append("  X ");
				else
					writer.append("    ");
			}
			writer.append("\n");

		}

		// Remove last newline so that multiple printlns don't have empty
		// lines in between
		String string = writer.toString();
		return string.substring(0, string.length() - 1);
	}

}

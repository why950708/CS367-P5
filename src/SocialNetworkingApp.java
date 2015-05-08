import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class SocialNetworkingApp {

	/**
	 * Returns a social network as defined in the file 'filename'. See
	 * assignment handout on the expected file format.
	 * 
	 * @param filename
	 *            filename of file containing social connection data
	 * @return
	 * @throws FileNotFoundException
	 *             if file does not exist
	 */
	public static SocialGraph loadConnections(String filename)
			throws FileNotFoundException {
		// TODO
		SocialGraph returnValue = new SocialGraph();
		File connections = new File(filename);
		Scanner fileIn = new Scanner(connections);
		while (fileIn.hasNextLine()) {
			String[] buffer = fileIn.nextLine().split(" ");
			// readin each line to add all the users into the system
			for (int i = 0; i < buffer.length; i++) {
				returnValue.addVertex(buffer[i]);
			}
			// add each edge
			for (int i = 1; i < buffer.length; i++) {
				returnValue.addEdge(buffer[0], buffer[i]);
			}

		}
		return returnValue;
	}

	static Scanner stdin = new Scanner(System.in);
	static SocialGraph graph;
	static String prompt = ">> "; // Command prompt

	/**
	 * Access main menu options to login or exit the application.
	 * 
	 * THIS METHOD HAS BEEN IMPLEMENTED FOR YOU.
	 */
	public static void enterMainMenu() {
		boolean exit = false;
		while (!exit) {
			System.out.print(prompt);
			String[] tokens = stdin.nextLine().split(" ");
			String cmd = tokens[0];
			String person = (tokens.length > 1 ? tokens[1] : null);

			switch (cmd) {
			case "login":
				System.out.println("Logged in as " + person);
				enterSubMenu(person);
				System.out.println("Logged out");
				break;
			case "exit":
				exit = true;
				break;
			default:
				System.out.println("Invalid command");
			}
		}
	}

	/**
	 * Access submenu options to view the social network from the perspective of
	 * currUser. Assumes currUser exists in the network.
	 * 
	 * @param currUser
	 */
	public static void enterSubMenu(String currUser) {

		// Define the set of commands that have no arguments or one argument
		String commands = "friends fof logout print\n"
				+ "connection friend unfriend";
		Set<String> noArgCmds = new HashSet<String>(Arrays.asList(commands
				.split("\n")[0].split(" ")));
		Set<String> oneArgCmds = new HashSet<String>(Arrays.asList(commands
				.split("\n")[1].split(" ")));

		boolean logout = false;
		while (!logout) {
			System.out.print(prompt);

			// Read user commands
			String[] tokens = stdin.nextLine().split(" ");
			String cmd = tokens[0];
			String otherPerson = (tokens.length > 1 ? tokens[1] : null);

			// Reject erroneous commands
			// You are free to do additional error checking of user input, but
			// this isn't necessary to receive a full grade.
			if (tokens.length == 0)
				continue;
			if (!noArgCmds.contains(cmd) && !oneArgCmds.contains(cmd)) {
				System.out.println("Invalid command");
				continue;
			}
			if (oneArgCmds.contains(cmd) && otherPerson == null) {
				System.out.println("Did not specify person");
				continue;
			}

			switch (cmd) {

			case "connection": {
				// TODO
				List<String> buffer = graph.getPathBetween(currUser,
						otherPerson);

				if (buffer == null) {
					System.out.println("You are not connected to "
							+ otherPerson);
				} else {
					List<String> sortingBuffer = new ArrayList(buffer);

					Collections.sort(sortingBuffer);
					System.out.print("[");
					for (String buf : sortingBuffer) {
						System.out.print(buf);
						if (!sortingBuffer.get(sortingBuffer.size() - 1)
								.equals(buf)) {
							System.out.print(", ");
						}
					}
					System.out.println("]");
				}
				break;
			}

			case "friends": {
				// TODO
				Set<String> buffer = graph.getNeighbors(currUser);
				if (buffer.size() == 0) {
					System.out.println("You do not have any friends");
				} else {
					// create a new list for the sorting
					List<String> sortingBuffer = new ArrayList(buffer);

					Collections.sort(sortingBuffer);
					System.out.print("[");
					for (String buf : sortingBuffer) {

						System.out.print(buf);
						// if it is not the last element print a comma after
						if (!sortingBuffer.get(sortingBuffer.size() - 1)
								.equals(buf)) {
							System.out.print(", ");
						}
					}
					System.out.println("]");
				}
				break;
			}

			case "fof": {
				// TODO
				Set<String> buffer = graph.friendsOfFriends(currUser);
				if (buffer.size() == 0) {
					System.out
							.println("You do not have any friends of friends");
				} else {
					// create a new list for the sorting
					


					System.out.print("[");
					for (String buf : buffer) {
						System.out.print(buf);
						// if it is not the last element, print a comma ager
						
					}
					System.out.println("]");
				}
				break;
			}

			case "friend": {
				// TODO
				// check if they are already friends
				boolean buffer = graph.addEdge(currUser, otherPerson);
				if (buffer) {
					System.out.println("You are now friends with "
							+ otherPerson);
				} else {
					System.out.println("You are already friends with "
							+ otherPerson);
				}

				break;
			}

			case "unfriend": {
				// TODO
				// check if they are friend
				Set<String> buffer = graph.getNeighbors(currUser);
				if (buffer.contains(otherPerson)) {
					System.out.println("You are already not friends with "
							+ otherPerson);

				} else {
					graph.removeEdge(currUser, otherPerson);
					System.out.println("You are no longer friends with "
							+ otherPerson);
				}
				break;
			}

			case "print": {
				// This command is left here for your debugging needs.
				// You may want to call graph.toString() or graph.pprint() here
				// You are free to modify this or remove this command entirely.
				//
				// YOU DO NOT NEED TO COMPLETE THIS COMMAND
				// THIS COMMAND WILL NOT BE PART OF GRADING
				for (String a : graph.getAllVertices())
					System.out.println(a);
				;

				break;
			}

			case "logout":
				logout = true;
				break;
			} // End switch
		}
	}

	/**
	 * Commandline interface for a social networking application.
	 *
	 * THIS METHOD HAS BEEN IMPLEMENTED FOR YOU.
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Usage: java SocialNetworkingApp <filename>");
			return;
		}
		try {
			graph = loadConnections(args[0]);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}

		enterMainMenu();

	}

}

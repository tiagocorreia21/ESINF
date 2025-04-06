package examples.matrix;

import graph.Algorithms;
import graph.Graph;
import graph.matrix.MatrixGraph;

import java.util.*;

public class LabyrinthCheater {

    private static class Room {

        public final String name;
        public final boolean hasExit;

        public Room(String n, boolean exit) {
            name = n;
            hasExit = exit;
        }

        /*
         * Implementation of equals
         * Comparison of rooms is by name only
         */
        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Room)) return false;
            return name.equals(((Room) other).name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, hasExit);
        }
    }

    private static class Door {
    }

    private final Graph<Room, Door> lab;

    public LabyrinthCheater() {
        lab = new MatrixGraph<>(false);
    }

    /**
     * Inserts a new room in the map
     *
     * @param name    the room name
     * @param hasExit wheather the room has an exit
     * @return false if city exists in the map
     */

    public boolean insertRoom(String name, boolean hasExit) {

        Room r = new Room(name, hasExit);

        if (lab.key(r) == -1) {
            lab.addVertex(r);
            return true;
        }
        return false;
    }

    /**
     * Inserts a new door in the map
     * fails if room does not exist or door already exists
     *
     * @param from room
     * @param to   room
     * @return false if a room does not exist or door already exists between rooms
     */

    public boolean insertDoor(String from, String to) {

        Room r1 = new Room(from, false);
        Room r2 = new Room(to, false);

        if (lab.validVertex(r1) && lab.validVertex(r2)) {

            lab.addEdge(r1, r2, new Door());
            return true;
        }
        return false;
    }

    /**
     * Returns a list of rooms which are reachable from one room
     *
     * @param room room
     * @return list of room names or null if room does not exist
     */

    public Collection<String> roomsInReach(String room) {

        if (!lab.validVertex(new Room(room, false))) {
            return null;
        }

        LinkedList<Room> lst = Algorithms.DepthFirstSearch(lab, new Room(room, false));
        ArrayList<String> lstRooms = new ArrayList<>();

        for (Room r : lst) {
            lstRooms.add(r.name);
        }
        return lstRooms;
    }

    /**
     * Returns the nearest room with an exit
     *
     * @param room from room
     * @return room name or null if from room does not exist or there is no reachable exit
     */

    public String nearestExit(String room) {

        if (!lab.validVertex(new Room(room, false))) {
            return null;
        }

        LinkedList<Room> lst = Algorithms.BreadthFirstSearch(lab, new Room(room, false));

        for (Room r : lst) {

            if (r.hasExit) {
                return r.name;
            }
        }
        return null;
    }

    /**
     * Returns the shortest path to the nearest room with an exit
     *
     * @param from from room
     * @return list of room names or null if from room does not exist or there is no reachable exit
     */
    public LinkedList<String> pathToExit(String from) {

        String nearExit = nearestExit(from);

        if (nearExit == null) return null;

        LinkedList<Room> shortPath = new LinkedList<>();

        //shortPath = BFSshortestPath(new Room(from, false), new Room(nearExit, true));

        LinkedList<String> roomsToExit = new LinkedList<>();

        for (Room r : shortPath) {

            roomsToExit.add(r.name);
        }
        return roomsToExit;
    }

    /*private LinkedList<Room> BFSshortestPath(Room rOrig, Room rDest) {

        int numVertices = lab.numVertices();

        boolean ends = false;

        LinkedList<Room> qaux = new LinkedList<>();

        boolean[] visited = new boolean[numVertices];

        Integer[] pred = new Integer[numVertices];

        for (int i = 0; i < lab.numVertices(); i++) {
            pred[i] = -1;
        }

        qaux.add(rOrig);
    }*/

}

import java.util.*;

public class ListGraph extends AbstractGraph {
    /**
     * An array of Lists to contain the edges that originate with each vertex
     */
    private List<Edge>[] edges;

    /**
     * Construct a graph with the specified number of vertices and directionality
     * @param numV The number of vertices
     * @param directed The directionality flag
     */
    @SuppressWarnings("unchecked")
    public ListGraph(int numV, boolean directed) {
        super(numV, directed);
        edges = new List[numV];
        for(int i = 0; i < numV; i++){
            edges[i] = new LinkedList<Edge>();
        }
        Floor floor0right = new Floor(0,"floor 0 right side",0);
        Floor floor0left = new Floor(1,"floor 0 left side",10);
        Floor floor1right = new Floor(2,"floor 1 right side",20);
        Floor floor1left = new Floor(3,"floor 1 left side",30);
        Floor floor2right = new Floor(4,"floor 2 right side",40);
        Floor floor2left = new Floor(5,"floor 2 left side",50);

        insert(floor0right,floor0left);
        insert(floor1right,floor1left);
        insert(floor2right,floor2left);
        insert(floor0right,floor1right);
        insert(floor1right,floor2right);
        insert(floor0left,floor1left);
        insert(floor1left,floor2left);
    }

    public boolean isEdge(Floor source, Floor dest){
        return edges[source.getFloorNumber()].contains(new Edge(source, dest));
    }
    public void insert(Floor source, Floor dest){
        Edge edge = new Edge(source,dest);
        edges[edge.getSource().getFloorNumber()].add(edge);
        if(!isDirected()){
            edges[edge.getDest().getFloorNumber()].add(new Edge(edge.getDest(),
                    edge.getSource(),
                    edge.getWeight()));
        }
    }
    public void insert(Edge edge){
        edges[edge.getSource().getFloorNumber()].add(edge);
        if(!isDirected()){
            edges[edge.getDest().getFloorNumber()].add(new Edge(edge.getDest(),
                    edge.getSource(),
                    edge.getWeight()));
        }
    }

    public Iterator<Edge> edgeIterator(int source){
        return edges[source].iterator();
    }

    public Edge getEdge(Floor source, Floor dest){
        Edge target = new Edge(source, dest, Double.POSITIVE_INFINITY);
        for(Edge edge : edges[source.getFloorNumber()]){
            if(edge.equals(target))
                return edge; //Desired edge found; return it
        }
        //Assert: All edges for source checked.
        return target; //Desired edge not found.
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        int end = edges.length;
        for(int i=0; i < end; i++){
            sb.append("Node " + i + "-->\n");
            for(Edge e : edges[i]){
                sb.append("\tnode: " + e.getDest() + ", weight: " + e.getWeight() + "\n");
            }
        }
        return sb.toString();
    }

    private static int[] breadthFirstSearch(ListGraph graph,int start){
        if(start < 0 || start >= graph.getNumV()){
            System.out.println(User.ANSI_RED + "Wrong start vertex" + User.ANSI_RESET);
            return null;
        }
        Deque<Integer> queue = new LinkedList<>();
        int[] parent = new int[graph.getNumV()];
        for (int i = 0; i < graph.getNumV(); i++) {
            parent[i] = -1;
        }

        boolean[] visited = new boolean[graph.getNumV()];
        visited[start] = true;
        queue.addLast(start);
        while (!queue.isEmpty()){
            int current = queue.remove();
            Iterator<Edge> iterator = graph.edgeIterator(current);
            while (iterator.hasNext()){
                Edge edge = iterator.next();
                int neighbor = edge.getDest().floorNumber;
                if (!visited[neighbor]){
                    visited[neighbor] = true;
                    queue.addLast(neighbor);
                    parent[neighbor] = current;
                }
            }
        }
        return parent;
    }

    public static void map(ListGraph graph, String destTable){
        int parent[] = breadthFirstSearch(graph,0);

        if(destTable.length() < 2) {
            System.out.print(User.ANSI_GREEN + "After logging into the library " + User.ANSI_RESET);
            System.out.print(User.ANSI_GREEN + " walk to " + " floor 0 right side" + " then " + User.ANSI_RESET);
            System.out.println(User.ANSI_GREEN + " walk to " + destTable.charAt(0) + ". table is your table" + User.ANSI_RESET);
            return;
        }

        int dest = Character.getNumericValue(destTable.charAt(0));
        int parentIndex = parent[dest];
        ArrayList<Edge> path = new ArrayList<>();
        int i = 0;
        while (parentIndex != -1){
            Edge edge = graph.getEdge(new Floor(parentIndex),new Floor(dest));
            path.add(edge);
            int temp = parentIndex;
            parentIndex = parent[parentIndex];
            dest = temp;
        }
        System.out.print(User.ANSI_GREEN + "After logging into the library " + User.ANSI_RESET );
        for (int j = path.size()-1; j >= 0; j--) {
            String temp = path.get(j).getSource().floorId;
            System.out.print(User.ANSI_GREEN + " walk to "+temp+" then " +User.ANSI_RESET);
        }
        System.out.println(User.ANSI_GREEN + " walk to "+path.get(0).getDest().floorId+ " ." +User.ANSI_RESET);
        System.out.print(User.ANSI_GREEN + "After coming to "+path.get(0).getDest().floorId +User.ANSI_RESET);
        System.out.println(User.ANSI_GREEN + ". The "+(Integer.valueOf(destTable) %10 +1)
                + ". table is your table" +User.ANSI_RESET);
    }
}
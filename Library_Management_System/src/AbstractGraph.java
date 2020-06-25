import java.util.Iterator;

public abstract class AbstractGraph implements Graph {

    //Data Fields

    /**
     * Number of vertices
     */
    private int numV;
    /**
     * Flag to indicate whether this is a directed graph
     */
    private boolean directed;
    /**
     * Comma delimiter string parsing in graphs
     */
    public static final String DELIM = ","; //for file parsing


    //Constructor

    /**
     * Construct a graph with the specified number of vertices.
     * The boolean directed indicates whether or not this is a directed graph
     * @param numV The number of vertices
     * @param directed The directed flag
     */
    public AbstractGraph(int numV, boolean directed){
        this.numV = numV;
        this.directed = directed;
    }


    //Methods

    /**
     * @return number of vertex
     */
    public int getNumV() {
        return numV;
    }

    /**
     * @return if true graph directed else not directed
     */
    public boolean isDirected() {
        return directed;
    }

    /**
     *
     * @param e The new edge
     */
    public void insert(Edge e) {
        // Completed in non abstract implementations
    }

    /**
     * @param source The source vertex
     * @param dest The destination vertex
     * @return from source to dest it is edge
     */
    public boolean isEdge(int source, int dest) {
        //completed in non abstract implementations
        return false;
    }

    /**
     * @param source The source vertex
     * @param dest The destination vertex
     * @return edge
     */
    public Edge getEdge(int source, int dest) {
        //completed in non abstract implementations
        return null;
    }

    /**
     * @param source The source vertex
     * @return iterator
     */
    public Iterator<Edge> edgeIterator(int source) {
        //completed in non abstract implementations
        return null;
    }

}
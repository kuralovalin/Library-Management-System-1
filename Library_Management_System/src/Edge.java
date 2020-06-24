public class Edge {
    //Data Fields
    /**
     * Destination vertex for an edge
     */
    private Floor dest;
    /**
     * Source vertex for an edge
     */
    private Floor source;
    /**
     * Weight of an edge
     */
    private double weight;
    /**
     * Base weight for unweighted edges
     */
    private static double UNWEIGHTED_EDGE = 1.0;

    //Constructors
    /**
     * Construct an edge from source to dest and sets weight to 1.0
     * @param source The source vertex for the edge
     * @param dest The destination vertex for the edge
     */
    public Edge(Floor source, Floor dest){
        this.source = source;
        this.dest = dest;
        this.weight = UNWEIGHTED_EDGE;
    }

    /**
     * Construct an edge from source to dest with the given weight
     * @param source The source vertex
     * @param dest The destination vertex
     * @param weight The specified weight of the edge
     */
    public Edge(Floor source, Floor dest, double weight){
        this.source = source;
        this.dest = dest;
        this.weight = weight;
    }

    //Methods
    /**
     * Compares two edges for equality. Edges are equal if their source and destination vertices are the same.
     *    Weight is not considered.
     * @param e The edge we are comparing to.
     */
    public boolean equals(Edge e){
        return (this.source.getFloorNumber() == e.source.getFloorNumber()
                && this.dest.getFloorNumber() == e.dest.getFloorNumber());
    }

    /**
     * Getter for destination vertex
     * @return The destination vertex of this edge
     */
    public Floor getDest(){
        return this.dest;
    }

    /**
     * Getter for source vertex
     * @return The source vertex of this edge
     */
    public Floor getSource(){
        return this.source;
    }

    /**
     * Getter for the edge weight
     * @return The weight of this edge
     */
    public double getWeight(){
        return this.weight;
    }

    /**
     * Return the hash code for an edge. The hash code depends only on the source and destination
     * @return The hash code for this edge
     */
    public int hashCode(){
        return Integer.hashCode(source.floorNumber) + Integer.hashCode(dest.floorNumber);
    }

    /**
     * String representation of edge
     */
    public String toString(){
        return "Source: " + source.toString() + ", Destination: " + dest.toString() + "Weight: " + weight;
    }
}
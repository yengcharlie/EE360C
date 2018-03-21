

public class Vertex {
    private int node;
    private int distance;
    private Vertex previousVertex;

    public int getNode() {
        return node;
    }

    public void setNode(int node) {
        this.node = node;
    }

    public Vertex(int node, int dist, Vertex prevVertex){
        setNode(node);
        setDistance(dist);
        setPreviousVertex(prevVertex);
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Vertex getPreviousVertex() {
        return previousVertex;
    }

    public void setPreviousVertex(Vertex previousVertex) {
        this.previousVertex = previousVertex;
    }
}

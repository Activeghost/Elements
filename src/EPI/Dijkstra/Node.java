package EPI.Dijkstra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Node
{
    private final List<Edge> _edges = new ArrayList<>();
    private final int _id;

    public Node(int id)
    {
        _id = id;
    }

    public void addEdge(int nodeId, int length)
    {
        _edges.add(new Edge(nodeId, _id, length));
    }

    public void removeEdge(int id){
        _edges.removeIf(s -> s.get_targetId() == id);
    }

    public List<Edge> getConnectedEdges()
    {
        List<Edge> edges = new ArrayList<>();
        Collections.copy(edges, _edges);
        return edges;
    }

    public int getId() { return _id;}
}


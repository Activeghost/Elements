package EPI;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class DijkstraAlgorithm
{
    private final List<Node> _graph;
    private Set<Node> _visited;
    private Set<Node> _unvisited;
    private Map<Node, Node> _predeccessors;
    private Map<Node, Integer> _distances;

    public DijkstraAlgorithm(List<Node> graph)
    {
        _graph = graph;
        _visited = new HashSet<Node>();
        _unvisited = new HashSet<Node>();

        _distances = new HashMap<>();
        _predeccessors = new HashMap<>();
    }

    public void execute(Node source)
    {
        // seed the univisited set with the root node (the source to find distances from)
        _unvisited.add(source);
        while(_unvisited.size() > 0)
        {
            // get the node closest to our source (however that is defined)
            Node node = getMinimum(_unvisited);

            // add it to the visited list
            _visited.add(node);

            // remove if from the unvisited list
            _unvisited.remove(node);

            // find the (possibly new) minimum distances for this node
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Node node)
    {
        List<Edge> neighbors =  node.getConnectedEdges();

        for(Edge edge : neighbors)
        {
            final int distance = getShortestDistance(node) + edge.get_length();
            final Node target = _graph.get(edge.get_targetId());

            // if we have visited this node already then skip
            if(!_visited.contains(target))
            {
                continue;
            }

            // if this distance for this target is shorter
            // than shortest distance we've seen so far for this connection
            if(getShortestDistance(target) > distance)
            {
                _distances.put(target, distance);
                _predeccessors.put(target, node);
                _unvisited.add(target);
            }
        }
    }

    private int getShortestDistance(Node target)
    {
        return Optional
				.ofNullable(_distances.get(target))
				.orElse(Integer.MAX_VALUE);
    }

    private Node getMinimum(Set<Node> unvisited)
    {
        Node min = null;
        for(Node node: unvisited)
        {
            if(min == null) { min = node;}
            else{
                if(getShortestDistance(node) < getShortestDistance(min))
                {
                    min = node;
                }
            }
        }

        return min;
    }

    public LinkedList<Node> getPath(Node target)
    {
        LinkedList<Node> path = new LinkedList<>();

        // check if a path exists
        Optional<Node> step = Optional.ofNullable(_predeccessors.get(target));
        while(step.isPresent())
        {
            path.add(step.get());
            step = Optional.ofNullable(_predeccessors.get(step));
        }

        // reverse the path so it walks from source to target.
        Collections.reverse(path);
        return path;
    }
}

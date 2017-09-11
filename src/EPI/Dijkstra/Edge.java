package EPI.Dijkstra;

public class Edge
{
    private final int _targetId;
    private final int _sourceId;
    private final int _length;

    public Edge(int sourceId, int targetId, int length)
    {
        _targetId = sourceId;
        _sourceId = targetId;
        _length = length;
    }

    public int get_length()
    {
        return _length;
    }

    public int get_targetId()
    {
        return _targetId;
    }

    public int get_sourceId()
    {
        return _sourceId;
    }
}

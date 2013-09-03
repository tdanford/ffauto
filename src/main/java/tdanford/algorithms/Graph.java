package tdanford.algorithms;

import java.util.*;

/**
 * Graph implements an undirected, weighted graph.
 *
 * User: tdanford
 * Date: 8/12/13
 */
public class Graph {

    public Map<String,TreeMap<Edge,Integer>> edges;

    public Graph() {
        edges = new TreeMap<String,TreeMap<Edge,Integer>>();
    }

    public Graph(Graph g) {
        this();
        for(String node : g.edges.keySet()) {

            edges.put(node, new TreeMap<Edge,Integer>());
            for(Edge e : g.edges.get(node).keySet()) {

                Edge ne = new Edge(e);
                edges.get(node).put(ne, g.edges.get(node).get(e));
            }
        }
    }

    public void setEdgeWeight(Edge e, int w) {
        if(edges.containsKey(e.from) && edges.get(e.from).containsKey(e)) {
            e.weight = w;
            edges.get(e.from).put(e, w);
        }
    }

    public void addNode(String n) {
        if(!edges.containsKey(n)) { edges.put(n, new TreeMap<Edge,Integer>()); }
    }

    public void addEdge(String n1, String n2) {
        addEdge(n1, n2, 1);
    }

    public void addEdge(String n1, String n2, int weight) {
        addNode(n1);
        addNode(n2);
        edges.get(n1).put(new Edge(n1, n2, weight), weight);
        edges.get(n2).put(new Edge(n2, n1, weight), weight);
    }

    public boolean containsNode(String n) { return edges.containsKey(n); }

    public boolean containsEdge(String n1, String n2) {
        return edges.containsKey(n1) && edges.get(n1).containsKey(new Edge(n1, n2));
    }

    public int getEdgeWeight(String n1, String n2) {
        return edges.get(n1).get(new Edge(n1, n2));
    }

    public Collection<String> getNeighbors(String n) {
        ArrayList<String> nbs = new ArrayList<String>();
        for(Edge e : edges.get(n).keySet()) {
            nbs.add(e.to);
        }
        return nbs;
    }

    public void breadthFirstSearch(String start, GraphEdgeVisitor ev) {

        // 'visited' contains the nodes whose outgoing edges have been added to 'pending' in the past.
        TreeSet<String> visited = new TreeSet<String>();

        // 'pending' contains the edges that we are yet to search.
        LinkedList<Edge> pending = new LinkedList<Edge>();

        pending.addAll(edges.get(start).keySet());
        visited.add(start);

        while(!pending.isEmpty()) {

            Edge next = pending.removeFirst();

            if(!ev.visitEdge(this, next)) {
                break;
            }

            if(!visited.contains(next.to)) {
                pending.addAll(edges.get(next.to).keySet());
                visited.add(next.to);
            }
        }
    }

    public Collection<Edge> getEdges(String node) {
        return edges.get(node).keySet();
    }

    public Path findBestMaxWeightPath(String n1, String n2) {
        Comparator<Path> comp = new PathWeightComparator();
        PathFindingVisitor v = new PathFindingVisitor(n1, comp);
        breadthFirstSearch(n1, v);
        return v.getPath(n2);
    }

    public Path findBestMinWeightPath(String n1, String n2) {
        Comparator<Path> comp = new MinPathWeightComparator();
        PathFindingVisitor v = new PathFindingVisitor(n1, comp);
        breadthFirstSearch(n1, v);
        return v.getPath(n2);
    }

    public boolean connected(String n1, String n2) {
        ConnectionVisitor v = new ConnectionVisitor(n2);
        breadthFirstSearch(n1, v);
        return v.isConnectionFound();
    }

    public Integer distance(String n1, String n2) {
        DistanceEdgeVisitor dvisitor = new DistanceEdgeVisitor(n1);
        breadthFirstSearch(n1, dvisitor);
        return dvisitor.getDistance(n2);
    }
}

class FordFulkerson implements Runnable {

    private Graph capacities, residuals;
    private String source, sink;
    private Flow preflow;

    public FordFulkerson(Graph g, String source, String sink) {
        this.capacities = g;
        this.source = source;
        this.sink = sink;
        this.residuals = new Graph(g);
        this.preflow = new Flow(this.residuals, source, sink);
    }

    public int findAndPushFlow() {
        int minResidual = 0;

        Path p = residuals.findBestMinWeightPath(source, sink);

        int flowAdd = p.getMinWeight();
        preflow.pushFlow(p, p.getMinWeight());

        for(Edge e : p.edges()) {
            residuals.setEdgeWeight(e, e.weight-flowAdd);
        }

        return minResidual;
    }

    public void run() {
        while(findAndPushFlow() > 0) {
            // do nothing.
        }
    }

    public Flow getFlow() { return preflow; }
}

interface GraphEdgeVisitor {

    public boolean visitEdge(Graph g, Edge edge);
}

class ConnectionVisitor implements GraphEdgeVisitor {

    String target;
    boolean connectionFound;

    public ConnectionVisitor(String target) {
        this.target = target;
        connectionFound = false;
    }

    public boolean visitEdge(Graph g, Edge e) {
        if(e.to.equals(target)) {
            connectionFound = true;
        }

        return !connectionFound;
    }

    public boolean isConnectionFound() { return connectionFound; }
}

class PathFindingVisitor implements GraphEdgeVisitor {

    private Map<String,Path> paths;
    private Comparator<Path> comparator;

    public PathFindingVisitor(String start, Comparator<Path> c) {
        paths = new TreeMap<String,Path>();
        paths.put(start, new Path());
        this.comparator = c;
    }

    public Path getPath(String to) { return paths.get(to); }

    @Override
    public boolean visitEdge(Graph g, Edge edge) {
        if(!paths.containsKey(edge.to)) {
            paths.put(edge.to, new Path(paths.get(edge.from), edge));
        } else {
            Path oldPath = paths.get(edge.to);
            Path newPath = new Path(paths.get(edge.from), edge);
            int c = comparator.compare(newPath, oldPath);
            if(c < 0) {
                paths.put(edge.to, newPath);
            }
        }
        return true;
    }
}


class MinPathWeightComparator implements Comparator<Path> {
    public int compare(Path p1, Path p2) {
        int w1 = p1.getMinWeight(), w2 = p2.getMinWeight();
        if(w1 > w2) { return -1; }
        if(w1 < w2) { return 1; }
        return 0;
    }
}

class PathWeightComparator implements Comparator<Path> {
    public int compare(Path p1, Path p2) {
        int w1 = p1.getSumWeight(), w2 = p2.getSumWeight();
        if(w1 < w2) { return -1; }
        if(w1 > w2) { return 1; }
        return 0;
    }
}

class PathLengthComparator implements Comparator<Path> {
    public int compare(Path p1, Path p2) {
        int w1 = p1.length(), w2 = p2.length();
        if(w1 < w2) { return -1; }
        if(w1 > w2) { return 1; }
        return 0;
    }
}



class DistanceEdgeVisitor implements GraphEdgeVisitor {

    private Map<String,Integer> distances;

    public DistanceEdgeVisitor(String start) {
        distances = new TreeMap<String,Integer>();
        distances.put(start, 0);
    }

    public Integer getDistance(String node) {
        return distances.containsKey(node) ? distances.get(node) : null;
    }

    @Override
    public boolean visitEdge(Graph g, Edge edge) {
        if(!distances.containsKey(edge.to)) {
            distances.put(edge.to, distances.get(edge.from) + edge.weight);
        } else {
            int prevDist = distances.get(edge.to);
            int newDist = distances.get(edge.from) + edge.weight;
            if(newDist < prevDist) {
                distances.put(edge.to, newDist);
            }
        }
        return true;
    }
}


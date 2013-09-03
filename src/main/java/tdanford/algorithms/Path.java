package tdanford.algorithms;

import java.util.*;

/**
 * User: tdanford
 * Date: 8/13/13
 */
public class Path {

    private ArrayList<Edge> edges;
    private int sumWeight;
    private Integer minWeight, maxWeight;

    public Path() {
        edges = new ArrayList<Edge>();
        sumWeight = 0;
        minWeight = Integer.MAX_VALUE;
        maxWeight = Integer.MIN_VALUE;
    }

    public Path(Edge... es) {
        this();
        edges.addAll(Arrays.asList(es));

        for(Edge e : es) {
            sumWeight += e.weight;
            minWeight = Math.min(minWeight, e.weight);
            maxWeight = Math.max(maxWeight, e.weight);
        }

        for(int i = 1; i < edges.size(); i++) {
            if(!edges.get(i-1).to.equals(edges.get(i).from)) {
                throw new IllegalArgumentException(String.format("Edge #%d to %s doesn't match Edge #%d from %s",
                        i-1, edges.get(i-1).to, i, edges.get(i).from));
            }
        }
    }

    public Path(Path p, Edge e) {
        if(p.edges.size() > 0) {
            if(!e.from.equals(p.getTo())) {
                throw new IllegalArgumentException(String.format("edge.from %s doesn't match path.to %s", e.from, p.getTo()));
            }
        }

        edges = new ArrayList<Edge>(p.edges);
        edges.add(e);
        sumWeight = p.sumWeight + e.weight;
        maxWeight = Math.max(p.maxWeight, e.weight);
        minWeight = Math.min(p.minWeight, e.weight);
    }

    public String getFrom() { return edges.get(0).from; }
    public String getTo() { return edges.get(edges.size()-1).to; }
    public int length() { return edges.size(); }

    public int getMinWeight() { return minWeight; }
    public int getMaxWeight() { return maxWeight; }
    public int getSumWeight() { return sumWeight; }

    public List<Edge> edges() { return edges; }
}
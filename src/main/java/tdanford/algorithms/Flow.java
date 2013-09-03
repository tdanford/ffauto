package tdanford.algorithms;

import java.util.*;

/**
 * User: tdanford
 * Date: 8/13/13
 */
public class Flow {

    private Graph graph;
    private String source, sink;
    private Map<Edge,Integer> flow;

    public Flow(Graph g, String source, String sink) {
        this.graph = g;
        this.source = source;
        this.sink = sink;
        flow = new TreeMap<Edge,Integer>();
    }

    public int getValue() {
        int sum = 0;
        for(Edge e : graph.getEdges(source)) {
            sum += flow.containsKey(e) ? flow.get(e) : 0;
        }
        return sum;
    }

    public void pushFlow(Path p, int f) {
        for(Edge e : p.edges()) {
            addFlow(e, f);
        }
    }

    public void addFlow(Edge e, int f) {
        if(!flow.containsKey(e)) {
            flow.put(e, f);
            flow.put(e.reverse(), f);
        } else {
            flow.put(e, flow.get(e) + f);
            flow.put(e.reverse(), flow.get(e) - f);
        }
    }
}

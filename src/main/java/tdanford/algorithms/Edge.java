package tdanford.algorithms;

/**
 * User: tdanford
 * Date: 8/13/13
 */
public class Edge implements Comparable<Edge> {

    public String from, to;
    public int weight;

    public Edge(String from, String to) {
        this(from, to, 1);
    }

    public Edge(Edge e) {
        this.from = e.from;
        this.to = e.to;
        this.weight = e.weight;
    }

    public Edge(String from, String to, int w) {
        this.from = from;
        this.to = to;
        this.weight = w;

        if(from == null) throw new IllegalArgumentException("Null from node");
        if(to == null) throw new IllegalArgumentException("Null to node");
    }

    public Edge reverse() {
        return new Edge(to, from, weight);
    }

    public int hashCode() {
        int code = 17;
        code += from.hashCode(); code *= 37;
        code += to.hashCode(); code *= 37;
        return code;
    }

    public boolean equals(Object o) {
        if(!(o instanceof Edge)) { return false; }
        Edge e = (Edge)o;
        return from.equals(e.from) && to.equals(e.to);
    }

    @Override
    public int compareTo(Edge edge) {
        int c = from.compareTo(edge.from);
        if(c != 0) { return c; }
        return to.compareTo(edge.to);
    }
}

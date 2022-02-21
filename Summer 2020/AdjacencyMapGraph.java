import java.util.Set;
import java.util.HashSet;
@SuppressWarnings({ "unchecked" })

public class AdjacencyMapGraph<V, E> implements Graph<V, E> {
    // instance varialbes for AMG
    private boolean isDirected;
    private LinkedPositionalList<Vertex<V>> vertices = new LinkedPositionalList<>();
    private LinkedPositionalList<Edge<E>> edges = new LinkedPositionalList<>();

    // ** Constructs an empty graph (either undirected or directed) */
    public AdjacencyMapGraph(boolean directed) {
        isDirected = directed;
    }

    // ** Returns the number of vertices of the graph */
    public int numVertices() {
        return vertices.size();
    }

    // ** Returns the vertices of the graph as an iterable collection */
    public Iterable<Vertex<V>> vertices() {
        return (Iterable<Vertex<V>>) vertices;
    }

    // ** Returns the number of edges of the graph */
    public int numEdges() {
        return edges.size();
    }

    // ** Returns the edges of the graph as an iterable collection */
    public Iterable<Edge<E>> edges() {
        return (Iterable<Edge<E>>) edges;
    }

    // ** Returns the number of edges for which vertex v is the origin */
    public int outDegree(Vertex<V> v) {
        InnerVertex<V> vert = validate(v);
        return vert.getOutgoing().size();
    }

    // ** Returns an iterable collection of edges for which vertex v is the origin
    // */
    public Iterable<Edge<E>> outgoingEdges(Vertex<V> v) {
        InnerVertex<V> vert = validate(v);
        return vert.getOutgoing().values();
    }

    // ** Returns the number of edges for which vertex v is the destination */
    public int inDegree(Vertex<V> v) {
        InnerVertex<V> vert = validate(v);
        return vert.getIncoming().size();
    }

    // ** Returns an iterable collection of edges for which vertex c is the
    // destination */
    public Iterable<Edge<E>> incomingEdges(Vertex<V> v) {
        InnerVertex<V> vert = validate(v);
        return vert.getIncoming().values();
    }

    // ** Returns the edge from u to v or null if they are not adjacent */
    public Edge<E> getEdge(Vertex<V> u, Vertex<V> v) {
        InnerVertex<V> origin = validate(u);
        return origin.getOutgoing().get(v);
    }

    // ** Returns the vertices of edge e as an array of length 2 */
    public Vertex<V>[] endVertices(Edge<E> e) {
        InnerEdge<E> edge = validate(e);
        return edge.getEndpoints();
    }

    //** Returns the vertex that is opposite vertex v on edge e */
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException{
        InnerEdge<E> edge = validate(e);
        Vertex<V>[] endpoints = edge.getEndpoints();
        if(endpoints[0] == v){
            return endpoints[1];
        } else if (endpoints[1] == v){
            return endpoints[0];
        } else {
            throw new IllegalArgumentException("v is not incident to this edge");
        }

    }

    //** Inserts and returns a new vertex with the given element */
    public Vertex<V> insertVertex(V element){
        InnerVertex<V> v = new InnerVertex<>(element, isDirected);
        v.setPosition(vertices.addLast(v));
        return v;
    }

    //** Inserts and returns a new edge between u and v storing the given element */
    public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element) throws IllegalArgumentException{
        if(getEdge(u, v) == null){
            InnerEdge<E> e = new InnerEdge<>(u, v, element);
            e.setPosition(edges.addLast(e));
            InnerVertex<V> origin = validate(u);
            InnerVertex<V> dest = validate(v);
            origin.getOutgoing().put(v, e);
            dest.getIncoming().put(u,e);
            return e;
        } else {
            throw new IllegalArgumentException("Edge from u to v exists");
        }
    }
    //** Removes a vertex and all its incident edges from the graph*/
    public void removeVertex(Vertex<V> v){
        InnerVertex<V> vert = validate(v);
        //** remove all incident edges from the graph */
        for(Edge<E> e : vert.getOutgoing().values()){
            removeEdge(e);
        }
        for(Edge<E> e : vert.getIncoming().values()){
            removeEdge(e);
        }
        //** remove this vertex from the list of vertices */
        vertices.remove(vert.getPosition());
    }
    public void removeEdge(Edge<E> e) throws IllegalArgumentException {
        InnerEdge<E> edge = validate(e);
        // remove this edge from vertices' adjacencies
        InnerVertex<V>[] verts = (InnerVertex<V>[]) edge.getEndpoints();
        verts[0].getOutgoing().remove(verts[1]);
        verts[1].getIncoming().remove(verts[0]);
        // remove this edge from the list of edges
        edges.remove(edge.getPosition());
        edge.setPosition(null);             // invalidates the edge
      }
    
 
    private InnerVertex<V> validate(Vertex<V> v) {
        if (!(v instanceof InnerVertex))
            throw new IllegalArgumentException("Invalid vertex");
        InnerVertex<V> vert = (InnerVertex<V>) v; // safe cast
        if (!vert.validate(this))
            throw new IllegalArgumentException("Invalid vertex");
        return vert;
    }
 
    private InnerEdge<E> validate(Edge<E> e) {
        if (!(e instanceof InnerEdge))
            throw new IllegalArgumentException("Invalid edge");
        InnerEdge<E> edge = (InnerEdge<E>) e; // safe cast
        if (!edge.validate(this))
            throw new IllegalArgumentException("Invalid edge");
        return edge;
    }

    // ** A vertex of an adjeacency mao graph representation */
    private class InnerVertex<V> implements Vertex<V> {
        private V element;
        private Position<Vertex<V>> pos;
        private Map<Vertex<V>, Edge<E>> outgoing, incoming;

        // ** Constructs a new InnerVertex instance storing the given element */
        public InnerVertex(V elem, boolean graphIsDirected) {
            element = elem;
            outgoing = new ProbeHashMap<>();
            if (graphIsDirected) {
                incoming = new ProbeHashMap<>();
            } else {
                incoming = outgoing; // if undirected call by outgoing
            }
        }
        public boolean validate(Graph<V,E> graph) {
            return (AdjacencyMapGraph.this == graph && pos != null);
          }

        // ** Returns the element associated with the vertex */
        public V getElement() {
            return element;
        }

        // ** Stores the position of this vertex within the graph's vertex list */
        public void setPosition(Position<Vertex<V>> p) {
            pos = p;
        }

        // ** Returns the position of this vertex within the graph's vertex list */
        public Position<Vertex<V>> getPosition() {
            return pos;
        }

        // ** Returns reference to the underlying map of outgoing edges */
        public Map<Vertex<V>, Edge<E>> getOutgoing() {
            return outgoing;
        }

        // ** Returns reference to the underlying map of incoming edges */
        public Map<Vertex<V>, Edge<E>> getIncoming() {
            return incoming;
        }
    } // end of nested InnerVertex class

    // ** An edge between two vertices */
    private class InnerEdge<E> implements Edge<E> {
        private E element;
        private Position<Edge<E>> pos;
        private Vertex<V>[] endpoints;
        // ** Constructs InnerEdge instance from u to v, storing the given element */
        public InnerEdge(Vertex<V> u, Vertex<V> v, E elem) {
            element = elem;
            endpoints = (Vertex<V>[]) new Vertex[] { u, v }; // array of length 2
        }
        public boolean validate(Graph<V,E> graph) {
            return AdjacencyMapGraph.this == graph && pos != null;
          }

        // ** Returns the element associated with the edge */
        public E getElement() {
            return element;
        }

        // ** Returns reference to the endpoint array */
        public Vertex<V>[] getEndpoints() {
            return endpoints;
        }

        // ** Stores the position of this edge within the graph's vertex list */
        public void setPosition(Position<Edge<E>> p) {
            pos = p;
        }

        // ** Returns the position of this edge within the graph's vertex list */
        public Position<Edge<E>> getPosition() {
            return pos;
        }
    }

    //** Performs depth-first search of Graph g starting at Vertex u */
    public static <V,E> void DFS(Graph<V,E> g, Vertex<V> u, Set<Vertex<V>> known, Map<Vertex<V>, Edge<E>> forest){
        known.add(u);   //u has been discovered
        for(Edge<E> e : g.outgoingEdges(u)){    //for every outgoing edge from Vertex u
            Vertex<V> v = g.opposite(u, e);
            if(!known.contains(v)){
                forest.put(v,e);
                DFS(g, v, known, forest);
            }
        }
    }

    //** Returns an ordered list of edges comprising the directed path from u to v */
    public static <V,E> PositionalList<Edge<E>> constructPath(Graph<V,E> g, Vertex<V> u, Vertex<V> v, Map<Vertex<V>, Edge<E>> forest){
        PositionalList<Edge<E>> path = new LinkedPositionalList<>();
        if(forest.get(v) != null){
            Vertex<V> walk = v;
            while(walk != u){
                Edge<E> edge = forest.get(walk);
                path.addFirst(edge);
                walk = g.opposite(walk, edge);
            }
        }
        return path;
    }

    //** Performs DFS for the entire graph and returns the DFS forest as a map */
    public static <V,E> Map<Vertex<V>, Edge<E>> DFSComplete(Graph<V,E> g){
        Set<Vertex<V>> known = new HashSet<>();
        Map<Vertex<V>, Edge<E>> forest = new ProbeHashMap<>();
        for(Vertex<V> u : g.vertices()){
            if(!known.contains(u)){
                DFS(g, u, known, forest);
            }
        }
        return forest;
    }

    //** Performs breadth-first search of Graph g starting at Vertex u */
    public static <V,E> void BFS(Graph<V,E> g, Vertex<V> s, Set<Vertex<V>> known, Map<Vertex<V>, Edge<E>> forest){
        PositionalList<Vertex<V>> level = new LinkedPositionalList<>();
        known.add(s);
        level.addLast(s);
        while(!level.isEmpty()){
            PositionalList<Vertex<V>> nextLevel = new LinkedPositionalList<>();
            for(Vertex<V> u : (Iterable<Vertex<V>>) level){
                for(Edge<E> e : g.outgoingEdges(u)){
                    Vertex<V> v = g.opposite(u, e);
                    if(!known.contains(v)){
                        known.add(v);
                        forest.put(v,e);
                        nextLevel.addLast(v);
                    }
                }
            }
        }
    }

    //** Converts graph g into its transitive closure */
    public static <V,E> void transitiveClosure(Graph<V,E> g){
        for(Vertex<V> k : g.vertices()){
            for(Vertex<V> i : g.vertices()){
                //verify that edge (i,k) exists in the partial closure
                if(i != k && g.getEdge(i,k) != null){
                    for(Vertex<V> j : g.vertices()){
                        //verify that edge (k,j) exists in the partial closure
                        if(i != j && j != k && g.getEdge(k, j) != null){
                            //if (i,j) not yet included, add it to the closure
                            if(g.getEdge(i, j) == null){
                                g.insertEdge(i,j,null);
                            }
                        }
                    }
                }
            }
        }
    }

    //** Returns a list of vertices of directed acyclic graph g in topological order */
    public static <V,E> PositionalList<Vertex<V>> topologicalSort(Graph<V,E> g){
        //list of vertices places in topological order
        PositionalList<Vertex<V>> topo = new LinkedPositionalList<>();
        //container of vertices that have no remaining constraints
        Stack<Vertex<V>> ready = new LinkedStack<>();
        //map keeping track of remaining in-degree for each vertex
        Map<Vertex<V>, Integer> inCount = new ProbeHashMap<>();
        for(Vertex<V> u : g.vertices()){
            inCount.put(u, g.inDegree(u));  //initialize with actual in-degree
            if(inCount.get(u) == 0){        //if u has no incoming edges
                ready.push(u);              //it is free of constraints
            }
        }
        while(!ready.isEmpty()){
            Vertex<V> u = ready.pop();
            topo.addLast(u);
            for(Edge<E> e : g.outgoingEdges(u)){    //consider all outgoing neighbors of u
                Vertex<V> v = g.opposite(u, e);
                inCount.put(v, inCount.get(v) - 1); // v has one less constraint without u
                if(inCount.get(v) == 0){
                    ready.push(v);
                }
            }
        }
        return topo;
    }

    //** Computes the shortest-path distances from src vertex to all reachable vertices of g */
    public static <V> Map<Vertex<V>, Integer> shortestPathLengths(Graph<V, Integer> g, Vertex<V> src){
        //d.get(v) is upper bound on distance from src to v
        Map<Vertex<V>, Integer> d = new ProbeHashMap<>();
        //map reachable v to its d value
        Map<Vertex<V>, Integer> cloud = new ProbeHashMap<>();
        //pq will have vertices as elements, weth d.get(v) as key
        HeapAdaptablePriorityQueue<Integer, Vertex<V>> pq = new HeapAdaptablePriorityQueue<>();
        //maps from vertex to pq locator 
        Map<Vertex<V>, Entry<Integer, Vertex<V>>> pqTokens = new ProbeHashMap<>();
        //for each vertex v of the graph, add an entry to the PQ, with the source having distance 0 and all others having 
        //infinite distance
        for(Vertex<V> v : g.vertices()){
            if(v == src){
                d.put(v,0);
            } else {
                d.put(v, Integer.MAX_VALUE);
            }
            pqTokens.put(v, pq.insert(d.get(v), v));    //save entries for future updates 
        }
        // now begin adding reachable vertices to the cloud 
        while(!pq.isEmpty()){
            Entry<Integer, Vertex<V>> entry = pq.removeMin();
            int key = entry.getKey();
            Vertex<V> u = entry.getValue();
            cloud.put(u,key);               //this is the actual distance to u
            pqTokens.remove(u);             //u is no longer in the pq 
            for(Edge<Integer> e : g.outgoingEdges(u)){
                Vertex<V> v = g.opposite(u, e);
                if(cloud.get(v) == null){
                    //perform relaxation step of edge(u,v)
                    int wgt = e.getElement();
                    if(d.get(u) + wgt < d.get(v)){          //better path to v?
                        d.put(v, d.get(u) + wgt);           //update the distance 
                        pq.replaceKey(pqTokens.get(v), d.get(v));   //update the pq entry 
                    }
                }
            }
        }
        return cloud;   //only returns the vertices which are reachable 
    }

    //** Reconstructs a shortest path tree rooted at vertex s, given distance map d. The tree
    // is represented as a map from each reachable vertex v (other than s) 
    // to the edge e = (u,v) that is used to reach v from its parent u in the tree */
    public static <V> Map< Vertex<V>, Edge<Integer>> spTree(Graph<V, Integer> g, Vertex<V> s, Map<Vertex<V>, Integer> d){
        Map<Vertex<V>, Edge<Integer>> tree = new ProbeHashMap<>();
        for(Vertex<V> v : d.keySet()){
            if(v != s){
                for(Edge<Integer> e : g.incomingEdges(v)){
                    Vertex<V> u = g.opposite(v, e);
                    int wgt = e.getElement();
                    if(d.get(v) == d.get(u) + wgt){
                        tree.put(v,e);
                    }
                }
            }
        }
        return tree;
    }

    public static void main(String[] args) {
        
    }



}

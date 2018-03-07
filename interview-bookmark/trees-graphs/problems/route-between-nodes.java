// Route Between Nodes: Given a directed graph, design an algorithm to find out whether there is a
// route between two nodes.


enum State {Unvisited, Visited, Visiting;}

boolean search(Graph g, Node start, Node end){
  if(start == end) return true;

  Queue<Node> q = new LinkedList<Node>();

  for(Node n : g.getNodes()){
    n.state = State.Unvisited;
  }

  start.state = State.Visiting;
  q.add(start);

  while(!q.isEmpty()){
    Node current = q.poll();
    if(current == null) continue;
    if(current == end) return true;
    for(Node next : current.getAjacent()){
      if(next.state == State.Unvisited){
        next.state = State.Visiting;
        q.add(next);
      }
    }
    current.state = State.Visited;
  }
  return false;
}

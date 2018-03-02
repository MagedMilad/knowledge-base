// Delete Middle Node: Implement an algorithm to delete a node in the middle (i.e., any node but
// the first and last node, not necessarily the exact middle) of a singly linked list, given only access to
// that node.

// EXAMPLE
// lnput:the node c from the linked list a->b->c->d->e->f
// Result: nothing is returned, but the new linked list looks like a ->b->d- >e- >f


boolean deleteNode(LinkedListNode n){
  if(n == null || n.next == null) return false;

  n.data = n.next.data;
  n.next = n.next.next;
  return true;
}

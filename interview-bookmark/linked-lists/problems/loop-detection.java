// Loop Detection: Given a circular linked list, implement an algorithm that returns the node at the
// beginning of the loop.
// DEFINITION
// Circular linked list: A (corrupt) linked list in which a node's next pointer points to an earlier node, so
// as to make a loop in the linked list.
// EXAMPLE
// Input: A - > B - > C - > D - > E - > C [the same C as earlier]
// Output: C

LinkedListNode FindBeginning(LinkedListNode head) {
  LinkedListNode slow = head, fast = head;

  while(fast != null && fast.next != null){
    slow = slow.next;
    fast = fast.next.next;
    if(slow == fast) break;
  }

  if(fast == null || fast.next == null) return null;

  slow = head;
  while(slow != head){
    slow = slow.next;
    fast = fast.next;
  }

  return fast;
}

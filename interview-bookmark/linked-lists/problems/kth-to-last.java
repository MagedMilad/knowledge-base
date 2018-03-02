// Return Kth to Last: Implement an algorithm to find the kth to last element of a singly linked list.

int LinkedListSize(LinkedlistNode head){
  int len = 0;
  while (head != null) {
    len++;
    head = head.next;
  }
  return len;
}

LinkedListSize printKthToLast(LinkedlistNode head, int k) {
  int size = LinkedListSize(head);
  int idx = size - k;
  int currentIdx = 0;
  LinkedlistNode current = head;
  while(currentIdx < idx){
    currentIdx++;
    current = current.next;
  }
  return current;
}


LinkedListSize printKthToLast(LinkedlistNode head, int k) {
  LinkedlistNode p1 = head;
  LinkedlistNode p2 = head;
  for(int i=0;i<k;i++){
    p2 = p2.next;
  }
  while(p2 != null){
    p2 = p2.next;
    p1 = p1.next;
  }

  return p1;
}

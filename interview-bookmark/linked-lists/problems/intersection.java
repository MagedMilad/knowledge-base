// Intersection: Given two (singly) linked lists, determine if the two lists intersect. Return the
// intersecting node. Note that the intersection is defined based on reference, not value. That is, if the
// kth node of the first linked list is the exact same node (by reference) as the jth node of the second
// linked list, then they are intersecting.


int getLen(LinkedListNode head){
  int ret = 0;
  while(head != null){
    head = head.next;
    ret++;
  }
  return ret;
}

LinkedListNode advance(LinkedListNode head, int cnt){
  while(cnt--){
    head = head.next;
  }
  return head;
}

LinkedlistNode findintersection(LinkedListNode list1, LinkedListNode list2) {
  int l1 = getLen(list1);
  int l2 = getLen(list2);

  if(l1 > l2) list1 = advance(list1, l1 - l2);
  else if(l2 > l1) list2 = advance(list2, l2 - l1);

  while(list1 != null){
    if(list1 == list2) return list1;
    list1 = list1.next;
    list2 = list2.next;
  }

  return null;
}

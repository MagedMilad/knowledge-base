// Sum Lists: You have two numbers represented by a linked list, where each node contains a single
// digit. The digits are stored in reverse order, such that the 1 's digit is at the head of the list. Write a
// function that adds the two numbers and returns the sum as a linked list.

// EXAMPLE
// Input: (7-> 1 -> 6) + (5 -> 9 -> 2).That is,617 + 295.
// Output: 2 -> 1 -> 9. That is, 912.
// FOLLOW UP
// Suppose the digits are stored in forward order. Repeat the above problem.
// Input: (6 -> 1 -> 7) + (2 -> 9 -> 5).That is,617 + 295.
// Output: 9 -> 1 -> 2. That is, 912.


LinkedlistNode addLists(LinkedListNode l1, LinkedListNode l2){
  return addListsHelper(l1, l2, 0);
}


LinkedlistNode addListsHelper(LinkedListNode l1, LinkedListNode l2, int carry){
  if(l1 == null && l2 == null) return null;

  int value = carry;
  if(l1 != null) value += l1.data;
  if(l2 != null) value += l2.data;

  LinkedListNode root = new LinkedlistNode(value%10);
  root.next = addListsHelper(
    l1 == null ? null : l1.next,
    l2 == null ? null : l2.next,
    value > 10 ? 1 : 0
  )
  return root;
}

////////////////////////////////////////////////////////////

class PartialSum{
  LinkedListNode node = null;
  int carry = 0;
}

LinkedlListNode addLists(LinkedListNode l1, LinkedListNode l2){
  int len1 = getLength(l1);
  int len2 = getLength(l2);

  if(len1 > len2){
    l2 = appendZeroToList(l2, len1 - len2);
  }else{
    l1 = appendZeroToList(l1, len2 - len1);
  }

  PartialSum res = addListsHelper(l1, l2);

  if(res.carry == 0) return res.node;
  return insertBefore(res.node, res.carry);
}

int getLength(LinkedListNode head){
  int len=0;
  while(head != null){
    len++;
    head = head.next;
  }
  return len;
}


LinkedListNode addListsHelper(LinkedListNode l1, LinkedListNode l2){
  if(l1 == null){
    PartialSum res = new Partia1Sum();
    return res;
  }

  PartialSum res = addListsHelper(l1.next, l2.next);
  int val = l1.data + l2.data + res.carry;

  LinkedListNode head = insertBefore(res.node, val%10);

  res.node = head;
  res.carry = val/10;

  return res;
}

LinkedListNode appendZeroToList(LinkedListNode head, int len){
  while(len-- > 0){
    head = insertBefore(head, 0);
  }
  return head;
}

LinkedListNode insertBefore(LinkedlListNode head, int data){
  LinkedListNode new_head = new LinkedlListNode(data);
  new_head.next = head;
  return new_head;
}








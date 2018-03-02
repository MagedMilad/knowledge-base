// Palindrome: Implement a function to check if a linked list is a palindrome.

// reverse and clone

boolean isPalindrome(LinkedListNode head){
  LinkedListNode revHead = reverseAndClone(head);
  return isEqual(head, revHead);
}

LinkedListNode reverseAndClone(LinkedListNode head){
  LinkedListNode newHead = null;
  while(head != null){
    LinkedListNode current = new LinkedListNode(head.data);
    current.next = newHead;
    newHead = current;
    head = head.next;
  }
  return newHead;
}

boolean isEqual(LinkedListNode l1, LinkedListNode l2){
  while(l1!= null && l2!=null){
    if(l1.data != l2.data) return false;
    l1 = l1.next;
    l2 = l2.next;
  }
  return l1 == null && l2 == null;
}



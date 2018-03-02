// Remove Dups: Write code to remove duplicates from an unsorted linked list


void deleteDups(LinkedListNode n){
  HashSet<Integer> set = new HashSet<Integer>();
  LinkedListNode prev = null;
  while(n.next != null){
    if(set.contains(n.data)){
      prev.next = n.next;
    }else{
      set.add(n.data);
      prev = n;
    }
    n = n.next;
  }
}

// Without extra memory
void deleteDups(LinkedListNode n){
  LinkedListNode current = n;
  while(current != null){
    LinkedListNode runner = current;
    while(runner.next != null){
      if(runner.next.data == current.data){
        runner.next = runner.next.next;
      }else{
        runner = runner.next;
      }
    }
    current = current.next;
  }
}



























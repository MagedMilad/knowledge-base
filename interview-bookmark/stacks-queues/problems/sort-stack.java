// Sort Stack: Write a program to sort a stack such that the smallest items are on the top. You can use
// an additional temporary stack, but you may not copy the elements into any other data structure
// (such as an array). The stack supports the following operations: push, pop, peek, and is Empty.


Stack<T> sortStack(Stack<T> stack){
  Stack<T> sorted = new Stack<T>();

  while(!stack.isEmpty()){
    T data = stack.pop();
    while(!sorted.isEmpty() && sorted.peek() > data){
      stack.push(sorted.pop());
    }
    sorted.push(data);
  }

  return sorted;
}

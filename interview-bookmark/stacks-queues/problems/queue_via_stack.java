// Queue via Stacks: Implement a MyQueue class which implements a queue using two stacks.


public class MyQueue<T> {
  private Stack<T> s1, s2;

  public MyQueue<T>(){
    s1 = new Stack<T>();
    s2 = new Stack<T>();
  }

  public void add(T data){
    s1.push(data);
  }

  public T remove(){
    refersh();
    return s2.pop();
  }

  public T peek(){
    refersh();
    return s2.peek();
  }

  private void refersh(){
    if(s2.isEmpty()){
      while(!s1.isEmpty()){
        s2.push(s1.pop());
      }
    }
  }
}

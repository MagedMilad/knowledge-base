// Stack Min: How would you design a stack which, in addition to push and pop, has a function min
// which returns the minimum element? Push, pop and min should all operate in 0(1) time.



public class MinStack extends Stack<T> {
  private Stack<T> mins;

  public MinStack<T>(){
    mins = new Stack<T>();
  }

  public void push (T data){
    if(data <= min)){
      mins.push(data);
    }
    super.push(data);
  }

  public T pop(){
    if(super.peek() == min()) mins.pop();
    return super.pop();
  }

  public T min(){
    if(mins.isEmpty()) return INTEGER.MAX_VALUE;
    return mins.peek();
  }
}

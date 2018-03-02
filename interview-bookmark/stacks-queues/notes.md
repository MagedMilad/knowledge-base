# Stack
- `pop()` : Remove the top item from the stack.
- `push(item)`: Add an item to the top of the stack.
- `peek()`: Return the top of the stack.
- `isEmpty()`: Return true if and only if the stack is empty.

```java
public class Stack<T>{
    private static class StackNode<T>{
        private T data;
        private StackNode<T> next;

        public StackNode(T data){
            this.data = data;
        }
    }

    private StackNode<T> top;

    public T pop(){
        if(top == null) throw new EmptystackException();
        T item = top.data;
        top = top.next;
        return item;
    }

    public void push(T item){
        StackNode<T> new_top = new StackNode<T>(item);
        new_top.next = top;
        top = new_top;
    }

    public T peek(){
        if(top == null) throw new EmptystackException();
        return top.data;
    }

    public boolean isEmpty(){
        return top == null;
    }
}
```

----

# Queue
- `add(iterm)`: Add an item to the end of the list.
- `remove()`: Remove the first item in the list.
- `peek()` : Return the top of the queue.
- `isEmpty()`: Return true if and only if the queue is empty.

```java
public class Queue<T>{
    private static class QueueNode<T>{
        private T data;
        private QueueNode<T> next;

        public QueueNode(T data){
            this.data = data;
        }
    }

    QueueNode<T> tail, top;

    public void add(T data){
        QueueNode new_node = new QueueNode<T>(data);
        if(tail != null){
            tail.next = new_node;
            tail = new_node;
        }else{
            top = tail = new_node;
        }
    }

    public T remove(){
        if(top == null) throw new EmptyqueueException();
        T item = top.data;
        top = top.next;
        if(top == null) last = null;
        return item;
    }

    public T peek(){
        if(top == null) throw new EmptyqueueException();
        return top.data;
    }

    public boolean isEmpty(){
        return top == null;
    }
}
```














// Random Node: You are implementing a binary search tree class from scratch, which, in addition
// to insert, find, and delete, has a method getRandomNode() which returns a random node
// from the tree. All nodes should be equally likely to be chosen. Design and implement an algorithm
// for getRandomNode, and explain how you would implement the rest of the methods.

class Tree{
  private TreeNode root;

  public int size() { root == null ? 0 : root.size(); }

  public TreeNode getRandomNode(){
    if(root == null) return root;

    Random rand = new Random();
    int i = rand.nextInt(size());
    return root.getIthNode(i);
  }

}

class TreeNode{
  private int data;
  private int size;

  public TreeNode left, right;

  public TreeNode(int data){
    this.data = data;
    this.size = 1;
  }

  public TreeNode getIthNode(int i){
    int leftSize = left == null ? 0 : left.size();
    if(i < leftSize) return left.getIthNode(i);
    else if(i == leftSize) return this;
    else return right.getIthNode(i - (leftSize + 1));
  }

  public void insertInOrder(int d){
    if(d <= data){
      if(left == null)
        left = new TreeNode(d);
      else
        left.insertInOrder(d);
    }else{
      if(right == null)
        right = new TreeNode(d);
      else
        right.insertInOrder(d);
    }
    size++;
  }

  public TreeNode find(int d){
    if(data == d) return this;
    else if(d < data) return left.find(d);
    else return right.find(d);
    return null;
  }

  public int size() { return size; }
  public int data() { return data; }



}

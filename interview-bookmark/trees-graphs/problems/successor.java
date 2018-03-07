// Successor: Write an algorithm to find the "next" node (i.e., in-order successor) of a given node in a
// binary search tree. You may assume that each node has a link to its parent.


TreeNode successor(TreeNode node){
  if(node.right != null){
    return getmin(node.right);
  }else{
    TreeNode parent = node.parent;
    while(parent != null && parent.right == node){
      node = parent;
      parent = node.parent;
    }
    return parent;
  }
}


TreeNode getmin(TreeNode node){
  while(node.left != null) node = node.left;
  return node;
}

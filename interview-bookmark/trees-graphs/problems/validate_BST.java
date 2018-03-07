// Validate BST: Implement a function to check if a binary tree is a binary search tree.


boolean validateBST(TreeNode root){
  return helper(root, Integer.MAX_VALUE, Integer.MIN_VALUE);
}


boolean helper(TreeNode root, int max, int min){
  if(root == null) return true;

  int data = root.data;

  if(data > max || data <= min) return false;

  return helper(root.left, data, min) &&
    helper(root.right, max, data);
}

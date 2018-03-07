// Check Balanced: Implement a function to check if a binary tree is balanced. For the purposes of
// this question, a balanced tree is defined to be a tree such that the heights of the two subtrees of any
// node never differ by more than one.


boolean isBalanced(TreeNode root) {
  return helper(root) != Integer.MAX_VALUE;
}


int helper(TreeNode root){
  if(root == null) return -1;

  int rightHight = helper(root.right);
  if(rightHight == Integer.MAX_VALUE) return rightHight;

  int leftHight = helper(root.left);
  if(leftHight == Integer.MAX_VALUE) return leftHight;

  int diff = Math.abs(rightHight - leftHight);

  if(diff > 1){
    return Integer.MAX_VALUE;
  }else{
    return Math.max(rightHight, leftHight) + 1;
  }

}

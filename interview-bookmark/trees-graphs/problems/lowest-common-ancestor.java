// First Common Ancestor: Design an algorithm and write code to find the first common ancestor
// of two nodes in a binary tree. Avoid storing additional nodes in a data structure. NOTE: This is not
// necessarily a binary search tree.



TreeNode lowestCommonAncestor(TreeNode root, TreeNode n1, TreeNode n2){
  if(root == null) return null;

  // so we found one branch during the search
  if(root == n1 || root == n2) return root;

  TreeNode left = lowestCommonAncestor(root.left, n1, n2);
  TreeNode right = lowestCommonAncestor(root.right, n1, n2);

  if(right != null && left != null) return root;

  return left == null ? right : left;
}

// short way
TreeNode lowestCommonAncestor(TreeNode root, TreeNode n1, TreeNode n2){
  if(root == null || root == n1 || root == n2) return root;
  TreeNode left = lowestCommonAncestor(root.left, n1, n2);
  TreeNode right = lowestCommonAncestor(root.right, n1, n2);
  return left == null ? right : right == null ? left : root;
}




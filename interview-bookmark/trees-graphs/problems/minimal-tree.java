// Minimal Tree: Given a sorted (increasing order) array with unique integer elements, write an
// algorithm to create a binary search tree with minimal height.


TreeNode createMinimalBST(int array[]) {
  return helper(array, 0, array.length-1);
}

TreeNode helper(int array[], int start, int end){
  if(start > end) return null;

  int mid = (start + end)/2;

  TreeNode root = new TreeNode(array[mid]);
  root.left = helper(array,start, mid-1);
  root.right = helper(array,mid+1, end);
  return root;
}

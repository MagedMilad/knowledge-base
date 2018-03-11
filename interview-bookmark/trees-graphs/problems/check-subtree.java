// Check Subtree: Tl and T2 are two very large binary trees, with Tl much bigger than T2. Create an
// algorithm to determine ifT2 is a subtree of Tl.
// A tree T2 is a subtree of Tl if there exists a node n in Tl such that the subtree of n is identical to T2.
// That is, if you cut off the tree at node n, the two trees would be identical.


// time O(m + n)
// memory O(m + n)
boolean containsTree(TreeNode t1, TreeNode t2) {
  StringBuilder sb1 = new StringBuilder();
  StringBuilder sb2 = new StringBuilder();

  treeToString(t1, sb1);
  treeToString(t2, sb2);

  return sb1.indexOf(s2.toString()) != -1;

}


void treeToString(TreeNode node, StringBuilder sb){
  if(node == null){
    sb.append("X");
    return;
  }
  sb.append(node.data + " ");
  treeToString(node.left, sb);
  treeToString(node.right, sb);
}


////////////////////////////////////////////////////////

// time O(nm)
// memory O(log(n) + log(m))
boolean containsTree(TreeNode t1, TreeNode t2) {
  if(t2 == null) return true;
  subtree(t1, t2);
}

boolean subtree(TreeNode t1, TreeNode t2){
  if(t1 == null) return false;
  if(t1.data == t2.data && matchTree(t1, t2)) return true;
  return subtree(t1.right, t2) || subtree(t2.left, t2);
}

boolean matchTree(TreeNode t1, TreeNode t2){
  if(t1 == null && t2 == null) return true;
  if(t1 == null || t2 == null) return false;
  if(t1.data != t2.data) return false;
  return matchTree(t1.left, t2,left) && matchTree(t1.left, t2.left);
}























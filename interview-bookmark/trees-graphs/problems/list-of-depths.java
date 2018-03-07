// List of Depths: Given a binary tree, design an algorithm which creates a linked list of all the nodes
// at each depth (e.g., if you have a tree with depth D, you'll have D linked lists).


ArrayList<LinkedList<TreeNode>> createLevelLinkedList(TreeNode root) {
  ArrayList<LinkedList<TreeNode>> lists = new ArrayList<LinkedList<TreeNode>>();
  helper(root, lists, 0);
  return lists;
}

void helper(TreeNode root, ArrayList<LinkedList<TreeNode>> lists, depth){
  if(root == null) return;

  LinkedList<TreeNode> list = null;
  if(lists.size() == depth){
    list = new LinkedList<TreeNode>();
    lists.add(list);
  }else{
    list = lists.get(depth);
  }

  list.add(root);
  helper(root.left, lists, depth+1);
  helper(root.right, lists, depth+1);
}




















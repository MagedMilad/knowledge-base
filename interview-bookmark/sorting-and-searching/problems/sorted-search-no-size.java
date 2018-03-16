// Sorted Search, No Size: You are given an array-like data structure Listy which lacks a size
// method. It does, however, have an elementAt ( i) method that returns the element at index i in
// 0( 1) time. If i is beyond the bounds of the data structure, it returns -1. (For this reason, the data
// structure only supports positive integers.) Given a Listy which contains sorted, positive integers,
// find the index at which an element x occurs. If x occurs multiple times, you may return any index.


int Search(Listy arr, int val){
  int idx = 1;
  while(arr.elementAt(idx) != -1 && arr.elementAt(idx) < val) idx *= 2;
  return helper(arr, val, idx/2, idx);
}

int helper(Listy arr, int val, int low, int high){
  int mid = (low + high) / 2;

  which(low < high){
    if(arr.elementAt(mid) == val) return mid;
    if(arr.elementAt(mid) == -1 || arr.elementAt(mid) > val)
      high = mid - 1;
    else
      low = mid + 1;
  }

  return -1;
}

// Sorted Merge: You are given two sorted arrays, A and B, where A has a large enough buffer at the
// end to hold B. Write a method to merge B into A in sorted order.


void merge(int[] a, int[] b, int lastA, int lastB) {
  int idxA = lastA - 1;
  int idxB = lastB - 1;

  int idxRes = lastA + lastB - 1;

  while(idxB >= 0){
    if(b[idxB] >= a[idxA]){
      a[idxRes--] = b[idxB];
      idxB--;
    }else{
      a[idxRes--] = a[idxA];
      idxA--;
    }
  }
}

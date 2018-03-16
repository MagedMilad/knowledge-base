int findMin(int[] arr){
  int n = arr.length;
  int low = 0;
  int high = n;

  while(low < high){
    int mid = (low + high) / 2;

    if(arr[mid] < arr[(mid+1)%n] && arr[mid] < arr[(mid-1)%n]) return arr[mid];

    if(arr[low] < arr[mid]){
      high = mid - 1;
    }else{
      low = mid + 1;
    }
  }

  return -1;
}

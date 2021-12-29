public class Sorting{
	public static void main(String[] args) {
		int[] arr = new int[1000000];

		for(int i=0; i< arr.length; i++){
			arr[i] = arr.length - i;
		}
		//printArr(arr);
		System.out.println("Generated!");

		long start = System.currentTimeMillis();
		mergeSort(arr);
		long end = System.currentTimeMillis();


		System.out.println("Done! time " + (end-start)/1000.0);

	}

	public static void mergeSort(int[] inputArray){
		if(inputArray.length < 2)
			return;

		int[] leftArr = new int[inputArray.length/2];
		int[] rightArr = new int[inputArray.length - leftArr.length];

		for(int i = 0; i<leftArr.length; i++){
			leftArr[i] = inputArray[i];
		}

		for(int j=inputArray.length/2; j< inputArray.length; j++){
			rightArr[j - inputArray.length/2] = inputArray[j];
		}

		mergeSort(leftArr);
		mergeSort(rightArr);
		merge(inputArray, leftArr, rightArr);
	}

	public static void merge(int[] inputArray, int[] leftArr, int[] rightArr){
		int i=0, j=0, k =0;

		while(i < leftArr.length && j<rightArr.length){
			if(leftArr[i] <= rightArr[j]){
				inputArray[k] = leftArr[i++];
			}

			else{
				inputArray[k] = rightArr[j++];
			}
			k++;
		}

		while(i < leftArr.length){
			inputArray[k] = leftArr[i];
			k++;
			i++;
		}
		while(j < rightArr.length){
			inputArray[k] = rightArr[j];
			k++;
			j++;
		}
	}

	public static void printArr(int[] arr){
		for(int x: arr){
			System.out.println(x);
		}
		System.out.println();
	}
}
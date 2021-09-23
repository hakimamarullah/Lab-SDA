import java.util.Arrays;
class Test{
	public static void main(String[] args) {
		// t(16);
		// System.out.println();
		// System.out.println(c(11));
		//System.out.println(r(4,2));
		//System.out.println(maxSum(new int[]{1,1,5,5,6,3,4,2,5,1}));
		int[] a = new int[]{1,1,5,5,6,3,4,2,5,1};
		MySort(a);
		System.out.println(Arrays.toString(a));
		// t(10);
		// t(16);
	}
	static void t(int a){
		if(a>1){
			t(a/2);
			t(a/2);
			System.out.println("P ");
		}
		System.out.print("* ");
		
	}
	static int d(int a, int b){
        if(a==0)
            return b;
        else
            return (a>b)? d(a-2, b+1):d(a-1, b-1);
    }
    static int c(int a){
    	if(a == 1)
    		return 0;
    	else
    		return 1+c(a/2);
    }

    static int r(int a, int b){
    	if(a==b)
    		return a;
    	else if(a<b)
    		return r(a, b-1);
    	else
    		return r(a-1, b);
    }
    static int maxSum(int[] x){
    	int max = Integer.MIN_VALUE;
    	int sum=0;
    	int iterasi=Integer.MAX_VALUE;
    	int[] z = new int[]{1,1,1,1,1};
    	for(int i=0; i<x.length; i++){
    		sum  += x[i];
    		if((sum + z[i/2])>max && i<iterasi){
    			max =sum+z[i/2];
    			System.out.println(max+" "+1);
    			iterasi=i;
    		}
    		if(sum<0){
    			sum=0;
    		}
    	}
    	return max;
    }
    //Selection sort
    static void funcA(int a[]){

    for (int i = 0; i < a.length; i++) {

       int max = i;

 

       for (int j = i + 1; j < a.length; j++)

            if (a[max] < a[j])

                max = j;


       int T = a[i];

       a[i] = a[max];

       a[max] = T;
       System.out.println(Arrays.toString(a));

    }

	}

	public static void MySort(int[] a){

    for (int ii = 1; ii < a.length; ii++) {

        int temp = a[ii];

        int jj = ii - 1;

        while (jj>0 && a[jj]<=temp) {

            a[jj+1]=a[jj];
            System.out.println(Arrays.toString(a));

            jj--;


        }
        System.out.println("JJ "+jj);

        a[jj + 1] = temp;

    }

}
}
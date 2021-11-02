/* Tuliskan Nama, NPM, dan Kelasmu di sini:
** Nama Lengkap: Hakim Amarullah
** NPM         : 1906293051
** Kelas       : A
*/
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.max;
public class Soal2{
        
		/*TO DO*/
		/*Letakkan ketiga baris ini pada main method atau pada constructor,
		*sehingga program dapat berjalan (lolos test cases level 1)*/
		
    public static void main(String[] args) {
        Scanner ss = new Scanner(System.in);
        while(ss.hasNext())
        processThis(ss.nextLine());
    }

	/*TO DO
	*
	* Lanjutkan implementasi method-method berikut ini
	* (boleh memodifikasi lebih dari 1 method)
	*/
    static void processThis(String stringinput) {
        String[] tokens = stringinput.split(" ");
        Stack<String> s = new Stack<String>(); 
        s.push("");
        for (String t : tokens) {
            if(t == "("){
                s.push(t);
            }
            else if(t == ")"){
            
                while (!s.isEmpty() &&
                        s.peek() != "(")
                    System.out.print(s.pop());
                 
                    s.pop();
            
            }
            else if (isOperator(t)) {
                while (!isHigher(t, s.peek())) {
                    String v = s.pop();
                    System.out.print(v);
                }
                s.push(t);
            }
            else System.out.print(t);
        }
        while (!s.isEmpty()) 
            System.out.print(s.pop());
        System.out.println();
    }

    static boolean isOperator(String t) {
        return t.equals("+") || t.equals("*") || t.equals("-") || t.equals("/") || t.equals("^") || t.equals("(") || t.equals(")");
    }
	
    static boolean isHigher(String x, String y) {
        if (y.equals("")) return true;
        if (orderOf(x) > orderOf(y)) return true;
        return false;
    }
	
    static int orderOf(String op) {
        if (op.equals("+")) return 1;
        if (op.equals("*")) return 2;
        if (op.equals("-")) return 1;
        if (op.equals("/")) return 2;
        if (op.equals("^")) return 3;
        return -99;
    }
}

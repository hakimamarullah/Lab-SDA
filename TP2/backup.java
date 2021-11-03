/*

Nama	: Hakim Amarullah
NPM		: 1906293051
Tugas	: TP2
Topik	: LinkedList, BST
Tahun	: 2021

*/
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class TP2{
	private static InputReader in;
    public static OutputStream outputStream = System.out;
    public static Map<String, Pulau> pulau = new HashMap<String, Pulau>();
    public static PrintWriter out = new PrintWriter(outputStream);
    public static Raiden raiden;

    public static void main(String[] args) {
    	InputStream inputStream = System.in;
        in = new InputReader(inputStream);

        int N = in.nextInt();

        String namaPulau="";
        for(int i=0; i<N; i++){
        	namaPulau = in.next();
        	int jumlahDataran = in.nextInt();
        	pulau.put(namaPulau, new Pulau(namaPulau));

        	for(int j=0; j<jumlahDataran; j++){
        		int tinggi = in.nextInt();
        		pulau.get(namaPulau).addLast(tinggi);
        	}
        	

        }

        //SET UP RAIDEN
        String pulauRaiden = in.next();
        int indexDataran = in.nextInt();
        raiden = new Raiden(pulau.get(pulauRaiden), indexDataran);
        //pulau.get(pulauRaiden).setRaiden(raiden);
      
        int Q = in.nextInt(); //jumlah kejadian

        for(int i=0; i<Q; i++){
        	String kejadian = in.next();
        	switch(kejadian){
        		case "CRUMBLE":
        			out.println(pulau.get(raiden.pulau.getName()).crumble(raiden.current));
        			//pulau.get(raiden.pulau.getName()).print();
        			break;
        		case "UNIFIKASI":
        			String pulauUtama = in.next();
        			String pulauSecondary = in.next();
        			out.println(pulau.get(pulauUtama).unifikasi(pulau.get(pulauSecondary)));
        			pulau.put(pulauSecondary, pulau.get(pulauUtama)); //Update pulau after unification
        			break;
        		case "TELEPORTASI":
        			String namaKuil = in.next();
        			out.println(raiden.teleportasi(pulau.get(namaKuil), namaKuil));
        			break;
        		case "QUAKE":
        			String namaPulauToQuake= in.next();
        			int height = in.nextInt();
        			int minus = in.nextInt();
        			out.println(pulau.get(namaPulauToQuake).quake(height, minus));
        			break;
        		case "TEBAS":
        			String arah = in.next();
        			int langkah = in.nextInt();
        			out.println(raiden.tebas(arah, langkah));
        			break;
        		case "RISE":
        			String namaPulauToRise = in.next();
        			int acuan = in.nextInt();
        			int rise = in.nextInt();
        			out.println(pulau.get(namaPulauToRise).rise(acuan, rise));
        			break;
        		case "GERAK":
        			String arahGerak = in.next();
        			int banyakLangkah = in.nextInt();
        			out.println(raiden.gerak(arahGerak, banyakLangkah));
        			break;
        		case "PISAH":
        			String kuilToCut = in.next();
        			pulau.get(kuilToCut).pisah(kuilToCut);
        			break;
        		case "STABILIZE":
        			out.println(raiden.stabilize());
        			break;
        			
        	}

        }
        out.flush();

    }





    // taken from https://codeforces.com/submissions/Petr
    // together with PrintWriter, these input-output (IO) is much faster than the usual Scanner(System.in) and System.out
    // please use these classes to avoid your fast algorithm gets Time Limit Exceeded caused by slow input-output (IO)
    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;
 
        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }
 
        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }
 
        public int nextInt() {
            return Integer.parseInt(next());
        }
 
    }

}

class Pulau extends TP2{
	String nama;
	int size;
	Dataran header;
	Dataran last;
	

	public Pulau(String nama){
		this.header = new Dataran(0, null);
		this.nama = nama;
		this.last = header;
	}

	public void addLast(int tinggi){
		if(header.next == null){
			header.next = last = new Dataran(tinggi);
			header.next.prev = header;
			header.next.setAsKuil(this.getName());
			this.size++;
			//TP2.kuil.put(header.next.getKuilName(), this);
		}
		else{
			Dataran dataran = new Dataran(tinggi, null);
			last.next = dataran ;
            dataran.prev = last;
            last = dataran;
            this.size++;
			
		}
	}

	public void pisah(String namaKuil){
		Dataran tmp = this.header.next;
		int initialSize = this.size;
		int counter = 0;
		while(tmp != null){
			if(tmp.isKuil()){
				if(tmp.getKuilName().equals(namaKuil)){
					Pulau newPulau = new Pulau(namaKuil);
					Dataran lastKanan = this.last;
					newPulau.header.next = tmp;
					this.last = tmp.prev;
					tmp.prev = newPulau.header;
					newPulau.last = lastKanan;
					this.last.next = null;
					TP2.pulau.put(namaKuil, newPulau);
					if(TP2.raiden.current == newPulau.header.next){
						TP2.raiden.update(newPulau, newPulau.header.next);
					}
					out.print(String.valueOf(counter) + " ");
					out.println(initialSize - counter);

				}
			}
			tmp = tmp.next;
			counter++;
		}
	}
	public int rise(int acuan, int rise){
		Dataran tmp = this.header.next;
    	int counter =0;
    	while(tmp != null){
    		if(tmp.getHeight() > acuan){
    			tmp.increaseHeight(rise);
    			counter++;
    		}
    		tmp = tmp.next;
    	}
    	return counter;
	}
	
	public int crumble(Dataran x){
		int tinggi = 0;
		Dataran raiden = x;
		//TP2.out.println(x.isKuil() + " " + x.getHeight());
		if(raiden.isKuil()){
			return tinggi;
		}
		if(raiden.next == null){
			Dataran tmp = raiden.prev;
			raiden.prev.next = null;
			TP2.raiden.update(this, tmp);
			return x.getHeight();

		}
		if (raiden.next != null) {
			Dataran tmp = raiden.prev;
            raiden.next.prev = raiden.prev;
            TP2.raiden.update(this, tmp);
        }
 
        // Change prev only if node to be deleted
        // is NOT the first node
        if (raiden.prev != null) {
            raiden.prev.next = raiden.next;
        }
 
		// else if(raiden.prev == header && raiden.next != null){
  //           tinggi = raiden.getHeight();
  //           raiden = raiden.next;
  //           raiden.prev = header;
  //           header.next = raiden;
  //           TP2.raiden.update(this,raiden);
  //       }
  //       else if(raiden.prev == header && raiden.next == null){
  //           tinggi = raiden.getHeight();
  //           header.next = null;
  //       }
  //       else if(raiden.prev != header && raiden != last){
  //           tinggi = raiden.getHeight();
  //           raiden = raiden.prev;
  //           raiden.next = raiden.next.next;
  //           TP2.raiden.update(this,raiden);
  //       }
  //       else if(raiden.prev != header && raiden == last){
  //           tinggi = raiden.getHeight();
  //           raiden = raiden.prev;
  //           raiden.next = raiden.next.next;
  //           last = raiden;
  //           System.out.println("DEBUG4");
  //           TP2.raiden.update(this,raiden);
  //       }
        return x.getHeight();
    }
    public Dataran search(Dataran dataran, String arah){
    	Dataran tmp = arah.equals("KANAN") ? dataran.next : dataran.prev;
    	int height = dataran.getHeight();
    	while(tmp != null){
    		if(tmp.getHeight() ==  height){
    			return tmp;
    		}
    		tmp = arah.equals("KANAN") ? tmp.next : tmp.prev;
    	}
    	return dataran;
    }

    public int quake(int height, int minus){
    	Dataran tmp = this.header.next;
    	int counter =0;
    	while(tmp != null){
    		if(tmp.getHeight() < height){
    			tmp.decreaseHeight(minus);
    			counter++;
    		}
    		tmp = tmp.next;
    	}
    	return counter;
    }

	public int unifikasi(Pulau pulau){
		this.size = this.size + pulau.size;
		this.last.next = pulau.header.next;
		pulau.header.next.prev = this.last;
		this.last = pulau.last;
		pulau.header = null;
		return this.size;

	}

	public void print(){
		Dataran curr = header.next;
		while(curr != null){
			TP2.out.print(String.valueOf(curr.getHeight()) + " ");
			curr = curr.next;
		}
		TP2.out.println();
	}

	public String getName(){
		return this.nama;
	}
}

class Dataran{
	Dataran next;
	Dataran prev;
	boolean isKuil;
	String namaKuil;
	int tinggi;

	public Dataran(int tinggi){
		this.tinggi = tinggi;
		this.isKuil = false;
	}

	public Dataran(int tinggi, Dataran next){
		this.tinggi = tinggi;
		this.next = next;
		this.isKuil= false;
	}

	public int getHeight(){
		return this.tinggi;
	}

	public void setAsKuil(String nama){
		this.isKuil = true;
		this.namaKuil = nama;
	}

	public String getKuilName(){
		if(this.isKuil){
			return this.namaKuil;
		}
		return "BUKAN KUIL";
	}

	public boolean isKuil(){
		return this.isKuil;
	}

	public void decreaseHeight(int minus){
		this.tinggi = Math.abs(this.tinggi - minus);
	}

	public void increaseHeight(int rise){
		this.tinggi = this.tinggi + rise;
	}
}

class Raiden{
	Dataran left;
	Dataran right;
	Pulau pulau;
	Dataran current;

	public Raiden(Pulau pulau, int indexDataran){
		this.pulau = pulau;
		this.setInitial(pulau,indexDataran);
		this.left = current.prev;
		this.right = current.next;
	}

	public Dataran setInitial(Pulau pulau, int indexDataran){
		Dataran out = pulau.header;
		for(int i=0; i<indexDataran; i++){
			out = out.next;
		}
		this.current = out;
		return out;
	}

	public void update(Pulau pulau,Dataran newCurrent){
		this.pulau = pulau;
		this.current = newCurrent;
		this.left = current.prev;
		this.right = current.next;
	}

	public int teleportasi(Pulau pulau, String namaKuil){
		Dataran tmp = pulau.header.next;
		this.pulau = pulau;
		while(tmp != null){
			if(tmp.getKuilName().equals(namaKuil)){
				this.update(this.pulau, tmp);
				return tmp.getHeight();
			}
			tmp = tmp.next;
		}
		return 0;
	}

	public int tebas(String arah, int langkah){
		Dataran current = this.current;	
		for(int i=0; i<langkah; i++){
			current = this.pulau.search(current, arah);

	
		}
		this.update(pulau, current);
		int height = arah.equals("KANAN") ? this.left.getHeight() : this.right.getHeight();
		
		return height;
	}
	
	public int gerak(String arah, int langkah){
		if(pulau.header.next == null){
			return 0;
		}
		if(arah.equals("KIRI") && this.left.getHeight() == 0){
			return this.current.getHeight();
		}
		else if(arah.equals("KANAN") && this.right.getHeight() == 0){

			return this.current.getHeight();
		}
		else if(arah.equals("KIRI")){
			for(int i=0; i<langkah; i++){
				this.current = this.current.prev;
			}
			this.update(this.pulau, this.current);
			return this.current.getHeight();
		}
		else{
			for(int i=0; i<langkah; i++){
				this.current = this.current.next;
			}
			this.update(this.pulau, this.current);

			return this.current.getHeight();
		}
	}

	public int stabilize(){
		Dataran raiden = this.current;
		Dataran last = this.pulau.last;
		int height = Math.min(this.current.getHeight(), this.left.getHeight());
		if(raiden.isKuil()){
			return 0;
		}
		if(raiden != last){
	        Dataran newDataran = new Dataran(height);
	 
	        /* 4. Make next of new lantai as next of prev */
	        newDataran.next = raiden.next;
	 
	        /* 5. Make the next of prev_lantai as newDataran */
	        newDataran.prev = raiden;
	        raiden.next = newDataran;
	        //raiden = newDataran;
	 
	        /* 6. Make prev as previous of newDataran */
	        
	 
	        /* 7. Change previous of newDataran's next lantai */
	        if (newDataran.next != null)
	            newDataran.next.prev = newDataran;
	        return height;
        }

        else if(raiden==last){
            Dataran dataran= new Dataran(height, null);
            last.next = dataran;
            dataran.prev = last;
            last = dataran;
            raiden = dataran;
            return height;
        }
        return 0;
	}
}
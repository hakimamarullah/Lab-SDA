import java.util.*;
public class VectorHeap{

  protected Vector<Dataran> storage;


  public VectorHeap(){
    storage = new Vector<Dataran>();
  }

  private int parent(int i){
        // if `i` is already a root node
        if (i == 0) {
            return 0;
        }
 
        return (i - 1) / 2;
  }

  // swap values at two indexes
  void swap(int x, int y){
      // swap with a child having greater value
      Dataran temp = storage.get(x);
      storage.setElementAt(storage.get(y), x);
      storage.setElementAt(temp, y);
  }
    public int size(){
        return storage.size();
    }

  // Recursive heapify-up procedure
  private void percolateUp(int i){
        // check if the node at index `i` and its parent violates
        // the heap property
        if (i > 0 && storage.get(parent(i)).getHeight() > storage.get(i).getHeight())
        {
            // swap the two if heap property is violated
            swap(i, parent(i));
 
            // call heapify-up on the parent
            percolateUp(parent(i));
        }
    }

  public void insert(Dataran dataran){
        // insert a new element at the end of the vector
        storage.addElement(dataran);
 
        // get element index and call heapify-up procedure
        int index = size() - 1;
        percolateUp(index);
  }

  public Dataran getMin(){ 
      // if the heap has no elements, throw an exception
      if (size() == 0) {
          return null;
      }

      // otherwise, return the top (first) element
      return storage.firstElement();    // or A.get(0);
       
    }

}


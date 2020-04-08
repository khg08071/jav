package cse2010.homework1;

import java.util.Arrays;

public class ArraySparseMatrix implements SparseMatrix {

    public static final int DEFAULT_CAPACITY = 1024;

    private int rowCount;
    private int columnCount;
    private int elemCount;
    private Entry[] elements = new Entry[0];

    public ArraySparseMatrix(int rowCount, int columnCount, int capacity) {
        elements = new Entry[capacity];
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.elemCount = 0;
    }

    public ArraySparseMatrix(int rowCount, int columnCount) {
        this(rowCount, columnCount, DEFAULT_CAPACITY);
    }

    /*
     * This routine simulates reading from files using two-dimensional matrix.
     */
    public static SparseMatrix create(double[][] aMatrix, int rowCount, int columnCount, int elemCount) {
        ArraySparseMatrix matrix = new ArraySparseMatrix(rowCount, columnCount, elemCount);

        int nonZeroCount = 0;
        for (int i = 0; i < aMatrix.length; i++)
            for (int j = 0; j < aMatrix[i].length; j++) {
                if (Double.compare(aMatrix[i][j], 0.0) != 0) {
                    matrix.put(new Entry(i, j, aMatrix[i][j]));
                    nonZeroCount++;
                }
            }
       if (nonZeroCount != elemCount) throw new IllegalStateException("Non zero count doesn't match");

            /*
             * your code goes here
             */ 
            // if (nonZeroCount != elemCount) throw IllegalStateException saying 
            // "Non zero count doesn't match"
        

        return matrix;
    }

    private void put(Entry entry) {
        elements[elemCount++] = entry;
    }
    
    private void sort(ArraySparseMatrix matrix) {
    	Entry temp = new Entry(0, 0, 0.0);
    	for(int i = 0; i < this.getElemCount(); i++) {
    		for(int j = 0; j < this.getElemCount(); j++) {
    			
    				if(((matrix.getElements())[i].getRow()) < ((matrix.getElements())[j].getRow())){
    					temp.setRow((matrix.getElements())[i].getRow());
    					temp.setCol((matrix.getElements())[i].getCol());
    					temp.setValue((matrix.getElements())[i].getValue());
    					
    					(matrix.getElements())[i].setRow((matrix.getElements())[j].getRow());
    					(matrix.getElements())[i].setCol((matrix.getElements())[j].getCol());
    					(matrix.getElements())[i].setValue((matrix.getElements())[j].getValue());
    					
    					(matrix.getElements())[j].setRow((temp.getRow()));
    					(matrix.getElements())[j].setCol((temp.getCol()));
    					(matrix.getElements())[j].setValue((temp.getValue()));
        			}
    				
    				if(((matrix.getElements())[i].getRow() == (matrix.getElements())[j].getRow()) && ((matrix.getElements())[i].getCol() < (matrix.getElements())[j].getCol()) ) {
    					temp.setRow((matrix.getElements())[i].getRow());
    					temp.setCol((matrix.getElements())[i].getCol());
    					temp.setValue((matrix.getElements())[i].getValue());
    					
    					(matrix.getElements())[i].setRow((matrix.getElements())[j].getRow());
    					(matrix.getElements())[i].setCol((matrix.getElements())[j].getCol());
    					(matrix.getElements())[i].setValue((matrix.getElements())[j].getValue());
    					
    					(matrix.getElements())[j].setRow((temp.getRow()));
    					(matrix.getElements())[j].setCol((temp.getCol()));
    					(matrix.getElements())[j].setValue((temp.getValue()));

        			}

    			
    			    	}
    	
    	}
    }

    
    @Override
    public SparseMatrix transpose() {
    	ArraySparseMatrix matrix = (ArraySparseMatrix)this;
    	ArraySparseMatrix matrix_t = new ArraySparseMatrix(this.columnCount, this.rowCount);
    	for(int i = 0; i < this.elemCount; i++) {
    		matrix_t.put(new Entry((matrix.getElements())[i].getCol(), (matrix.getElements())[i].getRow() , (matrix.getElements())[i].getValue()));
    	}
    	sort(matrix_t);
        /*
         *   Your code goes here
         */

        return matrix_t;
    }

    @Override
    public SparseMatrix add(SparseMatrix other) {
        if (this.rowCount != other.getRowCount() || this.columnCount != other.getColumnCount())
            throw new IllegalArgumentException("Matrix size doesn't match");
       ArraySparseMatrix matrix_other = (ArraySparseMatrix) other;
       ArraySparseMatrix new_matrix = new ArraySparseMatrix(this.rowCount, this.columnCount);
       boolean[] checkbox_other = new boolean[other.getElemCount()];
       boolean[] checkbox_this = new boolean[this.getElemCount()];
       Arrays.fill(checkbox_other, false);
       Arrays.fill(checkbox_this, false);

       for(int i = 0; i < this.getElemCount(); i++) {
    	   for(int j = 0; j < matrix_other.getElemCount(); j++) {
    		   if(checkbox_other[j] == false) {
				   if(new_matrix.elemCount > 0) {
    				   for(int p = 0; p < new_matrix.elemCount; p++) {
        				   if(((matrix_other.getElements())[j].getRow() == (new_matrix.getElements())[p].getRow()) && ((matrix_other.getElements())[j].getCol() == (new_matrix.getElements())[p].getCol())){
        					   (new_matrix.getElements())[p].setValue((matrix_other.getElements())[j].getValue() + (new_matrix.getElements())[p].getValue());
        					   checkbox_other[j] = true;
        					   break;
        				   }
        			   }
    			   }
				   if(checkbox_other[j] == false) {
				   new_matrix.put(new Entry((matrix_other.getElements())[j].getRow(), (matrix_other.getElements())[j].getCol(), (matrix_other.getElements())[j].getValue()));
				   checkbox_other[j] = true;
				   }
			   }
			   
			   if(checkbox_this[i]==false) {
				   if(new_matrix.elemCount > 0) {
    				   for(int p = 0; p < new_matrix.elemCount; p++) {
        				   if(((this.getElements())[i].getRow() == (new_matrix.getElements())[p].getRow()) && ((this.getElements())[i].getCol() == (new_matrix.getElements())[p].getCol())){
        					   (new_matrix.getElements())[p].setValue((this.getElements())[i].getValue() + (new_matrix.getElements())[p].getValue());
        					   checkbox_this[i] = true;
        					   break;
        				   }
        			   }
				   }
				  if(checkbox_this[i]==false) {
					  new_matrix.put(new Entry((this.getElements())[i].getRow(), (this.getElements())[i].getCol(), (this.getElements())[i].getValue()));   
					   checkbox_this[i] = true;
				  }
				   }
    	   }
    	   }
       
      Entry temp = new Entry(0, 0, 0);
      for(int i = 0; i < new_matrix.getElemCount()-1; i++) {
    	  for(int j = i+1; j < new_matrix.getElemCount(); j++) {
    		  if((new_matrix.getElements())[i].getRow() == (new_matrix.getElements())[j].getRow() && (new_matrix.getElements())[i].getCol() > (new_matrix.getElements())[j].getCol()) {
    			  temp = (new_matrix.getElements())[i];
    			  (new_matrix.getElements())[i] = (new_matrix.getElements())[j];
    			  (new_matrix.getElements())[j] = temp;
    		  }
    		  if((new_matrix.getElements())[i].getRow() > (new_matrix.getElements())[j].getRow()) {
    			  temp = (new_matrix.getElements())[i];
    			  (new_matrix.getElements())[i] = (new_matrix.getElements())[j];
    			  (new_matrix.getElements())[j] = temp;
    		  }
    		  
    	  }
      }
      return new_matrix;
    }

    public Entry[] getElements() {
        return elements;
    }

    @Override
    public SparseMatrix multiply(SparseMatrix aMatrix) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public int getElemCount() {
        return elemCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ArraySparseMatrix)) return false;
        ArraySparseMatrix other = (ArraySparseMatrix) obj;

        if (rowCount != other.rowCount || columnCount != other.columnCount || elemCount != other.elemCount)
            return false;

        for (int i = 0; i < elements.length; i++) {
            if (!elements[i].equals(other.elements[i])) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(rowCount + "\t" + columnCount + "\t" + elemCount + "\n");
        for (int i = 0; i < elemCount; i ++)
            builder.append(elements[i] + "\n");

        return builder.toString();
    }
 
}

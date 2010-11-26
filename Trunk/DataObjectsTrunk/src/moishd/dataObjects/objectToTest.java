package moishd.dataObjects;

import java.io.Serializable;

public class objectToTest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int first;
	
	private int last;
	
	public objectToTest(int f, int l) {
		this.first = f;
		this.last = l;
	}
	
	public objectToTest() {
		this.first = 0;
		this.last = 0;
	}
	
	public int GetFirst(){
		return first;
	}
	public int GetLast(){
			return last;
	}
	
	public void setFirst(int f){
		this.first=f;		
	}

	public void setlast(int l){
		this.last=l;		
	}
	
	
	

}

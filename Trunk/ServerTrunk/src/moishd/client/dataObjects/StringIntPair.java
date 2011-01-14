package moishd.client.dataObjects;

import java.io.Serializable;

public class StringIntPair implements Serializable, Comparable<StringIntPair> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8272331739443303038L;

	private String stringValue;

	private int numberValue;

	public StringIntPair(String stringValue, int numberValue) {
		super();
		this.setStringValue(stringValue);
		this.setNumberValue(numberValue);
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setNumberValue(int numberValue) {
		this.numberValue = numberValue;
	}

	public int getNumberValue() {
		return numberValue;
	}
	
	public int compareTo(StringIntPair obj) {
		return this.getStringValue().compareTo(obj.getStringValue());
		
	}
}
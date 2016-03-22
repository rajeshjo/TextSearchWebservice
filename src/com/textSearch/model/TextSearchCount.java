package com.textSearch.model;

public class TextSearchCount {

	private String text;
	private int count;
	
	public TextSearchCount(){
		
	}
	public TextSearchCount(String text,int count){
		this.text = text;
		this.count = count;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}

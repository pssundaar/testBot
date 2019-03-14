package com.stocks.cb.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Text {

@SerializedName("text")
@Expose
public List<String> text = null;

public List<String> getText() {
	return text;
}

public void setText(List<String> text) {
	this.text = text;
}

}
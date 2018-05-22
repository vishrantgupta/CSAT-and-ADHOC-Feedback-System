package com.vishrant.iduhead.constant;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;

public class DisplayType {

	Integer displayType = 0;

	public DisplayType(String type) {

		if (type.equalsIgnoreCase("TEXT_BOX")) {
			this.displayType = 1;
		} else if (type.equalsIgnoreCase("RADIO_BTN")) {
			this.displayType = 2;
		} else if (type.equalsIgnoreCase("SELECT_BOX")) {
			this.displayType = 3;
		} else if (type.equalsIgnoreCase("RATING")) {
			this.displayType = 4;
		} else {
			this.displayType = null;
		}

	}

	//TODO REMOVE DEFAULT CONSTRUCTOR
	public DisplayType() {

	}
	
	public Integer getDisplayType() {
		return displayType;
	}

	public DisplayType(View view) {

		if (view instanceof EditText) {
			this.displayType = 1;
		} else if (view instanceof RadioGroup) {
			this.displayType = 2;
		} else if (view instanceof Spinner) {
			this.displayType = 3;
		} else if (view instanceof RatingBar) {
			this.displayType = 4;
		} else {
			this.displayType = null;
		}

	}

}

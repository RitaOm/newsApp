package com.epam.testapp.presentation.action;

/**
 * ActionConstants --- an enum which contains constants being used in NewsAction 
 * 
 * @author Marharyta_Amelyanchuk
 */
public enum ActionConstants {
	SUCCESS, FAILURE, UNDEFINED, CANCEL_FORWARD, LANG;

	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}

}

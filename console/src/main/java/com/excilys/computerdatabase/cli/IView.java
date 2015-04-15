package com.excilys.computerdatabase.cli;

import com.excilys.computerdatabase.modele.ComputerDTO;

/**
 * Provides console functions that call the service layer
 * @author Jordan Mosseri
 *
 */
public interface IView {
	
	/**
	 * Called at the beginning of the program
	 */
	public void initWebservice();

	/**
	 * Entry point for the View, holds a loop corresponding to the console program life cycle
	 */
	public abstract void runProgram();

	/**
	 * Display the functionalities' menu and return the user's choice
	 * @return
	 */
	public abstract int showMenuAndGetChoice();

	/**
	 * Get an integer typed by the user
	 * @param message
	 * @return
	 */
	public abstract int getIntFromConsole(String message);

	/**
	 * Get a String typed by the user
	 * @param message
	 * @return
	 */
	public abstract String getStringFromConsole(String message);

	/**
	 * Get a ComputerDTO after getting all info from the user
	 * @return
	 */
	public abstract ComputerDTO getComputerDTOFromConsole();

	/**
	 * Get a String date typed by the user
	 * @param message
	 * @return
	 */
	public abstract String getDateFromConsole(String message);

	
	public abstract void print(Object o);

	public abstract void println(Object o);

	public abstract void println();

}
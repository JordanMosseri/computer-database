package com.excilys.computerdatabase.ui.cli;

import com.excilys.computerdatabase.modele.ComputerDTO;

public interface IView {

	public abstract void lancerProgramme();

	public abstract int showMenuAndGetChoice();

	public abstract int getIntFromConsole(String message);

	public abstract String getStringFromConsole(String message);

	public abstract ComputerDTO getComputerDTOFromConsole();

	public abstract String getDateFromConsole(String message);

	public abstract void print(Object o);

	public abstract void println(Object o);

	public abstract void println();

}
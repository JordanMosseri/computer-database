package com.excilys.computerdatabase.main;

public class Checks_Service {
	
	//atej
	/*public Paging<Computer> getAll(int offset, int limit){
		if(offset < 0){
			throw new IllegalStateException("ComputerDAO getAll : Negative offset.");
		}
		return new Paging<Computer>(
				offset, 
				getPart(offset, limit), 
				(offset+1)/limit,
				getTotalCount()
				);
	}*/
	//
	
	//Paging<Computer> pagingComputer = ComputerDAO.getInstance().getAll(offset, limit);//atej
	
	
	/*public static Date stringToDate(String strRecuperee){
		Date dateRetour = null;
		
		if(checkString(Constantes.REGEX_DATE, strRecuperee)){
			try {
				dateRetour = Constantes.dateFormat.parse(strRecuperee);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return dateRetour;
		}
		return null;
	}*/
	
	/*public static int stringToInt(String strRecuperee){
		int id = -1;
		if (Service.checkString(Constantes.REGEX_INTEGER, strRecuperee)) {
			try{
				//id = in.nextInt();
				id = Integer.parseInt(strRecuperee);
			}
			catch(Exception e){ }//java.util.InputMismatchException
		}
		return id;
	}*/

}

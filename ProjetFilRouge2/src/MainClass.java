import java.util.Date;
import java.util.List;

import modele.*;



public class MainClass {

	public static void main(String[] args) {
		ProduitDAO produitdao = new ProduitDAO();
		
		List<Computer> p = produitdao.getListComputers();
		System.out.println(p);
		System.out.println("taille:"+p.size());
		
		System.out.println("i_test="+ProduitDAO.i_test);
		
		produitdao.insereComputer(new Computer("Chateau Laffite 2003",new Date(System.currentTimeMillis()),new Fabriquant("fab")));
		
		p = produitdao.getListComputers();
		System.out.println(p);
	}

}

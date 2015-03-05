package com.excilys.computerdatabase.ui.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.excilys.computerdatabase.main.Service;
import com.excilys.computerdatabase.modele.Company;

public class ListCompaniesTag extends TagSupport {
	
	int idSelected;

	@Override
	public int doStartTag() throws JspException {
		
		JspWriter out = pageContext.getOut();
		
		try {
	        List<Company> liste = (new Service()).getCompanies();
			for(Company c : liste){
				out.print("<option value=\"" + c.id + "\"");
				if(idSelected == c.id){//${pageScope["computer"].company.id}
					out.print(" selected=\"selected\"");
				}
				out.print(">" + c.name + "</option>");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return SKIP_BODY;
	}
	
	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	
	public int getIdSelected() {
		return idSelected;
	}

	public void setIdSelected(int idSelected) {
		this.idSelected = idSelected;
	}

	
}

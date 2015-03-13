package com.excilys.computerdatabase.ui.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.service.IService;
import com.excilys.computerdatabase.service.Service;

public class ListCompaniesTag extends TagSupport {
	
	List<Company> companyList;
	
	int idSelected;

	@Override
	public int doStartTag() throws JspException {
		
		JspWriter out = pageContext.getOut();
		
		try {
	        
			for(Company c : companyList){
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

	public List<Company> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<Company> companyList) {
		this.companyList = companyList;
	}

	
}

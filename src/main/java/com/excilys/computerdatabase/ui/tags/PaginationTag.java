package com.excilys.computerdatabase.ui.tags;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.excilys.computerdatabase.modele.ComputerDTO;
import com.excilys.computerdatabase.modele.Paging;

public class PaginationTag extends TagSupport {
	
	/**
	 * Attribute specified in the custom jsp tag
	 */
	Paging<ComputerDTO> pagingObject;
	
	
	/*int indexPage;
	int offset;
	int limit;
	int totalSize;*/
	

	@Override
	public int doStartTag() throws JspException {
		//pagingObject = (Paging<Computer>) pageContext.getRequest().getAttribute("pagingObject");
		
		JspWriter out = pageContext.getOut();
		try {
			
			//Get var via session
			/*Paging att = (Paging) pageContext.getSession().getAttribute("panier");//request
			if (att == null) {
				att = new Paging();
				pageContext.getSession().setAttribute("panier", att);
			}*/
			
			//Left arrow
			if(pagingObject.indexPage>0) {
				out.print("<li>");
				out.print("	<a href=\"?offset=" + (pagingObject.offset-pagingObject.getLimit()) + "&pageSize=" + pageContext.getRequest().getAttribute("pageSize") + "\" aria-label=\"Previous\">");
				out.print("		<span aria-hidden=\"true\">&laquo;</span>");
				out.print("	</a>");
				out.print("</li>");
			}
			
			//Numbers
			String bold = "style=\"font-weight:bold;\"";
			int numberOfPages = 1;
			if(pagingObject.getLimit() != 0){
				numberOfPages = pagingObject.totalSize/pagingObject.getLimit();
			}
			for(int i=pagingObject.indexPage-2; i<pagingObject.indexPage+2+1; i++){
				if(i>=0 && i<numberOfPages) {
					out.print("<li><a ");
					if(i == pagingObject.indexPage){
						out.print(bold);
					}
					out.print(" href=\"?offset=" + (i*pagingObject.getLimit()) + "&pageSize=" + pageContext.getRequest().getAttribute("pageSize") + "\">" + i + "</a></li>");
				}
			}
			//<%-- <li><a href="#"><%= paging.indexPage %></a></li> --%>
			
			//Right arrow
			if(pagingObject.indexPage<numberOfPages-1) {
				out.print("<li>");
				out.print("	<a href=\"?offset="+ (pagingObject.offset+pagingObject.getLimit()) +"&pageSize=" + pageContext.getRequest().getAttribute("pageSize") + "\" aria-label=\"Next\">");
				out.print("		<span aria-hidden=\"true\">&raquo;</span>");
				out.print("	</a>");
				out.print("</li>");
			}
			//
			
			//out.println(pagingObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return SKIP_BODY;
	}
	
	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
	
	
	
	
	public Paging<ComputerDTO> getPagingObject() {
		return pagingObject;
	}

	public void setPagingObject(Paging<ComputerDTO> pagingObject) {
		this.pagingObject = pagingObject;
	}

}

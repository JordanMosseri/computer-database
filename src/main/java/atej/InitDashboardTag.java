package atej;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.math.NumberUtils;

import com.excilys.computerdatabase.main.Service;
import com.excilys.computerdatabase.modele.Computer;
import com.excilys.computerdatabase.modele.ComputerDTO;
import com.excilys.computerdatabase.modele.Paging;

public class InitDashboardTag extends TagSupport {
	
	public static final int DEFAULT_PAGE_SIZE = 15;

	@Override
	public int doStartTag() throws JspException {
		
		int intOffset, pageSize = DEFAULT_PAGE_SIZE;
		Paging<ComputerDTO> paginationObject;

	    if (pageContext.getRequest().getParameter("offset") == null) {
	    	intOffset=0;
	    } else {
	    	intOffset = NumberUtils.toInt( pageContext.getRequest().getParameter("offset") );
	    }

	    if (pageContext.getRequest().getParameter("pageSize") == null) {
	    	pageSize=DEFAULT_PAGE_SIZE;
	    } else {
	    	pageSize = NumberUtils.toInt( pageContext.getRequest().getParameter("pageSize") );
	    }

	    paginationObject = (new Service()).getComputers(intOffset, pageSize, ""); 
		
		pageContext.setAttribute("pageSize", pageSize);
		pageContext.setAttribute("paginationObject", paginationObject);
		
		return SKIP_BODY;
	}
	
	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

}

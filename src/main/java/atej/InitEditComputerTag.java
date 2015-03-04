package atej;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.math.NumberUtils;

import com.excilys.computerdatabase.dao.ComputerDAO;
import com.excilys.computerdatabase.main.Service;
import com.excilys.computerdatabase.modele.Computer;

public class InitEditComputerTag extends TagSupport {

	@Override
	public int doStartTag() throws JspException {
		
		int intId;
		Computer computer;
		
	    if (pageContext.getRequest().getParameter("id") == null) {
	    	intId=-1;
	    } else {
	    	intId = NumberUtils.toInt( pageContext.getRequest().getParameter("id") );
	    }
	    
	    computer = ComputerDAO.getInstance().get(intId); 
		
		pageContext.setAttribute("intId", intId);
		pageContext.setAttribute("computer", computer);
		
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

}

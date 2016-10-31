package base.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class SystemBasePathFilter implements Filter {
	
	public void destroy() {
	}

	/**
	 * 获得<base href="${ROOT}"/>
	 * */
	public void doFilter(ServletRequest req, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)req;
		ServletContext ctx = request.getSession().getServletContext();
		if(ctx.getAttribute("ROOT") == null) {
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
			System.out.println(basePath);
			ctx.setAttribute("ROOT", basePath);
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

	
}

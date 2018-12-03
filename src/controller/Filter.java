package controller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Auther: YangTao
 * @Date: 2018/11/7 0007
 */
@WebFilter(filterName = "Filter",urlPatterns = "/admin/*")
public class Filter implements javax.servlet.Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        HttpSession session = request.getSession(false);
        if (requestURI.equals("/admin/index.jsp")||requestURI.equals("/admin/AdminServlet")||requestURI.startsWith("/admin/css")
                ||requestURI.startsWith("/admin/images")||requestURI.startsWith("/admin/js")){
            chain.doFilter(req ,resp);
            return;
        }
        if (session!=null){
            Object admin = session.getAttribute("admin");
            if (admin!=null){
                chain.doFilter(req ,resp);
                return;
            }

        }
        response.getWriter().println("请先登陆");
        response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/index.jsp");



    }

    public void init(FilterConfig config) throws ServletException {

    }

}

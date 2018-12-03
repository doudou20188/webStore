package controller;

import bean.Product;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import service.ImpService;
import service.Service;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Auther: YangTao
 * @Date: 2018/11/9 0009
 */
@WebServlet(name = "AddProductServlet",urlPatterns = "/admin/AddProductServlet")
public class AddProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //添加商品处理项
        Service service=new ImpService();
        //1创建工厂对象
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        //设置临时目录
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File)servletContext.getAttribute("javax.servlet.context.tempdir");//临时储存目录
        diskFileItemFactory.setRepository(repository);
        ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
        List<FileItem> fileItems ;

        Map map = new HashMap();
        Product product = new Product();

        try {
            fileItems=fileUpload.parseRequest(request);
            Iterator<FileItem> iterator = fileItems.iterator();
            while (iterator.hasNext()){
                FileItem fileItem = iterator.next();
                if (fileItem.isFormField()){
                    handleFormField(fileItem,map,request,response);
                }else {
                    handleFileupLoad(fileItem,map,request,response);
                }
            }


        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        try {
            BeanUtils.populate(product,map);

            System.out.println(map);
            boolean check=service.checkAddProduct(map);
            if (check){
                response.getWriter().println("添加项不可为空，请重新添加！");
                response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/CategoryServlet?op=findCategory");
            }else {
                boolean b = service.addProduct(product);
                if (b){
                    response.getWriter().println("添加成功");
                    response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/CategoryServlet?op=findCategory");

                }
                else {
                    response.getWriter().println("添加失败");
                    response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/CategoryServlet?op=findCategory");
                }
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }
    //处理表单文件对象
    private void handleFileupLoad(FileItem fileItem, Map map, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String Name = fileItem.getFieldName();
        String fileName = fileItem.getName();

        map.put(Name,"pic/"+fileName);
        //目录拼接，完成相同文件名的储存硬盘工作
        String realPath = getServletContext().getRealPath("pic");
        File file = new File(realPath + "/" + fileName);
        //String absolutePath = file.getAbsolutePath();
        try {
            fileItem.write(file);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            fileItem.delete();
        }
    }
    //处理普通表单对象
    private void handleFormField(FileItem fileItem, Map map, HttpServletRequest request, HttpServletResponse response) {
        String key = fileItem.getFieldName();
        String value = null;
        try {
            value = fileItem.getString("utf-8");


            map.put(key,value);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}

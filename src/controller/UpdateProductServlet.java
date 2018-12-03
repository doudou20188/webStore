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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Auther: YangTao
 * @Date: 2018/11/9 0009
 */
@WebServlet(name = "UpdateProductServlet",urlPatterns = "/admin/UpdateProductServlet")
public class UpdateProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.getWriter().println(666);
        Service service2=new ImpService();
        //1创建工厂对象
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        //设置临时目录
        ServletContext servletContext2 = this.getServletConfig().getServletContext();
        File repository = (File)servletContext2.getAttribute("javax.servlet.context.tempdir");//临时储存目录
        diskFileItemFactory.setRepository(repository);
        ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
        List<FileItem> fileItems ;

        Service service=new ImpService();
        Map map = new HashMap();
        Product product = new Product();
        try {
            fileItems=fileUpload.parseRequest(request);
            Iterator<FileItem> iterator = fileItems.iterator();
            while (iterator.hasNext()){
                FileItem fileItem = iterator.next();
                if (fileItem.isFormField()){
                    handleFormField(fileItem,map);
                }else {
                    handleFileupLoad(fileItem,map);
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(product.getImgurl());

            BeanUtils.populate(product,map);//封装类信息
            //System.out.println(product);
        }catch (Exception e){
            e.printStackTrace();
        }

            if (product.getImgurl().equals("/admin/pic/")){
                //不更新它的图片地址
                try {

                    boolean b1 = service.updateProductOT(product);
                    if (b1){
                        response.getWriter().println("修改成功！");
                        //service.getProduct();
                        response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/ProductServlet?op=findAllProduct&num=1");

                    }
                    else {
                        response.getWriter().println("修改失败");
                        response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/ProductServlet?op=findAllProduct&num=1");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                try {

                    boolean b1 = service.updateProduct(product);
                    if (b1){
                        response.getWriter().println("修改成功！");
                        response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/ProductServlet?op=findAllProduct&num=1");

                    }
                    else {
                        response.getWriter().println("修改失败");
                        response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/ProductServlet?op=findAllProduct&num=1");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }


    }
    //处理表单文件对象
    private void handleFileupLoad(FileItem fileItem,Map map) {
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
    private void handleFormField(FileItem fileItem,Map map) {
        String key = fileItem.getFieldName();
        String value = null;
        try {
            value = fileItem.getString("utf-8");
            map.put(key,value);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}

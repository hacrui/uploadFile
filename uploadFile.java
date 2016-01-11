// �������� java ��
import java.io.*;
import java.util.*;
 
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;

public class UploadServlet extends HttpServlet 
{
   
    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 50 * 1024;
    private int maxMemSize = 4 * 1024;
    private File file;

    public void init( )
    {
       // ��ȡ�ļ������洢��λ��
       filePath = 
              getServletContext().getInitParameter("file-upload"); 
    }
    
    public void doPost(HttpServletRequest request, 
               HttpServletResponse response)
              throws ServletException, java.io.IOException 
    {
        // ���������һ���ļ��ϴ�����
        isMultipart = ServletFileUpload.isMultipartContent(request);
        response.setContentType("text/html");
        java.io.PrintWriter out = response.getWriter( );
        if( !isMultipart ){
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet upload</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<p>No file uploaded</p>"); 
            out.println("</body>");
            out.println("</html>");
            return;
        }
      
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // �ļ���С�����ֵ�����洢���ڴ���
        factory.setSizeThreshold(maxMemSize);
        // Location to save data that is larger than maxMemSize.
        factory.setRepository(new File("c:\\temp"));
  
        // ����һ���µ��ļ��ϴ��������
        ServletFileUpload upload = new ServletFileUpload(factory);
        // �����ϴ����ļ���С�����ֵ
        upload.setSizeMax( maxFileSize );
  
        try{ 
        // �������󣬻�ȡ�ļ���
        List fileItems = upload.parseRequest(request);
  	
        // �����ϴ����ļ���
        Iterator i = fileItems.iterator();
  
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet upload</title>");  
        out.println("</head>");
        out.println("<body>");
        while ( i.hasNext () ) 
        {
         FileItem fi = (FileItem)i.next();
         if ( !fi.isFormField () )	
         {
            // ��ȡ�ϴ��ļ��Ĳ���
            String fieldName = fi.getFieldName();
            String fileName = fi.getName();
            String contentType = fi.getContentType();
            boolean isInMemory = fi.isInMemory();
            long sizeInBytes = fi.getSize();
            // д���ļ�
            if( fileName.lastIndexOf("\\") >= 0 ){
               file = new File( filePath + 
               fileName.substring( fileName.lastIndexOf("\\"))) ;
            }else{
               file = new File( filePath + 
               fileName.substring(fileName.lastIndexOf("\\")+1)) ;
            }
            fi.write( file ) ;
            out.println("Uploaded Filename: " + fileName + "<br>");
         }
      }
      out.println("</body>");
      out.println("</html>");
   }catch(Exception ex) {
       System.out.println(ex);
   }
    }
    
    public void doGet(HttpServletRequest request, 
                       HttpServletResponse response)
        throws ServletException, java.io.IOException {
        	
        	// ������Ӧ��������
        response.setContentType("text/html");

      		PrintWriter out = response.getWriter();
      		//���߿ⷿ
String wareHouse = new String();
if(request.getParameter("gz").equals("on"))
{
    wareHouse.add("gz");		
}
if(request.getParameter("guan").equals("on"))
{
    wareHouse.add("guan");		
}
if(request.getParameter("sh").equals("on"))
{
    wareHouse.add("sh");		
}

String module = request.getParameter("module");

for(int i = 0; i < wareHouse.length; i++)
{
    filePath = "localhost:8080/"+wareHouse[i]+module;
}
//Enumeration<String> parameterNames = request.getParameterNames();

   } 
}
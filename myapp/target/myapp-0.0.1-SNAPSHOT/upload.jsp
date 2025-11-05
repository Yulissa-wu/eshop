<%@ page import="java.util.*, java.io.*, org.apache.commons.fileupload.*, org.apache.commons.fileupload.disk.*, org.apache.commons.fileupload.servlet.*" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head><title>檔案上傳結果</title></head>
<body>
<h2>檔案上傳結果</h2>

<%
    DiskFileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload upload = new ServletFileUpload(factory);

    try {
        List<FileItem> items = upload.parseRequest(request);
        for (FileItem item : items) {
            if (!item.isFormField()) {
                String fileName = item.getName();
                out.println("上傳位置:" + application.getRealPath("/") + "uploads"+ fileName);
                File uploadFile = new File(application.getRealPath("/") + "uploads", fileName);
                uploadFile.getParentFile().mkdirs(); // 建立目錄
                item.write(uploadFile);
                out.println("<p> 已上傳：" + fileName + "</p>");
                
            }
        }
    } catch (Exception e) {
        out.println("<p> 上傳失敗：" + e.getMessage() + "</p>");
    }
%>

</body>
</html>
package ar.devfest.csv2datastore;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import ar.devfest.csv2datastore.services.Importer;

@SuppressWarnings("serial")
public class FileUploadServlet extends HttpServlet {
  public static final String ENTITY_NAME_PARAMETER = "entity";

  public void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    ServletFileUpload upload = new ServletFileUpload();
    FileItemIterator fileIterator;
    try {
      fileIterator = upload.getItemIterator(req);

      Importer importer = new Importer();
      String entityName = req.getParameter(ENTITY_NAME_PARAMETER);
      if (entityName == null || "".equals(entityName)) {
        throw new ServletException(
            "Entity name was not found. Expected parameter:" + ENTITY_NAME_PARAMETER);
      }

      while (fileIterator.hasNext()) {
        FileItemStream item = fileIterator.next();
        InputStream stream = item.openStream();
        importer.importCSV(entityName, stream);
      }
      if (importer.getImportedRows() == 0) {
        throw new ServletException("No CSV file was imported. Missing CSV stream in the request.");
      }
      res.setContentType("text/plain");
      res.getWriter().print("Done");
    } catch (FileUploadException ex) {
      throw new ServletException(ex);
    } catch (IOException ex) {
      throw new ServletException(ex);
    }
  }

}

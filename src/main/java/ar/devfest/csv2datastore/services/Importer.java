package ar.devfest.csv2datastore.services;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Importer {
  private static final Logger log = Logger.getLogger(Importer.class.getName());
  private int importedRows;

  public Importer() {
    this.importedRows = 0;
  }

  public void importCSV(String entityName, InputStream in) throws IOException {
    log.info(String.format("Parsing CSV for entity:%s", entityName));
    Reader data = new InputStreamReader(in);
    CSVParser parser = CSVFormat.DEFAULT.parse(data);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Iterator<CSVRecord> it = parser.iterator();
    ArrayList<String> propertyNames = new ArrayList<>();
    if (it.hasNext()) {
      CSVRecord header = it.next();
      for (String propertyName : header) {
        propertyNames.add(propertyName);
      }
    }
    log.info(String.format("Header properties detected:%s", propertyNames));
    while (it.hasNext()) {
      CSVRecord record = it.next();
      Entity entity = new Entity(entityName);
      ArrayList<String> values = new ArrayList<>(record.size());
      for (int i = 0; i < record.size(); i++) {
        String value = record.get(i);
        String propertyName = propertyNames.get(i);
        entity.setProperty(propertyName, value);
        values.add(value);
      }
      log.info(String.format("Storing %s(%s)", entityName, values));
      datastore.put(entity);
      ++this.importedRows;
    }
  }

  public int getImportedRows() {
    return importedRows;
  }

}

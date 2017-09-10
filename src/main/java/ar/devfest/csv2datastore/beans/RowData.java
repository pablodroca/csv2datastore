package ar.devfest.csv2datastore.beans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RowData implements Iterable<AttributeData> {
  private List<AttributeData> attributeData = new ArrayList<>();

  @Override
  public Iterator<AttributeData> iterator() {
    return this.attributeData.iterator();
  }
}

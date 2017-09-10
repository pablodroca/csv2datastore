package ar.devfest.csv2datastore;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;

import ar.devfest.csv2datastore.beans.EntityData;

@Api(name = "filemanagement", version = "v1",
    namespace = @ApiNamespace(ownerDomain = "csv2datastore.devfest.ar",
        ownerName = "csv2datastore.devfest.ar", packagePath = ""))
public class RegistersDataAPI {
  @ApiMethod(name = "dump")
  public EntityData dump(@Named("entityName") String entityName) {
    EntityData response = new EntityData();
    response.setName(entityName);
    return response;
  }
}

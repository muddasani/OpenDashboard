/**
 * 
 */
package od.framework.api;

import java.util.ArrayList;
import java.util.List;

import od.framework.model.Tenant;
import od.repository.mongo.MongoTenantRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ggilbert
 *
 */
@RestController
public class TenantController {
  private static final Logger log = LoggerFactory.getLogger(TenantController.class);
  @Autowired private MongoTenantRepository mongoTenantRepository;
  
  @Secured("ROLE_ADMIN")
  @RequestMapping(value = "/api/tenant", method = RequestMethod.POST, 
      produces = "application/json;charset=utf-8", consumes = "application/json")
  public Tenant create(@RequestBody Tenant tenant) {
    log.debug("{}",tenant);
    return mongoTenantRepository.save(tenant);
  }
  
  @Secured("ROLE_ADMIN")
  @RequestMapping(value = "/api/tenant", method = RequestMethod.PUT, 
      produces = "application/json;charset=utf-8", consumes = "application/json")
  public Tenant update(@RequestBody Tenant tenant) {
    return mongoTenantRepository.save(tenant);
  }
  
  @Secured("ROLE_ADMIN")
  @RequestMapping(value = "/api/tenant", method = RequestMethod.GET, 
      produces = "application/json;charset=utf-8")
  public List<Tenant> getAll() {
    return mongoTenantRepository.findAll();
  }
  
  //@Secured("ROLE_ADMIN")
  @RequestMapping(value = "/api/tenant/{id}", method = RequestMethod.GET, 
      produces = "application/json;charset=utf-8")
  public Tenant getOne(@PathVariable("id") final String id) {
    return mongoTenantRepository.findOne(id);
  }
  
  @RequestMapping(value = "/tenant", method = RequestMethod.GET, 
      produces = "application/json;charset=utf-8")
  public List<Tenant> getAllTenantMetadata() {
    List<Tenant> tenants = mongoTenantRepository.findAll();
    List<Tenant> metadata = new ArrayList<Tenant>();
    if (tenants != null) {
      for (Tenant t : tenants) {
        Tenant mt = new Tenant();
        mt.setId(t.getId());
        mt.setName(t.getName());
        mt.setDescription(t.getDescription());
        mt.setIdpEndpoint(t.getIdpEndpoint());
        metadata.add(mt);
      }
    }
    
    return metadata;
  }
  
//  @RequestMapping(value = "/tenant/{id}", method = RequestMethod.GET, 
//      produces = "application/json;charset=utf-8")
//  public Tenant getOneTentantMetadata(@PathVariable("id") final String id) {
//    Tenant tenant = mongoTenantRepository.findOne(id);
//    Tenant metadata = new Tenant();
//    metadata.setId(tenant.getId());
//    metadata.setName(tenant.getName());
//    metadata.setDescription(tenant.getDescription());
//    metadata.setIdpEndpoint(tenant.getIdpEndpoint());
//    
//    return metadata;
//  }
  
  @RequestMapping(value = "/api/tenant/key/{key}", method = RequestMethod.GET, 
      produces = "application/json;charset=utf-8")
  public Tenant getOneWithConsumerKey(@PathVariable("key") final String key) {
    return mongoTenantRepository.findByConsumersOauthConsumerKey(key);
  }
  
  @Secured("ROLE_ADMIN")
  @RequestMapping(value = "/api/tenant/{id}", method = RequestMethod.DELETE, 
      produces = "application/json;charset=utf-8")
  public void delete(@PathVariable("id") final String id) {
    mongoTenantRepository.delete(id);
  }
  
//  @RequestMapping(value = "/api/providerdata/{type}/{key}", method = RequestMethod.PUT, 
//      produces = "application/json;charset=utf-8", consumes = "application/json")
//  public ProviderData update(@RequestBody ProviderData providerData) {
//     return providerDataRepositoryInterface.save(providerData);
//  }
//  
//  @RequestMapping(value = "/api/providerdata/{type}/{key}", method = RequestMethod.DELETE, 
//      produces = "application/json;charset=utf-8", consumes = "application/json")
//  public boolean delete(@RequestBody ProviderData providerData) {
//     providerDataRepositoryInterface.delete(providerData);
//     return true;
//  }
//
//  @RequestMapping(value = "/api/providerdata", method = RequestMethod.POST, 
//      produces = "application/json;charset=utf-8", consumes = "application/json")
//  public ProviderData create(@RequestBody ProviderData providerData) {
//     return providerDataRepositoryInterface.save(providerData);
//  }
//
//  @RequestMapping(value = {"/api/providerdata/{type}"}, method = RequestMethod.GET, produces="application/json;charset=utf-8")
//  public List<ProviderData> providerDataByType(@PathVariable("type") final String type) {
//    return providerDataRepositoryInterface.findByProviderType(type);
//  }
//  
//  @RequestMapping(value = {"/api/providerdata/{type}/{key}"}, method = RequestMethod.GET, produces="application/json;charset=utf-8")
//  public ProviderData provideDataByTypeAndKey(@PathVariable("type") final String type, @PathVariable("key") final String key) {
//    return providerDataRepositoryInterface.findByProviderKey(key);
//  }


}

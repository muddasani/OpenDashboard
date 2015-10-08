/*******************************************************************************
 * Copyright 2015 Unicon (R) Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *******************************************************************************/
/**
 * 
 */
package od.providers.api;

import od.TenantService;
import od.providers.ProviderOptions;
import od.providers.ProviderService;
import od.providers.modeloutput.ModelOutputProvider;

import org.apereo.lai.ModelOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ggilbert
 *
 */
@RestController
public class ModelOutputController {
  private static final Logger log = LoggerFactory.getLogger(ModelOutputController.class);
  @Autowired private ProviderService providerService;
  @Autowired private TenantService tenantService;
  
  @Secured("ROLE_INSTRUCTOR")
  @RequestMapping(value = "/api/modeloutput/course/{courseId}", method = RequestMethod.POST)
  public Page<ModelOutput> modelOutputForCourse(@RequestBody ProviderOptions options, @PathVariable("courseId") final String courseId, @RequestParam(value="page", required=false) int page,
      @RequestParam(value="size", required=false) int size)
      throws Exception {

    if (log.isDebugEnabled()) {
      log.debug("options " + options);
    }
    
    ModelOutputProvider modelOutputProvider = providerService.getModelOutputProvider();
    
    return modelOutputProvider.getModelOutputForCourse(options, tenantService.getTenant(), courseId, new PageRequest(page, size));
  }
  
  @Secured({"ROLE_INSTRUCTOR","ROLE_STUDENT"})
  @RequestMapping(value = "/api/modeloutput/user/{userId}", method = RequestMethod.POST)
  public Page<ModelOutput> modelOutputForUser(@RequestBody ProviderOptions options, @PathVariable("userId") final String userId, @RequestParam(value="page", required=false) int page,
      @RequestParam(value="size", required=false) int size)
      throws Exception {

    if (log.isDebugEnabled()) {
      log.debug("options " + options);
    }
    
    ModelOutputProvider modelOutputProvider = providerService.getModelOutputProvider();
    
    return modelOutputProvider.getModelOutputForStudent(options, tenantService.getTenant(), userId, new PageRequest(page, size));
  }

}

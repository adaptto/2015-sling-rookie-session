/*
 * #%L
 * adaptTo()
 * %%
 * Copyright (C) 2014-2015 adaptTo() Conference
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.adaptto.rookie.demo.components;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.commons.lang.ArrayUtils;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

/**
 * Like update action using Sling CRUD API to write to repository.
 */
@SlingServlet(resourceTypes="/apps/rookiedemo/components/talk", selectors="likeme", methods="POST")
public class LikeMe extends SlingAllMethodsServlet {
  private static final long serialVersionUID = -827047552677135151L;

  @Override
  protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

    updateLikeCounter(request);

    // return to main view
    response.sendRedirect(request.getResource().getPath() + ".html");
  }

  private void updateLikeCounter(SlingHttpServletRequest request) throws PersistenceException {

    ValueMap props = request.getResource().getValueMap();

    // check if a user with this ip address has already liked this
    String ipAddress = request.getRemoteAddr();
    String[] likedAddresses = props.get("likedAddresses", new String[0]);
    if (ArrayUtils.contains(likedAddresses, ipAddress)) {
      return;
    }

    // increment like counter and store ip address
    ValueMap writeProps = request.getResource().adaptTo(ModifiableValueMap.class);
    writeProps.put("likes", writeProps.get("likes", 0L) + 1);

    List<String> updatedLikedAddresses = new ArrayList<>(Arrays.asList(likedAddresses));
    updatedLikedAddresses.add(ipAddress);
    writeProps.put("likedAddresses", updatedLikedAddresses.toArray());

    // save to repository
    request.getResourceResolver().commit();

  }

}

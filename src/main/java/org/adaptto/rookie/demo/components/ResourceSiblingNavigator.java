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
import java.io.Writer;

import javax.servlet.ServletException;

import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;

/**
 * Navigation links for talk "sibling" nodes next talk/previous talk.
 */
@SlingServlet(resourceTypes="/apps/rookiedemo/components/resourceSiblingNavigator")
public class ResourceSiblingNavigator extends SlingSafeMethodsServlet {
  private static final long serialVersionUID = 6609210109107816202L;

  @Override
  protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
    Writer out = response.getWriter();

    // get previous/next sibling
    Resource previousResource = null;
    Resource currentResource = null;
    Resource nextResource = null;
    for (Resource sibling : request.getResource().getParent().getChildren()) {
      if (currentResource!=null) {
        nextResource = sibling;
        break;
      }
      else if (StringUtils.equals(sibling.getPath(), request.getResource().getPath())) {
        currentResource = request.getResource();
      }
      else {
        previousResource = sibling;
      }
    }

    // anchor for previous/next sibling
    if (previousResource!=null) {
      out.write(" | <a href=\"" + previousResource.getPath() + ".html\">Previous</a>");
    }
    if (nextResource!=null) {
      out.write(" | <a href=\"" + nextResource.getPath() + ".html\">Next</a>");
    }

  }

}

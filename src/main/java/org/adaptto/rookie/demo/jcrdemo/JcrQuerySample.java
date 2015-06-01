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
package org.adaptto.rookie.demo.jcrdemo;

import java.io.IOException;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;

/**
 * JCR query example
 */
@SlingServlet(resourceTypes="/apps/rookiedemo/components/index", selectors="jcrquerysample")
@SuppressWarnings("deprecation")
public class JcrQuerySample extends SlingSafeMethodsServlet {
  private static final long serialVersionUID = -8909492203133496844L;

  @Override
  protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
    Session session = request.getResourceResolver().adaptTo(Session.class);
    try {
      String jcrContent = queryJcrContent(session);
      response.setContentType("text/plain;charset=UTF-8");
      response.getWriter().write(jcrContent);
    }
    catch (RepositoryException ex) {
      throw new ServletException(ex);
    }
  }

  String queryJcrContent(Session session) throws RepositoryException {

    // get query manager
    QueryManager queryManager = session.getWorkspace().getQueryManager();

    // query for all nodes with tag "JCR"
    Query query = queryManager.createQuery("/jcr:root/content/adaptto//*[tags='JCR']", Query.XPATH);

    // iterate over results
    QueryResult result = query.execute();
    NodeIterator nodes = result.getNodes();
    StringBuilder output = new StringBuilder();
    while (nodes.hasNext()) {
      Node node = nodes.nextNode();
      output.append("path=" + node.getPath() + "\n");
    }

    return output.toString();
  }

}

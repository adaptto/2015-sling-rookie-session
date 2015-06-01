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
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;

/**
 * JCR write example.
 */
@SlingServlet(resourceTypes="/apps/rookiedemo/components/index", selectors="jcrwritesample")
public class JcrWriteSample extends SlingSafeMethodsServlet {
  private static final long serialVersionUID = -3387175284108086362L;

  @Override
  protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
    Session session = request.getResourceResolver().adaptTo(Session.class);
    try {
      writeJcrContent(session);
      response.sendRedirect(request.getResource().getPath() + ".jcrreadsample.txt");
    }
    catch (RepositoryException ex) {
      throw new ServletException(ex);
    }
  }

  void writeJcrContent(Session session) throws RepositoryException {

    // get node directly
    Node talk = session.getNode("/content/adaptto/2015/day1/rookie-session");

    // write property values
    talk.setProperty("jcr:title", "My Rookie Session");
    talk.setProperty("durationMin", talk.getProperty("durationMin").getLong() + 10);
    talk.setProperty("tags", new String[] { "Sling", "JCR", "Rookie" });

    // save changes to repository (implicit transaction)
    session.save();
  }

}

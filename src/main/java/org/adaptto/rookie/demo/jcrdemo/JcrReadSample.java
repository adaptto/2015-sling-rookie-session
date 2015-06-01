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
import java.util.Arrays;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;

/**
 * JCR read example.
 */
@SlingServlet(resourceTypes="/apps/rookiedemo/components/index", selectors="jcrreadsample")
public class JcrReadSample extends SlingSafeMethodsServlet {
  private static final long serialVersionUID = -2975383706747400409L;

  @Override
  protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
    Session session = request.getResourceResolver().adaptTo(Session.class);
    try {
      String jcrContent = readJcrContent(session);
      response.setContentType("text/plain;charset=UTF-8");
      response.getWriter().write(jcrContent);
    }
    catch (RepositoryException ex) {
      throw new ServletException(ex);
    }
  }

  String readJcrContent(Session session) throws RepositoryException {

    // get node directly
    Node day1 = session.getNode("/content/adaptto/2013/day1");

    // get first child node
    Node firstTalk = day1.getNodes().nextNode();

    // read property values
    String title = firstTalk.getProperty("jcr:title").getString();
    long duration = firstTalk.getProperty("durationMin").getLong();

    // read multi-valued property
    Value[] tagValues = firstTalk.getProperty("tags").getValues();
    String[] tags = new String[tagValues.length];
    for (int i=0; i<tagValues.length; i++) {
      tags[i] = tagValues[i].getString();
    }

    return "First talk: " + title + " (" + duration + " min)\n"
        + "Tags: " + Arrays.toString(tags) + "\n"
        + "Path: " + firstTalk.getPath();
  }

}

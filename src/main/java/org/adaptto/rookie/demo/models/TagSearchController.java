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
package org.adaptto.rookie.demo.models;

import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;

/**
 * Controller model that implements a search for all talks with the tag name give as suffix.
 */
@Model(adaptables=SlingHttpServletRequest.class)
@SuppressWarnings("javadoc")
public class TagSearchController {

  private final String tag;
  private final List<Resource> result;

  @SuppressWarnings("unchecked")
  public TagSearchController(SlingHttpServletRequest request) {
    Resource resource = request.getResource();
    ResourceResolver resolver = request.getResourceResolver();

    // get tag name to search for form suffix
    String suffix = request.getRequestPathInfo().getSuffix();
    this.tag = StringUtils.substringAfter(suffix, "/");

    // execute JCR query via Sling API
    String xpathQuery = "/jcr:root" + resource.getPath() + "//*[tags='" + this.tag + "']";
    this.result = IteratorUtils.toList(resolver.findResources(xpathQuery, "xpath"));
  }

  public String getTag() {
    return this.tag;
  }

  public List<Resource> getResult() {
    return this.result;
  }

}

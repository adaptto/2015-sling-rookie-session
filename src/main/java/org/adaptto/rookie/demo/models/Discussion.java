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
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Lists als child resource of a "discussion" subresource of the current resourc.e
 */
@Model(adaptables=Resource.class)
@SuppressWarnings("javadoc")
public class Discussion {

  private List<Resource> comments;

  @SuppressWarnings("unchecked")
  public Discussion(Resource resource) {
    Resource commentsParent = resource.getChild("discussion");
    if (commentsParent!=null) {
      this.comments = IteratorUtils.toList(commentsParent.listChildren());
    }
  }

  public List<Resource> getComments() {
    return this.comments;
  }

}

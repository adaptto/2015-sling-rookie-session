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
package org.adaptto.rookie.demo.services;

import java.util.Iterator;

import javax.jcr.query.Query;

import org.adaptto.rookie.demo.models.Comment;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Background job to automatically remove empty comments.
 */
@Component(immediate = true, metatype = true, label = "adaptTo() Rookie Demo Comment Cleanup Service",
    description = "Removes all empty comments.")
@Service(value = Runnable.class)
public class CommentCleanUpCronJob implements Runnable {

  @Property(value = "0 0/15 * * * ?", // run every 15 minutes
  label = "Scheduler Expression",
      description = "Cron expression for scheduling, see http://www.quartz-scheduler.org/docs/tutorials/crontrigger.html for examples.")
  private static final String PROPERTY_CRON_EXPRESSION = "scheduler.expression";

  @Reference
  private ResourceResolverFactory resourceResolverFactory;

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Override
  public void run() {
    ResourceResolver adminResolver = null;
    try {

      // get administrative resolver
      adminResolver = resourceResolverFactory.getServiceResourceResolver(null);

      // fire query to get all comment nodes
      log.debug("Query for all comments.");
      Iterator<Resource> commentResources = adminResolver.findResources("SELECT * "
          + "FROM [sling:OrderedFolder] "
          + "WHERE ISDESCENDANTNODE([/content/adaptto]) "
          + "AND [sling:resourceType]='/apps/rookiedemo/components/social/comment'", Query.JCR_SQL2);

      // iterate over all comments and remove those that have empty text
      while (commentResources.hasNext()) {
        Resource commentResource = commentResources.next();
        log.debug("Check comment {}", commentResource.getPath());

        Comment comment = commentResource.adaptTo(Comment.class);
        if (comment.isEmpty()) {
          log.info("Delete empty comment {}", commentResource.getPath());
          adminResolver.delete(commentResource);
        }
      }

      // save changes to repository
      if (adminResolver.hasChanges()) {
        adminResolver.commit();
      }

    }
    catch (LoginException ex) {
      log.error("Error getting administrativ resolver.", ex);
    }
    catch (PersistenceException ex) {
      log.error("Error persisting changes to repository.", ex);
    }
    finally {
      if (adminResolver!=null) {
        adminResolver.close();
      }
    }
  }

}

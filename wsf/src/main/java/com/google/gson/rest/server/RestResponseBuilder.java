/*
 * Copyright (C) 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.gson.rest.server;

import com.google.greaze.definition.HttpMethod;
import com.google.greaze.definition.rest.ID;
import com.google.greaze.definition.rest.RestCallSpec;
import com.google.greaze.definition.rest.RestRequest;
import com.google.greaze.definition.rest.RestResource;
import com.google.greaze.definition.rest.RestResponse;

public class RestResponseBuilder<I extends ID, R extends RestResource<I, R>> {
  protected final Repository<I, R> resources;

  public RestResponseBuilder(Repository<I, R> resources) {
    this.resources = resources;
  }

  public void buildResponse(RestCallSpec callSpec, RestRequest<I, R> request,
      RestResponse.Builder<I, R> responseBuilder) {
    HttpMethod method = request.getMethod();
    R responseBody = null;
    switch (method) {
      case GET:
        responseBody = get(request.getId());
        break;
      case POST:
        responseBody = post(request.getBody());
        break;
      case DELETE:
        delete(request.getId());
        break;
      case PUT:
      default:
        throw new IllegalStateException("Unexpected method: " + method);
    }
    responseBuilder.setBody(responseBody);
  }

  public R get(I resourceId) {
    return resources.get(resourceId);
  }

  public R post(R resource) {
    return resources.put(resource);
  }

  public void delete(I resourceId) {
    resources.delete(resourceId);
  }

  public R put(R resource) {
    return resources.put(resource);
  }
}
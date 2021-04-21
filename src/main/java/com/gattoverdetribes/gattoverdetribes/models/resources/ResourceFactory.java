package com.gattoverdetribes.gattoverdetribes.models.resources;

import static com.gattoverdetribes.gattoverdetribes.models.resources.ResourceType.generateResource;
import com.gattoverdetribes.gattoverdetribes.exceptions.InvalidResourceException;

public class ResourceFactory {

  public static Resource createResource(String resourceType) throws InvalidResourceException {
    for (ResourceType type : ResourceType.values()) {
      if (ResourceType.valueOf(resourceType.toUpperCase()).equals(type)) {
        return generateResource(type);
      }
    }
    throw new InvalidResourceException("Created such resource can not be. Yrsssss.");
  }
}

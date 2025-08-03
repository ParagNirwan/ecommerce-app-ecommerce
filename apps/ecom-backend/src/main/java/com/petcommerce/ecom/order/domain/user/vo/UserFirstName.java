package com.petcommerce.ecom.order.domain.user.vo;

import com.petcommerce.ecom.shared.error.domain.Assert;

public record UserFirstName(String value) {
  public UserFirstName {
    Assert.field("value",value).maxLength(255);
  }
}

package com.petcommerce.ecom.order.domain.user.vo;

import com.petcommerce.ecom.shared.error.domain.Assert;

public record UserLastName(String value) {
  public UserLastName {
    Assert.field("value",value).maxLength(255);
  }
}

package com.petcommerce.ecom.order.domain.user.vo;

import com.petcommerce.ecom.shared.error.domain.Assert;

public record UserImageUrl(String value) {

  public UserImageUrl {
    Assert.field("value",value).maxLength(1000);
  }
}

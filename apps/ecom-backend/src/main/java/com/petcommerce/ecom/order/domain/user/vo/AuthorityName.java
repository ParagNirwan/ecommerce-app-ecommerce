package com.petcommerce.ecom.order.domain.user.vo;

import com.petcommerce.ecom.shared.error.domain.Assert;

public record AuthorityName(String name) {
  public AuthorityName {
    Assert.field("name",name).notNull();
  }
}

package com.petcommerce.ecom.order.domain.user.vo;

import com.petcommerce.ecom.shared.error.domain.Assert;
import org.jilt.Builder;

@Builder
public record UserAddressToUpdate(UserPublicId userPublicId, UserAddress userAddress) {
  public UserAddressToUpdate {
    Assert.notNull("userPublicId",userPublicId);
    Assert.notNull("userAddress",userAddress);
  }
}

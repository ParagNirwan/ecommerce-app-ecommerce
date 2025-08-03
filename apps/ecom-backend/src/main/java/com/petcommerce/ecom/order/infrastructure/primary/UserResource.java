package com.petcommerce.ecom.order.infrastructure.primary;

import com.petcommerce.ecom.order.application.UsersApplicationService;
import com.petcommerce.ecom.order.domain.user.aggregate.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserResource {

  private final UsersApplicationService usersApplicationService;


  public UserResource(UsersApplicationService usersApplicationService) {
    this.usersApplicationService = usersApplicationService;
  }

  @GetMapping("/authenticated")
  public ResponseEntity<RestUser> authenticated(@AuthenticationPrincipal Jwt jwtToken, @RequestParam boolean foreceResync) {
    User authenticatedUser =  usersApplicationService.getAuthenticatedUserWithSync(jwtToken, foreceResync);
    RestUser restUser = RestUser.from(authenticatedUser);
    return ResponseEntity.ok(restUser);
  }


}

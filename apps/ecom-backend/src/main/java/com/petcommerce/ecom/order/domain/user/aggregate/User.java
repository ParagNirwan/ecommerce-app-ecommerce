package com.petcommerce.ecom.order.domain.user.aggregate;

import com.petcommerce.ecom.order.domain.user.vo.*;
import com.petcommerce.ecom.shared.error.domain.Assert;
import org.jilt.Builder;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
public class User {
  private UserLastName lastName;
  private UserFirstName firstName;
  private UserEmail email;
  private UserPublicId userPublicId;
  private UserImageUrl imageUrl;
  private Instant lastModifiedDate;
  private Instant createdDate;
  private Set<Authority> authorities;
  private Long dbId;
  private UserAddress address;
  private Instant lastSeen;


  //Constructor


  public User(UserLastName lastName, UserFirstName firstName, UserEmail email, UserPublicId userPublicId, UserImageUrl imageUrl, Instant lastModifiedDate, Instant createdDate, Set<Authority> authorities, Long dbId, UserAddress address, Instant lastSeen) {
    this.lastName = lastName;
    this.firstName = firstName;
    this.email = email;
    this.userPublicId = userPublicId;
    this.imageUrl = imageUrl;
    this.lastModifiedDate = lastModifiedDate;
    this.createdDate = createdDate;
    this.authorities = authorities;
    this.dbId = dbId;
    this.address = address;
    this.lastSeen = lastSeen;
  }

  private void assertMandatoryField(){
    Assert.notNull("lastName",lastName);
    Assert.notNull("firstName",firstName);
    Assert.notNull("email",email);
    Assert.notNull("authorities",authorities);
  }

  public void updateFromUser(User user){
    this.email = user.email;
    this.imageUrl = user.imageUrl;
    this.firstName = user.firstName;
    this.lastName = user.lastName;
  }

  public void initFieldForSignup(){
    this.userPublicId = new UserPublicId(UUID.randomUUID());
  }

  public static User fromTokenAttributes(Map<String, Object> attributes, List<String> rolesFromAccessToken){

    UserBuilder userBuilder = UserBuilder.user();

    if(attributes.containsKey("preferred_email")){
      userBuilder.email(new UserEmail(attributes.get("preferred_email").toString()));
    }

    if(attributes.containsKey("last_name")){
      userBuilder.lastName(new UserLastName(attributes.get("last_name").toString()));
    }

    if(attributes.containsKey("first_name")){
      userBuilder.firstName(new UserFirstName(attributes.get("first_name").toString()));
    }
    if(attributes.containsKey("picture")){
      userBuilder.imageUrl(new UserImageUrl(attributes.get("picture").toString()));
    }

    if(attributes.containsKey("last_signed_in")){
      userBuilder.lastSeen(Instant.parse(attributes.get("last_signed_in").toString()));
    }

    Set<Authority> authorities = rolesFromAccessToken
      .stream()
      .map(authority -> AuthorityBuilder.authority().name(new AuthorityName(authority)).build())
      .collect(Collectors.toSet());

    userBuilder.authorities(authorities);

    return userBuilder.build();

  }

  public UserLastName getLastName() {
    return lastName;
  }

  public void setLastName(UserLastName lastName) {
    this.lastName = lastName;
  }

  public UserFirstName getFirstName() {
    return firstName;
  }

  public void setFirstName(UserFirstName firstName) {
    this.firstName = firstName;
  }

  public UserEmail getEmail() {
    return email;
  }

  public void setEmail(UserEmail email) {
    this.email = email;
  }

  public UserPublicId getUserPublicId() {
    return userPublicId;
  }

  public void setUserPublicId(UserPublicId userPublicId) {
    this.userPublicId = userPublicId;
  }

  public UserImageUrl getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(UserImageUrl imageUrl) {
    this.imageUrl = imageUrl;
  }

  public Instant getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(Instant lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  public Instant getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Instant createdDate) {
    this.createdDate = createdDate;
  }

  public Set<Authority> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(Set<Authority> authorities) {
    this.authorities = authorities;
  }

  public Long getDbId() {
    return dbId;
  }

  public void setDbId(Long dbId) {
    this.dbId = dbId;
  }

  public UserAddress getAddress() {
    return address;
  }

  public void setAddress(UserAddress address) {
    this.address = address;
  }

  public Instant getLastSeen() {
    return lastSeen;
  }

  public void setLastSeen(Instant lastSeen) {
    this.lastSeen = lastSeen;
  }
}

package com.petcommerce.ecom.order.infrastructure.secondary.entity;

import com.petcommerce.ecom.order.domain.user.aggregate.User;
import com.petcommerce.ecom.order.domain.user.aggregate.UserBuilder;
import com.petcommerce.ecom.order.domain.user.vo.*;
import com.petcommerce.ecom.shared.jpa.AbstractAuditingEntity;
import jakarta.persistence.*;
import org.jilt.Builder;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "ecommerce_user")
@Builder
public class UserEntity extends AbstractAuditingEntity<Long> {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequenceGenerator")
  @SequenceGenerator(name = "userSequenceGenerator", sequenceName = "user_sequence", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Column(name = "last_name")
  private String lastname;

  @Column(name = "first_name")
  private String firstname;

  @Column(name = "email")
  private String email;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "public_id")
  private UUID publicId;

  @Column(name = "address_street")
  private String addressStreet;

  @Column(name = "address_city")
  private String addressCity;

  @Column(name = "address_zip_code")
  private String addressZipCode;

  @Column(name = "address_country")
  private String addressCountry;

  @Column(name = "last_seen")
  private Instant lastSeen;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
    name = "user_authority",
    joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")}
  )
  private Set<AuthorityEntity> authorities = new HashSet<>();

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public UserEntity() {
  }

  public UserEntity(Long id, String lastname, String firstname, String email, String imageUrl, UUID publicId, String addressStreet, String addressCity, String addressZipCode, String addressCountry, Instant lastSeen, Set<AuthorityEntity> authorities) {
    this.id = id;
    this.lastname = lastname;
    this.firstname = firstname;
    this.email = email;
    this.imageUrl = imageUrl;
    this.publicId = publicId;
    this.addressStreet = addressStreet;
    this.addressCity = addressCity;
    this.addressZipCode = addressZipCode;
    this.addressCountry = addressCountry;
    this.lastSeen = lastSeen;
    this.authorities = authorities;
  }

  //Utility Methods
  public void updateFromUser(User user) {
    this.email = user.getEmail().value();
    this.lastname = user.getLastName().value();
    this.firstname = user.getFirstName().value();
    this.imageUrl = user.getImageUrl().value();
    this.lastSeen = user.getLastSeen();
  }

  public static UserEntity from(User user) {
    UserEntityBuilder userEntityBuilder = UserEntityBuilder.userEntity();
    if (user.getImageUrl() != null) {
      userEntityBuilder.imageUrl(user.getImageUrl().value());
    }

    if (user.getUserPublicId() != null) {
      userEntityBuilder.publicId(user.getUserPublicId().value());
    }

    if (user.getAddress() != null) {
      userEntityBuilder.addressCity(user.getAddress().city());
      userEntityBuilder.addressCountry(user.getAddress().country());
      userEntityBuilder.addressStreet(user.getAddress().street());
      userEntityBuilder.addressZipCode(user.getAddress().zipCode());
    }

    return userEntityBuilder
      .authorities(AuthorityEntity.from(user.getAuthorities()))
      .email(user.getEmail().value())
      .firstname(user.getFirstName().value())
      .lastname(user.getLastName().value())
      .lastSeen(user.getLastSeen())
      .id(user.getDbId())
      .build();
  }

  public static User toDomain(UserEntity userEntity) {

    UserBuilder ub = UserBuilder.user();

    if (userEntity.getImageUrl() != null) {
      ub.imageUrl(new UserImageUrl(userEntity.getImageUrl()));
    }

    if (userEntity.getAddressStreet() != null) {
      ub.address(
        UserAddressBuilder.userAddress()
          .city(userEntity.getAddressCity())
          .country(userEntity.getAddressCountry())
          .street(userEntity.getAddressStreet())
          .zipCode(userEntity.getAddressZipCode())
          .build()
      );
    }

    return ub
      .email(new UserEmail(userEntity.getEmail()))
      .lastName(new UserLastName(userEntity.getLastname()))
      .firstName(new UserFirstName(userEntity.getFirstname()))
      .authorities(AuthorityEntity.toDomain(userEntity.getAuthorities()))
      .userPublicId(new UserPublicId(userEntity.getPublicId()))
      .lastModifiedDate(userEntity.getLastModifiedDate())
      .createdDate(userEntity.getCreatedDate())
      .dbId(userEntity.getId())
      .build();

  }


  public static Set<UserEntity> from(List<User> users){
    return users.stream().map(UserEntity::from).collect(Collectors.toSet());
  }

  public static Set<User> toDomain(List<UserEntity> userEntities){
    return userEntities.stream().map(UserEntity::toDomain).collect(Collectors.toSet());
  }



  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public UUID getPublicId() {
    return publicId;
  }

  public void setPublicId(UUID publicId) {
    this.publicId = publicId;
  }

  public String getAddressStreet() {
    return addressStreet;
  }

  public void setAddressStreet(String addressStreet) {
    this.addressStreet = addressStreet;
  }

  public String getAddressCity() {
    return addressCity;
  }

  public void setAddressCity(String addressCity) {
    this.addressCity = addressCity;
  }

  public String getAddressZipCode() {
    return addressZipCode;
  }

  public void setAddressZipCode(String addressZipCode) {
    this.addressZipCode = addressZipCode;
  }

  public String getAddressCountry() {
    return addressCountry;
  }

  public void setAddressCountry(String addressCountry) {
    this.addressCountry = addressCountry;
  }

  public Instant getLastSeen() {
    return lastSeen;
  }

  public void setLastSeen(Instant lastSeen) {
    this.lastSeen = lastSeen;
  }

  public Set<AuthorityEntity> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(Set<AuthorityEntity> authorities) {
    this.authorities = authorities;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof UserEntity that)) return false;
    return Objects.equals(publicId, that.publicId);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(publicId);
  }
}

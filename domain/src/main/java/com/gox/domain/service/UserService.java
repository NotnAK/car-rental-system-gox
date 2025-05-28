    package com.gox.domain.service;

    import com.gox.domain.entity.user.LoyaltyLevel;
    import com.gox.domain.entity.user.User;
    import com.gox.domain.entity.wishlist.Wishlist;
    import com.gox.domain.exception.*;
    import com.gox.domain.repository.UserRepository;
    import com.gox.domain.validation.api.ValidationResult;
    import com.gox.domain.validation.api.ValidationRule;
    import com.gox.domain.validation.user.UserValidationContext;
    import com.gox.domain.validation.user.rules.*;

    import java.util.List;
    import java.util.stream.Collectors;

    public class UserService implements UserFacade{
        private final UserRepository userRepository;
        private final List<ValidationRule<UserValidationContext>> createRules;
        private final List<ValidationRule<UserValidationContext>> updateRules;
        private final List<ValidationRule<UserValidationContext>> adminUpdateRules;


        public UserService(UserRepository userRepository) {
            this.userRepository = userRepository;
            this.updateRules = List.of(
                    new UserNotNullRule(),
                    new UserNameNotEmptyRule(),
                    new UserNameFormatRule(),
                    new UserAddressNotEmptyRule(),
                    new UserPhoneNotEmptyRule(),
                    new UserPhonePatternRule()
            );
            this.createRules = List.of(
                    new UserNotNullRule(),
                    new UserEmailNotEmptyRule(),
                    new UserNameNotEmptyRule(),
                    new UserRoleNotNullRule()
            );
            this.adminUpdateRules = List.of(
                    new UserNotNullRule(),
                    new UserNameNotEmptyRule(),
                    new UserNameFormatRule(),
                    new UserAddressNotEmptyRule(),
                    new UserPhoneNotEmptyRule(),
                    new UserPhonePatternRule(),
                    new LoyaltyLevelFormatRule()
            );
        }

        @Override
        public User get(Long id) {
            if (id == null || id <= 0) {
                throw new CarValidationException("User ID must be positive");
            }
            User user = userRepository.read(id);
            if (user == null) {
                throw new CarNotFoundException("User with ID " + id + " not found");
            }
            return user;
        }

        @Override
        public User getByEmail(String email) {
            return userRepository.findByEmail(email);
        }

        @Override
        public List<User> getAllUsers() {
            return userRepository.findAll();
        }

        @Override
        public User create(User user) {
            var ctx = UserValidationContext.builder()
                    .user(user)
                    .build();
            var vr  = new ValidationResult();
            for (var r : createRules) {
                r.validate(ctx, vr);
            }
            if (vr.hasErrors()) {
                // если email уже занят, наш UserEmailUniqueRule бросит
                throw new UserValidationException(vr.getCombinedMessage());
            }
    /*        if(user.getRole() != UserRole.ADMIN){
                Wishlist wishlist = new Wishlist();
                user.setWishlist(wishlist);
            }*/
            if (userRepository.findByEmail(user.getEmail()) != null) {
                throw new UserAlreadyExistsException(user.getEmail());
            }
            Wishlist wishlist = new Wishlist();
            user.setWishlist(wishlist);
            User createdUser = userRepository.create(user);
            return createdUser;
        }
/*        private void validateUser(User user) {
            if (user == null) {
                throw new UserValidationException("User object must not be null.");
            }
            if (user.getEmail() == null || user.getEmail().isBlank()) {
                throw new UserValidationException("User email must not be empty.");
            }

            if (user.getName() == null || user.getName().isBlank()) {
                throw new UserValidationException("User name must not be empty.");
            }
            if (user.getRole() == null) {
                throw new UserValidationException("User role must be specified.");
            }

        }*/

        @Override
        public List<Long> getWishlistCarIds(Long userId) {
            User user = userRepository.read(userId);
            if (user == null) {
                throw new UserNotFoundException("User not found: " + userId);
            }
            return user.getWishlist()
                    .getCars()
                    .stream()
                    .map(car -> car.getId())
                    .collect(Collectors.toList());
        }

        @Override
        public User updateProfile(User user, String name, String phone, String address) {
            user.setName(name);
            user.setPhone(phone);
            user.setAddress(address);
            var ctx = UserValidationContext.builder().user(user).build();
            var vr  = new ValidationResult();
            for (var rule : updateRules) {
                rule.validate(ctx, vr);
            }
            if (vr.hasErrors()) {
                throw new UserValidationException(vr.getCombinedMessage());
            }
            return userRepository.update(user);
        }

        @Override
        public User updateByAdmin(Long userId,
                                  String name,
                                  String phone,
                                  String address,
                                  String loyaltyLevel) {
            User user = userRepository.read(userId);
            if (user == null) {
                throw new UserNotFoundException("User not found: " + userId);
            }
            user.setName(name);
            user.setPhone(phone);
            user.setAddress(address);
            UserValidationContext ctx = UserValidationContext.builder()
                    .user(user)
                    .loyaltyLevel(loyaltyLevel)
                    .build();
            ValidationResult vr = new ValidationResult();
            for (var rule : adminUpdateRules) {
                rule.validate(ctx, vr);
            }
            if (vr.hasErrors()) {
                throw new UserValidationException(vr.getCombinedMessage());
            }
            user.setLoyaltyLevel(LoyaltyLevel.valueOf(loyaltyLevel));
            return userRepository.update(user);
        }

        @Override
        public void delete(Long id) {
            userRepository.delete(id);
        }
    }

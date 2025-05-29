    package com.gox.domain.service;

    import com.gox.domain.entity.user.LoyaltyLevel;
    import com.gox.domain.entity.user.User;
    import com.gox.domain.entity.wishlist.Wishlist;
    import com.gox.domain.exception.*;
    import com.gox.domain.repository.BookingRepository;
    import com.gox.domain.repository.ReviewRepository;
    import com.gox.domain.repository.UserRepository;
    import com.gox.domain.validation.ValidationExecutor;
    import com.gox.domain.validation.api.ValidationRule;
    import com.gox.domain.validation.user.UserValidationContext;
    import com.gox.domain.validation.user.rules.*;

    import java.util.List;
    import java.util.stream.Collectors;

    public class UserService implements UserFacade{
        private final UserRepository userRepository;
        private final ReviewRepository reviewRepository;
        private final BookingRepository bookingRepository;
        private final List<ValidationRule<UserValidationContext>> createRules;
        private final List<ValidationRule<UserValidationContext>> updateRules;
        private final List<ValidationRule<UserValidationContext>> adminUpdateRules;


        public UserService(UserRepository userRepository, ReviewRepository reviewRepository,
                           BookingRepository bookingRepository) {
            this.userRepository = userRepository;
            this.reviewRepository = reviewRepository;
            this.bookingRepository = bookingRepository;
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
                throw new UserValidationException("User ID must be positive");
            }
            User user = userRepository.read(id);
            if (user == null) {
                throw new UserNotFoundException("User not found with id: " + id);
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
            ValidationExecutor.validateOrThrow(
                    ctx,
                    createRules,
                    UserValidationException::new
            );
            if (userRepository.findByEmail(user.getEmail()) != null) {
                throw new UserAlreadyExistsException(user.getEmail());
            }
            Wishlist wishlist = new Wishlist();
            user.setWishlist(wishlist);
            User createdUser = userRepository.create(user);
            return createdUser;
        }

        @Override
        public List<Long> getWishlistCarIds(Long userId) {
            User user = userRepository.read(userId);
            if (user == null) {
                throw new UserNotFoundException("User not found with id: " + userId);
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
            ValidationExecutor.validateOrThrow(
                    ctx,
                    updateRules,
                    UserValidationException::new
            );
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
                throw new UserNotFoundException("User not found with id: " + userId);
            }
            user.setName(name);
            user.setPhone(phone);
            user.setAddress(address);
            UserValidationContext ctx = UserValidationContext.builder()
                    .user(user)
                    .loyaltyLevel(loyaltyLevel)
                    .build();
            ValidationExecutor.validateOrThrow(
                    ctx,
                    adminUpdateRules,
                    UserValidationException::new
            );
            user.setLoyaltyLevel(LoyaltyLevel.valueOf(loyaltyLevel));
            return userRepository.update(user);
        }

        @Override
        public void delete(Long id) {
            reviewRepository.deleteByUserId(id);
            bookingRepository.deleteByUserId(id);
            userRepository.delete(id);
        }
    }

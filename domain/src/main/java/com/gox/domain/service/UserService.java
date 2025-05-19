    package com.gox.domain.service;

    import com.gox.domain.entity.user.User;
    import com.gox.domain.entity.wishlist.Wishlist;
    import com.gox.domain.exception.UserAlreadyExistsException;
    import com.gox.domain.exception.UserValidationException;
    import com.gox.domain.repository.UserRepository;
    import com.gox.domain.validation.api.ValidationResult;
    import com.gox.domain.validation.api.ValidationRule;
    import com.gox.domain.validation.user.UserValidationContext;
    import com.gox.domain.validation.user.rules.UserEmailNotEmptyRule;
    import com.gox.domain.validation.user.rules.UserNameNotEmptyRule;
    import com.gox.domain.validation.user.rules.UserNotNullRule;
    import com.gox.domain.validation.user.rules.UserRoleNotNullRule;

    import java.util.List;

    public class UserService implements UserFacade{
        private final UserRepository userRepository;
        private final List<ValidationRule<UserValidationContext>> rules;


        public UserService(UserRepository userRepository) {
            this.userRepository = userRepository;
            this.rules = List.of(
                    new UserNotNullRule(),
                    new UserEmailNotEmptyRule(),
                    new UserNameNotEmptyRule(),
                    new UserRoleNotNullRule()
            );
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
            for (var r : rules) {
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
    }

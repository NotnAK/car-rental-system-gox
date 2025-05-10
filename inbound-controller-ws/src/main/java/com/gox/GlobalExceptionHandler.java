    package com.gox;

    import com.gox.domain.exception.*;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.bind.annotation.RestControllerAdvice;

    @RestControllerAdvice
    public class GlobalExceptionHandler {

        // --- REVIEW ---
        @ExceptionHandler(ReviewNotFoundException.class)
        public ResponseEntity<String> handleReviewNotFound(ReviewNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

        @ExceptionHandler(ReviewValidationException.class)
        public ResponseEntity<String> handleReviewValidation(ReviewValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        // --- CAR ---
        @ExceptionHandler(CarNotFoundException.class)
        public ResponseEntity<String> handleCarNotFound(CarNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

        @ExceptionHandler(CarValidationException.class)
        public ResponseEntity<String> handleCarValidation(CarValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        // --- USER ---
        @ExceptionHandler(UserNotFoundException.class)
        public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

        @ExceptionHandler(UserValidationException.class)
        public ResponseEntity<String> handleUserValidation(UserValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        @ExceptionHandler(UserAlreadyExistsException.class)
        public ResponseEntity<String> handleUserAlreadyExists(UserAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }

        // --- WISHLIST ---
        @ExceptionHandler(WishlistNotFoundException.class)
        public ResponseEntity<String> handleWishlistNotFound(WishlistNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

        @ExceptionHandler(WishlistValidationException.class)
        public ResponseEntity<String> handleWishlistValidation(WishlistValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        // --- PHOTO ---
        @ExceptionHandler(PhotoNotFoundException.class)
        public ResponseEntity<String> handlePhotoNotFound(PhotoNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

        @ExceptionHandler(PhotoValidationException.class)
        public ResponseEntity<String> handlePhotoValidation(PhotoValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        // --- LOCATION ---
        @ExceptionHandler(LocationNotFoundException.class)
        public ResponseEntity<String> handleLocationNotFound(LocationNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

        @ExceptionHandler(LocationValidationException.class)
        public ResponseEntity<String> handleLocationValidation(LocationValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        // --- BOOKING ---
        @ExceptionHandler(BookingNotFoundException.class)
        public ResponseEntity<String> handleBookingNotFound(BookingNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

        @ExceptionHandler(BookingValidationException.class)
        public ResponseEntity<String> handleBookingValidation(BookingValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        // --- DEFAULT ---
        @ExceptionHandler(Exception.class)
        public ResponseEntity<String> handleAll(Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error: " + ex.getMessage());
        }
    }

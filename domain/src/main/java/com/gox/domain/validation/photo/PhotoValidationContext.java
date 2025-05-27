package com.gox.domain.validation.photo;

import com.gox.domain.entity.photo.Photo;

public class PhotoValidationContext {
    private final Long carId;
    private final Photo photo;

    private PhotoValidationContext(Builder b) {
        this.carId = b.carId;
        this.photo = b.photo;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getCarId() {
        return carId;
    }

    public Photo getPhoto() {
        return photo;
    }

    public static class Builder {
        private Long carId;
        private Photo photo;

        public Builder carId(Long carId) {
            this.carId = carId;
            return this;
        }

        public Builder photo(Photo p) {
            this.photo = p;
            return this;
        }

        public PhotoValidationContext build() {
            return new PhotoValidationContext(this);
        }
    }
}

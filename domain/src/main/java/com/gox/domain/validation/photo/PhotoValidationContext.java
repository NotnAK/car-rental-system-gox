package com.gox.domain.validation.photo;

public class PhotoValidationContext {
    private final Long carId;
    private final String name;
    private final Boolean isPreview;
    private final byte[] content;
    private PhotoValidationContext(Builder b) {
        this.carId = b.carId;
        this.name = b.name;
        this.isPreview = b.isPreview;
        this.content = b.content;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getCarId() {
        return carId;
    }

    public String getName() {
        return name;
    }

    public Boolean getPreview() {
        return isPreview;
    }

    public byte[] getContent() {
        return content;
    }

    public static class Builder {
        private Long carId;
        private String name;
        private Boolean isPreview;
        private byte[] content;

        public Builder carId(Long carId) {
            this.carId = carId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder isPreview(Boolean isPreview) {
            this.isPreview = isPreview;
            return this;
        }
        public Builder content(byte[] content){
            this.content = content;
            return this;
        }

        public PhotoValidationContext build() {
            return new PhotoValidationContext(this);
        }
    }
}

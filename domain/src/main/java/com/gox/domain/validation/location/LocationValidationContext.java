package com.gox.domain.validation.location;

import com.gox.domain.entity.location.Location;

public class LocationValidationContext {
    private final Location location;

    private LocationValidationContext(Builder builder) {
        this.location = builder.location;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Location getLocation() {
        return location;
    }

    public static class Builder {
        private Location location;

        public Builder location(Location location) {
            this.location = location;
            return this;
        }

        public LocationValidationContext build() {
            return new LocationValidationContext(this);
        }
    }
}

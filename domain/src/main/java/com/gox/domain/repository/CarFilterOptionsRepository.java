package com.gox.domain.repository;

import com.gox.domain.vo.CarFilterOptions;

public interface CarFilterOptionsRepository {
    /**
     * Returns all possible values for filtering machines:
     * brands, years, price range, enum field parameters, etc.
     */
    CarFilterOptions getFilterOptions();
}
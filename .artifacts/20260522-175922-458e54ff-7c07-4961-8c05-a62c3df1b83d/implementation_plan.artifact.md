# Implementation Plan - Refactoring Product Mapping

Refactor `ProductMapper` to inherit from `BaseMapper` and unify product mapping logic across different data sources (FoodDataCentral, OpenFoodFacts, and Edamam).

## Proposed Changes

### Data Repository Implementation

#### [ProductMapper.kt](file:///Users/work/StudioProjects/Meal Planner/data-repository-impl/src/commonMain/kotlin/dev/stukalo/mealplanner/data/repository/impl/mapper/ProductMapper.kt)

- Update to inherit from `BaseMapper`.
- Add mapping for Edamam `EdamamRecipeNetModel` to `ProductDomainModel`.
- Keep existing mappings for FDC and OFF but structured for clarity.

#### [RecipeMapper.kt](file:///Users/work/StudioProjects/Meal Planner/data-repository-impl/src/commonMain/kotlin/dev/stukalo/mealplanner/data/repository/impl/mapper/RecipeMapper.kt)

- Inject `ProductMapper` and use it to map the product part of the recipe instead of manual mapping.

#### [SearchRepositoryImpl.kt](file:///Users/work/StudioProjects/Meal Planner/data-repository-impl/src/commonMain/kotlin/dev/stukalo/mealplanner/data/repository/impl/SearchRepositoryImpl.kt)

- Ensure it uses the updated `ProductMapper` methods.

## Verification Plan

### Automated Tests
- Run existing builds to ensure no compilation errors.
- Since no unit tests were found for mappers, I will verify by code inspection and ensuring Koin module is correctly updated.

### Manual Verification
- Verify that `RecipeMapper` now uses `ProductMapper` for its `ProductDomainModel` creation.

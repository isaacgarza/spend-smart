package com.spendsmart.cache;

import com.spendsmart.dto.Category;
import com.spendsmart.dto.PersonSubcategory;
import com.spendsmart.dto.Subcategory;
import com.spendsmart.repository.CategoryRepository;
import com.spendsmart.repository.ExpenseRepeatScheduleRepository;
import com.spendsmart.repository.FundingScheduleRepository;
import com.spendsmart.repository.SubcategoryRepository;
import com.spendsmart.service.PersonSubcategoryService;
import com.spendsmart.service.ServiceException;
import com.spendsmart.util.CategoryEnum;
import com.spendsmart.util.ExceptionConstants;
import com.spendsmart.util.ExpenseRepeatScheduleEnum;
import com.spendsmart.util.FundingScheduleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class Cache {

    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final ExpenseRepeatScheduleRepository expenseRepeatScheduleRepository;
    private final FundingScheduleRepository fundingScheduleRepository;
    private final PersonSubcategoryService personSubcategoryService;

    private Map<UUID, Category> categoryMap = new HashMap<>();
    private Map<UUID, Subcategory> subcategoryMap = new HashMap<>();
    private Map<UUID, ExpenseRepeatScheduleEnum> expenseRepeatScheduleTypeMap = new HashMap<>();
    private Map<UUID, FundingScheduleEnum> fundingScheduleTypeMap = new HashMap<>();

    @Autowired
    public Cache(CategoryRepository categoryRepository,
                 SubcategoryRepository subcategoryRepository,
                 ExpenseRepeatScheduleRepository expenseRepeatScheduleRepository,
                 PersonSubcategoryService personSubcategoryService,
                 FundingScheduleRepository fundingScheduleRepository) {
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
        this.expenseRepeatScheduleRepository = expenseRepeatScheduleRepository;
        this.fundingScheduleRepository = fundingScheduleRepository;
        this.personSubcategoryService = personSubcategoryService;
    }

    public Set<Category> getCategories() {
        return new HashSet<>(categoryMap.values());
    }

    public Category getCategoryById(UUID categoryId) {
        return categoryMap.get(categoryId);
    }

    public UUID getCategoryIdByName(String categoryName) {
        Optional<Map.Entry<UUID, Category>> optionalCategoryEntry =
                categoryMap
                        .entrySet()
                        .stream()
                        .filter(categoryEntry ->
                                categoryEntry.getValue().getName().equals(categoryName))
                        .findFirst();
        if (optionalCategoryEntry.isPresent()) {
            return optionalCategoryEntry.get().getKey();
        } else {
            throw new CacheException(ExceptionConstants.CATEGORY_NOT_FOUND);
        }
    }

    public Set<Subcategory> getSubcategories() {
        return new HashSet<>(subcategoryMap.values());
    }

    public Set<Subcategory> getSubcategoriesByCategoryName(CategoryEnum category) {
        Set<Subcategory> subcategories = new HashSet<>();
        subcategoryMap.forEach((uuid, subcategory) -> {
            if (subcategory.getCategory().getName().equals(category.getName())) {
                subcategories.add(subcategory);
            }
        });
        return subcategories;
    }

    public Set<Subcategory> getSubcategoriesByCategoryId(UUID categoryId) {
        Set<Subcategory> subcategories = new HashSet<>();
        subcategoryMap.forEach((uuid, subcategory) -> {
            if (subcategory.getCategory().getId().equals(categoryId)) {
                subcategories.add(subcategory);
            }
        });
        return subcategories;
    }

    public Set<Subcategory> getSubcategoriesByPerson(UUID userId) {
        try {
            Set<Subcategory> subcategories = getSubcategories();
            Set<PersonSubcategory> personSubcategories = personSubcategoryService.getCustomSubcategories(userId);
            return combineSubcategories(subcategories, personSubcategories);
        } catch (Exception e) {
            throw new ServiceException("Exception occurred retrieving subcategories for person " + userId, e);
        }
    }

    public UUID getExpenseRepeatScheduleId(ExpenseRepeatScheduleEnum expenseRepeatScheduleEnum) {
        final UUID[] expenseRepeatScheduleId = new UUID[1];
        expenseRepeatScheduleTypeMap.entrySet()
                .stream()
                .filter(entry -> expenseRepeatScheduleEnum.equals(entry.getValue()))
                .findFirst()
                .ifPresent(expenseRepeatSchedule -> expenseRepeatScheduleId[0] = expenseRepeatSchedule.getKey());
        return expenseRepeatScheduleId[0];
    }

    public UUID getFundingScheduleId(FundingScheduleEnum fundingScheduleEnum) {
        final UUID[] fundingScheduleId = new UUID[1];
        fundingScheduleTypeMap.entrySet()
                .stream()
                .filter(entry -> fundingScheduleEnum.equals(entry.getValue()))
                .findFirst()
                .ifPresent(fundingSchedule -> fundingScheduleId[0] = fundingSchedule.getKey());
        return fundingScheduleId[0];
    }

    public ExpenseRepeatScheduleEnum getExpenseRepeatScheduleTypeEnum(UUID expenseRepeatScheduleId) {
        return expenseRepeatScheduleTypeMap.get(expenseRepeatScheduleId);
    }

    public FundingScheduleEnum getFundingScheduleTypeEnum(UUID fundingScheduleId) {
        return fundingScheduleTypeMap.get(fundingScheduleId);
    }

    @PostConstruct
    private void loadCache() {
        loadCategoryMap();
        loadSubcategoryMap();
        loadExpenseRepeatScheduleTypeMap();
        loadFundingScheduleTypeMap();
    }

    private void loadCategoryMap() {
        categoryRepository.findAll().forEach(categoryTable ->{
            Category category = Category.builder()
                    .id(categoryTable.getId())
                    .name(categoryTable.getName())
                    .build();
            categoryMap.put(categoryTable.getId(), category);
        });
    }

    private void loadSubcategoryMap() {
        subcategoryRepository.findAll().forEach(subcategoryTable -> {
            UUID categoryId = subcategoryTable.getCategoryId();
            Category category = Category.builder()
                    .id(categoryId)
                    .name(categoryMap.get(categoryId).getName())
                    .build();
            Subcategory subcategory = Subcategory.builder()
                    .id(subcategoryTable.getId())
                    .name(subcategoryTable.getName())
                    .category(category)
                    .build();
            subcategoryMap.put(subcategoryTable.getId(), subcategory);
        });
    }

    private void loadExpenseRepeatScheduleTypeMap() {
        expenseRepeatScheduleRepository.findAll().forEach(expenseRepeatScheduleTypeTable -> {
            ExpenseRepeatScheduleEnum expenseRepeatScheduleEnum =
                    ExpenseRepeatScheduleEnum.valueOf(expenseRepeatScheduleTypeTable.getType());
            expenseRepeatScheduleTypeMap.put(expenseRepeatScheduleTypeTable.getId(), expenseRepeatScheduleEnum);
        });
    }

    private void loadFundingScheduleTypeMap() {
        fundingScheduleRepository.findAll().forEach(fundingScheduleTypeTable -> {
            FundingScheduleEnum fundingScheduleEnum =
                    FundingScheduleEnum.valueOf(fundingScheduleTypeTable.getType());
            fundingScheduleTypeMap.put(fundingScheduleTypeTable.getId(), fundingScheduleEnum);
        });
    }

    private Set<Subcategory> combineSubcategories(Set<Subcategory> subcategories,
                                                  Set<PersonSubcategory> personSubcategories) {
        personSubcategories.forEach(personSubcategory -> {
            Subcategory subcategory = Subcategory.builder()
                    .id(personSubcategory.getId())
                    .category(personSubcategory.getCategory())
                    .name(personSubcategory.getName())
                    .build();
            subcategories.add(subcategory);
        });
        return subcategories;
    }
}

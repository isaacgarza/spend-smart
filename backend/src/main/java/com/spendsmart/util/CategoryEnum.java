package com.spendsmart.util;

public enum CategoryEnum {
    AUTO_AND_TRANSPORT("Auto & Transport"),
    BILLS_AND_UTILITIES("Bills & Utilities"),
    EDUCATION("Education"),
    ENTERTAINMENT("Entertainment"),
    FEES_AND_CHARGES("Fees & Charges"),
    FOOD_AND_DRINK("Food & Drink"),
    GIFTS_AND_DONATIONS("Gifts & Donations"),
    HEALTH_AND_FITNESS("Health & Fitness"),
    HOME("Home"),
    INCOME("Income"),
    INVESTMENTS_AND_SAVINGS("Investments/Savings"),
    KIDS("Kids"),
    MISC_EXPENSES("Misc Expenses"),
    PERSONAL_CARE("Personal Care"),
    PETS("Pets"),
    SHOPPING("Shopping"),
    TAXES("Taxes"),
    TRANSFER("Transfer"),
    TRAVEL("Travel"),
    UNCATEGORIZED("Uncategorized");

    private String name;

    CategoryEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

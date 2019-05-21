insert into funding_schedule_type(id, type)
values (uuid_to_bin('8bdd707e-6d63-11e9-8d1c-ca6496c4120e'), 'DAILY'),
       (uuid_to_bin('8bdd729a-6d63-11e9-8d1c-ca6496c4120e'), 'WEEKLY'),
       (uuid_to_bin('8bdd5922-6d63-11e9-8d1c-ca6496c4120e'), 'BI_WEEKLY'),
       (uuid_to_bin('8bdd71a0-6d63-11e9-8d1c-ca6496c4120e'), 'MONTHLY'),
       (uuid_to_bin('8bdd6ed0-6d63-11e9-8d1c-ca6496c4120e'), 'CUSTOM');

insert into expense_repeat_schedule_type(id, type)
values (bin_to_uuid('c199b42e-777d-11e9-8d1c-ca6496c4120e'), 'WEEKLY'),
       (bin_to_uuid('c199aec0-777d-11e9-8d1c-ca6496c4120e'), 'BI_WEEKLY'),
       (bin_to_uuid('c199b2f8-777d-11e9-8d1c-ca6496c4120e'), 'MONTHLY'),
       (bin_to_uuid('c199b730-777d-11e9-8d1c-ca6496c4120e'), 'BI_MONTHLY'),
       (bin_to_uuid('c199b514-777d-11e9-8d1c-ca6496c4120e'), 'THREE_MONTHS'),
       (bin_to_uuid('c19923ec-777d-11e9-8d1c-ca6496c4120e'), 'SIX_MONTHS'),
       (bin_to_uuid('c199b910-777d-11e9-8d1c-ca6496c4120e'), 'YEARLY');

insert into category(id, name)
values (uuid_to_bin('ec849198-6d6b-11e9-8d1c-ca6496c4120e'), 'Auto & Transport'),
       (uuid_to_bin('ec849616-6d6b-11e9-8d1c-ca6496c4120e'), 'Bills & Utilities'),
       (uuid_to_bin('ec84988c-6d6b-11e9-8d1c-ca6496c4120e'), 'Education'),
       (uuid_to_bin('ec8499e0-6d6b-11e9-8d1c-ca6496c4120e'), 'Entertainment'),
       (uuid_to_bin('ec849aee-6d6b-11e9-8d1c-ca6496c4120e'), 'Fees & Charges'),
       (uuid_to_bin('ec849cc4-6d6b-11e9-8d1c-ca6496c4120e'), 'Food & Drink'),
       (uuid_to_bin('ec849ddc-6d6b-11e9-8d1c-ca6496c4120e'), 'Gifts & Donations'),
       (uuid_to_bin('ec849ed6-6d6b-11e9-8d1c-ca6496c4120e'), 'Health & Fitness'),
       (uuid_to_bin('ec849fe4-6d6b-11e9-8d1c-ca6496c4120e'), 'Home'),
       (uuid_to_bin('ec84a0f2-6d6b-11e9-8d1c-ca6496c4120e'), 'Income'),
       (uuid_to_bin('ec84a1ec-6d6b-11e9-8d1c-ca6496c4120e'), 'Investments/Savings'),
       (uuid_to_bin('ec84a368-6d6b-11e9-8d1c-ca6496c4120e'), 'Kids'),
       (uuid_to_bin('ec84a462-6d6b-11e9-8d1c-ca6496c4120e'), 'Misc Expenses'),
       (uuid_to_bin('ec84a5ac-6d6b-11e9-8d1c-ca6496c4120e'), 'Personal Care'),
       (uuid_to_bin('ec84a746-6d6b-11e9-8d1c-ca6496c4120e'), 'Pets'),
       (uuid_to_bin('ec84a8ea-6d6b-11e9-8d1c-ca6496c4120e'), 'Shopping'),
       (uuid_to_bin('ec84aaa2-6d6b-11e9-8d1c-ca6496c4120e'), 'Taxes'),
       (uuid_to_bin('ec84ac3c-6d6b-11e9-8d1c-ca6496c4120e'), 'Transfer'),
       (uuid_to_bin('ec84ad9a-6d6b-11e9-8d1c-ca6496c4120e'), 'Travel'),
       (uuid_to_bin('ec84ae94-6d6b-11e9-8d1c-ca6496c4120e'), 'Uncategorized');


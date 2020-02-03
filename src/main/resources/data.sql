DELETE FROM user_accounts;
DELETE FROM transactions;
DELETE FROM accounts;

-- accounts sample data:
-- Same as in provided examples
INSERT INTO accounts(id, account_number, account_name, account_type, balance_date, currency, opening_balance)
VALUES(1, '585309209', 'SGSavings726', 'SAVINGS', PARSEDATETIME('2018-11-08', 'yyyy-MM-dd'), 'SGD', 8432751);

INSERT INTO accounts(id, account_number, account_name, account_type, balance_date, currency, opening_balance)
VALUES(2, '791066619', 'AUSavings933', 'SAVINGS', PARSEDATETIME('2018-11-08', 'yyyy-MM-dd'), 'AUD', 8800593);

INSERT INTO accounts(id, account_number, account_name, account_type, balance_date, currency, opening_balance)
VALUES(3, '321143048', 'AUCurrent433', 'CURRENT', PARSEDATETIME('2018-11-08', 'yyyy-MM-dd'), 'AUD', 3801062);

INSERT INTO accounts(id, account_number, account_name, account_type, balance_date, currency, opening_balance)
VALUES(4, '347786244', 'SGCurrent166', 'CURRENT', PARSEDATETIME('2018-11-08', 'yyyy-MM-dd'), 'SGD', 5066465);

INSERT INTO accounts(id, account_number, account_name, account_type, balance_date, currency, opening_balance)
VALUES (5, '680168913', 'AUCurrent374', 'CURRENT', PARSEDATETIME('2018-11-08', 'yyyy-MM-dd'), 'AUD', 4132728);

INSERT INTO accounts(id, account_number, account_name, account_type, balance_date, currency, opening_balance)
VALUES (6, '136056165', 'AUSavings938', 'SAVINGS', PARSEDATETIME('2018-11-08', 'yyyy-MM-dd'), 'AUD', 4892879);

INSERT INTO accounts(id, account_number, account_name, account_type, balance_date, currency, opening_balance)
VALUES (7, '453963528', 'SGSavings842', 'CURRENT', PARSEDATETIME('2018-11-08', 'yyyy-MM-dd'), 'SGD', 7211753);

INSERT INTO accounts(id, account_number, account_name, account_type, balance_date, currency, opening_balance)
VALUES (8, '334666982', 'AUSavings253', 'CURRENT', PARSEDATETIME('2018-11-08', 'yyyy-MM-dd'), 'AUD', 2058816);

INSERT INTO accounts(id, account_number, account_name, account_type, balance_date, currency, opening_balance)
VALUES (9, '793949180', 'AUCurrent754', 'CURRENT', PARSEDATETIME('2018-11-08', 'yyyy-MM-dd'), 'AUD', 8879448);

INSERT INTO accounts(id, account_number, account_name, account_type, balance_date, currency, opening_balance)
VALUES (10, '768759901', 'SGCurrent294', 'CURRENT', PARSEDATETIME('2018-11-08', 'yyyy-MM-dd'), 'SGD', 590655);

INSERT INTO accounts(id, account_number, account_name, account_type, balance_date, currency, opening_balance)
VALUES (11, '847257972', 'AUCurrent591', 'CURRENT', PARSEDATETIME('2018-11-08', 'yyyy-MM-dd'), 'AUD', 9256168);

-- user_accounts sample data
-- I assign all accounts from the provided example to user '101' and keep them al unlocked for that user
-- and I also assign the first four accounts from the provider example to user '102' but only the first
-- three of them are unlocked for this user

-- User id 101, all unlocked:
INSERT INTO user_accounts (id, user_id, account_id, locked)
VALUES (1, 101, 1, false);

INSERT INTO user_accounts (id, user_id, account_id, locked)
VALUES (2, 101, 2, false);

INSERT INTO user_accounts (id, user_id, account_id, locked)
VALUES (3, 101, 3, false);

INSERT INTO user_accounts (id, user_id, account_id, locked)
VALUES (4, 101, 4, false);

INSERT INTO user_accounts (id, user_id, account_id, locked)
VALUES (5, 101, 5, false);

INSERT INTO user_accounts (id, user_id, account_id, locked)
VALUES (6, 101, 6, false);

INSERT INTO user_accounts (id, user_id, account_id, locked)
VALUES (7, 101, 7, false);

INSERT INTO user_accounts (id, user_id, account_id, locked)
VALUES (8, 101, 8, false);

INSERT INTO user_accounts (id, user_id, account_id, locked)
VALUES (9, 101, 9, false);

INSERT INTO user_accounts (id, user_id, account_id, locked)
VALUES (10, 101, 10, false);

INSERT INTO user_accounts (id, user_id, account_id, locked)
VALUES (11, 101, 11, false);

-- User id 102, all but one unlocked:
INSERT INTO user_accounts (id, user_id, account_id, locked)
VALUES (12, 102, 1, false);

INSERT INTO user_accounts (id, user_id, account_id, locked)
VALUES (13, 102, 2, false);

INSERT INTO user_accounts (id, user_id, account_id, locked)
VALUES (14, 102, 3, false);

INSERT INTO user_accounts (id, user_id, account_id, locked)
VALUES (15, 102, 4, true);


-- transactions sample data:
-- For one existing account with recent transaction date times, both debit and credit amounts
INSERT INTO transactions(id, account_id, transaction_date_time, amount, narrative)
VALUES(1, 5, PARSEDATETIME('2018-11-08 09:15:27', 'yyyy-MM-dd HH:mm:ss'), 114632, 'Barry Plant rent');

INSERT INTO transactions(id, account_id, transaction_date_time, amount, narrative)
VALUES(2, 5, PARSEDATETIME('2018-11-09 11:20:45', 'yyyy-MM-dd HH:mm:ss'), -26911, 'WOOLWORTHS 3055CASH OUT $200.00');

INSERT INTO transactions(id, account_id, transaction_date_time, amount, narrative)
VALUES(3, 5, PARSEDATETIME('2018-11-10 16:20:20', 'yyyy-MM-dd HH:mm:ss'), 592000, 'John Doe Salary');

INSERT INTO transactions(id, account_id, transaction_date_time, amount, narrative)
VALUES(4, 5, PARSEDATETIME('2018-11-11 08:12:32', 'yyyy-MM-dd HH:mm:ss'), -3774, 'Miscellaneous debit');

INSERT INTO transactions(id, account_id, transaction_date_time, amount, narrative)
VALUES(5, 5, PARSEDATETIME('2018-11-11 10:23:44', 'yyyy-MM-dd HH:mm:ss'), -6984, 'Miscellaneous debit');

INSERT INTO transactions(id, account_id, transaction_date_time, amount, narrative)
VALUES(6, 5, PARSEDATETIME('2018-11-11 12:10:32', 'yyyy-MM-dd HH:mm:ss'), -7512, 'EG Diamond Greek Petrol');

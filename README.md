# address-book
#### Introduction
This is a spring boot application to facilitate fetching accounts summary and transaction history data for a given user
#### High level how it works
* I used a H2 database set to persist data on java.io.tmpdir location
* Data is persisted on application restart. Just note on Linux OS /tmp gets cleaned up on system restart
* Data gets persisted in three tables: user_accounts, accounts, transactions. The structure is minimal and not production ready
* While application is running you can go to http://localhost:8080/h2-console/ to add more test data if you want to
* There is a repository component where I implemented two methods: one to retrieve all accounts for a given user and one to retrieve just an account based on user id and account id
* I used JPA so just the first "all accounts" method in the repository would have been enough. However for performance reasons it makes more sense to retrieve just an account data rather than all even if lazy loaded
* Same account can be own by multiple users. An account can be locked for some user (e.g. because of stolen/lost card) and unlocked for others
* The other important components are the accounts controller, the accounts service and the controller advice for exception handling
* The data structure as it is stored in the database is different than the one shown to the user. The conversion is done at the accounts service level    
#### Build
Build with Java 8 and gradle. Gradle 5 or later is required. You can use your own or provided gradle wrapper
#### Testing
Use the browser or curl from command prompt or postman to access the following resources:
* GET http://localhost:8080/accounts?userId=101
 This will retrieve all accounts summary data for userId 101
* GET http://localhost:8080/accounts?userId=102&excludeLocked=true
This will retrieve all unlocked accounts for userId 101. excludeLocked flag is optional and by default is false 
* GET http://localhost:8080/accounts/5?userId=102
This will retrieve transaction history data for accountId 5 and userId 101 

#### Notes
Given the limited lime to implement this:
* I ignored any security concerns. Given the authentication was not a requirement I was considering including some basic authorization but I decided not to do it not only because it was taking longer to implement and test but also because because it adds to the complexity making it harder to understand and evaluate. 
* I did not included any API description such as swagger annotations again as it would affect the delivery time
* I did not configured any runtime environment such as docker
* I only included very basic validation such as NonEmpty, Digits, etc as provided by javax.validation.constraints.
  However given there were no POST/PUT requirements this should not matter that much
* Account locked functionality was not required. I added it myself to diversify the accounts service logic and support my unit/mock testing skills
* I tried to have as much coverage as possible either with unit tests or integration tests but I am not claim is a bug free application. So far I am not aware of any but not absolutely certain 

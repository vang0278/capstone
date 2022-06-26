Hotel Booking

USER STORIES
To use the system there must be users (user/customer, employee, admin).

To book a reservation there must be building and room.

To see what's available, there must be a search.

To help users/customers, there must be a lookup system.

A user/customer must be able to book, checkin, checkout, or cancel a reservation.


STRUCTURE OF CODE
Entities have a repository to interact with the database.
Service interfaces declare the methods available.
Service Impl implements the services.
Controllers provide end points, and use the services to process the request.
Thymeleaf is used to create dynamic web pages.
DTOs are used to maintain session-like features to the forms.

TESTING
Located in src/test

TECHNICAL CHALLENGES
More can always be added and changed.
Functionality is built first, then code refactored/reorganized.
Authorization can be tricky when implementing. Care must be taken when allowing users to modify the database.

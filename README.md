<img src="https://user-images.githubusercontent.com/77248387/211789662-15b18c26-10d3-4c4d-a30c-da3ffb9a22bd.jpg" alt="drawing" width="200"/>


# DoMore

DoMore is a volunteer management application that allows association managers to easily manage their volunteers and volunteer opportunities. The application has two types of users: volunteers and association managers.

## Features for Association Managers
- Register and log in
- Create, edit, and delete volunteer profiles
- Contact volunteers
- Manage volunteer opportunities

## Features for Volunteers
- Search for available volunteer opportunities by priority (category, date, etc.)
- Contact associations
- Register for volunteer opportunities
- Cancel registration for volunteer opportunities

## Technical Details
- Uses Firestore as a database
- Built using the 3-layer model (MVVM): 
    - [Activity layers](https://github.com/eitansh28/DoMore/tree/main/app/src/main/java/com/example/myapplication/activitiy)
    - [Logical model layers](https://github.com/eitansh28/DoMore/tree/main/app/src/main/java/com/example/myapplication/model)
    - [DB model layers](https://github.com/eitansh28/DoMore/tree/main/app/src/main/java/com/example/myapplication/db)
- Separate Node.js server that communicates with the application and Firestore, and also performs specific logic functions.

## Authors

1. Noamya Shani
2. Eitan Shenkolevski
3. Haim Goldfisher


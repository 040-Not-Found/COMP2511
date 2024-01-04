## Authentication

1. Why do we need authentication/a login for applications such as gmail/myunsw/facebook?

> To protect the user's imformation.

## SSO

1. What patterns can you see in the flow.

> State pattern, as there are login and not login out states.

## State Pattern

1. What could you represent as a state system in this example?

> There are two sub-states:
    - Hoogle(Provider): 
        - login
        - not login
    - Browser: 
        - not selected Provider
        - selected Provider (if provider is hoogle, get hoogle's state)
            - login
            - not login 

2. Fill in the following state table

> Note: the cleaner way to represent the below table is through the use of a 'sub-state' (to handle locking/invalid login) but that just makes the below table even longer and we don't really talk about sub-states in enough detail in this course.  Also we could make the table a lot more compact but for your first table separating it out like this will be easier to think about.

For details on how InstaHam works check out Task 3)'s description.

```
+----------------------+----------------------+---------------------+---------------------+---------------------+-----------------+
|       Action         |   Condition          |      Home Page      |   Provider Select   |   Provider Login    |      Locked     |
+----------------------+----------------------+---------------------+---------------------+---------------------+-----------------+
| Visits App           | Cache Token Valid    |  Home (no movement) |                     |                     |                 |
|                      | Cache Token Invalid  |       Login in      |                     |                     |                 |
|                      | Cache Token Locked   |    Go to Locked     |                     |                     |                 |
|----------------------|----------------------|---------------------|---------------------|---------------------|-----------------|
| 'Back Button' (null) |     Used Cache       |    No page (null)   |                     |                     |                 |
| 'Back Button' (null) |                      |                     |    No page (null)   |                     |                 |
| 'Back Button' (null) |                      |                     |                     |   Provider Select   |                 |
| 'Back Button' (null) |     Used Login       |    Provider Login   |                     |                     |                 |
| 'Back Button' (null) |                      |                     |                     |                     | Provider Select |
|----------------------|----------------------|---------------------|---------------------|---------------------|-----------------|
|    Select Provider   |                      |                     |   Provider Login    |                     |                 |
|----------------------|----------------------|---------------------|---------------------|---------------------|-----------------|
|Valid token for Hoogle|    Using Hoogle      |                     |                     |      Home Page      |                 |
|     Enter email      |   Using InstaHam     |                     |                     |    Provider Login   |                 |
|  Code from email     |    Reset password    |                     |                     |      Home Page      |                 |
|  Invalid login used  | Third time incorrect |                     |                     |       Locked        |                 |
|  Correct login used  | *but* user is locked |                     |                     |  Provider Selected  |                 |
|----------------------|----------------------|---------------------|---------------------|---------------------|-----------------|
```

## Strategy Pattern

1. How could you use the strategy pattern in this example?

> Provider's can have login interface.

2. Give an example of why that's useful here, and the benefits of the strategy pattern over simply using `if `statements.

> It can have better decoupling between the behaviour and context class, so that it will not need to call too many methods and 
> this will decrease the time complexity

## Task 1) Cache

1. What did you modify to implement this?

> 1. Added a list of visited pages in each app
> 2. Clear all the histories in the app when clearCache
> 3. Set the starting page to "Select a Provider"

## Task 2) Providers/Pages

1. What pattern/approach did you do to refactor `Hoogle` and providers in general.

> Again keep this short, and try to write it before you start coding.

2. What pattern/approach did you do to refactor how pages are managed / transitioned from/to.

> Again keep this short, and try to write it before you start coding.

## Task 3) Testing your design 1: InstaHam

1. What pattern/approach did you do to finish the implementation of `InstaHam` and link it up to Browser.

> Again keep this short, and try to write it before you start coding.

## Task 4) Testing your design 2: Locking

1. What approach did you take here to implement locking, did you modify an existing pattern, did you use a new one?

> Again keep this short, and try to write it before you start coding.  Don't overcomplicate this!!  If the pattern is significantly longer than the simpler version of the code it's probably not the right fit.

2. How did you change your code to handle the complex case.

> Again keep this short, and try to write it before you start coding.

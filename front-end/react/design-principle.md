# Project Structure

```
|_ src
  |_ api (api can be used same folder)
        |_ index.js
        |_ base.js
        |_ auth.js
  |_ components (store all common components)
  |_ config (store app configuration)
  |_ containers
      |_ Authentication
        |_ Login
           |_ index.js
           |_ style.js
           |_ reducer.js (for reducer, we will discuss more in another topic)
           |_ sagas.js
        |_ EnterActivationCode
           |_ index.js
           |_ style.js
           |_ reducer.js
           |_ sagas.js
      |_ AnotherWorkFlow
  |_ store
  |_ utils
  |_ style
    |_ main.css
    |_ colors.js
    |_ fonts.js
    |_ images.js
  |_ localization
    |_ strings.js
    |_ termsAndCondition.js
```

# Dependencies Version
- [package.json dependencies](https://docs.npmjs.com/files/package.json#dependencies)

# QA
- react env configuration

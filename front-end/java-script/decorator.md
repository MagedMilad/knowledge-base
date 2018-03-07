# What is a Decorator?
In its simplest form, a decorator is simply a way of wrapping one piece of code with another – literally 'decorating' it.

```javascript
function sayHello(name) {
  console.log('Hello, ' + name);
}

function log(wrapped) {
  return function() {
    console.log('Starting');
    const result = wrapped.apply(this, arguments);
    console.log('Finished');
    return result;
  }
}

const wrapped = log(sayHello);

sayHello('Maged');
// Hello, Maged
wrapped('Maged');
// Starting
// Hello, Maged
// Finished
```

# How to use JavaScript Decorators?

Decorators use a special syntax in JavaScript, whereby they are prefixed with an `@` symbol and placed immediately before the code being decorated.

At present, using decorators requires transpiler support, since no current browser or Node release has support for them yet. If you are using Babel, this is enabled simply by using the `transform-decorators-legacy` and 
`babel-plugin-syntax-decorators` plugin.

# Different Types of Decorator
At present, the only types of decorator that are supported are on classes and members of classes. This includes properties, methods, getters, and setters.

Decorators are actually nothing more than functions that return another function, and that are called with the appropriate details of the item being decorated. These decorator functions are evaluated once when the program first runs, and the decorated code is replaced with the return value.


# Class member decorators

- `target` – The class that the member is on.
- `name` – The name of the member in the class.
- `descriptor` – The member descriptor. This is essentially the object that would have been passed to `Object.defineProperty`.

The classic example used here is `@readonly`. This is implemented as simply as:

```javascript
function readonly(target, name, descriptor) {
  descriptor.writable = false;
  return descriptor;
}

class Example {
  a() {}
  @readonly
  b() {}
}

const e = new Example();
e.a = 1;
e.b = 2;
// TypeError: Cannot assign to read only property 'b' of object '#<Example>'
```

more complex
```javascript
function log(target, name, descriptor) {
  const original = descriptor.value;
  if (typeof original === 'function') {
    descriptor.value = function(...args) {
      console.log(`Arguments: ${args}`);
      try {
        const result = original.apply(this, args);
        console.log(`Result: ${result}`);
        return result;
      } catch (e) {
        console.log(`Error: ${e}`);
        throw e;
      }
    }
  }
  return descriptor;
}

class Example {
    @log
    sum(a, b) {
        return a + b;
    }
}

const e = new Example();
e.sum(1, 2);
// Arguments: 1,2
// Result: 3
```

more complex
```javascript
function log(name) {
  return function decorator(t, n, descriptor) {
    const original = descriptor.value;
    if (typeof original === 'function') {
      descriptor.value = function(...args) {
        console.log(`Arguments for ${name}: ${args}`);
        try {
          const result = original.apply(this, args);
          console.log(`Result from ${name}: ${result}`);
          return result;
        } catch (e) {
          console.log(`Error from ${name}: ${e}`);
          throw e;
        }
      }
    }
    return descriptor;
  };
}
class Example {
  @log('SUM_TAG')
  sum(a, b) {
    return a + b;
  }
}

const e = new Example();
e.sum(1, 2);
// Arguments for SUM_TAG: 1,2
// Result from SUM_TAG: 3
```

# Class decorators
Class decorators are applied to the entire class definition all in one go. The decorator function is called with a single parameter which is the constructor function being decorated. 

Note that this is applied to the constructor function and not to each instance of the class that is created. This means that if you want to manipulate the instances you need to do so yourself by returning a wrapped version of the constructor.

```javascript
function log(Class) {
  return (...args) => {
    console.log(args);
    return new Class(...args);
  };
}

@log
class Example {
  constructor(name, age) {
  }
}

const e = new Example('Maged', 5);
// [ 'Maged', 5 ]
console.log(e);
// Example {}
```

more complex
```javascript
function log(name) {
  return function decorator(Class) {
    return (...args) => {
      console.log(`Arguments for ${name}: args`);
      return new Class(...args);
    };
  }
}

@log('Demo')
class Example {
  constructor(name, age) {}
}

const e = new Example('Maged', 5);
// Arguments for Demo: args
console.log(e);
// Example {}
```

# JavaScript Function Decorator with React

```jsx
class MyReactComponent extends React.Component {}
export default connect(mapStateToProps, mapDispatchToProps)(MyReactComponent);

@connect(mapStateToProps, mapDispatchToProps)
export default class MyReactComponent extends React.Component {}
```

# Reference
https://www.sitepoint.com/javascript-decorators-what-they-are/
https://survivejs.com/react/appendices/understanding-decorators/


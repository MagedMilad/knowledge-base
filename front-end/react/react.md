# React

# Component
- `render()` - the one required function on every ReactComponent
- `props` - the “input parameters” to our components
- `context` - a “global variable” for our components
- `state` - a way to hold data that is local to a component (that affects rendering)
- `Stateless components` - a simplified way to write reusable components
- `children` - how to interact and manipulate child components
- `statics` - how to create “class methods” on our components

In React, if we want to easily access a DOM element in a component we can use `refs` (references). and we have the ability to access it with `this.refs`

when the `state` or `props` of a component update, the component will re-render itself.
Every React component is rendered as a function of its `this.props` and `this.state` . This
rendering is deterministic

## React component lifecycle
- componentWillMount
  - when
    - before initial render, both server and client
  - why
    - getup initial state
- componentDidMount
  - when
    - after render
  - why
    - Access DOM
    - integrate with frameworks
    - set timers
    - AJAX requests
- componentWillReceiceProps
  - when
    - when receiving new props, not called on inital render
  - why
    - set state before a render
- shouldComponentUpdate
  - when
    - before render when new props or state are being received, not called in initial state
  - why
    performance, return false to avoid unnecessary re-renders
- componentWillUpdate
  - when
    - before rending
  - why
    - prepare for update
- componentDidUpdate
  - when
    - after update & render
  - why
    - work with the DOM after update
- componentWillUnmount
  - when
    - before unmount from the DOM
  - why
    - cleanup

## Props
### `PropTypes`
- `PropTypes` are a way to validate the values that are passed in through our `props`
- it's only running in the development mode
### `defaultProps`

## State
- `getInitialState`
whenever a state update depends on the current state, it is preferable to pass a function to `setState()` because it's asynchronous.

```js
decrement = () => {
    this.setState(prevState => {
        return {
            value: prevState.value - 1,
        };
    });
}
```

## Statless Componenet
- stateless components do not have a `this` property to reference.
- there isn't `propTypes` and `defaultProps`
- there isn't any compoenet lifecycle methods
- there isn't refs
- Stateless components are a great way to create reusable components.

## Children
- we can asscess children components with `this.props.children`
- `this.props.children` can be an object or array
- `React.Children.toArray()` helper function to convert children to an array anyway `React.Children.toArray(this.props.children)`
- 


## Context
Sometimes we might find that we have a prop which we want to expose “globally”. In this case, we
might find it cumbersome to pass this particular prop down from the root, to every leaf, through
every intermediate component.

Instead, specifying context allows us to automatically pass down variables from component to
component, rather than needing to pass down our props at every level,

If you want your application to be stable, don’t use context. It is an experimental API and it is likely to break in future releases of React.

- Parent compoenent
    - `childContextTypes`
    - `getChildContext()`
- Chlid compoenent
    - `contextTypes`

- If `contextTypes` is defined on a component, then several of it’s lifecycle methods will get passed an
additional argument of `nextContext`
- In a functional stateless component, `context` will get passed as the second argument
---

# Router
- [documentation](https://reacttraining.com/react-router/web/guides/philosophy)
- modifying url
  - `Link`
  - `Redirect`
- determining what to render
  - `Route`
  - `Switch`

## `Route`
determines whether or not to render a specified
component based on the app’s location.We’ll need to supply Route with two arguments as `props` :

- The `path` to match against the location
- The `component` to render when the location matches path

## `Link`

need props:

- `to`

## `Router`
Our basic version of Router should do two things:
1. Supply its children with context for both location and history
2. Re-render the app whenever the history changes

## `Redirect`
need props:

- `to`

---

# `Redux`

- All of your application’s data is in a single data structure called the `state` which is held in the
`store`
- Your app reads the `state` from this `store`
- The `state` is never mutated directly outside the `store`
- The `views` emit `actions` that describe what happened
- A new `state` is created by combining the `old state` and the `action` by a function called the
`reducer`


- `Actions` in Redux are objects. Actions always have a `type` property.
- `reducers` must be pure functions
- `Reducers` should treat the `state` object as immutable.


# QA

## State criteria
1. Is it passed in from a parent via props? If so, it probably isn’t state.
2. Does it change over time? If not, it probably isn’t state.
3. Can you compute it based on any other state or props in your component? If so, it’s not state

## Determine in which component each piece of state should live
- Identify every component that renders something based on that state.
- Find a common owner component (a single component above all the components
that need the state in the hierarchy).
- Either the common owner or another component higher up in the hierarchy
should own the state.
- If you can’t find a component where it makes sense to own the state, create a new
component simply for holding the state and add it somewhere in the hierarchy
above the common owner component.

## **Presentational and container components**

In React, a **presentational component** is a component that just renders HTML. The component’s
only function is presentational markup. In a Redux-powered app, a presentational component does
not interact with the Redux store.

The **presentational component** accepts props from a **container component**.

The **container component** specifies the data a **presentational component** should render. The container component also
specifies behavior. If the presentational component has any interactivity — like a button — it calls
a prop-function given to it by the container component.

The **container component** is the one to
dispatch an action to the Redux store


import { createStore, applyMiddleware, compose } from 'redux';
import thunk from 'redux-thunk';
import logger from 'redux-logger';
import rootReducer from './handling/reducers/index';
//import { LOCATION_CHANGE } from 'react-router-redux';

//export const store = createStore(rootReducer, applyMiddleware(thunk, logger));

const initialState = {};
// const historySaver = store => next => action => {
//     if (action.type === LOCATION_CHANGE) {
//       // Do whatever you wish with action.payload
//       // Send it an HTTP request to the server, save it in a cookie, localStorage, etc.
//     }
//     return next(action)
//   }
  
const middleware = [thunk, logger];

const ReactReduxDevTools = window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__();
let store;



if(window.navigator.userAgent.includes("Chrome") && ReactReduxDevTools) {
    store = createStore(
        rootReducer,
        initialState,
        compose(
            applyMiddleware(...middleware),
            ReactReduxDevTools)
    )
} else {
    store = createStore(
        rootReducer,
        initialState,
        compose(
            applyMiddleware(...middleware))
    )
}

export default store;
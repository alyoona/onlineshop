import {createStore, applyMiddleware, compose} from 'redux';
import thunk from 'redux-thunk';
import logger from 'redux-logger';
import rootReducer from './handling/reducers/index';

const initialState = {};

const middleware = [thunk, logger];

const ReactReduxDevTools = window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__();
let store;

if (window.navigator.userAgent.includes("Chrome") && ReactReduxDevTools) {
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
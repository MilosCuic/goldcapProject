import {createStore, applyMiddleware, compose} from "redux";
import thunk from "redux-thunk";
import  rootReducer from "./reducers";

const initState = {};
const middleware = [thunk]

let store;

const reactAndReduxDevTools = window.__REDUX_DEVTOOLS_EXTENSION__ &&
    window.__REDUX_DEVTOOLS_EXTENSION__();

if (window.navigator.userAgent.includes("Chrome") && reactAndReduxDevTools) {
    store = createStore(
        rootReducer,
        initState,
        compose(applyMiddleware(...middleware) ,
            reactAndReduxDevTools
        )
    );
}else {
    store = createStore(
        rootReducer,
        initState,
        compose(applyMiddleware(...middleware))
    );
}

export default store;
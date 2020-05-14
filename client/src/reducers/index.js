import {combineReducers} from "redux";
import errorReducer from "./errorReducer";
import orderReducer from "./orderReducer";
import securityReducer from "./securityReducer";

export default combineReducers({
    errors: errorReducer,
    orders: orderReducer,
    security: securityReducer
});
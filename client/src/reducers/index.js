import {combineReducers} from "redux";
import errorReducer from "./errorReducer";
import orderReducer from "./orderReducer";

export default combineReducers({
    errors: errorReducer,
    orders: orderReducer
});
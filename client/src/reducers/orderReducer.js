import {
    GET_ERRORS,
    GET_ORDERS,
    CREATE_ORDER,
    DELETE_ORDER,
    GET_REALMS,
    GET_ORDER_BY_ID, UPDATE_ORDER
} from "../actions/types";

const initialState = {
    orders: [],
    order: {},
    realms: []
};

export default function (state=initialState , action) {
    console.log("ACTIONNNNNNNN")
    console.log(action)
    switch (action.type) {
        default:
            return state;
        case GET_ORDERS:
            return {
            ...state ,
            orders: action.payload
            }
        case CREATE_ORDER:
            return {
                ...state,
                orders: [...state.orders, action.payload]
            }
        case DELETE_ORDER:
            return {
                ...state,
                orders:  state.orders.filter(order => order.id !== action.payload)
            }
        case GET_REALMS:
            return {
                ...state,
                realms: action.payload
            }
        case GET_ORDER_BY_ID:
            return {
                ...state,
                order: action.payload
            }
        case UPDATE_ORDER:
            return {
                ...state,
                orders: setStateAfterUpdate(state.orders , action.payload)
            }
    }
}

 const setStateAfterUpdate = (initList , payload) => {
     return initList.map(item => {
         if (item.id === payload.id) {
             item = payload;
         }
         return item;
     })
}
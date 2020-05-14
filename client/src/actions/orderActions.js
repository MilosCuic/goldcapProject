import {CREATE_ORDER, GET_ERRORS, GET_ORDERS, DELETE_ORDER, GET_ORDER_BY_ID, UPDATE_ORDER} from "./types";
import axios from "axios";

//TODO move path to constants file
const GET_ORDERS_PATH = "http://localhost:8080/orders/0/10";
const SAVE_ORDER_PATH = "http://localhost:8080/orders";
const DELETE_ORDER_PATH = "http://localhost:8080/orders";
const ORDERS_PATH = "http://localhost:8080/orders"



export const getOrderById = (id , history) => {
    return (dispatch) => {
        axios.get(`${ORDERS_PATH}/${id}`).then(
            res => dispatch({
                type: GET_ORDER_BY_ID,
                payload: res.data
            }),
            err => dispatch({
                type: GET_ERRORS,
                payload : err.response.data
            })
        )
    }
}

export const getOrders = () => {
    return (dispatch) => {
        axios.get(GET_ORDERS_PATH).then(
            res => dispatch({
                type: GET_ORDERS,
                payload: res.data
            }),
            err => dispatch({
            type: GET_ERRORS,
            payload : err.response.data
        })
        )
    }
}

export const createOrder = (order) =>{
    return (dispatch) => {
        axios.post(SAVE_ORDER_PATH, order).then(
            res =>
                dispatch({
                    type: CREATE_ORDER,
                    payload : res.data
                }),
            err => dispatch({
                type: GET_ERRORS,
                payload : err.response.data
            })
        );
    }
}

export const updateOrder = (order , history ) =>{
    return (dispatch) => {
        history.push("/orders");
        axios.post(`${SAVE_ORDER_PATH}/${order.id}` , order).then(
            res =>
                dispatch({
                type: UPDATE_ORDER,
                payload : res.data
            }),
            err => dispatch({
                type: GET_ERRORS,
                payload : err.response.data
            })
        );
    }
}

export const deleteOrder = (id) =>{
    return (dispatch) => {
        axios.delete(  `${DELETE_ORDER_PATH}/${id}`).then(
            res =>
                dispatch({
                    type: DELETE_ORDER,
                    payload : id
                }),
            err => dispatch({
                type: GET_ERRORS,
                payload : err.response.data
            })
        );
    }
}
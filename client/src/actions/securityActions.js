import axios from 'axios';
import {GET_ERRORS, SET_CURRENT_USER , LOGOUT_USER} from "./types";
import {setJWTToken} from "../securityUtills/setJWTToken";
import jwt_decode from "jwt-decode";

//TODO
const REGISTER_USER_PATH = "http://localhost:8080/register";
const LOGIN_USER_PATH = "http://localhost:8080/login";


export const createNewUser = (newUser , history ) =>{
    return (dispatch) => {
        axios.post(REGISTER_USER_PATH , newUser).then(
            response => history.push("/login"),
            err => dispatch({
                type: GET_ERRORS,
                payload : err.response.data
            })
        );
    }
}

export const login = (loginRequest , history) => {
    return async (dispatch) => {
        try {
            // post login request
            const res = await axios.post(LOGIN_USER_PATH, loginRequest);

            //extract token from response
            const {token} = res.data;

            //set token in the local storage
            localStorage.setItem("jwtToken", token);

            //set our token in the header so we can use it on each request
            setJWTToken(token);
            //decode token on React //npm install jwt-decode
            const decoded = jwt_decode(token)
            //dispatch to our securityReducer
            dispatch({
                type: SET_CURRENT_USER,
                payload: decoded
            })
        }catch (err) {
            dispatch({
                type: GET_ERRORS,
                payload : err.response.data
            })
        }
    }
}

export const logout = () => {
    return async (dispatch) => {
        localStorage.removeItem("jwtToken");
        setJWTToken(false);
        dispatch({
            type: LOGOUT_USER,
            payload: {}
        })
    }
}
import axios from "axios";

export const setJWTToken = token => {
    if (token) {
        axios.defaults.headers.Authorization = token
    }else {
        delete axios.defaults.headers.Authorization;
    }
}
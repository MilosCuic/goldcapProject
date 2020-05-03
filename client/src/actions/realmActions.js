import axios from "axios";
import {GET_ERRORS, GET_REALMS} from "./types";

//TODO
//MOVE ALL PATH CONSTANTS TO ONE FILE

const REALMS_PATH = "http://localhost:8080/realms/all";

export const createRealm = (realm , history ) =>{
    return (dispatch) => {
        axios.post(REALMS_PATH , realm).then(
            response => history.push("/orders"),
            err => dispatch({
                type: GET_ERRORS,
                payload : err.response.data
            })
        );
    }
}

export const getRealms = () =>{
    return (dispatch) => {
        axios.get(REALMS_PATH).then(
            res => dispatch({
                type: GET_REALMS,
                payload: res.data
            })
        )
    }
}


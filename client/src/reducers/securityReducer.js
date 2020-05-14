import {LOGOUT_USER, SET_CURRENT_USER} from "../actions/types";

const initialState = {
    user: {},
    isValid: false,
    isVerified: false
};

export default function (state=initialState , action) {
    switch (action.type) {
        default:
            return state;
        case SET_CURRENT_USER:
            return {
                ...state,
                isValid: validateUser(action.payload),
                user: action.payload,
                isVerified: verifyUser(action.payload)
            }
        case LOGOUT_USER:
            return {
                ...state,
                isValid: false,
                isVerified: false,
                user: {}
            }

    }
}

const validateUser = (payload) => {
    if (payload){
        return true;
    }else {
        return false;
    }
}

const verifyUser = (payload) => {
    if (payload !== undefined && payload.isVerified){
        return payload.isVerified;
    }else {
        return false;
    }
}
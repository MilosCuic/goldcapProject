import {GET_ERRORS} from "../actions/types";

const initialState = {};

export default function (state=initialState , action) {
    switch (action.type) {
        default:
            return state;
        case GET_ERRORS:
            return action.payload;
    }
}
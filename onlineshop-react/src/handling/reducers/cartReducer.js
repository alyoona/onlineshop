import { OPEN_CART } from '../actions/types';

const initialState = [];

export default function(state = initialState, action) {
    switch(action.type) { 
        case OPEN_CART:
            return action.payload
        default:
            return state;
    }
}
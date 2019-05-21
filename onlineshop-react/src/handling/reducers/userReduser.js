import { AUTHENTICATED, LOGOUT } from '../actions/types';

const initialState = {
    username: "",
    role: "",
    loggedIn: false
};

export default function(state = initialState, action) {
    switch(action.type) { 
        case AUTHENTICATED:
            return action.payload
        case LOGOUT:
            return action.payload;    
        default:
            return state;
        
    }
}
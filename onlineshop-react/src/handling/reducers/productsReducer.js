import {GET_ALL_PRODUCTS, DELETE_PRODUCT, GET_PRODUCT_FOR_UPDATE, CLEAR_UPDATING_PRODUCT} from '../actions/types';

const initialState = {
    products: [],
    updatingProduct: {}
};

export default function (state = initialState, action) {
    switch (action.type) {

        case GET_ALL_PRODUCTS:
            return {
                ...state,
                products: action.payload,
            };
        case GET_PRODUCT_FOR_UPDATE:
            return {
                ...state,
                updatingProduct: action.payload,
            };
        case CLEAR_UPDATING_PRODUCT:
            return {
                ...state,
                updatingProduct: {},
            };
        case DELETE_PRODUCT:
            return {
                ...state,
                products: state.products.filter(
                    product => product.id !== action.payload
                )
            };
        default:
            return state;
    }
}

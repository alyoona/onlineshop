import axios from 'axios';
import { GET_ERRORS, CLEAR_ERRORS,
    GET_ALL_PRODUCTS, 
    DELETE_PRODUCT, 
    GET_PRODUCT_FOR_UPDATE,
    CLEAR_UPDATING_PRODUCT
 } from './types'
import { apiUrl } from '../../util/network';


export const addProduct = (product, history) => async dispatch => {

    try {
        await axios.post(apiUrl("/products/add"), product);
         history.push("/");
    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        });
    }    
}

export const updateProduct = (product, history) => async dispatch => {
    try {
        await axios.put(apiUrl("/products/update"), product);
         history.push("/");

    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        });
    }    
}

export const getAllProducts = () => async dispatch => {
    const response = await axios.get(apiUrl("/"));
    const products = response.data;
    dispatch({
        type: GET_ALL_PRODUCTS,
        payload: products,
    });
    
};

export const deleteProduct = productId => async dispatch => {
    if (
        window.confirm(`You are deleting product ${productId}, this action cannot be undone`)
    ) {
        await axios.delete(apiUrl(`/products/${productId}`));
        dispatch({
            type: DELETE_PRODUCT,
            payload: productId,
        })
    }
}

export const addToCart =  product => async () => {
    if (
        window.confirm(`Product, id: ${product.id}, will be added to cart`)
    ) {
        await axios.post(apiUrl(`/addToCart`), product, {withCredentials: true});
    }
}

export const getProductForUpdate = (productId, history) => async dispatch => {
    try {
        const response = await axios.get(apiUrl(`/products/${productId}`));
        dispatch({
            type: GET_PRODUCT_FOR_UPDATE,
            payload: response.data,
        })
    } catch (error) {
        history.push("/");
    }
}

export const clearUpdatingProduct = () => async dispatch => {
    dispatch({
        type: CLEAR_UPDATING_PRODUCT,
    })
}

export const clearErrors = () => async dispatch => {
    dispatch({
        type: CLEAR_ERRORS,
    })
}





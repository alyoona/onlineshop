import axios from 'axios';
import { GET_ERRORS, CLEAR_ERRORS,
    GET_ALL_PRODUCTS, 
    DELETE_PRODUCT, 
    GET_PRODUCT_FOR_UPDATE,
    CLEAR_UPDATING_PRODUCT, 
    OPEN_CART

 } from './types'
import { url } from '../../util/network';


export const addProduct = (product, history) => async dispatch => {

    try {
        await axios.post(url("/products/add"), product);
         history.push("/");
        //  dispatch({
        //     type: GET_ERRORS,
        //     payload: {}
        // });
    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        });
    }    
}

export const updateProduct = (product, history) => async dispatch => {
    try {
        await axios.put(url("/products/update"), product);
         history.push("/");

    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        });
    }    
}

export const getAllProducts = () => async dispatch => {
    const response = await axios.get(url("/"));
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
        await axios.delete(url(`/products/${productId}`));
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
        const response = await axios.post(url(`/addToCart`), product);
        console.log("response add to cart ---, ", response.data);
    }
}

export const openCart = () => async dispatch => {
    
    const response = await axios.get(`http://localhost:8080/cart`);
    const products = response.data;
    console.log("response open Cart ---, ", response.data);
    dispatch({
        type: OPEN_CART,
        payload: products,
    });
    
};

export const getProductForUpdate = (productId, history) => async dispatch => {
    try {
        const response = await axios.get(url(`/products/${productId}`));
        dispatch({
            type: GET_PRODUCT_FOR_UPDATE,
            payload: response.data,
        })
    } catch (error) {
        console.log("------error getProductForUpdate", error);
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





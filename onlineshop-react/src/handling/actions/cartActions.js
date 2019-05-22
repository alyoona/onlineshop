import axios from 'axios';
import {
    OPEN_CART
} from './types'
import {apiUrl} from '../../util/network';

export const openCart = () => async dispatch => {

    const response = await axios.get(apiUrl(`/cart`), {withCredentials: true});
    const products = response.data;
    dispatch({
        type: OPEN_CART,
        payload: products,
    });

};
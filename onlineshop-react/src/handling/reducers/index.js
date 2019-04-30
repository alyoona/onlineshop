import { combineReducers } from 'redux';
import errorsReducer from './errorsReducer';
import productsReducer from './productsReducer';
import cartReducer from './cartReducer';

export default combineReducers({
   errors: errorsReducer,
   products: productsReducer,
   cart: cartReducer,
}); 
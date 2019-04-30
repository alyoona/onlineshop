import React, {Component} from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Route} from "react-router-dom";
import {Provider} from "react-redux";
import store from './store';

import Products from "./components/Products";
import AddProduct from "./components/AddProduct";
import Header from "./components/header/Header";
import Cart from "./components/Cart";
import UpdateProduct from "./components/UpdateProduct";


class App extends Component {
    
    render() {
        return (
            <Provider store={store}>
                <Router >
                    <div>
                        <Header />
                        <Route exact path={"/"} component={Products}/>
                        <Route exact path="/products/add" component={AddProduct} />
                        <Route exact path="/cart" component={Cart} />
                        <Route exact path="/products/update/:productId" component = {UpdateProduct} />
                        
                    </div>
                </Router>
            </Provider>
        );
    }
}


export default (App);

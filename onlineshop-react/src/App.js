import React, {Component} from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import {Router, Route} from "react-router-dom";


import Products from "./components/Products";
import AddProduct from "./components/AddProduct";
import Header from "./components/header/Header";
import Cart from "./components/Cart";
import UpdateProduct from "./components/UpdateProduct";
import LoginForm from "./components/LoginForm";

import RegisterForm from "./components/RegisterForm";
import PrivateRoute from "./components/PrivateRoute";
import {connect} from 'react-redux';
import {Role} from "./util/roles";
import {history} from "./util/history";

class App extends Component {

    render() {


        return (

            <Router history={history}>
                <div>
                    <Header />

                    <PrivateRoute exact path="/" component={Products} currentUser={this.props.currentUser}/>
                    <PrivateRoute exact path="/products/add" component={AddProduct}
                                  currentUser={this.props.currentUser}
                                  role={Role.Admin}
                    />
                    <PrivateRoute exact path="/cart" component={Cart} currentUser={this.props.currentUser}/>
                    <PrivateRoute exact path="/products/update/:productId" component={UpdateProduct}
                                  currentUser={this.props.currentUser}
                                  role={Role.Admin}/>
                    <Route exact path="/login" component={LoginForm}/>
                    <Route exact path="/register" component={RegisterForm}/>


                </div>
            </Router>

        );
    }
}

const mapStateToProps = store => ({
    currentUser: store.userAuthenticated
});
export default connect(mapStateToProps)(App);


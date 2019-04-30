import React, { Component } from 'react'
import { openCart } from '../handling/actions/productActions';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import Product from './Product';
import axios from 'axios';

class Cart extends Component {

  state = {
    cart: []
  }

  componentDidMount() {
    //this.props.openCart();

    axios.get(`http://localhost:8080/cart`, {headers: {Authorization: `Bearer ${"cookie_value"}`}})
      .then(response => console.log("open cart result, ", response.data))
      .catch(error => console.log("open cart errors, ", error));

      // fetch(`http://localhost:8080/cart`, {
      //       method: 'GET',
      //       credentials: 'include',
      //     })
      //       .then(function(response){
      //           console.log(response); 
      //       });
}

// componentWillReceiveProps(nextProps) {
//     this.setState({
//       cart: nextProps.cart
//     })
// }

  render() {
    const { cart }  = this.state;
    console.log("render props", this.props);
    console.log("render state", this.state);

    const empty = <div className="alert alert-info text-center" role="alert">
                      Your Cart is empty.
                  </div>;
    return (
      cart ?  
            <section className="products-grid">
                        {
                            cart.map(product => 
                                <Product key={product.id + `f${(~~(Math.random()*1e8)).toString(16)}`} product={product} />                             
                            )
                        }
            </section>
            : empty
    )
  }
}

Cart.prototypes = {
  openCart: PropTypes.func.isRequired,
  cart: PropTypes.array.isRequired,
} 

const mapStateToProps = store => ({
  cart: store.cart,
})

export default connect(mapStateToProps, {openCart}) (Cart); 

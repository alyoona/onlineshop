import React, { Component } from 'react'
import { openCart } from '../handling/actions/cartActions';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import Product from './Product';

class Cart extends Component {

  componentDidMount() {
    this.props.openCart();
}

  render() {
    const { cart }  = this.props;
    const empty = <div className="alert alert-info text-center" role="alert">
                      Your Cart is empty.
                  </div>;
    return (
      cart.length !== 0 ?  
            <section className="products-grid">
                        {
                            cart.map(product => 
                                <Product key={product.id + `f${(~~(Math.random()*1e8)).toString(16)}`} 
                                product={product}
                                isCartProduct={true} />                             
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

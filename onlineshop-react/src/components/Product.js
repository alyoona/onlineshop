import React, { Component } from 'react'
import { deleteProduct, addToCart } from '../handling/actions/productActions';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';
import axios from 'axios';
class Product extends Component {

    onDeleteClick = (productId) => {
        this.props.deleteProduct(productId);
    }

    onAddToCartClick = (product) => {
        //--------1
        //this.props.addToCart(product);
        // -------2
        axios.post(`http://localhost:8080/addToCart`, product)
        .then(response => console.log("add to cart, response , ", response))
        .catch(error => console.log("add to cart, errors, ", error));
        ;
        // -------3
        // fetch(`http://localhost:8080/addToCart/${product.id}`, {
        //     method: 'POST',
        //     credentials: 'include',
        //   })
        //     .then(function(response){
        //         console.log(response); 
        //     });
    }

  render() {
      const { product } = this.props;
    return (
        <div key={ product.id } className="card products-grid__item">
            <img className="card-img-top zoom p-0" src={ product.picturePath } alt="Product"/>
            <div className="card-body">
                <div className="card-title m-0 p-0 d-flex justify-content-between">
                    <h6>{ product.name }</h6>
                    <p>{ product.price }</p>
                </div>
                
                <div className="card-text d-flex justify-content-between">
                    
                    <p> Description: { product.description }</p>
                </div>

                <div className="card-text d-flex justify-content-between">
                    <button className="btn btn-light bg-custom ml-4"
                            onClick={() => this.onDeleteClick(product.id)}>Delete</button>
                    
                    <Link to={`/products/update/${product.id}`} 
                            className="btn bg-custom ml-4">Update</Link>
                    
                    <button className="btn bg-custom  mb-0"
                            onClick={() => this.onAddToCartClick(product)}
                            >Add to cart
                            <i className="fas fa-cart-plus text-custom"></i>
                    </button>
                    
                    
                </div>

            
               
            </div>
        </div>
    )
  }
}

Product.prototypes = {
    deleteProduct: PropTypes.func.isRequired,
    addToCart: PropTypes.func.isRequired
}

export default connect(null, { deleteProduct, addToCart })(Product);

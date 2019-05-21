import React, { Component } from 'react'
import { deleteProduct, addToCart } from '../handling/actions/productActions';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';
import { Role } from '../util/roles';

class Product extends Component {

    onDeleteClick = (productId) => {
        this.props.deleteProduct(productId);
    }

    onAddToCartClick = (product) => {
        
        this.props.addToCart(product);
        
    }

  render() {
      const { isCartProduct, product } = this.props;
      const isAdmin = this.props.currentUser.role === Role.Admin;

      const deleteButton = 
                <button className="btn btn-light bg-custom ml-4"
                    onClick={() => this.onDeleteClick(product.id)}>Delete</button>;
      const updateButton =
                    <Link to={`/products/update/${product.id}`} 
                className="btn bg-custom ml-4">Update</Link>;
            

      const manageButtons =                 
                <div className="card-text d-flex justify-content-between">
                    {isAdmin && deleteButton}
                    {isAdmin && updateButton}
                    <button className="btn bg-custom  mb-0"
                            onClick={() => this.onAddToCartClick(product)}
                            >Add to cart
                            <i className="fas fa-cart-plus text-custom"></i>
                    </button>  
                </div>;

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
            
                { !isCartProduct && manageButtons }
               
            </div>
        </div>
    )
  }
}

Product.prototypes = {
    deleteProduct: PropTypes.func.isRequired,
    addToCart: PropTypes.func.isRequired,
}

const mapStateToProps = store => ({
    currentUser: store.userAuthenticated,
})

export default connect(mapStateToProps, { deleteProduct, addToCart })(Product);

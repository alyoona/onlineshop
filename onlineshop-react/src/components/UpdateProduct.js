import React, { Component } from 'react';
import classnames from 'classnames';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import { getProductForUpdate, updateProduct, clearUpdatingProduct, clearErrors} from '../handling/actions/productActions';

class UpdateProduct extends Component {

state = {
    id: "",
    name: "",
    price: 0,
    picturePath: "",
    description: "",
    errors: {},
}

componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
        this.setState({
            errors: nextProps.errors
        });
    } 
        const { id, name, price, picturePath, description } = nextProps.updatingProduct;
        
        this.setState({ id, name, price, picturePath, description });
   
}

componentDidMount() {
    const { productId } = this.props.match.params;
    this.props.getProductForUpdate(productId);
}


onChange = (e) => {
   this.setState({ [e.target.name]:e.target.value }) 
}

onSubmit = (e) => {
    e.preventDefault();
    const updatedProduct = {
        id: this.state.id,
        name: this.state.name,
        price: this.state.price,
        picturePath: this.state.picturePath,
        description: this.state.description,
    }

    this.props.updateProduct(updatedProduct, this.props.history);

}

onClickCancel = (e) => {
    e.preventDefault();
    this.props.history.push("/");
}

componentWillUnmount() {
    this.props.clearUpdatingProduct();
    this.props.clearErrors();
}

  render() {

    const { errors } = this.state;

    return (
    <div className="container my-5">

        <form onSubmit={ this.onSubmit }>
        
            <div className="form-group ">
                <label htmlFor="name" className="font-weight-light">Product name:</label>
                <input type="text" name="name" 
                className={classnames("form-control", { "is-invalid": errors.name })} 
                id="name" 
                placeholder="Enter product name"
                value={this.state.name}
                onChange={this.onChange}
                />
                {
                errors.name && (
                <div className="invalid-feedback">{errors.name}</div>
                )} 
            </div>

            <div className="form-group ">
                <label htmlFor="price" className="font-weight-light">Product price:</label>
                <div className="input-group">
                    <input type="number" step="0.01" name="price" id="price" 
                        className={classnames("form-control", { "is-invalid": errors.price })} 
                        aria-label="Dollar amount (with dot and two decimal places)"
                        placeholder="Enter product price" 
                        value={this.state.price}
                        onChange={this.onChange}/>
                    <div className="input-group-append">
                        <span className="input-group-text bg-custom">$</span>
                        <span className="input-group-text bg-custom">0.00</span>
                    </div>
                    {
                errors.price && (
                <div className="invalid-feedback">{errors.price}</div>
                )} 
                </div>
                

            </div>

            <div className="form-group ">
                <label htmlFor="picturePath" className="font-weight-light">Product image:</label>
                <div className="input-group mb-3 bg-custom">
                    <div className="input-group-prepend">
                        <span className="input-group-text bg-custom" id="productPicturePath">url</span>
                    </div>
                    <input type="text" name="picturePath" id="picturePath" className="form-control"
                        placeholder="Enter product picture path"
                        aria-label="product picture path" aria-describedby="productPicturePath"                      
                        value={this.state.picturePath}
                        onChange={this.onChange} />
                </div>
            </div>

            <div className="form-group ">
                <label htmlFor="productDescription" className="font-weight-light">Product description:</label>
                <textarea className="form-control" name="description" id="productDescription" rows="2"
                    value={this.state.description}
                    onChange={this.onChange}></textarea>
            </div>
            
            <div className="d-flex justify-content-between">
            <button type="submit" className="btn bg-custom">Submit</button>
            <button className="btn bg-custom"
                     onClick={this.onClickCancel}>Cancel</button>
            </div>
            
          
        </form>
    </div>
    );
  }
}

UpdateProduct.propTypes = {
    updatingProduct: PropTypes.object.isRequired,
    errors: PropTypes.object.isRequired,
    getProductForUpdate: PropTypes.func.isRequired,
    updateProduct: PropTypes.func.isRequired,
    clearUpdatingProduct: PropTypes.func.isRequired,
    clearErrors: PropTypes.func.isRequired
};

const mapStateToProps = store => ({
    updatingProduct: store.products.updatingProduct,
    errors: store.errors,
});

export default connect(mapStateToProps, { getProductForUpdate, updateProduct, clearUpdatingProduct, clearErrors }) (UpdateProduct);
import React, {Component} from 'react';
import '../styles/custom.css';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { addProduct, clearErrors } from '../handling/actions/productActions';
import classnames from 'classnames';

class AddProduct extends Component {

    state = {
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
    }

    componentWillUnmount() {
        this.props.clearErrors();
    }
    
    onChange = (e) => {
        this.setState({ [e.target.name]:e.target.value })
    }

    onSubmit = (e) => {
        e.preventDefault();
        const newProduct = {
            name: this.state.name,
            price: this.state.price,
            picturePath: this.state.picturePath,
            description: this.state.description,
        }
        
        this.props.addProduct(newProduct, this.props.history);
       
    }

    render() {
        const { errors, name, price, picturePath, description } = this.state;
        
        return (
            <div className="container my-5">
                <form action="/products/add" method="POST" onSubmit={this.onSubmit}>
                    

                        <div className="form-group">
                            <label htmlFor="name" className="font-weight-light">Product name:</label>
                            <input type="text" name="name" 
                                    className={classnames("form-control", { "is-invalid": errors.name })}
                                    id="name"
                                    placeholder="Enter product name"
                                    value={name}
                                    onChange={this.onChange}/>
                            {
                            errors.name && (<div className="invalid-feedback">{errors.name}</div>)
                            }        
                        </div>

                        <div className="form-group">
                            <label htmlFor="price" className="font-weight-light">Product price:</label>
                            <div className="input-group">
                                <input type="number" step="0.01" name="price" id="price" 
                                        className={classnames("form-control", {
                                            "is-invalid": errors.price
                                        })}
                                        aria-label="Dollar amount (with dot and two decimal places)"
                                        placeholder="Enter product price"
                                        value={price}
                                        onChange={this.onChange}/>

                                <div className="input-group-append">
                                    <span className="input-group-text bg-custom">$</span>
                                    {/* <span className="input-group-text bg-custom">0.00</span> */}
                                </div>
                                {
                                errors.price && (
                                <div className="invalid-feedback">{errors.price}</div>
                                )}                            
                            </div>  
                        </div>

                    <div className="form-group">
                        <label htmlFor="picturePath" className="font-weight-light">Product image:</label>
                        <div className="input-group mb-3 bg-custom">
                            <div className="input-group-prepend">
                                <span className="input-group-text bg-custom" id="productPicturePath">url</span>
                            </div>
                            <input type="text" name="picturePath" id="picturePath" className="form-control"
                                placeholder="Enter product picture path"
                                aria-label="product picture path" aria-describedby="productPicturePath"
                                value={picturePath}
                                onChange={this.onChange}/>
                        </div>
                    </div>

                    <div className="form-group">
                        <label htmlFor="productDescription" className="font-weight-light">Product description:</label>
                        <textarea className="form-control" name="description" id="productDescription"
                                rows="2"
                                value={description}
                                onChange={this.onChange}>{}</textarea>
                    </div>

                    <button type="submit" className="btn bg-custom">Submit</button>
                </form>
            </div>
        )
    }
}

AddProduct.prototypes = {
    addProduct: PropTypes.func.isRequired,
    clearErrors: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired
}

const mapStateToProps = store => ({
    errors: store.errors
})

export default connect(mapStateToProps, {addProduct, clearErrors}) (AddProduct);

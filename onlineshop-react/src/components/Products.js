import React, {Component} from 'react'
import '../styles/custom.css';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import { getAllProducts } from '../handling/actions/productActions';
import Product from './Product';


class Products extends Component {

    componentDidMount() {
        this.props.getAllProducts();
    }

    render() {
        const { products } = this.props.products;
        return (
            <section className="products-grid">
                        {
                            products.map(product => 
                                <Product key={product.id} product={product} />                             
                            )
                        }
            </section>
        )
    }
}

Products.prototypes = {
    getAllProducts: PropTypes.func.isRequired,
    products: PropTypes.array.isRequired,
} 

const mapStateToProps = store => ({
    products: store.products,
})

export default connect(mapStateToProps, {getAllProducts}) (Products); 

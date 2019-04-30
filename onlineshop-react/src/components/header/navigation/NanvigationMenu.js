import React, { Component } from 'react'
import AllProductsNavMenuItem from './navitems/AllProductsNavMenuItem';
import AddProductNavMenuItem from './navitems/AddProductNavMenuItem';
import CartNavMenuItem from './navitems/CartNavMenuItem';
import LogoutNavMenuItem from './navitems/LogoutNavMenuItem';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import UpdateProductNavMenuItem from './navitems/UpdateProductNavItem';


class NanvigationMenu extends Component {

    render() {

        let isUpdating = false;
        if (JSON.stringify(this.props.updatingProduct) !== "{}") {
            isUpdating = true;
        }

        console.log("isUpdating: ", isUpdating);

        return (
            <nav>
                <ul className="nav nav-tabs nav-justified">
                    <AllProductsNavMenuItem/>
                    
                    {       
                        !isUpdating && <AddProductNavMenuItem />
                    }

                    {
                        isUpdating && <UpdateProductNavMenuItem/>
                    }

                    <CartNavMenuItem />
                    <ul className="nav nav-tabs nav-justified">
                        <LogoutNavMenuItem />
                    </ul>                
                </ul>
            </nav>
        )
    }
    
}

NanvigationMenu.propTypes = {
    updatingProduct: PropTypes.object.isRequired,
};

const mapStateToProps = store => ({
    updatingProduct: store.products.updatingProduct,
})

export default connect(mapStateToProps, null, null, { pure: false }) (NanvigationMenu);




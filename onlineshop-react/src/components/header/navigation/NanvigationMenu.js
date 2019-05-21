import React, { Component } from 'react'
import AllProductsNavMenuItem from './navitems/AllProductsNavMenuItem';
import AddProductNavMenuItem from './navitems/AddProductNavMenuItem';
import CartNavMenuItem from './navitems/CartNavMenuItem';
import LogoutNavMenuItem from './navitems/LogoutNavMenuItem';
import LoginNavMenuItem from './navitems/LoginNavMenuItem';
import RegisterNavMenuItem from './navitems/RegisterNavMenuItem';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import UpdateProductNavMenuItem from './navitems/UpdateProductNavItem';



class NanvigationMenu extends Component {

     render() {
        const isAuth = this.props.currentUser.loggedIn;
        
        let isUpdating = false;
        if (JSON.stringify(this.props.updatingProduct) !== "{}") {
            isUpdating = true;
        }                 

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

                    { 
                        !isAuth && <LoginNavMenuItem />                                 
                    }

                    { 
                        !isAuth && <RegisterNavMenuItem />                                 
                    }

                    { 
                        isAuth && <CartNavMenuItem />                                 
                    }


                    {
                        isAuth && <ul className="nav nav-tabs nav-justified">
                                    <LogoutNavMenuItem hisory={this.props.hisory}/>
                                    </ul>                                 
                    }               
                                                         
                </ul>
            </nav>
        )
    }
    
}

NanvigationMenu.propTypes = {
    updatingProduct: PropTypes.object.isRequired,
    currentUser: PropTypes.object.isRequired,
};

const mapStateToProps = store => ({
    updatingProduct: store.products.updatingProduct,
    currentUser: store.userAuthenticated,
})

export default connect(mapStateToProps, null, null, { pure: false }) (NanvigationMenu);




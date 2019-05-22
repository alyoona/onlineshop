import React from 'react';
import {Route, Redirect} from 'react-router-dom';

const PrivateRoute = ({component: Component, currentUser, role, ...rest}) => (

    <Route
        {...rest}
        render={ props => {

            if (!currentUser.loggedIn) {
                return <Redirect to={{pathname: "/login", state: {from: props.location}}}/>
            }

            if (role && role.indexOf(currentUser.role) === -1) {
                props.history.goBack();
            }
            return <Component {...props} />
        }
        }
    />
);

export default PrivateRoute;
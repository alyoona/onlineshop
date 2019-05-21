import React, { Component } from 'react'


import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { register } from '../handling/actions/userActions';

class RegisterForm extends Component {

state = {
    username: "",
    password: "",
}

onChange = (e) => {
    this.setState({ [e.target.name]: e.target.value })
}

onSubmit = (e) => {
    e.preventDefault();

    this.props.register(this.state, this.props.history);
}

render() {

    return(
        <div className="container my-5">

    
        <form onSubmit={this.onSubmit} className="mt-3">
    
            <div className="form-group">
                <label htmlFor="username" className="control-label font-weight-light">Username:</label>
                <input type="text" 
                        name="username" 
                        className="form-control" 
                        id="username" 
                        placeholder="Enter username"
                       required
                       value={this.state.login}
                       onChange={this.onChange}/>
                
            </div>
    
            <div className="form-group">
                <label htmlFor="password" className="font-weight-light">Password:</label>
                <input type="password" className="form-control" name="password" id="password"
                       placeholder="Enter user password"
                       required
                       value={this.state.password}
                       onChange={this.onChange}/>
            </div>
    
            <button type="submit" className="btn bg-custom">Register</button>
    
        </form>
    </div>
    )
}

}

RegisterForm.prototypes = {
    register: PropTypes.func.isRequired,
};

export default connect(null, { register }) (RegisterForm);



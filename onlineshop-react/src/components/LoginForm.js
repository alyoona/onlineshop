import React, {Component} from 'react'
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import {login} from '../handling/actions/userActions';

class LoginForm extends Component {

    state = {
        username: "",
        password: "",
    };

    onChange = (e) => {
        this.setState({[e.target.name]: e.target.value})
    };

    onSubmit = (e) => {
        e.preventDefault();

        this.props.login(this.state, this.props.history);
    };

    render() {

        return (
            <div className="container my-5">
                <form onSubmit={this.onSubmit} className="mt-3">
                    <div className="form-group">
                        <label htmlFor="username" className="control-label font-weight-light">Username:</label>
                        <input type="text" name="username" className="form-control" id="login"
                               placeholder="Enter username"
                               required
                               value={this.state.username}
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
                    <div className="form-row">
                        <div className="col-2">
                            <button type="submit" className="btn bg-custom">Login</button>
                        </div>
                    </div>

                </form>
            </div>
        )
    }

}

LoginForm.prototypes = {
    login: PropTypes.func.isRequired,
};

export default connect(null, {login})(LoginForm);


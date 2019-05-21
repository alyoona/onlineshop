import React, { Component } from 'react'
import '../../../../styles/custom.css';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import { logout } from '../../../../handling/actions/userActions';

class LogoutNavMenuItem extends Component  {

  onClickLogout = () => {
    
    this.props.logout();
  
  }

  render() {

  
  return (
    <li className="nav-item">
        <div className="nav-link">
                <button className="btn btn-sm bg-transparent text-custom mb-0" title="Logout" data-toggle="tooltip"
                        onClick={this.onClickLogout}>
                    <i className="fas fa-sign-out-alt text-custom "></i>
                </button>
        </div>
    </li>
  )
  }
}
LogoutNavMenuItem.propTypes = {
  logout: PropTypes.func.isRequired,
  
};

export default connect(null, { logout }) (LogoutNavMenuItem);


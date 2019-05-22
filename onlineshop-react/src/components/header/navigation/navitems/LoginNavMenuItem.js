import React from 'react'
import {NavLink} from 'react-router-dom'


export default function LoginNavMenuItem() {

    return (
        <li className="nav-item">
            <NavLink to="/login"
                     className="nav-link"
                     activeClassName="active"
            >
                <i className="fas fa-sign-in-alt text-custom my-auto"/>
                Login</NavLink>
        </li>
    )
}